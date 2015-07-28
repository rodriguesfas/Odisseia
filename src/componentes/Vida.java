package componentes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import core.Framework;
import efeitoVisual.Efeito;

/**
 * @author Francisco Assis Souza Rodrigues
 */

public class Vida {

	// Para a criação de novas Vidas.
	private static final long tempoEntreNovaVidaInicializar = Framework.secInNanosec * 3;
	public static long tempoEntreNovaVida = tempoEntreNovaVidaInicializar;
	public static long tempoPassadoCriouVida = 0;

	// Coordenada.
	public int xCoordenada;
	public int yCoordenada;

	// Velocidade e direção em movimento.
	private static final double movimentoVelocidadeInicialX = 0;
	private static double movimentoVelocidadeX = movimentoVelocidadeInicialX;

	// As imagens são carregadas e definidas na Classe Game no método
	// Carregarconteudo().
	public static BufferedImage imgVida;
	public static BufferedImage imgEfVida;

	// Animação da vida.
	private Efeito animVida;

	// Ajusta Coordenada da vida.
	public int ajustaCoordenadaVidaX;
	public int ajustaCoordenadaVidaY;

	/**
	 * 
	 * @param xCoordenada
	 * @param yCoordenada
	 */
	public void Inicializar(int xCoordenada, int yCoordenada) {

		// Cordenada Vida..
		this.xCoordenada = xCoordenada;
		this.yCoordenada = yCoordenada;

		// Inicializar animação da imagem vida (Referenciando tamanho da
		// posição da imagem, e suas respectivas coordenadas).
		animVida = new Efeito(imgVida, 82, 82, 4, 150, true, xCoordenada
				+ ajustaCoordenadaVidaX, yCoordenada + ajustaCoordenadaVidaY, 0);

		// Velocidade e direção da vida em movimento.
		Vida.movimentoVelocidadeX = -0.5;

		// Ajusta Cordenada da vida.
		this.ajustaCoordenadaVidaX = 0;
		this.ajustaCoordenadaVidaY = 0;
	}

	/**
	 * Define a velocidade eo tempo entre vidas para as propriedades iniciais.
	 */
	public static void reiniciar() {
		Vida.tempoEntreNovaVida = tempoEntreNovaVidaInicializar;
		Vida.tempoPassadoCriouVida = 0;
		Vida.movimentoVelocidadeX = movimentoVelocidadeInicialX;
	}

	/**
	 * Aumenta a velocidade da vida e diminuir o tempo entre as nova vidas.
	 */
	public static void speedUp() {
		if (Vida.tempoEntreNovaVida > Framework.secInNanosec)
			Vida.tempoEntreNovaVida -= Framework.secInNanosec / 100;

		Vida.movimentoVelocidadeX -= 0.15;
	}

	/**
	 * Verifica se a vida saiu da tela.
	 * 
	 * @ Return true se a vida saio da tela, caso contrário, false.
	 */
	public boolean isLeftScreen() {
		// Quando esta fora da tela.
		if (xCoordenada < 0 - imgVida.getWidth())
			return true;
		else
			return false;
	}

	/**
	 * Atualizações posição do Inimigo, animações.
	 */
	public void Atualizar() {
		// Move PowerUp em x de coordenadas.
		xCoordenada += movimentoVelocidadeX;

		//
		animVida.changeCoordinates(xCoordenada + ajustaCoordenadaVidaX,
				yCoordenada + ajustaCoordenadaVidaY);
	}

	/**
	 * Desenha inimigo na tela.
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	public void Draw(Graphics2D g2d) {
		// Desenha Amimação da Vida.
		animVida.Draw(g2d);
	}

}
