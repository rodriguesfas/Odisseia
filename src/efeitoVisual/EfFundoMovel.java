package efeitoVisual;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import core.Framework;

/**
 *@author Francisco Assis Souza Rodrigues 
 */

/**
 * Imagem de fundo Movel em Loop.
 * 
 */

public class EfFundoMovel {

	/* Imagem de Fundo */
	private BufferedImage imgFundo;

	/* Velocidade da Imagem de Fundo */
	private double velocidadeImgFundo;

	/* Posição da Imagem de Fundo */
	private double xPosicaoImgFundo[];
	private int yPosicaoImgFundo;

	/**
	 * Inicializar objeto de imagem em movimento. @ Param imagem Imagem que vai
	 * estar se movendo. @ Param velocidade @ Param yPosition Y de coordenadas
	 * da imagem.
	 * 
	 * Qual a velocidade e em que direção devem mover a imagem? se Número é
	 * negativo, a imagem vai passar para esquerda. Se você usar Número decimal,
	 * em alguns casos imagem pode cobrir um sobre o outro No ponto final, ou
	 * poderia ser espaços entre as imagens, de modo a tentar Outro número.
	 */
	public void Inicializar(BufferedImage imagem, double velocidade,
			int yPosicao) {

		this.imgFundo = imagem;
		this.velocidadeImgFundo = velocidade;
		this.yPosicaoImgFundo = yPosicao;

		/*
		 * Divide o tamanho da janela com o tamanho da imagem quantas vezes
		 * Precisa desenhar imagem na tela. Precisa adicionar 2 de modo que não
		 * tenha espaços em branco entre as imagens.
		 */
		int numeroDePosicoes = (Framework.frameAltura / this.imgFundo
				.getHeight()) + 2;
		xPosicaoImgFundo = new double[numeroDePosicoes];

		/*
		 * Definir coordenada yPosicaoImgFundo para cada imagem que tera que
		 * desenhar
		 */
		for (int i = 0; i < xPosicaoImgFundo.length; i++) {
			xPosicaoImgFundo[i] = i * imagem.getHeight();
		}
	}

	/**
	 * Move Imagens.
	 */
	private void Atualizar() {
		for (int i = 0; i < xPosicaoImgFundo.length; i++) {
			// Move Imagem
			xPosicaoImgFundo[i] += velocidadeImgFundo;

			/* Condição para a Imagem movimentar para a esquerda */
			if (velocidadeImgFundo < 0) {
				/*
				 * Se imagem estiver fora da tela, ele move para o fim da linha
				 * de imagens
				 */
				if (xPosicaoImgFundo[i] <= -imgFundo.getHeight()) {
					xPosicaoImgFundo[i] = imgFundo.getHeight()
							* (xPosicaoImgFundo.length - 1);
				}
			}
			/* Condição para a Imagem movimentar para a Direita */
			else {
				/*
				 * Se imagem estiver fora da tela, ele move para o fim da linha
				 * de Imagens.
				 */
				if (xPosicaoImgFundo[i] >= imgFundo.getHeight()
						* (xPosicaoImgFundo.length - 1)) {
					xPosicaoImgFundo[i] = -imgFundo.getHeight();
				}
			}
		}
	}

	/**
	 * Dezenha Imagens na Tela
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	public void Draw(Graphics2D g2d) {
		this.Atualizar();

		for (int i = 0; i < xPosicaoImgFundo.length; i++) {
			g2d.drawImage(imgFundo, (int) xPosicaoImgFundo[i],
					yPosicaoImgFundo, null);
		}
	}
}