package gerenteSom;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gerenciador de áudio baseado em {@link Clip} (javax.sound.sampled).
 * Aceita WAV no classpath. Efeitos curtos são clonados para sobreposição.
 */
public class GerenteSom {

    private static final Logger LOGGER = Logger.getLogger(GerenteSom.class.getName());
    private static GerenteSom instance;

    private final Map<String, byte[]> pcmCache = new HashMap<>();
    private final Map<String, AudioFormat> formatCache = new HashMap<>();
    private Clip musicaAtual;
    private String musicaAtualNome;
    private boolean mudo;
    private float volumeEfeitos = 0.85f;
    private float volumeMusica = 0.55f;

    private GerenteSom() {
    }

    public static synchronized GerenteSom getInstance() {
        if (instance == null) {
            instance = new GerenteSom();
        }
        return instance;
    }

    public void setMudo(boolean mudo) {
        this.mudo = mudo;
        if (mudo) {
            pararMusica();
        }
    }

    public boolean isMudo() {
        return mudo;
    }

    /** Toca efeito curto (laser, explosão, clique). */
    public void play(String resourcePath) {
        if (mudo) {
            return;
        }
        try {
            Clip clip = criarClip(resourcePath);
            if (clip == null) {
                return;
            }
            aplicarVolume(clip, volumeEfeitos);
            clip.addLineListener(event -> {
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.start();
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Falha ao tocar " + resourcePath, e);
        }
    }

    /** Loop de música de fundo. */
    public void playMusica(String resourcePath) {
        if (mudo) {
            return;
        }
        if (resourcePath.equals(musicaAtualNome) && musicaAtual != null && musicaAtual.isRunning()) {
            return;
        }
        pararMusica();
        try {
            musicaAtual = criarClip(resourcePath);
            if (musicaAtual == null) {
                return;
            }
            aplicarVolume(musicaAtual, volumeMusica);
            musicaAtual.loop(Clip.LOOP_CONTINUOUSLY);
            musicaAtualNome = resourcePath;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Falha ao tocar música " + resourcePath, e);
        }
    }

    public void pararMusica() {
        if (musicaAtual != null) {
            try {
                musicaAtual.stop();
                musicaAtual.close();
            } catch (Exception ignored) {
            }
            musicaAtual = null;
            musicaAtualNome = null;
        }
    }

    private Clip criarClip(String resourcePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        garantirCache(resourcePath);
        byte[] data = pcmCache.get(resourcePath);
        AudioFormat format = formatCache.get(resourcePath);
        if (data == null || format == null) {
            return null;
        }
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(format, data, 0, data.length);
        return clip;
    }

    private void garantirCache(String resourcePath) throws UnsupportedAudioFileException, IOException {
        if (pcmCache.containsKey(resourcePath)) {
            return;
        }
        URL url = getClass().getResource("/" + resourcePath);
        if (url == null) {
            LOGGER.warning("Áudio não encontrado: /" + resourcePath);
            return;
        }
        try (InputStream raw = new BufferedInputStream(url.openStream());
             AudioInputStream in = AudioSystem.getAudioInputStream(raw)) {
            AudioFormat base = in.getFormat();
            AudioFormat decoded = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    base.getSampleRate(),
                    16,
                    base.getChannels(),
                    base.getChannels() * 2,
                    base.getSampleRate(),
                    false);
            try (AudioInputStream pcm = AudioSystem.getAudioInputStream(decoded, in)) {
                byte[] bytes = pcm.readAllBytes();
                pcmCache.put(resourcePath, bytes);
                formatCache.put(resourcePath, decoded);
            }
        }
    }

    private void aplicarVolume(Clip clip, float volumeLinear) {
        if (!clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            return;
        }
        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float vol = Math.max(0.0001f, Math.min(1f, volumeLinear));
        float db = (float) (20.0 * Math.log10(vol));
        db = Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), db));
        gain.setValue(db);
    }
}
