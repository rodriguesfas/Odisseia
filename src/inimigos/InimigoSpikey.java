package inimigos;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import core.Framework;
import efeitoVisual.Efeito;

/**
 * @author Francisco Assis Souza Rodrigues
 */

public class InimigoSpikey {

	// Para a criação de novos inimigos.
	private static final long tempoEntreNovosInimigosInicializar = Framework.secInNanosec * 3;
	public static long tempoEntreNovosInimigos = tempoEntreNovosInimigosInicializar;
	public static long tempoPassadoCriouInimigos = 0;

	// Saúde.
	public int saudeInicial = 150;
	public int saude;

	public int danoColisao = 5;

	// Coordenada.
	public int xCoordenada;
	public int yCoordenada;

	// Velocidade e direção em movimento.
	private static final double movimentoVelocidadeInicialX = 0;
	private static double movimentoVelocidadeX = movimentoVelocidadeInicialX;

	// Imagem do inimigo. As imagens são carregadas e definidas na
	// classe Game no método Carregarconteudo().
	public static BufferedImage imgInimigoSpikey;
	public static BufferedImage imgGiroInimigoSpikey;

	// Animação do Propusor Inimigo.
	private Efeito animInimigoSpikey;

	// Ajusta Coordenada do Propusor.
	public int ajustaCoordenadaGiroX;
	public int ajustaCoordenadaGiroY;

	/**
	 * Inicializar Inimigo.
	 * 
	 * @ Param xCoordinate Iniciando coordenada x helicóptero. @ Param
	 * yCoordinate Iniciando coordenada y de helicóptero. @ Param
	 * helicopterBodyImg Imagem do corpo de helicóptero. @ Param
	 * helicopterFrontPropellerAnimImg Imagem de hélice de helicóptero frente. @
	 * Param helicopterRearPropellerAnimImg Imagem de hélice de helicóptero
	 * traseiro.
	 */
	public void Inicializar(int xCoordenada, int yCoordenada) {
		// Inicializa Saúde.
		this.saude = saudeInicial;

		// Define a posição do inimigo.
		this.xCoordenada = xCoordenada;
		this.yCoordenada = yCoordenada;

		// Inicializar animação do Efeito Girar (Referenciando tamanho da
		// posição da
		// imagem, e suas respectivas coordenadas).
		animInimigoSpikey = new Efeito(imgGiroInimigoSpikey, 74, 74, 10, 0,
				true, xCoordenada + ajustaCoordenadaGiroX, yCoordenada
						+ ajustaCoordenadaGiroY, 0);

		// Velocidade e direção do inimigo em movimento.
		InimigoSpikey.movimentoVelocidadeX = -1;

		// Ajusta Cordenada do Propusor (De onde ele sairá da nave).
		this.ajustaCoordenadaGiroX = 0;
		this.ajustaCoordenadaGiroY = 0;
	}

	/**
	 * Ele define a velocidade eo tempo entre inimigos para as propriedades
	 * iniciais.
	 */
	public static void reiniciarInimigo() {
		InimigoSpikey.tempoEntreNovosInimigos = tempoEntreNovosInimigosInicializar;
		InimigoSpikey.tempoPassadoCriouInimigos = 0;
		InimigoSpikey.movimentoVelocidadeX = movimentoVelocidadeInicialX;
	}

	/**
	 * Ele aumenta a velocidade do inimigo e diminuir o tempo entre os novos
	 * inimigos.
	 */
	public static void speedUp() {
		if (InimigoSpikey.tempoEntreNovosInimigos > Framework.secInNanosec)
			InimigoSpikey.tempoEntreNovosInimigos -= Framework.secInNanosec / 100;

		InimigoSpikey.movimentoVelocidadeX -= 0.15;
	}

	/**
	 * Verifica se o inimigo é deixado na tela.
	 * 
	 * @ Return true se o inimigo é deixado na tela, caso contrário, false.
	 */
	public boolean isLeftScreen() {
		// Quando esta fora da tela.
		if (xCoordenada < 0 - imgInimigoSpikey.getWidth())
			return true;
		else
			return false;
	}

	/**
	 * Atualizações posição do Inimigo, animações.
	 */
	public void Atualizar() {
		// Move inimigo em x de coordenadas.
		xCoordenada += movimentoVelocidadeX;

		//
		animInimigoSpikey.changeCoordinates(
				xCoordenada + ajustaCoordenadaGiroX, yCoordenada
						+ ajustaCoordenadaGiroY);
	}

	/**
	 * Desenha inimigo na tela.
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	public void Draw(Graphics2D g2d) {
		// Desenha Inimigo
		g2d.drawImage(imgInimigoSpikey, xCoordenada, yCoordenada, null);

		// Desenha Amimação do PropusorInimigo
		animInimigoSpikey.Draw(g2d);
	}

}
