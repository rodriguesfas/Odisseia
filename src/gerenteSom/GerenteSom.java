package gerenteSom;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class GerenteSom {

	static private GerenteSom instance;
	private HashMap<String, AudioClip> clips;

	private GerenteSom() {
		clips = new HashMap<String, AudioClip>();
	}

	static public GerenteSom getInstance() {
		if (instance == null) {
			instance = new GerenteSom();
		}
		return instance;
	}

	public AudioClip loadAudio(String fileName) throws IOException {
		URL url = getClass().getResource("/" + fileName);
		if (url == null) {
			throw new RuntimeException("O áudio /" + fileName
					+ " não foi encontrado.");
		} else {
			if (clips.containsKey(fileName)) {
				return clips.get(fileName);
			} else {
				AudioClip clip = Applet.newAudioClip(getClass().getResource(
						"/" + fileName));
				clips.put(fileName, clip);
				return clip;
			}
		}
	}

}