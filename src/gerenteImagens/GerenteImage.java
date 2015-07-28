package gerenteImagens;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * @author Francisco Assis Souza Rodrigues
 */

public class GerenteImage {

	static private GerenteImage instance;
	private HashMap<String, BufferedImage> imagens;

	/**
	 * 
	 */
	private GerenteImage() {
		imagens = new HashMap<String, BufferedImage>();
	}

	/**
	 * 
	 * @return
	 */
	static public GerenteImage getInstance() {
		if (instance == null) {
			instance = new GerenteImage();
		}
		return instance;
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public BufferedImage loadImage(String fileName) throws IOException {
		URL url = getClass().getResource("/" + fileName);
		if (url == null) {
			throw new RuntimeException("A imagem /" + fileName
					+ " não foi encontrada.");
		} else {
			String path = url.getPath();
			if (imagens.containsKey(path)) {
				return imagens.get(path);
			} else {
				BufferedImage img = ImageIO.read(url);
				imagens.put(path, img);
				return img;
			}
		}
	}

	/**
	 * 
	 * @param fileName
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 * @throws IOException
	 */
	public BufferedImage loadImage(String fileName, int x, int y, int w, int h)
			throws IOException {
		BufferedImage sheet = loadImage(fileName);
		BufferedImage img = sheet.getSubimage(x, y, w, h);
		return img;
	}
}
