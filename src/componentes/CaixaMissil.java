package componentes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import core.Framework;
import efeitoVisual.Efeito;

/**
 * @author Francisco Assis Souza Rodrigues
 */

public class CaixaMissil {

	// Para a criação de Missil.
	private static final long tempoEntreNovaCaixaMissilInicializar = Framework.secInNanosec * 3;
	public static long tempoEntreNovaCaixaMissil = tempoEntreNovaCaixaMissilInicializar;
	public static long tempoPassadoCriouCaixaMissil = 0;

	// Coordenada.
	public int xCoordenada;
	public int yCoordenada;

	// Velocidade e direção em movimento.
	private static final double movimentoVelocidadeInicialX = 0;
	private static double movimentoVelocidadeX = movimentoVelocidadeInicialX;

	// Imagem do inimigo. As imagens são carregadas e definidas na
	// classe Game no método Carregarconteudo().
	public static BufferedImage imgCaixaMissil;

	// Animação da CaixaMissil.
	private Efeito animCaixaMissil;

	// Ajusta Coordenada da Caixa Missil.
	public int ajustaCoordenadaCaixaMissilX;
	public int ajustaCoordenadaCaixaMissilY;

	public void Inicializar(int xCoordenada, int yCoordenada) {

		// Define a posição da Caixa Missil.
		this.xCoordenada = xCoordenada;
		this.yCoordenada = yCoordenada;

		// Inicializar animação da CaixaMissil (Referenciando tamanho da
		// posição da imagem, e suas respectivas coordenadas).
		animCaixaMissil = new Efeito(imgCaixaMissil, 82, 82, 3, 150, true,
				xCoordenada + ajustaCoordenadaCaixaMissilX, yCoordenada
						+ ajustaCoordenadaCaixaMissilY, 0);

		// Velocidade e direção da CaixaMissil em Movimento.
		CaixaMissil.movimentoVelocidadeX = -0.5;

		// Ajusta Cordenada da CaixaMissil.
		this.ajustaCoordenadaCaixaMissilX = 0;
		this.ajustaCoordenadaCaixaMissilY = 0;
	}

	/**
	 * Define a velocidade e o tempo entre CaixaMissil para as propriedades
	 * iniciais.
	 */
	public static void reiniciar() {
		CaixaMissil.tempoEntreNovaCaixaMissil = tempoEntreNovaCaixaMissilInicializar;
		CaixaMissil.tempoPassadoCriouCaixaMissil = 0;
		CaixaMissil.movimentoVelocidadeX = movimentoVelocidadeInicialX;
	}

	/**
	 * Aumenta a velocidade da CaixaMissil e diminuir o tempo entre as novas
	 * CaixaMissil.
	 */
	public static void speedUp() {
		if (CaixaMissil.tempoEntreNovaCaixaMissil > Framework.secInNanosec)
			CaixaMissil.tempoEntreNovaCaixaMissil -= Framework.secInNanosec / 100;

		CaixaMissil.movimentoVelocidadeX -= 0.15;
	}

	/**
	 * Verifica se a CaixaMissil saiu da tela.
	 * 
	 * @ Return true se a CaixaMissil saiu da tela, caso contrário, false.
	 */
	public boolean isLeftScreen() {
		// Quando esta fora da tela.
		if (xCoordenada < 0 - imgCaixaMissil.getWidth())
			return true;
		else
			return false;
	}

	/**
	 * Atualizações posição da CaixaMissil, animações.
	 */
	public void Atualizar() {
		// Move CaixaMissil em x de coordenadas.
		xCoordenada += movimentoVelocidadeX;

		//
		animCaixaMissil.changeCoordinates(xCoordenada
				+ ajustaCoordenadaCaixaMissilX, yCoordenada
				+ ajustaCoordenadaCaixaMissilY);
	}

	/**
	 * Desenha CaixaMissil na tela.
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	public void Draw(Graphics2D g2d) {
		// Desenha CaixaMissil
		// g2d.drawImage(imgCaixaMissil, xCoordenada, yCoordenada, null);

		// Desenha Amimação da Caixa Missil
		animCaixaMissil.Draw(g2d);
	}

}
