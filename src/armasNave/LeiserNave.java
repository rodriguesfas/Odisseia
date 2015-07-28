package armasNave;

import core.Framework;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Francisco Assis Souza Rodrigues
 */

/**
 * Class Leiser
 */

public class LeiserNave {
    // Tempo que deve passar antes que outro leiser pode ser emitido.
    public final static long tempoEntreNovoLeiser = Framework.secInNanosec / 4;
    public static long tempoUltimoLeiserCriado = 0;

    // O dano que é feito no inimigo quando ele é atingido por um Leiser.
    public static int danoDeEnergia = 25;

    // Coordenada Leiser
    public int xCoordenada;
    public int yCoordenada;

    // Velocidade e também sentido de deslocamento. Leiser vai sempre em
    // frente, de modo a movê-lo apenas na coordenada x.
    private double movendoVelocidadeX;

    // Tempo de vida de atual de fumaça do Leiser.
    public long tempoAtualDaFumaca;

    // Imagem do Leiser.
    // Carregado e configurada na classe Jogo em CarregarConteudo (método).
    public static BufferedImage imgLeiser;

    /**
     * Definir variáveis ​​e objetos para esta classe.
     */
    public void Inicializar(int xCoordenada, int yCoordenada) {
        this.xCoordenada = xCoordenada;
        this.yCoordenada = yCoordenada;

        this.movendoVelocidadeX = 23;

        this.tempoAtualDaFumaca = Framework.secInNanosec / 2;
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
        g2d.drawImage(imgLeiser, xCoordenada, yCoordenada, null);
    }
}
