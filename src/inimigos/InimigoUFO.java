package inimigos;

import armasInimigos.LeiserInimigo;
import core.Framework;
import efeitoVisual.Efeito;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Francisco Assis Souza Rodrigues
 */

public class InimigoUFO {

    // Para a criação de novos inimigos.
    private static final long tempoEntreNovosInimigosInicializar = Framework.secInNanosec * 3;
    public static long tempoEntreNovosInimigos = tempoEntreNovosInimigosInicializar;
    public static long tempoPassadoCriouInimigos = 0;

    // Saúde.
    public int saudeInicial = 100;
    public int saude;

    public int danoColisao = 10;

    // Coordenada.
    public int xCoordenada;
    public int yCoordenada;

    // Velocidade e direção em movimento.
    private static final double movimentoVelocidadeInicialX = 0;
    private static double movimentoVelocidadeX = movimentoVelocidadeInicialX;
    private static final double movimentoVelocidadeInicialY = 0;
    private static double movimentoVelocidadeY = movimentoVelocidadeInicialY;

    // Imagem do inimigo. As imagens são carregadas e definidas na
    // classe Game no método Carregarconteudo().
    public static BufferedImage imgInimigoUFO;
    public static BufferedImage imgPropusorInimigo;

    // Animação do Propusor Inimigo.
    private Efeito animPropusorInimigo;

    // Ajusta Coordenada do Leiser.
    public int ajustaCoordenadaLeiserX;
    public int ajustaCoordenadaLeiserY;

    // Ajusta Coordenada do Propusor.
    public int ajustaCoordenadaPropusorX;
    public int ajustaCoordenadaPropusorY;

    /**
     * Inicializar Inimigo.
     *
     * @ Param xCoordinate Iniciando coordenada x helicóptero. @ Param
     * yCoordinate Iniciando coordenada y de helicóptero. @ Param
     * helicopterBodyImg Imagem do corpo de helicóptero. @ Param
     * helicopterFrontPropellerAnimImg Imagem de hélice de helicóptero frente. @
     * Param helicopterRearPropellerAnimImg Imagem de hélice de helicóptero
     * traseiro.
     */
    public void Inicializar(int xCoordenada, int yCoordenada) {
        // Inicializa Saúde.
        this.saude = saudeInicial;

        // Define a posição do inimigo.
        this.xCoordenada = xCoordenada;
        this.yCoordenada = yCoordenada;

        // Inicializar animação do Propusor (Referenciando tamanho da posição da
        // imagem, e suas respectivas coordenadas).
        animPropusorInimigo = new Efeito(imgPropusorInimigo, 30, 200, 17,
                0, true, xCoordenada + ajustaCoordenadaPropusorX, yCoordenada
                + ajustaCoordenadaPropusorY, 0);

        // Velocidade e direção do inimigo em movimento.
        InimigoUFO.movimentoVelocidadeX = -1;

        // Ajusta Cordenada do Leiser (De onde ele sairá da nave).
        this.ajustaCoordenadaLeiserX = 0;
        this.ajustaCoordenadaLeiserY = 0;

        // Ajusta Cordenada do Propusor (De onde ele sairá da nave).
        this.ajustaCoordenadaPropusorX = 20;
        this.ajustaCoordenadaPropusorY = 40;
    }

    /**
     * Define a velocidade eo tempo entre inimigos para as propriedades
     * iniciais.
     */
    public static void reiniciarInimigo() {
        InimigoUFO.tempoEntreNovosInimigos = tempoEntreNovosInimigosInicializar;
        InimigoUFO.tempoPassadoCriouInimigos = 0;
        InimigoUFO.movimentoVelocidadeX = movimentoVelocidadeInicialX;
    }

    /**
     * Aumenta a velocidade do inimigo e diminuir o tempo entre os novos
     * inimigos.
     */
    public static void speedUp() {
        if (InimigoUFO.tempoEntreNovosInimigos > Framework.secInNanosec)
            InimigoUFO.tempoEntreNovosInimigos -= Framework.secInNanosec / 100;

        InimigoUFO.movimentoVelocidadeX -= 0.15;
        InimigoUFO.movimentoVelocidadeY -= 0.15;
    }

    /**
     * Verifica se o inimigo saio da tela.
     *
     * @ Return true se o inimigo saio da tela, caso contrário, false.
     */
    public boolean isLeftScreen() {
        // Quando esta fora da tela.
        if (xCoordenada < 0 - imgInimigoUFO.getWidth())
            return true;
        else
            return false;
    }

    /**
     * Verifica disparo de LeiserInimigo. Verifica se pode dispara LeiserInimigo
     * (tempo entre Leiser).
     *
     * @param tempoJogo O tempo de jogo decorrido atual em nanosegundos.
     * @return true se estiver disparado Missil.
     */
    public boolean DispararLeiser(long tempoJogo) {
        // Verifica se é a hora de gerar novo LeiserInimigo.
        // Condição para disparar Leiser.
        if (tempoJogo - tempoPassadoCriouInimigos >= Framework.secInNanosec * 2
                && ((tempoJogo - LeiserInimigo.tempoUltimoLeiserCriado) >= LeiserInimigo.tempoEntreNovoLeiser)) {
            return true;
        } else
            return false;
    }

    /**
     * Atualizações posição do Inimigo, animações.
     */
    public void Atualizar() {
        // Move inimigo em x de coordenadas.
        xCoordenada += movimentoVelocidadeX;

        // Move Propusor do Inimigo.
        animPropusorInimigo.changeCoordinates(xCoordenada
                + ajustaCoordenadaPropusorX, yCoordenada
                + ajustaCoordenadaPropusorY);
    }

    /**
     * Desenha inimigo na tela.
     *
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d) {
        // Desenha Inimigo
        g2d.drawImage(imgInimigoUFO, xCoordenada, yCoordenada, null);

        // Desenha Amimação do PropusorInimigo
        animPropusorInimigo.Draw(g2d);
    }

}
