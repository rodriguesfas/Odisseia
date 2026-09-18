package inimigos;

import core.Tempo;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Base comum para inimigos (posição, saúde, movimento com delta time).
 */
public abstract class Inimigo {

    public int saudeInicial;
    public int saude;
    public int danoColisao;

    public int xCoordenada;
    public int yCoordenada;

    protected Inimigo(int saudeInicial, int danoColisao) {
        this.saudeInicial = saudeInicial;
        this.saude = saudeInicial;
        this.danoColisao = danoColisao;
    }

    protected abstract double velocidadeX();

    protected abstract BufferedImage imagemCorpo();

    protected abstract void atualizarEfeitos();

    protected abstract void desenharEfeitos(Graphics2D g2d);

    public void InicializarPosicao(int x, int y) {
        this.saude = saudeInicial;
        this.xCoordenada = x;
        this.yCoordenada = y;
    }

    public void Atualizar() {
        xCoordenada += velocidadeX() * Tempo.scale();
        atualizarEfeitos();
    }

    public boolean isLeftScreen() {
        BufferedImage img = imagemCorpo();
        return img != null && xCoordenada < -img.getWidth();
    }

    public Rectangle bounds() {
        BufferedImage img = imagemCorpo();
        int w = img != null ? img.getWidth() : 1;
        int h = img != null ? img.getHeight() : 1;
        return new Rectangle(xCoordenada, yCoordenada, w, h);
    }

    public void Draw(Graphics2D g2d) {
        BufferedImage img = imagemCorpo();
        if (img != null) {
            g2d.drawImage(img, xCoordenada, yCoordenada, null);
        }
        desenharEfeitos(g2d);
    }
}
