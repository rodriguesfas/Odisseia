package componentes;

import core.Framework;
import efeitoVisual.Efeito;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Francisco Assis Souza Rodrigues
 */

public class PowerUpLeiser {

    // Para a criação de novas Potencias.
    private static final long tempoEntreNovaPowerUpLeiserInicializar = Framework.secInNanosec * 3;
    public static long tempoEntreNovaPowerUpLeiser = tempoEntreNovaPowerUpLeiserInicializar;
    public static long tempoPassadoCriouPowerUpLeiser = 0;

    // Coordenada.
    public int xCoordenada;
    public int yCoordenada;

    // Velocidade e direção em movimento.
    private static final double movimentoVelocidadeInicialX = 0;
    private static double movimentoVelocidadeX = movimentoVelocidadeInicialX;

    // Imagem do inimigo. As imagens são carregadas e definidas na
    // classe Game no método Carregarconteudo().
    public static BufferedImage imgPowerUpLeiser;

    // Animação do PowerUp Leiser
    private Efeito animPowerUpLeiser;

    // Ajusta Coordenada do PowerUp Leiser.
    public int ajustaCoordenadaPowerUpLeiserX;
    public int ajustaCoordenadaPowerUpLeiserY;

    public void Inicializar(int xCoordenada, int yCoordenada) {

        // Define a posição do PowerUp Leiser.
        this.xCoordenada = xCoordenada;
        this.yCoordenada = yCoordenada;

        // Inicializar animação do PowerUp (Referenciando tamanho da
        // posição da imagem, e suas respectivas coordenadas).
        animPowerUpLeiser = new Efeito(imgPowerUpLeiser, 82, 82, 4, 150,
                true, xCoordenada + ajustaCoordenadaPowerUpLeiserX, yCoordenada
                + ajustaCoordenadaPowerUpLeiserY, 0);

        // Velocidade e direção do inimigo em movimento.
        PowerUpLeiser.movimentoVelocidadeX = -0.5;

        // Ajusta Cordenada do Propusor (De onde ele sairá da nave).
        this.ajustaCoordenadaPowerUpLeiserX = 0;
        this.ajustaCoordenadaPowerUpLeiserY = 0;
    }

    /**
     * Ele define a velocidade eo tempo entre PowerUp para as propriedades
     * iniciais.
     */
    public static void reiniciar() {
        PowerUpLeiser.tempoEntreNovaPowerUpLeiser = tempoEntreNovaPowerUpLeiserInicializar;
        PowerUpLeiser.tempoPassadoCriouPowerUpLeiser = 0;
        PowerUpLeiser.movimentoVelocidadeX = movimentoVelocidadeInicialX;
    }

    /**
     * Ele aumenta a velocidade do PowerUp e diminuir o tempo entre os novos
     * inimigos.
     */
    public static void speedUp() {
        if (PowerUpLeiser.tempoEntreNovaPowerUpLeiser > Framework.secInNanosec)
            PowerUpLeiser.tempoEntreNovaPowerUpLeiser -= Framework.secInNanosec / 100;

        PowerUpLeiser.movimentoVelocidadeX -= 0.15;
    }

    /**
     * Verifica se o PowerUp saiu da tela.
     *
     * @ Return true se o inimigo é deixado na tela, caso contrário, false.
     */
    public boolean isLeftScreen() {
        // Quando esta fora da tela.
        if (xCoordenada < 0 - imgPowerUpLeiser.getWidth())
            return true;
        else
            return false;
    }

    /**
     * Atualizações posição do Inimigo, animações.
     */
    public void Atualizar() {
        // Move PowerUp em x de coordenadas.
        xCoordenada += movimentoVelocidadeX;

        //
        animPowerUpLeiser.changeCoordinates(xCoordenada
                + ajustaCoordenadaPowerUpLeiserX, yCoordenada
                + ajustaCoordenadaPowerUpLeiserY);
    }

    /**
     * Desenha inimigo na tela.
     *
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d) {
        // Desenha Inimigo
        // g2d.drawImage(imgEfPowerUpLeiser, xCoordenada, yCoordenada, null);

        // Desenha Amimação do PropusorInimigo
        animPowerUpLeiser.Draw(g2d);
    }

}
