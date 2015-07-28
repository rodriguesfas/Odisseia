package armasNave;

import core.Framework;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Francisco Assis Souza Rodrigues
 */

/**
 * Class Missil
 */

public class MissilNave {

    // Tempo que deve passar antes que outro Missil pode ser emitido.
    public final static long tempoEntreNovoMissil = Framework.secInNanosec / 4;
    public static long tempoUltimoMissilCriado = 0;

    // O dano que é feito no inimigo quando ele é atingido por um Missil.
    public static int damagePower = 100;

    // Coordenadas do Missil
    public int xCoordenada;
    public int yCoordenada;

    // Velocidade e também sentido de deslocamento. Missil vai sempre em
    // frente, de modo a movê-lo apenas na coordenada x.
    private double movendoVelocidadeX;

    // Tempo de vida de atual de fumaça do Missil.
    public long tempoAtualDaFumaca;

    // Imagem do Missil.
    // Carregado e configurada na classe Jogo em CarregarConteudo (método).
    public static BufferedImage imgMissil;

    /**
     * Definir variáveis ​​e objetos para esta classe.
     */
    public void Inicializar(int xCoordenada, int yCoordenada) {
        this.xCoordenada = xCoordenada;
        this.yCoordenada = yCoordenada;

        this.movendoVelocidadeX = 20;

        this.tempoAtualDaFumaca = Framework.secInNanosec / 2;
    }

    /**
     * Verifica se o Missil saio da tela
     *
     * @return true se o Missil sair da tela, caso contrario false.
     */
    public boolean saiudaTela() {
        if (xCoordenada > 0 && xCoordenada < Framework.frameLargura)
            return false;
        else
            return true;
    }

    /**
     * Move Missil.
     */
    public void Atualizar() {
        xCoordenada += movendoVelocidadeX;
    }

    /**
     * Draws Missil na Tela
     *
     * @param g2d
     *            Graphics2D
     */
    public void Draw(Graphics2D g2d) {
        g2d.drawImage(imgMissil, xCoordenada, yCoordenada, null);
    }
}
