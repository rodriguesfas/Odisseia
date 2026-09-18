package inimigos;

import core.Framework;
import efeitoVisual.Efeito;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Inimigo Spikey — mais resistente, com animação de giro.
 */
public class InimigoSpikey extends Inimigo {

    private static final long tempoEntreNovosInimigosInicializar = Framework.secInNanosec * 3;
    public static long tempoEntreNovosInimigos = tempoEntreNovosInimigosInicializar;
    public static long tempoPassadoCriouInimigos = 0;

    private static final double movimentoVelocidadeInicialX = -1;
    private static double movimentoVelocidadeX = movimentoVelocidadeInicialX;

    public static BufferedImage imgInimigoSpikey;
    public static BufferedImage imgGiroInimigoSpikey;

    private Efeito animInimigoSpikey;

    public int ajustaCoordenadaGiroX;
    public int ajustaCoordenadaGiroY;

    public InimigoSpikey() {
        super(150, 5);
    }

    public void Inicializar(int xCoordenada, int yCoordenada) {
        InicializarPosicao(xCoordenada, yCoordenada);

        this.ajustaCoordenadaGiroX = 0;
        this.ajustaCoordenadaGiroY = 0;

        animInimigoSpikey = new Efeito(imgGiroInimigoSpikey, 74, 74, 10, 0, true,
                xCoordenada + ajustaCoordenadaGiroX,
                yCoordenada + ajustaCoordenadaGiroY, 0);

        if (movimentoVelocidadeX >= 0) {
            movimentoVelocidadeX = movimentoVelocidadeInicialX;
        }
    }

    public static void reiniciarInimigo() {
        tempoEntreNovosInimigos = tempoEntreNovosInimigosInicializar;
        tempoPassadoCriouInimigos = 0;
        movimentoVelocidadeX = movimentoVelocidadeInicialX;
    }

    public static void speedUp() {
        if (tempoEntreNovosInimigos > Framework.secInNanosec) {
            tempoEntreNovosInimigos -= Framework.secInNanosec / 100;
        }
        movimentoVelocidadeX -= 0.15;
    }

    @Override
    protected double velocidadeX() {
        return movimentoVelocidadeX;
    }

    @Override
    protected BufferedImage imagemCorpo() {
        return imgInimigoSpikey;
    }

    @Override
    protected void atualizarEfeitos() {
        if (animInimigoSpikey != null) {
            animInimigoSpikey.changeCoordinates(
                    xCoordenada + ajustaCoordenadaGiroX,
                    yCoordenada + ajustaCoordenadaGiroY);
        }
    }

    @Override
    protected void desenharEfeitos(Graphics2D g2d) {
        if (animInimigoSpikey != null) {
            animInimigoSpikey.Draw(g2d);
        }
    }
}
