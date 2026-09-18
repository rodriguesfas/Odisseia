package inimigos;

import armasInimigos.LeiserInimigo;
import core.Framework;
import efeitoVisual.Efeito;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Inimigo UFO — atira laser e tem propulsor animado.
 */
public class InimigoUFO extends Inimigo {

    private static final long tempoEntreNovosInimigosInicializar = Framework.secInNanosec * 3;
    public static long tempoEntreNovosInimigos = tempoEntreNovosInimigosInicializar;
    public static long tempoPassadoCriouInimigos = 0;

    private static final double movimentoVelocidadeInicialX = -1;
    private static double movimentoVelocidadeX = movimentoVelocidadeInicialX;

    public static BufferedImage imgInimigoUFO;
    public static BufferedImage imgPropusorInimigo;

    private Efeito animPropusorInimigo;

    public int ajustaCoordenadaLeiserX;
    public int ajustaCoordenadaLeiserY;
    public int ajustaCoordenadaPropusorX;
    public int ajustaCoordenadaPropusorY;

    public InimigoUFO() {
        super(100, 10);
    }

    public void Inicializar(int xCoordenada, int yCoordenada) {
        InicializarPosicao(xCoordenada, yCoordenada);

        this.ajustaCoordenadaLeiserX = 0;
        this.ajustaCoordenadaLeiserY = 0;
        this.ajustaCoordenadaPropusorX = 20;
        this.ajustaCoordenadaPropusorY = 40;

        animPropusorInimigo = new Efeito(imgPropusorInimigo, 30, 200, 17, 0, true,
                xCoordenada + ajustaCoordenadaPropusorX,
                yCoordenada + ajustaCoordenadaPropusorY, 0);

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

    public boolean DispararLeiser(long tempoJogo) {
        return tempoJogo - tempoPassadoCriouInimigos >= Framework.secInNanosec * 2
                && (tempoJogo - LeiserInimigo.tempoUltimoLeiserCriado) >= LeiserInimigo.tempoEntreNovoLeiser;
    }

    @Override
    protected double velocidadeX() {
        return movimentoVelocidadeX;
    }

    @Override
    protected BufferedImage imagemCorpo() {
        return imgInimigoUFO;
    }

    @Override
    protected void atualizarEfeitos() {
        if (animPropusorInimigo != null) {
            animPropusorInimigo.changeCoordinates(
                    xCoordenada + ajustaCoordenadaPropusorX,
                    yCoordenada + ajustaCoordenadaPropusorY);
        }
    }

    @Override
    protected void desenharEfeitos(Graphics2D g2d) {
        if (animPropusorInimigo != null) {
            animPropusorInimigo.Draw(g2d);
        }
    }
}
