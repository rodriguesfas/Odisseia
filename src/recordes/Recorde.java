package recordes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Francisco de Assis de Souza Rodrigues. 27/02/2014
 */
public class Recorde {

    private static final Logger LOGGER = Logger.getLogger(Recorde.class.getName());
    private static final String DEFAULT_SCORE = "0";

    private String primeiroRecorde = DEFAULT_SCORE;
    private String segundoRecorde = DEFAULT_SCORE;
    private String terceiroRecorde = DEFAULT_SCORE;
    private String naoClassificou = DEFAULT_SCORE;

    public Recorde() {
    }

    /**
     * Arquivo de recordes em {@code ~/.odisseia/arquivoRecorde.txt},
     * independente do diretório de trabalho.
     */
    public static Path caminhoArquivo() {
        Path dir = Path.of(System.getProperty("user.home"), ".odisseia");
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Não foi possível criar o diretório de recordes.", e);
        }
        return dir.resolve("arquivoRecorde.txt");
    }

    public void gravarRecorde() {
        Path arquivo = caminhoArquivo();
        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(arquivo, StandardCharsets.UTF_8))) {
            out.println(safe(getPrimeiroRecorde()));
            out.println(safe(getSegundoRecorde()));
            out.println(safe(getTerceiroRecorde()));
            out.println(safe(getNaoClassificou()));
        } catch (IOException erro) {
            LOGGER.log(Level.WARNING, "Não foi possível salvar o recorde em " + arquivo, erro);
        }
    }

    public void exibirRecordes() {
        Path arquivo = caminhoArquivo();
        if (!Files.isRegularFile(arquivo)) {
            migrarArquivoLegadoSeExistir(arquivo);
        }

        if (!Files.isRegularFile(arquivo)) {
            setPrimeiroRecorde(DEFAULT_SCORE);
            setSegundoRecorde(DEFAULT_SCORE);
            setTerceiroRecorde(DEFAULT_SCORE);
            setNaoClassificou(DEFAULT_SCORE);
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(arquivo, StandardCharsets.UTF_8)) {
            setPrimeiroRecorde(lerLinhaOuPadrao(br));
            setSegundoRecorde(lerLinhaOuPadrao(br));
            setTerceiroRecorde(lerLinhaOuPadrao(br));
            setNaoClassificou(lerLinhaOuPadrao(br));
        } catch (IOException erro) {
            LOGGER.log(Level.WARNING, "Erro ao ler recordes em " + arquivo, erro);
            setPrimeiroRecorde(DEFAULT_SCORE);
            setSegundoRecorde(DEFAULT_SCORE);
            setTerceiroRecorde(DEFAULT_SCORE);
            setNaoClassificou(DEFAULT_SCORE);
        }
    }

    /** Converte texto do arquivo em inteiro; valores inválidos viram 0. */
    public static int parsePontuacao(String valor) {
        if (valor == null || valor.isBlank()) {
            return 0;
        }
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static String lerLinhaOuPadrao(BufferedReader br) throws IOException {
        String linha = br.readLine();
        return (linha == null || linha.isBlank()) ? DEFAULT_SCORE : linha.trim();
    }

    private static String safe(String valor) {
        return (valor == null || valor.isBlank()) ? DEFAULT_SCORE : valor;
    }

    /** Migra {@code arquivoRecorde.txt} do CWD antigo, se existir. */
    private static void migrarArquivoLegadoSeExistir(Path destino) {
        Path legado = Path.of("arquivoRecorde.txt");
        if (Files.isRegularFile(legado)) {
            try {
                Files.copy(legado, destino);
            } catch (IOException e) {
                LOGGER.log(Level.FINE, "Falha ao migrar arquivo de recordes legado.", e);
            }
        }
    }

    public String getPrimeiroRecorde() {
        return primeiroRecorde;
    }

    public void setPrimeiroRecorde(String primeiroRecorde) {
        this.primeiroRecorde = primeiroRecorde;
    }

    public String getSegundoRecorde() {
        return segundoRecorde;
    }

    public void setSegundoRecorde(String segundoRecorde) {
        this.segundoRecorde = segundoRecorde;
    }

    public String getTerceiroRecorde() {
        return terceiroRecorde;
    }

    public void setTerceiroRecorde(String terceiroRecorde) {
        this.terceiroRecorde = terceiroRecorde;
    }

    public String getNaoClassificou() {
        return naoClassificou;
    }

    public void setNaoClassificou(String naoClassificou) {
        this.naoClassificou = naoClassificou;
    }
}
