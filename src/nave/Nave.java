package nave;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import armasNave.LeiserNave;
import armasNave.MissilNave;
import core.Canvas;
import core.Framework;
import efeitoVisual.Efeito;
import gerenteImagens.GerenteImage;

/**
 * @author Francisco Assis Souza Rodrigues
 */

public class Nave extends Framework {
	private static final long serialVersionUID = 1L;

	// Saúde Inicial.
	private final int saudeInicial = 100;

	// Saúde Atual.
	private int saudeAtual;

	// Retangulo Barra de Saúde.
	public Rectangle barraSaude;

	// Inidicador da Barra de Saúde inicial.
	private int barraSaudeInicial = 100;
	private int barraSaudeAtual;
	private int coodernadaBarraSaudeHorizontal = 64;
	private int coodernadaBarraSaudeVertical = 54;

	// Posição da Nave na Tela.
	public int xCoordenada;
	public int yCoordenada;

	// Velocidade de Movimento e Direção.
	private double moveVelocidadeX;
	private double moveVelocidadeY;
	private double acelerarVelocidadeX;
	private double acelerarVelocidadeY;
	private double paraVelocidadeX;
	private double paraVelocidadeY;

	// Imagens
	private BufferedImage imgNave;
	private BufferedImage imgEfNave;
	private BufferedImage imgPropusor;

	// Animação dos Componetes da Nave
	private Efeito efNave;
	private Efeito propusor;

	// Ajusta Coordenada dos Componentes da Nave
	private int ajustaCoordenadaEfNaveX;
	private int ajustaCoordenadaEfNaveY;

	// Ajusta Coordenada dos Componentes da Nave
	private int ajustaCoordenadaPropunsorX;
	private int ajustaCoordenadaPropunsorY;

	// Ajusta Coordenada do Leiser.
	private int ajustaCoordenadaLeiserX;
	private int ajustaCoordenadaLeiserY;

	// Ajusta Coordenada do Missel.
	private int ajustaCoordenadaMissilX;
	private int ajustaCoordenadaMissilY;

	// Ajusta Coordenada do Efeito do Missil.
	public int ajustaCoordenadaEfeitoMissilX;
	public int ajustaCoordenadaEfeitoMissilY;

	/**
	 * Construtor da class.
	 * 
	 * @param xCoordenada
	 *            Starting x coordenada nave.
	 * @param yCoordenada
	 *            Starting y coordenda nave.
	 */
	public Nave(int xCoordenada, int yCoordenada) {
		this.xCoordenada = xCoordenada;
		this.yCoordenada = yCoordenada;

		CarregaConteudo();
		Inicializar();
	}

	/**
	 * Inicializar (Definir variáveis ​​e objetos para esta classe).
	 */
	private void Inicializar() {
		// Inicia SaudeAtual como o valor de saudeInicial.
		this.setSaudeAtual(getSaudeInicial());

		// Inicia BarraSaudeAtual com o valor da barraSaudeInicial.
		this.setBarraSaudeAtual(getBarraSaudeInicial());

		// Barra de Saúde.
		barraSaude = new Rectangle(getCoodernadaBarraSaudeHorizontal(),
				getCoodernadaBarraSaudeVertical(), getBarraSaudeAtual(), 10);

		// (Movimento).
		this.moveVelocidadeX = 3;
		this.setMoveVelocidadeY(3);

		// (Velocidade de Deslocamneto).
		this.acelerarVelocidadeX = 0.1;
		this.acelerarVelocidadeY = 0.1;

		// (Velocidade de Parada).
		this.paraVelocidadeX = 0.1;
		this.paraVelocidadeY = 0.1;

		// Ajusta Coordenada (onde se posicionara os componentes na nave).
		this.ajustaCoordenadaPropunsorX = -40;
		this.ajustaCoordenadaPropunsorY = 23;

		// Ajusta Cordenada do Leiser (De onde ele saira da nave).
		this.setAjustaCoordenadaLeiserX(38);
		this.setAjustaCoordenadaLeiserY(24);

		// Ajusta Cordenada do Missil (De onde ele saira da nave).
		this.setAjustaCoordenadaMissilX(0);
		this.setAjustaCoordenadaMissilY(0);

		this.ajustaCoordenadaEfeitoMissilX = this.xCoordenada
				+ this.getAjustaCoordenadaMissilX();
		this.ajustaCoordenadaEfeitoMissilY = this.yCoordenada
				+ this.getAjustaCoordenadaMissilY();
	}

	/**
	 * Carrega Arquivos para essa class.
	 */
	private void CarregaConteudo() {
		// tratamento de erros
		try {

			// Carrega Imagem da Nave.
			setImgNave(GerenteImage.getInstance().loadImage(
					"images/nave/nave.png"));

			// Carrega Imagem Ef da Nave.
			imgEfNave = GerenteImage.getInstance().loadImage(
					"images/nave/efNave.png");

			// Carrega Imagem do Propusor.
			imgPropusor = GerenteImage.getInstance().loadImage(
					"images/nave/propusor.png");

		} catch (IOException ex) {
			Logger.getLogger(Nave.class.getName()).log(Level.SEVERE, null, ex);
		}

		// Inicia Animação da efNave.
		efNave = new Efeito(imgEfNave, 100, 78, 6, 80, true, xCoordenada
				+ ajustaCoordenadaPropunsorX, yCoordenada
				+ ajustaCoordenadaPropunsorY, 0);

		// Inicia Animação do Propusor Nave.
		propusor = new Efeito(imgPropusor, 60, 27, 3, 20, true, xCoordenada
				+ ajustaCoordenadaPropunsorX, yCoordenada
				+ ajustaCoordenadaPropunsorY, 0);
	}

	/**
	 * Reinicia Nave.
	 * 
	 * @param xCoordenada
	 *            Starting x coordenada nave.
	 * @param yCoordenada
	 *            Starting y coordenada nave.
	 */
	public void Reiniciar(int xCoordenada, int yCoordenada) {
		// Reinicar Saúde.
		this.setSaudeAtual(getSaudeInicial());

		// Reiniciar Barra de Saúde
		this.setBarraSaudeAtual(getBarraSaudeInicial());

		this.xCoordenada = xCoordenada;
		this.yCoordenada = yCoordenada;

		this.moveVelocidadeX = 0;
		this.setMoveVelocidadeY(0);

	}

	/**
	 * Verifica disparo de Leiser. Verifica se pode dispara Leiser (tempo entre
	 * Leiser).
	 * 
	 * @param tempoJogo
	 *            O tempo de jogo decorrido atual em nanosegundos.
	 * @return true se estiver disparado Missil.
	 */
	public boolean DispararLeiser(long tempoJogo) {
		// Verifica se foi pressionado && se é a hora de novo Leiser e se ainda
		// tem disponivel.
		// Condição para disparar Leiser
		if (Canvas.keyboardKeyState(KeyEvent.VK_SPACE)
				&& ((tempoJogo - LeiserNave.tempoUltimoLeiserCriado) >= LeiserNave.tempoEntreNovoLeiser)) {
			return true;
		} else
			return false;
	}

	/**
	 * Verifica disparo de Missiel. Verifica se pode dispara Missil (tempo entre
	 * Misseis, se ainda tem disponivel).
	 * 
	 * @param tempoJogo
	 *            O tempo de jogo decorrido atual em nanosegundos.
	 * @return true se estiver disparado Missil.
	 */
	public boolean DispararMissil(long tempoJogo) {
		// Verifica se foi pressionado && se é a hora de novo Missil e se ainda
		// tem disponivel.
		// Condição dispara Missil
		if (Canvas.keyboardKeyState(KeyEvent.VK_W)
				&& ((tempoJogo - MissilNave.tempoUltimoMissilCriado) >= MissilNave.tempoEntreNovoMissil)) {
			return true;
		} else
			return false;
	}

	/**
	 * Verifica se o jogador movimenta a nave e define sua velocidade de
	 * movimento.
	 */
	public void Mover() {
		// Coordenada X.
		// (Para a Frente)
		if (Canvas.keyboardKeyState(KeyEvent.VK_RIGHT))
			moveVelocidadeX += acelerarVelocidadeX;
		// (Para Traz)
		else if (Canvas.keyboardKeyState(KeyEvent.VK_LEFT))
			moveVelocidadeX -= acelerarVelocidadeX;

		// Para
		else if (moveVelocidadeX < 0)
			moveVelocidadeX += paraVelocidadeX;

		else if (moveVelocidadeX > 0)
			moveVelocidadeX -= paraVelocidadeX;

		// Coordenada Y.
		// (Para Cima)
		if (Canvas.keyboardKeyState(KeyEvent.VK_UP))
			setMoveVelocidadeY(getMoveVelocidadeY() - acelerarVelocidadeY);

		// (Para Baixo)
		else if (Canvas.keyboardKeyState(KeyEvent.VK_DOWN))
			setMoveVelocidadeY(getMoveVelocidadeY() + acelerarVelocidadeY);

		// Para
		else if (getMoveVelocidadeY() < 0)
			setMoveVelocidadeY(getMoveVelocidadeY() + paraVelocidadeY);
		else if (getMoveVelocidadeY() > 0)
			setMoveVelocidadeY(getMoveVelocidadeY() - paraVelocidadeY);
	}

	/**
	 * Atualiza Posição da Nave, Animação de seus componentes.
	 */
	public void Atualizar() {
		// Move a Nave e seus Componentes
		xCoordenada += moveVelocidadeX;
		yCoordenada += getMoveVelocidadeY();

		// Restrinção para o deslocamneto da nave na tela
		if (this.xCoordenada < 1) {
			this.xCoordenada = 1;// MIN HORIZONTAL
			moveVelocidadeX = 0;
			// acelerarVelocidadeX = 0;
		}
		if (this.xCoordenada > frameLargura - 80) {
			this.xCoordenada = frameLargura - 80;// MAX HORIZONTAL
			moveVelocidadeX = 0;
			// acelerarVelocidadeX = 0;
		}
		if (this.yCoordenada < 1) {
			this.yCoordenada = 1;// MIN VERTICAL
			moveVelocidadeY = 0;
			// acelerarVelocidadeY = 0;
		}

		if (this.yCoordenada > frameAltura - 80) {
			this.yCoordenada = frameAltura - 80;// MAX VERTICAL
			moveVelocidadeY = 0;
			// acelerarVelocidadeY = 0;
		}
		efNave.changeCoordinates(xCoordenada + ajustaCoordenadaEfNaveX,
				yCoordenada + ajustaCoordenadaEfNaveY);

		propusor.changeCoordinates(
				xCoordenada + ajustaCoordenadaPropunsorX - 8, yCoordenada
						+ ajustaCoordenadaPropunsorY);

		// Ajusta a Coordenada do Efeito do Missil
		this.ajustaCoordenadaEfeitoMissilX = this.xCoordenada
				+ this.getAjustaCoordenadaMissilX();
		this.ajustaCoordenadaEfeitoMissilY = this.yCoordenada
				+ this.getAjustaCoordenadaMissilY();
	}

	/**
	 * Desenha Nave e seus Componentes na Tela.
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	public void Draw(Graphics2D g2d) {
		// Desenha Nave.
		// g2d.drawImage(getImgNave(), xCoordenada, yCoordenada, null);

		// Desenha eFNave.
		efNave.Draw(g2d);

		// Desenha Propusor.
		propusor.Draw(g2d);

		// Desenha a Cor da Barra de Saúde.
		if (getSaudeAtual() >= 70) {
			g2d.setColor(Color.GREEN);
		}
		if ((getSaudeAtual() < 70) && (getSaudeAtual() >= 30)) {
			g2d.setColor(Color.YELLOW);
		}
		if (getSaudeAtual() < 30) {
			g2d.setColor(Color.RED);
		}

		// Desenha a Barra de Saúde.
		g2d.fillRect(getCoodernadaBarraSaudeHorizontal(),
				getCoodernadaBarraSaudeVertical(), getBarraSaudeAtual(), 10);
	}

	/*
	 * #############################(METODOS DE ACESSO)#######################
	 */

	/**
	 * @return moveVelocidadeY
	 */
	public double getMoveVelocidadeY() {
		return moveVelocidadeY;
	}

	/**
	 * @param moveVelocidadeY
	 *            moveVelocidadeY set
	 */
	public void setMoveVelocidadeY(double moveVelocidadeY) {
		this.moveVelocidadeY = moveVelocidadeY;
	}

	/**
	 * @return the ajustaCoordenadaLeiserX
	 */
	public int getAjustaCoordenadaLeiserX() {
		return ajustaCoordenadaLeiserX;
	}

	/**
	 * @param ajustaCoordenadaLeiserX
	 *            the ajustaCoordenadaLeiserX to set
	 */
	public void setAjustaCoordenadaLeiserX(int ajustaCoordenadaLeiserX) {
		this.ajustaCoordenadaLeiserX = ajustaCoordenadaLeiserX;
	}

	/**
	 * @return the ajustaCoordenadaLeiserY
	 */
	public int getAjustaCoordenadaLeiserY() {
		return ajustaCoordenadaLeiserY;
	}

	/**
	 * @param ajustaCoordenadaLeiserY
	 *            the ajustaCoordenadaLeiserY to set
	 */
	public void setAjustaCoordenadaLeiserY(int ajustaCoordenadaLeiserY) {
		this.ajustaCoordenadaLeiserY = ajustaCoordenadaLeiserY;
	}

	/**
	 * @return the ajustaCoordenadaMissilX
	 */
	public int getAjustaCoordenadaMissilX() {
		return ajustaCoordenadaMissilX;
	}

	/**
	 * @param ajustaCoordenadaMissilX
	 *            the ajustaCoordenadaMissilX to set
	 */
	public void setAjustaCoordenadaMissilX(int ajustaCoordenadaMissilX) {
		this.ajustaCoordenadaMissilX = ajustaCoordenadaMissilX;
	}

	/**
	 * @return the ajustaCoordenadaMissilY
	 */
	public int getAjustaCoordenadaMissilY() {
		return ajustaCoordenadaMissilY;
	}

	/**
	 * @param ajustaCoordenadaMissilY
	 *            the ajustaCoordenadaMissilY to set
	 */
	public void setAjustaCoordenadaMissilY(int ajustaCoordenadaMissilY) {
		this.ajustaCoordenadaMissilY = ajustaCoordenadaMissilY;
	}

	/**
	 * @return the saudeInicial
	 */
	public int getSaudeInicial() {
		return saudeInicial;
	}

	/**
	 * @return the saudeAtual
	 */
	public int getSaudeAtual() {
		return saudeAtual;
	}

	/**
	 * @param saudeAtual
	 *            the saudeAtual to set
	 */
	public void setSaudeAtual(int saudeAtual) {
		this.saudeAtual = saudeAtual;
	}

	/**
	 * @return the barraSaudeInicial.
	 */
	public int getBarraSaudeInicial() {
		return barraSaudeInicial;
	}

	/**
	 * @return the BarraSaudeAtual
	 */
	public int getBarraSaudeAtual() {
		return barraSaudeAtual;
	}

	/**
	 * @param BarraSaudeAtual
	 *            the BarraSaudeAtual to set
	 */
	public void setBarraSaudeAtual(int barraSaudeAtual) {
		this.barraSaudeAtual = barraSaudeAtual;
	}

	/**
	 * @return the imgNave
	 */
	public BufferedImage getImgNave() {
		return imgNave;
	}

	/**
	 * @param imgNave
	 *            the imgNave to set
	 */
	public void setImgNave(BufferedImage imgNave) {
		this.imgNave = imgNave;
	}

	/**
	 * @return the coodernadaBarraSaudeHorizontal
	 */
	public int getCoodernadaBarraSaudeHorizontal() {
		return coodernadaBarraSaudeHorizontal;
	}

	/**
	 * @param coodernadaBarraSaudeHorizontal
	 *            the coodernadaBarraSaudeHorizontal to set
	 */
	public void setCoodernadaBarraSaudeHorizontal(
			int coodernadaBarraSaudeHorizontal) {
		this.coodernadaBarraSaudeHorizontal = coodernadaBarraSaudeHorizontal;
	}

	/**
	 * @return the coodernadaBarraSaudeVertical
	 */
	public int getCoodernadaBarraSaudeVertical() {
		return coodernadaBarraSaudeVertical;
	}

	/**
	 * @param coodernadaBarraSaudeVertical
	 *            the coodernadaBarraSaudeVertical to set
	 */
	public void setCoodernadaBarraSaudeVertical(int coodernadaBarraSaudeVertical) {
		this.coodernadaBarraSaudeVertical = coodernadaBarraSaudeVertical;
	}

}
