package armasInimigos;

import core.Framework;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Francisco Assis Souza Rodrigues
 */

/**
 * Class Leiser
 */

public class LeiserInimigo {
    // Tempo que deve passar antes que outro leiserInimigo pode ser emitido.
    public final static long tempoEntreNovoLeiser = Framework.secInNanosec / 4;
    public static long tempoUltimoLeiserCriado = 0;

    // O dano que é feito no inimigo quando ele é atingido por um Leiser.
    public static int danoDeEnergia = 1;

    // Coordenada Leiser
    public int xCoordenada;
    public int yCoordenada;

    // Velocidade e também sentido de deslocamento.
    // Leiser vai sempre em frente, de modo a movê-lo apenas na coordenada x.
    private double movendoVelocidadeX;

    // Imagem do Leiser.
    // Carregado e configurada na classe Jogo em CarregarConteudo (método).
    public static BufferedImage imgLeiserInimigo;

    /**
     * Definir variáveis ​​e objetos para esta classe.
     */
    public void Inicializar(int xCoordenada, int yCoordenada) {
        this.xCoordenada = xCoordenada;
        this.yCoordenada = yCoordenada;

        // Define Velocidade e Trajetoria do Disparo do Leiser Inimigo.
        this.movendoVelocidadeX = -4;
    }

    /**
     * Verifica se o leiser saio da tela
     *
     * @return true se o leiser sair da tela, caso contrario false.
     */
    public boolean saiuDaTela() {
        // Movimentos, Leiser na cordenada x, não precisa verificar y
        if (xCoordenada > 0 && xCoordenada < Framework.frameLargura)
            return false;
        else
            return true;
    }

    /**
     * Move Leiser
     */
    public void Atualizar() {
        xCoordenada += movendoVelocidadeX;
    }

    /**
     * Draws Leiser na Telas
     *
     * @param g2d
     *            Graphics2D
     */
    public void Draw(Graphics2D g2d) {
        g2d.drawImage(imgLeiserInimigo, xCoordenada, yCoordenada, null);
    }
}
