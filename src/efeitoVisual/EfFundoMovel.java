package efeitoVisual;

import core.Framework;
import core.Tempo;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Imagem de fundo móvel em loop (horizontal).
 */
public class EfFundoMovel {

    private BufferedImage imgFundo;
    private double velocidadeImgFundo;
    private double[] xPosicaoImgFundo;
    private int yPosicaoImgFundo;
    private int drawWidth;
    private int drawHeight;

    /**
     * @param imagem     imagem a repetir
     * @param velocidade negativa = esquerda; positiva = direita
     * @param yPosicao   coordenada Y
     */
    public void Inicializar(BufferedImage imagem, double velocidade, int yPosicao) {
        this.imgFundo = imagem;
        this.velocidadeImgFundo = velocidade;
        this.yPosicaoImgFundo = yPosicao;

        // Escala a tile para cobrir a altura da tela sem distorcer demais.
        float scale = Framework.frameAltura / (float) Math.max(1, imagem.getHeight());
        if (scale < 1f) {
            scale = 1f;
        }
        this.drawHeight = Math.round(imagem.getHeight() * scale);
        this.drawWidth = Math.round(imagem.getWidth() * scale);

        int numeroDePosicoes = (Framework.frameLargura / Math.max(1, drawWidth)) + 2;
        xPosicaoImgFundo = new double[Math.max(2, numeroDePosicoes)];
        for (int i = 0; i < xPosicaoImgFundo.length; i++) {
            xPosicaoImgFundo[i] = i * drawWidth;
        }
    }

    private void Atualizar() {
        if (xPosicaoImgFundo == null || imgFundo == null) {
            return;
        }
        double step = velocidadeImgFundo * Tempo.scale();
        for (int i = 0; i < xPosicaoImgFundo.length; i++) {
            xPosicaoImgFundo[i] += step;

            if (velocidadeImgFundo < 0) {
                if (xPosicaoImgFundo[i] <= -drawWidth) {
                    xPosicaoImgFundo[i] = drawWidth * (xPosicaoImgFundo.length - 1);
                }
            } else {
                if (xPosicaoImgFundo[i] >= (double) drawWidth * (xPosicaoImgFundo.length - 1)) {
                    xPosicaoImgFundo[i] = -drawWidth;
                }
            }
        }
    }

    public void Draw(Graphics2D g2d) {
        this.Atualizar();
        if (imgFundo == null || xPosicaoImgFundo == null) {
            return;
        }
        for (int i = 0; i < xPosicaoImgFundo.length; i++) {
            g2d.drawImage(imgFundo, (int) xPosicaoImgFundo[i], yPosicaoImgFundo,
                    drawWidth, drawHeight, null);
        }
    }
}
