package inimigos;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import armasInimigos.LeiserInimigo;
import core.Framework;
import efeitoVisual.Efeito;

/**
 * @author Francisco Assis Souza Rodrigues
 */

public class Inimigo {

	// Para a criação de novos inimigos.
	private static final long tempoEntreNovosInimigosInicializar = Framework.secInNanosec * 3;
	public static long tempoEntreNovosInimigos = tempoEntreNovosInimigosInicializar;
	public static long tempoPassadoCriouInimigos = 0;

	// Saúde.
	public int saudeInicial = 100;
	public int saude;

	public int danoColisao = 10;

	// Coordenada.
	public int xCoordenada;
	public int yCoordenada;

	// Velocidade e direção em movimento.
	private static final double movimentoVelocidadeInicialX = 0;
	private static double movimentoVelocidadeX = movimentoVelocidadeInicialX;

	// Imagem do inimigo. As imagens são carregadas e definidas na
	// classe Game no método Carregarconteudo().
	public static BufferedImage imgInimigo;
	public static BufferedImage imgComponenteInimigo;
	public static BufferedImage imgComponente2Inimigo;

	// Animação dos Componentes.
	private Efeito animacaoComponenteInimigo;
	private Efeito animacaoComponente2Inimigo;

	// Deslocamento para a hélice. Adicionamos deslocado para a posição da
	// posição de helicóptero.
	private static int offsetXFrontPropeller = 4;
	private static int offsetYFrontPropeller = -7;
	private static int offsetXRearPropeller = 205;
	private static int offsetYRearPropeller = 6;

	// Ajusta Coordenada do Leiser.
	public int ajustaCoordenadaLeiserX;
	public int ajustaCoordenadaLeiserY;

	/**
	 * 
	 * @param xCoordenada
	 * @param yCoordenada
	 */
	public void Inicializar(int xCoordenada, int yCoordenada) {
		// Inicializa.
		this.saude = saudeInicial;

		// Define a posição do inimigo.
		this.xCoordenada = xCoordenada;
		this.yCoordenada = yCoordenada;

		// Inicializar animação objeto.
		animacaoComponenteInimigo = new Efeito(imgComponenteInimigo, 158,
				16, 3, 20, true, xCoordenada + offsetXFrontPropeller,
				yCoordenada + offsetYFrontPropeller, 0);

		animacaoComponente2Inimigo = new Efeito(imgComponente2Inimigo, 47,
				47, 10, 10, true, xCoordenada + offsetXRearPropeller,
				yCoordenada + offsetYRearPropeller, 0);

		// Velocidade e direção do inimigo em movimento.
		Inimigo.movimentoVelocidadeX = -1;

		// Ajusta Cordenada do Leiser (De onde ele saira da nave).
		this.ajustaCoordenadaLeiserX = 38;
		this.ajustaCoordenadaLeiserY = 24;
	}

	/**
	 * Ele define a velocidade eo tempo entre inimigos para as propriedades
	 * iniciais.
	 */
	public static void reiniciarInimigo() {
		Inimigo.tempoEntreNovosInimigos = tempoEntreNovosInimigosInicializar;
		Inimigo.tempoPassadoCriouInimigos = 0;
		Inimigo.movimentoVelocidadeX = movimentoVelocidadeInicialX;
	}

	/**
	 * Ele aumenta a velocidade do inimigo e diminuir o tempo entre os novos
	 * inimigos.
	 */
	public static void speedUp() {
		if (Inimigo.tempoEntreNovosInimigos > Framework.secInNanosec)
			Inimigo.tempoEntreNovosInimigos -= Framework.secInNanosec / 100;

		Inimigo.movimentoVelocidadeX -= 0.25;
	}

	/**
	 * Verifica se o inimigo é deixado na tela.
	 * 
	 * @ Return true se o inimigo é deixado na tela, caso contrário, false.
	 */
	public boolean isLeftScreen() {
		// Quando esta para fora da tela.
		if (xCoordenada < 0 - imgInimigo.getWidth())
			return true;
		else
			return false;
	}

	/**
	 * Verifica disparo de LeiserInimigo. Verifica se pode dispara LeiserInimigo
	 * (tempo entre Leiser).
	 * 
	 * @param tempoJogo
	 *            O tempo de jogo decorrido atual em nanosegundos.
	 * @return true se estiver disparado Missil.
	 */
	public boolean DispararLeiser(long tempoJogo) {
		// Verifica se foi pressionado && se é a hora de novo Leiser e se ainda
		// tem disponivel.
		// Condição para disparar Leiser
		if (tempoJogo - tempoPassadoCriouInimigos >= Framework.secInNanosec * 2.5
				&& ((tempoJogo - LeiserInimigo.tempoUltimoLeiserCriado) >= LeiserInimigo.tempoEntreNovoLeiser)) {
			return true;
		} else
			return false;
	}

	/**
	 * Atualizações posição de helicóptero, animações.
	 */
	public void Atualizar() {
		// Mova inimigo em x de coordenadas.
		xCoordenada += movimentoVelocidadeX;

		//
		animacaoComponenteInimigo.changeCoordinates(xCoordenada
				+ offsetXFrontPropeller, yCoordenada + offsetYFrontPropeller);

		animacaoComponente2Inimigo.changeCoordinates(xCoordenada
				+ offsetXRearPropeller, yCoordenada + offsetYRearPropeller);
	}

	/**
	 * Desenha inimigo na tela.
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	public void Draw(Graphics2D g2d) {
		animacaoComponenteInimigo.Draw(g2d);
		g2d.drawImage(imgInimigo, xCoordenada, yCoordenada, null);
		animacaoComponente2Inimigo.Draw(g2d);
	}

}
