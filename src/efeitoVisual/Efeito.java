package efeitoVisual;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *@author Francisco Assis Souza Rodrigues 
 */

/**
 * Classe Explosão, cria a animação. 
 */
public class Efeito {

	// Imagem
	private BufferedImage animImage;

	// Largura da Janela
	private int frameLargura;

	// Altura da Janela
	private int frameAltura;

	// Numero de quadros da imagem
	private int numberOfFrames;

	// Quantidade de tempo entre os quadros em milissegundos. (Quantos tempo em
	// milissegundos será mostrado um quadro antes de mostrar o próximo quadro?)
	private long frameTime;

	// Time when the frame started showing. (We use this to calculate the time
	// for the next frame.)
	private long startingFrameTime;

	// Time when we show next frame. (When current time is equal or greater then
	// time in "timeForNextFrame", it's time to move to the next frame of the
	// animation.)
	private long timeForNextFrame;

	// Current frame number.
	private int currentFrameNumber;

	// Should animation repeat in loop?
	private boolean loop;

	/** x and y coordinates. Where to draw the animation on the screen? */
	public int x;
	public int y;

	// Starting x coordinate of the current frame in the animation image.
	private int startingXOfFrameInImage;

	// Ending x coordinate of the current frame in the animation image.
	private int endingXOfFrameInImage;

	/**
	 * State of animation. Is it still active or is it finished? We need this so
	 * that we can check and delete animation when is it finished.
	 */
	public boolean active;

	// In milliseconds. How long to wait before starting the animation and
	// displaying it?
	private long showDelay;

	// At what time was animation created.
	private long timeOfAnimationCration;

	/**
	 * Creates animation.
	 * 
	 * @param animImage
	 *            Image of animation.
	 * @param frameWidth
	 *            Width of the frame in animation image "animImage".
	 * @param frameHeight
	 *            Height of the frame in animation image "animImage" - height of
	 *            the animation image "animImage".
	 * @param numberOfFrames
	 *            Number of frames in the animation image.
	 * @param frameTime
	 *            Amount of time that each frame will be shown before moving to
	 *            the next one in milliseconds.
	 * @param loop
	 *            Should animation repeat in loop?
	 * @param x
	 *            x coordinate. Where to draw the animation on the screen?
	 * @param y
	 *            y coordinate. Where to draw the animation on the screen?
	 * @param showDelay
	 *            In milliseconds. How long to wait before starting the
	 *            animation and displaying it?
	 */
	public Efeito(BufferedImage animImage, int frameWidth, int frameHeight,
			int numberOfFrames, long frameTime, boolean loop, int x, int y,
			long showDelay) {

		this.animImage = animImage;
		this.frameLargura = frameWidth;
		this.frameAltura = frameHeight;
		this.numberOfFrames = numberOfFrames;
		this.frameTime = frameTime;
		this.loop = loop;

		this.x = x;
		this.y = y;

		this.showDelay = showDelay;

		timeOfAnimationCration = System.currentTimeMillis();

		startingXOfFrameInImage = 0;
		endingXOfFrameInImage = frameWidth;

		startingFrameTime = System.currentTimeMillis() + showDelay;
		timeForNextFrame = startingFrameTime + this.frameTime;
		currentFrameNumber = 0;
		active = true;
	}

	/**
	 * Changes the coordinates of the animation.
	 * 
	 * @param x
	 *            x coordinate. Where to draw the animation on the screen?
	 * @param y
	 *            y coordinate. Where to draw the animation on the screen?
	 */
	public void changeCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Verifica se é hora de mostrar a próxima frame da animação. Verifica se a
	 * animação for concluída.
	 */
	private void Update() {
		if (timeForNextFrame <= System.currentTimeMillis()) {
			// proximo frame.
			currentFrameNumber++;

			// If the animation is reached the end, we restart it by seting
			// current frame to zero. If the animation isn't loop then we set
			// that animation isn't active.
			if (currentFrameNumber >= numberOfFrames) {
				currentFrameNumber = 0;

				// Se a animação não esta em loop, em seguida, definir
				// que a animação não está ativa.
				if (!loop)
					active = false;
			}

			// Começando e terminando coordenadas para corta a imagem quadro
			// atual da imagem de animação.
			startingXOfFrameInImage = currentFrameNumber * frameLargura;
			endingXOfFrameInImage = startingXOfFrameInImage + frameLargura;

			// Define o tempo para o priximo quadro
			startingFrameTime = System.currentTimeMillis();
			timeForNextFrame = startingFrameTime + frameTime;
		}
	}

	/**
	 * Desenha quadro atual da animação.
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	public void Draw(Graphics2D g2d) {
		this.Update();
		// Verifica atraso.
		if (this.timeOfAnimationCration + this.showDelay <= System
				.currentTimeMillis())
			g2d.drawImage(animImage, x, y, x + frameLargura, y + frameAltura,
					startingXOfFrameInImage, 0, endingXOfFrameInImage,
					frameAltura, null);
	}
}