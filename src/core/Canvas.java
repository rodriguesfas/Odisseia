package core;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 *@author Francisco Assis Souza Rodrigues 
 */

/**
 * Criar um JPanel em que desenhar e ouvir eventos de teclado e mouse.
 * 
 */

public abstract class Canvas extends JPanel implements KeyListener,
		MouseListener {

	private static final long serialVersionUID = 1L;

	// Estados Teclado
	// Aqui estão os estados armazenados por teclas do teclado é precionada ou
	// não.
	private static boolean[] keyboardState = new boolean[525];

	// Mouse
	// Esta os estados de teclas do mouse armazenados - é precionado ou não.
	private static boolean[] mouseState = new boolean[3];

	public Canvas() {
		// Usamos buffer duplo para desenhar na tela.
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setBackground(Color.black);

		// Se você vai desenhar o seu próprio cursor do mouse ou se você só quer
		// que o cursor do mouse disapear, insira "verdadeiro" em se cursor do
		// mouse condição e serão removidos.
		if (false) {
			BufferedImage blankCursorImg = new BufferedImage(16, 16,
					BufferedImage.TYPE_INT_ARGB);

			Cursor blankCursor = Toolkit.getDefaultToolkit()
					.createCustomCursor(blankCursorImg, new Point(0, 0), null);

			this.setCursor(blankCursor);
		}

		// Adiciona o ouvinte teclado para JPanel para receber os principais
		// eventos deste componente.
		this.addKeyListener(this);

		// Adiciona o ouvinte mouse para JPanel para receber eventos de mouse a
		// partir deste componente.
		this.addMouseListener(this);
	}

	// Este método é substituído em Framework.java e é usado para desenhar na
	// tela.
	public abstract void Draw(Graphics2D g2d);

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		Draw(g2d);
	}

	// Teclado
	/**
	 * @param key
	 *            Número de chave para o qual você deseja verificar o estado.
	 * @return true se a tecla está em baixo, falso se a chave não está em
	 *         baixo.
	 */
	public static boolean keyboardKeyState(int key) {
		return keyboardState[key];
	}

	// Métodos do ouvinte teclado.
	@Override
	public void keyPressed(KeyEvent e) {
		keyboardState[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyboardState[e.getKeyCode()] = false;
		keyReleasedFramework(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public abstract void keyReleasedFramework(KeyEvent e);

	/**
	 * Botão do mouse "botão" é preciondao? O parâmetro "botão" pode ser
	 * "MouseEvent.BUTTON1" - Indica botão do mouse # 1 ou "MouseEvent.BUTTON2"
	 * - Indica botão do mouse # 2 ...
	 * 
	 * @param button
	 *            Número de botão do mouse para o qual você deseja verificar o
	 *            estado.
	 * @return true se o botão está em baixo, falso se o botão não precionadao.
	 */
	public static boolean mouseButtonState(int button) {
		return mouseState[button - 1];
	}

	// Define status da tecla mouse.
	private void mouseKeyStatus(MouseEvent e, boolean status) {
		if (e.getButton() == MouseEvent.BUTTON1)
			mouseState[0] = status;

		else if (e.getButton() == MouseEvent.BUTTON2)
			mouseState[1] = status;

		else if (e.getButton() == MouseEvent.BUTTON3)
			mouseState[2] = status;
	}

	// Métodos do ouvinte mouse.
	@Override
	public void mousePressed(MouseEvent e) {
		mouseKeyStatus(e, true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseKeyStatus(e, false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
