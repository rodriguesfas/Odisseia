package core;

import efeitoVisual.EfFundoMovel;
import gerenteImagens.GerenteImage;
import gerenteSom.Sons;
import jogoOdisseia.Game;
import recordes.Recorde;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Francisco Assis Souza Rodrigues
 */

/**
 * Framework que controla o jogo class Game.java, atualiza e Desenha na tela.
 */

public class Framework extends Canvas {

    private static final long serialVersionUID = 1L;

    /* Largura da janela */
    public static int frameLargura;

    /* Altura da janela */
    public static int frameAltura;

    /*
     * Tempo de um segundo em nanossegundos. 1 segundo = 1 000 000 000
     * nanossegundos
     */
    public static final long secInNanosec = 1000000000L;

    /*
     * Tempo de um milésimo de segundo em nanossegundos. 1 milissegundo = 1 000
     * 000 nanossegundos
     */
    public static final long milisecInNanosec = 1000000L;

    /*
     * FPS - Frames por segundo Quantas vezes por segundo, o jogo deve
     * atualizar?
     */
    private final int GAME_FPS = 60;

    /* Pausa entre as atualizações. É em nanossegundos */
    private final long PeriodoAtualizacaoJogo = secInNanosec / GAME_FPS;

    /* Possiveis estados do jogo */
    public static enum EstadoJogo {
        Partida, Visualizando, CarregandoConteudo, MenuPrincipal, Opcoes, Jogando, GAMEOVER, Destruido, Recordes, Creditos
    }

    // Estado atual do jogo (visível entre a thread do jogo e a EDT)
    public static volatile EstadoJogo estadoJogo;

    // Tempo de jogo decorrido em nanossegundos.
    private long tempoJogo;

    // É utilizado para o cálculo do tempo decorrido.
    private long tempoDecorrido;

    // Objeto da class Game
    private Game game;

    // Objeto Fonte
    private Font font;

    // Objetos Imagens ultilizadas no Menu.
    private BufferedImage imgTituloJogo;
    private BufferedImage imgTituloControle;
    private BufferedImage imgTituloRecordes;
    private BufferedImage imgTituloCreditos;
    private BufferedImage imgTituloOpcoes;
    private BufferedImage imgTituloGameOver;
    private BufferedImage imgBordaMenu;
    private BufferedImage imgFundoMenu;
    private BufferedImage imgFundoRecorde;
    private BufferedImage imgFundoGameOver;
    private BufferedImage imgFundoCreditos;
    private BufferedImage imgFundoOpcoes;
    private BufferedImage imgFundoCarregando;
    private BufferedImage imgCosmo1;
    private BufferedImage imgCosmo2;
    private BufferedImage imgPainelGameOver;
    private BufferedImage imgPontosGanhos;
    private BufferedImage imgPontosPerdidos;
    private BufferedImage imgPainelGameOver2;
    private BufferedImage imgRecordes;
    private BufferedImage imgEstrelas;

    // Objeto Imagem Teclado.
    private BufferedImage imgControleNave;
    private BufferedImage imgDisparaLeiser;
    private BufferedImage imgDisparaMissil;
    private BufferedImage imgBotao;

    // Objeto Menu Buttons
    private Rectangle botaoIniciarJogo;
    private Rectangle botaoCreditos;
    private Rectangle botaoOpcoes;
    private Rectangle botaoRecorde;
    private Rectangle botaoSair;
    private Rectangle botaoNovoJogo;
    private Rectangle botaoMenu;
    private Rectangle botaoMenu2;

    // Verifica se o botão está selecionado.
    boolean botaoIniciarJogoSelecionado;
    boolean botaoCreditosSelecionado;
    boolean botaoRecordeSelecionado;
    boolean botaoOpcoesSelecionado;
    boolean botaoSairSelecionado;
    boolean botaoNovoJogoSelecionado;
    boolean botaoMenuSelecionado;
    boolean botaoMenu2Selecionado;

    private boolean menuCarregado;
    private boolean opcoesCarregado;
    private boolean recordesCarregado;
    private boolean creditosCarregado;
    private boolean gameOverCarregado;
    private boolean controleCarregado;
    private boolean gameOverSomTocado;
    private EstadoJogo estadoAnteriorMusica;

    //
    private EfFundoMovel moverCosmo1;
    private EfFundoMovel moverCosmo2;
    private EfFundoMovel moverEstrela;

    // Objeto Imagem Painel Controle.
    private BufferedImage imgPainelControle;

    public Framework() {
        super();

        // Adiciona eventos do Mouse.
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseHandler());

        // Estado do Jogo (Visualizando)
        estadoJogo = EstadoJogo.Visualizando;

        // Inicia jogo no novo segmento. thread.
        Thread gameThread = new Thread() {
            @Override
            public void run() {
                GameLoop();
            }
        };
        gameThread.start();
    }

    /**
     * Definir variáveis ​​e objetos. Este método destina-se a definir as
     * variáveis e objetos para esta classe, variáveis ​​e objetos para o jogo
     * real pode ser definido na class Game.java.
     */
    private void Inicializar() {
        font = new Font("SansSerif", Font.BOLD, UiScale.su(22));

        botaoIniciarJogo = new Rectangle();
        botaoOpcoes = new Rectangle();
        botaoRecorde = new Rectangle();
        botaoCreditos = new Rectangle();
        botaoSair = new Rectangle();
        botaoMenu = new Rectangle();
        botaoMenu2 = new Rectangle();
        botaoNovoJogo = new Rectangle();
        atualizarLayout();

        // Instância Objetos Imagens em Movimento.
        moverCosmo1 = new EfFundoMovel();
        moverCosmo2 = new EfFundoMovel();
        moverEstrela = new EfFundoMovel();
    }

    /** Atualiza dimensões da janela e reposiciona botões/áreas clicáveis. */
    private void atualizarLayout() {
        int w = getWidth();
        int h = getHeight();
        if (w > 1) {
            frameLargura = w;
        }
        if (h > 1) {
            frameAltura = h;
        }
        if (frameLargura <= 1 || frameAltura <= 1) {
            return;
        }

        font = new Font("SansSerif", Font.BOLD, UiScale.su(22));

        int bw = UiScale.sw(250);
        int bh = UiScale.sh(38);
        int menuX = (frameLargura - bw) / 2;
        int menuY = UiScale.sy(350);
        int gap = UiScale.sh(34);

        if (botaoIniciarJogo != null) {
            botaoIniciarJogo.setBounds(menuX, menuY, bw, bh);
            botaoOpcoes.setBounds(menuX, menuY + gap, bw, bh);
            botaoCreditos.setBounds(menuX, menuY + gap * 2, bw, bh);
            botaoRecorde.setBounds(menuX, menuY + gap * 3, bw, bh);
            botaoSair.setBounds(menuX, menuY + gap * 4, bw, bh);

            int bottomY = frameAltura - UiScale.sh(80);
            botaoMenu.setBounds(menuX, bottomY, bw, bh);
            botaoMenu2.setBounds(menuX - bw / 2 - UiScale.sw(20), bottomY, bw, bh);
            botaoNovoJogo.setBounds(menuX + bw / 2 + UiScale.sw(20), bottomY, bw, bh);
        }
    }

    private void desenharFundo(Graphics2D g2d, BufferedImage img) {
        if (img == null) {
            return;
        }
        g2d.drawImage(img, 0, 0, frameLargura, frameAltura, null);
    }

    private void desenharImagemCentralizada(Graphics2D g2d, BufferedImage img, int designY) {
        if (img == null) {
            return;
        }
        int dw = UiScale.sw(img.getWidth());
        int dh = UiScale.sh(img.getHeight());
        int x = (frameLargura - dw) / 2;
        int y = UiScale.sy(designY);
        g2d.drawImage(img, x, y, dw, dh, null);
    }

    private void desenharImagem(Graphics2D g2d, BufferedImage img, int designX, int designY) {
        if (img == null) {
            return;
        }
        g2d.drawImage(img, UiScale.sx(designX), UiScale.sy(designY),
                UiScale.sw(img.getWidth()), UiScale.sh(img.getHeight()), null);
    }

    private void desenharImagem(Graphics2D g2d, BufferedImage img, int designX, int designY,
                                int designW, int designH) {
        if (img == null) {
            return;
        }
        g2d.drawImage(img, UiScale.sx(designX), UiScale.sy(designY),
                UiScale.sw(designW), UiScale.sh(designH), null);
    }

    private void desenharBotao(Graphics2D g2d, Rectangle botao, String texto, boolean selecionado) {
        if (botao == null || imgBotao == null) {
            return;
        }
        g2d.drawImage(imgBotao, botao.x, botao.y, botao.width, botao.height, null);
        Color overlay = selecionado ? new Color(40, 180, 40, 140) : new Color(255, 140, 0, 110);
        g2d.setColor(overlay);
        g2d.fillRoundRect(botao.x + 4, botao.y + 4, botao.width - 8, botao.height - 8, 8, 8);
        g2d.setFont(new Font("SansSerif", Font.BOLD, UiScale.su(18)));
        g2d.setColor(Color.WHITE);
        FontMetrics fm = g2d.getFontMetrics();
        int tx = botao.x + (botao.width - fm.stringWidth(texto)) / 2;
        int ty = botao.y + (botao.height + fm.getAscent() - fm.getDescent()) / 2;
        g2d.drawString(texto, tx, ty);
    }

    private static boolean contem(Rectangle r, int x, int y) {
        return r != null && r.contains(x, y);
    }

    private void atualizarMusicaPorEstado() {
        if (estadoJogo == estadoAnteriorMusica) {
            return;
        }
        estadoAnteriorMusica = estadoJogo;
        switch (estadoJogo) {
            case MenuPrincipal:
            case Opcoes:
            case Recordes:
            case Creditos:
                Sons.musicaMenu();
                gameOverSomTocado = false;
                break;
            case Jogando:
                Sons.musicaJogo();
                gameOverSomTocado = false;
                break;
            case GAMEOVER:
                Sons.pararMusica();
                if (!gameOverSomTocado) {
                    Sons.gameOver();
                    gameOverSomTocado = true;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Carrega os arquivos (imagens, sons e etc).
     */
    private void CarregarComponentesTelaMenuPrincipal() {
        // Tratamento de Erros
        try {
            // Carregar Imagem Borda do Menu Principal.
            imgBordaMenu = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/menuBorda.png");

            // Carregar Imagem Fundo do Menu Principal.
            imgFundoMenu = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/menu.jpg");

            // Carregar Imagem Titulo do Menu Principal.
            imgTituloJogo = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/tituloOdisseia.png");

            // Carregar Imagem Cosmo1.
            imgCosmo1 = GerenteImage.getInstance().loadImage(
                    "images/cosmos/cosmo2.png");

            // Carregar Imagem Cosmo2.
            imgCosmo2 = GerenteImage.getInstance().loadImage(
                    "images/cosmos/cosmo4.png");

            // Carrega Imagem Botão;
            imgBotao = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/botao.png");

        } catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        moverCosmo1.Inicializar(imgCosmo2, 1, 0);
        moverCosmo2.Inicializar(imgCosmo1, -2, 0);
    }

    /**
     * Carrega os arquivos (imagens, sons e etc).
     */
    private void CarregarComponentesTelaCreditos() {
        try {
            // Carregar Imagem Borda.
            imgBordaMenu = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/menuBorda.png");

            // Carregar Imagem Fundo Creditos.
            imgFundoCreditos = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/fundoCreditos.jpg");

            // Carrega Imagem Estrela.
            imgEstrelas = GerenteImage.getInstance().loadImage(
                    "images/estrelas/estrelas.png");

            // Carrega Imagem Botão;
            imgBotao = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/botao.png");

        } catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        moverEstrela.Inicializar(imgEstrelas, 0, 0);
    }

    /**
     * Carrega os arquivos (imagens, sons e etc).
     */
    private void CarregarComponentesTelaRecordes() {
        try {
            // Carrega Imagem Fundo Recorde;
            imgFundoRecorde = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/fundoRecordes.png");

            // Carregar Imagem Borda.
            imgBordaMenu = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/menuBorda.png");

            // Carrega Imagem Titulo Recordes.
            imgTituloRecordes = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/tituloRecordes.png");

            // Carrega Imagem Painel Recodes.
            imgRecordes = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/recordes.png");

            // Carrega Imagem Botão;
            imgBotao = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/botao.png");

        } catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    /**
     * Carrega os arquivos (imagens, sons e etc).
     */
    private void CarregarComponentesTelaGameOver() {
        try {

            // Carregar Imagem Titulo GameOver.
            imgTituloGameOver = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/tituloGameOver.png");

            imgFundoGameOver = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/fundoGameOver.jpg");

            // Carregar Imagem Painel GameOver.
            imgPainelGameOver = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/painelGameOver.png");

            // Carregar Imagem Painel GameOver2.
            imgPainelGameOver2 = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/painelGameOver2.png");

            // Carregar Imagem Pontos Ganhos.
            imgPontosGanhos = GerenteImage.getInstance().loadImage(
                    "images/painel/pontosGanhos.png");

            // Carrega Imagem Pontos Perdidos.
            imgPontosPerdidos = GerenteImage.getInstance().loadImage(
                    "images/painel/pontosPerdidos.png");

            // Carrega Imagem Botão;
            imgBotao = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/botao.png");

        } catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    /**
     * Carrega os arquivos (imagens, sons e etc).
     */
    private void CarregarComponentesTelaOpcoes() {
        try {
            // Carrega Imagem Fundo Opções;
            imgFundoOpcoes = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/fundoOpcoes.jpg");

            // Carrega Imagem Borda;
            imgBordaMenu = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/menuBorda.png");

            // Carrega Imagem Botão;
            imgBotao = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/botao.png");

        } catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    /**
     * Carrega os arquivos (imagens, sons e etc).
     */
    private void CarregarComponentesTelaControle() {
        try {
            // Carrega Imagem Fundo Opções.
            imgFundoCarregando = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/fundoCarregando.jpg");

            // Carrega Imagem Borda.
            imgBordaMenu = GerenteImage.getInstance().loadImage(
                    "images/menuPrincipal/menuBorda.png");

            // Carrega Imagem Teclas movimenta nave.
            imgControleNave = GerenteImage.getInstance().loadImage(
                    "images/teclado/controleNave.png");

            // Carrega Imagem Painel.
            imgPainelControle = GerenteImage.getInstance().loadImage(
                    "images/teclado/painelControle.png");

            // Carrega Imagem tecla dispara leiser.
            imgDisparaLeiser = GerenteImage.getInstance().loadImage(
                    "images/teclado/disparaLeiser.png");

            // Carrega Imagem tecla dispara missil.
            imgDisparaMissil = GerenteImage.getInstance().loadImage(
                    "images/teclado/disparaMissil.png");

        } catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    /**
     * Nos intervalos específicos de tempo (Atualização de Jogo por Periodo) o
     * jogo / lógica é Atualizado e, em seguida, a partida está na tela.
     */
    private void GameLoop() {
        // Estas duas variáveis ​​são usadas no estado do jogo visualizando.

        // Ulltilizadas para esperar algum tempo para corrigir frame / janela
        // Resolução.
        long tempoVisualizando = 0, tempoVisualizacaoAnterior = System
                .nanoTime();

        // Estas variáveis ​​são utilizadas para o cálculo do tempo que define
        // por quanto tempo deve-se colocar para dormir para atender a GAME_FPS.
        long comercaTempo, tempoNecessario, tempoEspera;

        while (true) {
            comercaTempo = System.nanoTime();
            atualizarLayout();
            atualizarMusicaPorEstado();

            switch (estadoJogo) {

                case Jogando: {
                    long agora = System.nanoTime();
                    long frameDelta = agora - tempoDecorrido;
                    Tempo.setDeltaNanos(frameDelta);
                    tempoJogo += frameDelta;
                    game.AtualizaJogo(tempoJogo);
                    tempoDecorrido = agora;
                    break;
                }

                case GAMEOVER:
                    if (!gameOverCarregado) {
                        CarregarComponentesTelaGameOver();
                        gameOverCarregado = true;
                    }
                    break;

                case MenuPrincipal:
                    if (!menuCarregado) {
                        CarregarComponentesTelaMenuPrincipal();
                        menuCarregado = true;
                    }
                    break;

                case Opcoes:
                    if (!opcoesCarregado) {
                        CarregarComponentesTelaOpcoes();
                        opcoesCarregado = true;
                    }
                    break;

                case CarregandoConteudo:
                    if (!controleCarregado) {
                        CarregarComponentesTelaControle();
                        controleCarregado = true;
                    }
                    break;

                case Partida:
                    Inicializar();
                    CarregarComponentesTelaMenuPrincipal();
                    menuCarregado = true;
                    estadoJogo = EstadoJogo.MenuPrincipal;
                    break;

                case Visualizando:
                    if (this.getWidth() > 1 && tempoVisualizando > secInNanosec) {
                        frameLargura = this.getWidth();
                        frameAltura = this.getHeight();
                        estadoJogo = EstadoJogo.Partida;
                    } else {
                        tempoVisualizando += System.nanoTime()
                                - tempoVisualizacaoAnterior;
                        tempoVisualizacaoAnterior = System.nanoTime();
                    }
                    break;

                case Recordes:
                    if (!recordesCarregado) {
                        CarregarComponentesTelaRecordes();
                        recordesCarregado = true;
                    }
                    break;

                case Creditos:
                    if (!creditosCarregado) {
                        CarregarComponentesTelaCreditos();
                        creditosCarregado = true;
                    }
                    break;
            }

            // Redesenhar a tela.
            repaint();

            // Aqui Calcula o tempo em que define por quanto tempo deve-se
            // Colocar para dormir, para atender a GAME_FPS.
            tempoNecessario = System.nanoTime() - comercaTempo;

            // em milissegundos
            tempoEspera = (PeriodoAtualizacaoJogo - tempoNecessario)
                    / milisecInNanosec;

            // Se o tempo é menor de 10 milissegundos, então coloca colocar fio
            // Para dormir por 10 milissegundos para que alguma outra thread
            // possa fazer algum Trabalho.
            if (tempoEspera < 10)
                // definir o mínimo
                tempoEspera = 10;
            try {
                // Fornece o atraso necessário e também produz controle para que
                // Outra thread pode fazer o trabalho.
                Thread.sleep(tempoEspera);
            } catch (InterruptedException ex) {
            }
        }
    }

    /**
     * Desenhar o jogo na tela. Ele é chamado por meio de método repaint () no
     * Método gameLoop ().
     */
    @Override
    public void Draw(Graphics2D g2d) {
        switch (estadoJogo) {

            case Jogando:
                // Desenha Tela Jogo.
                game.Draw(g2d, tempoJogo);
                break;

            case GAMEOVER:
                // Desenha Tela GameOver.
                drawTelaGameOver(g2d);
                break;

            case MenuPrincipal:
                // Desenha Tela Menu Principal
                drawTelaMenuPrincipal(g2d);
                break;

            case Opcoes:
                // Desenha Tela Opções
                drawTelaOpcoes(g2d);
                break;

            case CarregandoConteudo:
                // Desenha Tela Carregando Conteudo.
                drawTelaCarregandoConteudo(g2d);
                break;

            case Recordes:
                // Desenha Tela Recordes.
                drawTelaRecorde(g2d);
                break;

            case Creditos:
                // Desenha Tela Créditos.
                drawTelaCreditos(g2d);
                break;
        }
    }

    /**
     * Inicia novo jogo.
     */
    private void novoJogo() {
        tempoJogo = 0;
        tempoDecorrido = System.nanoTime();
        gameOverCarregado = false;
        gameOverSomTocado = false;
        game = new Game();
    }

    /**
     * Reinicie jogo - redefinir tempo de jogo e chamar ReiniciaJogo() método de
     * da classe Game.java, objeto para que redefinir algumas variáveis​​.
     */
    private void ReiniciarJogo() {
        // Redefine tempoJogo, tempoDecorrido
        tempoJogo = 0;
        tempoDecorrido = System.nanoTime();

        // Reinstância a class (Game.java)
        game.ReiniciarJogo();

        // Redefine o estado do jogo para que possa começar.
        estadoJogo = EstadoJogo.Jogando;
    }

    private void drawTelaMenuPrincipal(Graphics2D g2d) {
        desenharFundo(g2d, imgFundoMenu);
        desenharFundo(g2d, imgBordaMenu);
        desenharImagemCentralizada(g2d, imgTituloJogo, 70);
        if (moverCosmo2 != null) {
            moverCosmo2.Draw(g2d);
        }

        desenharBotao(g2d, botaoIniciarJogo, "Iniciar Jogo", botaoIniciarJogoSelecionado);
        desenharBotao(g2d, botaoOpcoes, "Opções", botaoOpcoesSelecionado);
        desenharBotao(g2d, botaoCreditos, "Créditos", botaoCreditosSelecionado);
        desenharBotao(g2d, botaoRecorde, "Recordes", botaoRecordeSelecionado);
        desenharBotao(g2d, botaoSair, "Sair", botaoSairSelecionado);

        if (moverCosmo1 != null) {
            moverCosmo1.Draw(g2d);
        }
    }

    private void drawTelaOpcoes(Graphics2D g2d) {
        desenharFundo(g2d, imgFundoOpcoes);
        desenharFundo(g2d, imgBordaMenu);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        String titulo = "OPÇÕES";
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(titulo, (frameLargura - fm.stringWidth(titulo)) / 2, UiScale.sy(100));
        g2d.setFont(new Font("SansSerif", Font.PLAIN, UiScale.su(18)));
        g2d.drawString("ESC — voltar ao menu", UiScale.sx(480), UiScale.sy(280));
        g2d.drawString("Som: efeitos e música ativos", UiScale.sx(450), UiScale.sy(330));
        desenharBotao(g2d, botaoMenu, "Menu", botaoMenuSelecionado);
        if (moverCosmo1 != null) {
            moverCosmo1.Draw(g2d);
        }
    }

    private void drawTelaRecorde(Graphics2D g2d) {
        desenharFundo(g2d, imgFundoRecorde);
        desenharFundo(g2d, imgBordaMenu);
        desenharImagemCentralizada(g2d, imgTituloRecordes, 50);
        desenharImagem(g2d, imgRecordes, 360, 120);

        Recorde recorde = new Recorde();
        recorde.exibirRecordes();

        g2d.setFont(new Font("SansSerif", Font.BOLD, UiScale.su(22)));
        g2d.setColor(Color.BLACK);
        g2d.drawString("PONTUAÇÃO", UiScale.sx(450), UiScale.sy(180));
        g2d.drawString("CLASSIFICAÇÃO", UiScale.sx(680), UiScale.sy(180));

        g2d.setFont(new Font("SansSerif", Font.BOLD, UiScale.su(48)));
        g2d.setColor(Color.WHITE);
        g2d.drawString(recorde.getPrimeiroRecorde(), UiScale.sx(450), UiScale.sy(250));
        g2d.drawString(recorde.getSegundoRecorde(), UiScale.sx(450), UiScale.sy(330));
        g2d.drawString(recorde.getTerceiroRecorde(), UiScale.sx(450), UiScale.sy(420));
        g2d.drawString(recorde.getNaoClassificou(), UiScale.sx(450), UiScale.sy(520));

        desenharBotao(g2d, botaoMenu, "Menu", botaoMenuSelecionado);
        if (moverCosmo1 != null) {
            moverCosmo1.Draw(g2d);
        }
    }

    private void drawTelaCarregandoConteudo(Graphics2D g2d) {
        desenharFundo(g2d, imgFundoCarregando);
        desenharFundo(g2d, imgBordaMenu);
        desenharImagemCentralizada(g2d, imgTituloControle, 60);

        g2d.setFont(new Font("SansSerif", Font.BOLD, UiScale.su(24)));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Carregando o Jogo...", UiScale.sx(100), UiScale.sy(700));

        int iconW = UiScale.sw(180);
        int iconH = UiScale.sh(90);
        if (imgControleNave != null) {
            g2d.drawImage(imgControleNave, UiScale.sx(120), UiScale.sy(220), iconW, iconH, null);
        }
        g2d.drawString("Movimentar Nave (setas / WASD)", UiScale.sx(330), UiScale.sy(270));

        if (imgDisparaLeiser != null) {
            g2d.drawImage(imgDisparaLeiser, UiScale.sx(120), UiScale.sy(340), iconW, iconH, null);
        }
        g2d.drawString("Disparar Laser (Espaço)", UiScale.sx(330), UiScale.sy(390));

        if (imgDisparaMissil != null) {
            g2d.drawImage(imgDisparaMissil, UiScale.sx(120), UiScale.sy(460), iconW, iconH, null);
        }
        g2d.drawString("Disparar Mísseis (X / Ctrl, com power-up)", UiScale.sx(330), UiScale.sy(510));
    }

    private void drawTelaCreditos(Graphics2D g2d) {
        desenharFundo(g2d, imgFundoCreditos);
        desenharFundo(g2d, imgBordaMenu);
        desenharImagemCentralizada(g2d, imgTituloCreditos, 50);
        if (moverEstrela != null) {
            moverEstrela.Draw(g2d);
        }
        if (moverCosmo1 != null) {
            moverCosmo1.Draw(g2d);
        }

        g2d.setFont(new Font("SansSerif", Font.BOLD, UiScale.su(22)));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Francisco Assis Souza Rodrigues", UiScale.sx(400), UiScale.sy(280));
        g2d.drawString("Versão BETA — Fevereiro/2014", UiScale.sx(430), UiScale.sy(330));
        g2d.setFont(new Font("SansSerif", Font.PLAIN, UiScale.su(16)));
        g2d.drawString("www.clubedosgeeks.com.br", UiScale.sx(20), frameAltura - UiScale.sh(20));

        desenharBotao(g2d, botaoMenu, "Menu", botaoMenuSelecionado);
    }

    private void drawTelaGameOver(Graphics2D g2d) {
        desenharFundo(g2d, imgFundoGameOver);
        desenharImagemCentralizada(g2d, imgTituloGameOver, 30);
        desenharImagem(g2d, imgPainelGameOver, 320, 100, 640, 560);

        desenharBotao(g2d, botaoMenu2, "Menu", botaoMenu2Selecionado);
        desenharBotao(g2d, botaoNovoJogo, "Novo Jogo", botaoNovoJogoSelecionado);

        if (game != null) {
            game.DrawEstatisticas(g2d, tempoJogo);
        }
        g2d.setFont(font);
    }

    /**
     * Este método é chamado quando a tecla do teclado é solta.
     *
     * @param e
     *            KeyEvent
     */
    @Override
    public void keyReleasedFramework(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (estadoJogo == EstadoJogo.Jogando || estadoJogo == EstadoJogo.GAMEOVER) {
                Sons.pararMusica();
                estadoJogo = EstadoJogo.MenuPrincipal;
            } else if (estadoJogo != EstadoJogo.MenuPrincipal) {
                estadoJogo = EstadoJogo.MenuPrincipal;
            }
        }
    }

    /**
     *
     * Class Interna MouserHandler.
     *
     */
    public class MouseHandler extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            switch (estadoJogo) {
                case MenuPrincipal:
                    botaoIniciarJogoSelecionado = contem(botaoIniciarJogo, x, y);
                    botaoOpcoesSelecionado = contem(botaoOpcoes, x, y);
                    botaoCreditosSelecionado = contem(botaoCreditos, x, y);
                    botaoRecordeSelecionado = contem(botaoRecorde, x, y);
                    botaoSairSelecionado = contem(botaoSair, x, y);
                    break;
                case Opcoes:
                case Recordes:
                case Creditos:
                    botaoMenuSelecionado = contem(botaoMenu, x, y);
                    break;
                case GAMEOVER:
                    botaoMenu2Selecionado = contem(botaoMenu2, x, y);
                    botaoNovoJogoSelecionado = contem(botaoNovoJogo, x, y);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            switch (estadoJogo) {
                case MenuPrincipal:
                    if (contem(botaoIniciarJogo, x, y)) {
                        Sons.menuClick();
                        novoJogo();
                    } else if (contem(botaoOpcoes, x, y)) {
                        Sons.menuClick();
                        estadoJogo = EstadoJogo.Opcoes;
                    } else if (contem(botaoCreditos, x, y)) {
                        Sons.menuClick();
                        estadoJogo = EstadoJogo.Creditos;
                    } else if (contem(botaoRecorde, x, y)) {
                        Sons.menuClick();
                        estadoJogo = EstadoJogo.Recordes;
                    } else if (contem(botaoSair, x, y)) {
                        Sons.menuClick();
                        System.exit(0);
                    }
                    break;
                case Opcoes:
                case Creditos:
                case Recordes:
                    if (contem(botaoMenu, x, y)) {
                        Sons.menuClick();
                        estadoJogo = EstadoJogo.MenuPrincipal;
                    }
                    break;
                case GAMEOVER:
                    if (contem(botaoMenu2, x, y)) {
                        Sons.menuClick();
                        estadoJogo = EstadoJogo.MenuPrincipal;
                    } else if (contem(botaoNovoJogo, x, y)) {
                        Sons.menuClick();
                        ReiniciarJogo();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
