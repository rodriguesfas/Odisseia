package core;

import efeitoVisual.EfFundoMovel;
import gerenteImagens.GerenteImage;
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

    // Estado atual do jogo
    public static EstadoJogo estadoJogo;

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
        font = new Font("Arial", Font.BOLD, 24);

        // Instância os objetos Rectangle (Botões) do Menu Principal.
        // Coordenada_X, Coordenada_Y, Largura, Altura.
        botaoIniciarJogo = new Rectangle(550, 350, 150, 25);
        botaoOpcoes = new Rectangle(550, 380, 150, 25);
        botaoRecorde = new Rectangle(550, 410, 150, 25);
        botaoCreditos = new Rectangle(550, 440, 150, 25);
        botaoSair = new Rectangle(550, 470, 150, 25);
        botaoMenu = new Rectangle(550, 700, 150, 25);
        botaoMenu2 = new Rectangle(450, 700, 150, 25);
        botaoNovoJogo = new Rectangle(700, 700, 150, 25);

        // Instância Objetos Imagens em Movimento.
        moverCosmo1 = new EfFundoMovel();
        moverCosmo2 = new EfFundoMovel();
        moverEstrela = new EfFundoMovel();
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
            switch (estadoJogo) {

                case Jogando:
                    tempoJogo += System.nanoTime() - tempoDecorrido;
                    game.AtualizaJogo(tempoJogo);
                    tempoDecorrido = System.nanoTime();
                    break;

                case GAMEOVER:
                    // Carrega os Componentes da Tela GameOver.
                    CarregarComponentesTelaGameOver();
                    // ...
                    break;

                case MenuPrincipal:
                    // ...
                    break;

                case Opcoes:
                    // Carrega os Componentes da Tela Opções.
                    CarregarComponentesTelaOpcoes();
                    // ...
                    break;

                case CarregandoConteudo:
                    // Carrega os Componetes da Tela Carregando Conteudo.
                    CarregarComponentesTelaControle();
                    // ...
                    break;

                case Partida:
                    // Define as variáveis ​​e objetos.
                    Inicializar();

                    // Carrega os Componentes da Tela do Menu Principal.
                    CarregarComponentesTelaMenuPrincipal();

                    // Quando os métodos chamados acima estiverem carregados, muda o
                    // estado do jogo para menuPrincipal.
                    estadoJogo = EstadoJogo.MenuPrincipal;
                    break;

                case Visualizando:
                    if (this.getWidth() > 1 && tempoVisualizando > secInNanosec) {
                        frameLargura = this.getWidth();
                        frameAltura = this.getHeight();

                        // Quando atingir tamanho da janela, mudar o estadoJogo.
                        estadoJogo = EstadoJogo.Partida;
                    } else {
                        tempoVisualizando += System.nanoTime()
                                - tempoVisualizacaoAnterior;
                        tempoVisualizacaoAnterior = System.nanoTime();
                    }
                    break;

                case Recordes:
                    // Carrega os componentes da Tela Recordes.
                    CarregarComponentesTelaRecordes();
                    // ...
                    break;

                case Creditos:
                    // Carrega os Componentes da Tela Creditos.
                    CarregarComponentesTelaCreditos();
                    // ...
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
        // Define tempoJogo, tempoDecorrido.
        tempoJogo = 0;
        tempoDecorrido = System.nanoTime();

        // Intância o objeto da class (Game.java)
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
        // Desenha Imagem do Fundo Menu Principal.
        g2d.drawImage(imgFundoMenu, 0, 0, Framework.frameLargura,
                Framework.frameAltura, null);

        // Desenha Imagem Borda do Menu
        g2d.drawImage(imgBordaMenu, 0, 0, Framework.frameLargura,
                Framework.frameAltura, null);

        // Desenha Titulo do Jogo.
        g2d.drawImage(imgTituloJogo, 250, 100, null);

        // Imagem em Movimento.
        // Desenha o Cosmo na frente.
        moverCosmo2.Draw(g2d);

        // Desenha Tipo de Fonte, tamanho, estilo, que será ultilisada nos
        // Botões Do Menu Principal.
        g2d.setFont(new Font("Arial", Font.BOLD, 18));

		/* Desenha Botão Iniciar Jogo. */
        g2d.drawImage(imgBotao, 498, 344, null);
        // Condição para mudar de cor.
        if (!botaoIniciarJogoSelecionado)
            g2d.setColor(Color.ORANGE);
        else
            g2d.setColor(Color.GREEN);
        g2d.fillRect(botaoIniciarJogo.x, botaoIniciarJogo.y,
                botaoIniciarJogo.width, botaoIniciarJogo.height);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Iniciar Jogo", botaoIniciarJogo.x + 20,
                botaoIniciarJogo.y + 17);

		/* Desenha Botão Opções. */
        g2d.drawImage(imgBotao, 498, 374, null);
        // Condição para mudar de cor.
        if (!botaoOpcoesSelecionado)
            g2d.setColor(Color.ORANGE);
        else
            g2d.setColor(Color.GREEN);
        g2d.fillRect(botaoOpcoes.x, botaoOpcoes.y, botaoOpcoes.width,
                botaoOpcoes.height);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Opções", botaoOpcoes.x + 20, botaoOpcoes.y + 17);

		/* Desenha Botão Creditos. */
        g2d.drawImage(imgBotao, 498, 404, null);
        // Condição para mudar de cor
        if (!botaoCreditosSelecionado)
            g2d.setColor(Color.ORANGE);
        else
            g2d.setColor(Color.GREEN);
        g2d.fillRect(botaoCreditos.x, botaoCreditos.y, botaoCreditos.width,
                botaoCreditos.height);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Créditos", botaoCreditos.x + 20, botaoCreditos.y + 17);

		/* Desenha Botão Recorde. */
        g2d.drawImage(imgBotao, 498, 434, null);
        // Condição para mudar de cor
        if (!botaoRecordeSelecionado)
            g2d.setColor(Color.ORANGE);
        else
            g2d.setColor(Color.GREEN);
        g2d.fillRect(botaoRecorde.x, botaoRecorde.y, botaoRecorde.width,
                botaoRecorde.height);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Recordes", botaoRecorde.x + 20, botaoRecorde.y + 17);

		/* Desenha Botão Sair */
        g2d.drawImage(imgBotao, 498, 464, null);
        // Condição para mudar de cor
        if (!botaoSairSelecionado)
            g2d.setColor(Color.ORANGE);
        else
            g2d.setColor(Color.GREEN);
        g2d.fillRect(botaoSair.x, botaoSair.y, botaoSair.width,
                botaoSair.height);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Sair", botaoSair.x + 20, botaoSair.y + 17);

        // Imagem em Movimento.
        // Desenha o Cosmo na frente.
        moverCosmo1.Draw(g2d);
    }

    /**/
    private void drawTelaOpcoes(Graphics2D g2d) {
        // Desenha Cor da Fonte
        g2d.setFont(font);

        // Desenha Imagem do Fundo Opções.
        g2d.drawImage(imgFundoOpcoes, 0, 0, Framework.frameLargura,
                Framework.frameAltura, null);

        // Desenha Imagem da Borda.
        g2d.drawImage(imgBordaMenu, 0, 0, Framework.frameLargura,
                Framework.frameAltura, null);

        // Desenha Título Créditos.
        g2d.drawImage(imgTituloCreditos, 450, 60, null);

		/* Desenha Botão Retornar ao Menu. */
        g2d.drawImage(imgBotao, 498, 694, null);

        // Condição para mudar de cor do Botão.
        if (!botaoMenuSelecionado)
            g2d.setColor(Color.ORANGE);
        else
            g2d.setColor(Color.GREEN);
        g2d.fillRect(botaoMenu.x, botaoMenu.y, botaoMenu.width,
                botaoMenu.height);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Menu", botaoMenu.x + 20, botaoMenu.y + 17);

        // Imagem em Movimento.
        // Desenha o Cosmo na frente.
        moverCosmo1.Draw(g2d);
    }

    /**/
    private void drawTelaRecorde(Graphics2D g2d) {
        // Desenha Cor da Fonte
        g2d.setFont(font);

        // Desenha Imagem do Fundo.
        g2d.drawImage(imgFundoRecorde, 0, 0, Framework.frameLargura,
                Framework.frameAltura, null);

        // Desenha Imgem do Cosmo.
        g2d.drawImage(imgCosmo1, 0, 0, Framework.frameLargura,
                Framework.frameAltura, null);

        // Desenha Imagem do Cosmo2.
        g2d.drawImage(imgCosmo2, 0, 0, Framework.frameLargura,
                Framework.frameAltura, null);

        // Desenha Imagem Borda do Menu
        g2d.drawImage(imgBordaMenu, 0, 0, Framework.frameLargura,
                Framework.frameAltura, null);

        // Desenha Fundo Estrelas.
        g2d.drawImage(imgEstrelas, 0, 0, null);

        // Desenha Título Recodes.
        g2d.drawImage(imgTituloRecordes, 450, 60, null);

        // Desenha Painel Recordes.
        g2d.drawImage(imgRecordes, 400, 120, null);

		/* Desenha Botão Retornar ao Menu. */
        g2d.drawImage(imgBotao, 498, 694, null);
        // Condição para mudar de cor.
        if (!botaoMenuSelecionado)
            g2d.setColor(Color.ORANGE);
        else
            g2d.setColor(Color.GREEN);
        g2d.fillRect(botaoMenu.x, botaoMenu.y, botaoMenu.width,
                botaoMenu.height);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Menu", botaoMenu.x + 20, botaoMenu.y + 17);

        /****** RECORDES ******/
        // Instancia de um objeto recorde da Class Recorde.java
        Recorde recorde = new Recorde();

        // Chamada do Metodo que Exibe Recorde Armazenados (Salvos).
        recorde.exibirRecordes();

        // Definição da Fonte.
        g2d.setFont(new Font("Arial", Font.BOLD, 22));

        // Definição Cor da Fonte.
        g2d.setColor(Color.BLACK);

        // Subtitulos.
        g2d.drawString("PONTUAÇÃO", 450, 180);
        g2d.drawString("CLASSIFICAÇÃO", 680, 180);

        // Definição da Fonte.
        g2d.setFont(new Font("Arial", Font.BOLD, 56));

        // Definição Cor da Fonte.
        g2d.setColor(Color.WHITE);

        // Desenha Valores dos Recordes.
        g2d.drawString(recorde.getPrimeiroRecorde(), 450, 250);
        g2d.drawString(recorde.getSegundoRecorde(), 450, 330);
        g2d.drawString(recorde.getTerceiroRecorde(), 450, 420);
        g2d.drawString(recorde.getNaoClassificou(), 450, 520);

        // Imagem em Movimento.
        // Desenha o Cosmo na frente.
        moverCosmo1.Draw(g2d);
    }

    /**/
    private void drawTelaCarregandoConteudo(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 24));

        // Desenha Cor da Fonte.
        g2d.setColor(Color.WHITE);

        // Desenha imagem de fundo.
        g2d.drawImage(imgFundoCarregando, 0, 0, null);

        // Desenha imagem Titulo Controle.
        g2d.drawImage(imgTituloControle, 200, 120, null);

        // Desenha Painel Controle.
        g2d.drawImage(imgPainelControle, 50, 50, null);

        // Menssagem Carregando Jogo.
        g2d.drawString("Carregando o Jogo..", 100, 600);

        // Imagem e Definição da Tecla Controla Nave.
        g2d.drawImage(imgControleNave, 100, 200, Framework.frameLargura - 1150,
                Framework.frameAltura - 650, null);
        g2d.drawString("Movimentar Nave.", frameLargura / 2 - 350,
                frameAltura / 2 - 120);

        // Imagem e Definição da Tecla Dispara Leiser.
        g2d.drawImage(imgDisparaLeiser, 100, 320,
                Framework.frameLargura - 1000, Framework.frameAltura - 700,
                null);
        g2d.drawString("Dispara Laiser", frameLargura / 2 - 230,
                frameAltura / 2 - 30);

        // Imagem e Definição da Tecla Dispara Missil.
        g2d.drawImage(imgDisparaMissil, 100, 400,
                Framework.frameLargura - 1250, Framework.frameAltura - 700,
                null);
        g2d.drawString("Dispara Misseis", frameLargura - 1130,
                frameAltura - 320);
    }

    /**/
    private void drawTelaCreditos(Graphics2D g2d) {
        // Desenha Cor da Fonte
        g2d.setFont(font);

        // Desenha Imagem de Fundo Crédotos.
        g2d.drawImage(imgFundoCreditos, 0, 0, Framework.frameLargura,
                Framework.frameAltura, null);

        // Desenha Imagem Borda
        g2d.drawImage(imgBordaMenu, 0, 0, Framework.frameLargura,
                Framework.frameAltura, null);

        // Desenha Título Créditos.
        g2d.drawImage(imgTituloCreditos, 450, 60, null);

        // Imagem em Movimento.
        // Desenha o Cosmo na frente.
        moverEstrela.Draw(g2d);
        moverCosmo1.Draw(g2d);

		/* Desenha Botão Retornar ao Menu. */
        g2d.drawImage(imgBotao, 498, 694, null);

        // Condição para mudar de cor do Botão.
        if (!botaoMenuSelecionado)
            g2d.setColor(Color.ORANGE);
        else
            g2d.setColor(Color.GREEN);
        g2d.fillRect(botaoMenu.x, botaoMenu.y, botaoMenu.width,
                botaoMenu.height);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Menu", botaoMenu.x + 20, botaoMenu.y + 17);

        // Desenha URL do site.
        g2d.drawString("www.clubedosgeeks.com.br", 7, frameAltura - 5);
    }

    /**/
    private void drawTelaGameOver(Graphics2D g2d) {
        // Desenha Cor da Fonte
        g2d.setColor(Color.BLACK);

        // Desenha Imagem de Fundo GAMEOVER.
        g2d.drawImage(imgFundoGameOver, 0, 0, null);

        // Desenha Titulo GameOver
        g2d.drawImage(imgTituloGameOver, 400, 40, null);

        // Desenha Painel GameOver2
        g2d.drawImage(imgPainelGameOver2, 400, 150, null);

        // Desenha Painel GameOver
        g2d.drawImage(imgPainelGameOver, 330, 100, 640, 600, null);

        // Desenha Tipo de Fonte, tamanho, estilo, que será ultilisada nos
        // Botões Do Menu Principal.
        g2d.setFont(new Font("Arial", Font.BOLD, 18));

		/* Desenha Botão Novo Jogo */
        g2d.drawImage(imgBotao, 648, 694, null);

        // Condição para mudar de cor do Botão.
        if (!botaoNovoJogoSelecionado)
            g2d.setColor(Color.ORANGE);
        else
            g2d.setColor(Color.GREEN);
        g2d.fillRect(botaoNovoJogo.x, botaoNovoJogo.y, botaoNovoJogo.width,
                botaoNovoJogo.height);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Novo Jogo", botaoNovoJogo.x + 20, botaoNovoJogo.y + 17);

		/* Desenha Botão Menu Principal */
        g2d.drawImage(imgBotao, 398, 694, null);

        // Condição para mudar de cor do Botão.
        if (!botaoMenu2Selecionado)
            g2d.setColor(Color.ORANGE);
        else
            g2d.setColor(Color.GREEN);
        g2d.fillRect(botaoMenu2.x, botaoMenu2.y, botaoMenu2.width,
                botaoMenu2.height);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Menu", botaoMenu2.x + 20, botaoMenu2.y + 17);

        // Desenha Estatistica da Class Game.java.
        game.DrawEstatisticas(g2d, tempoJogo);
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
            // Volta para o Menu Principal
            estadoJogo = EstadoJogo.Visualizando;
        }
    }

    /**
     *
     * Class Interna MouserHandler.
     *
     */
    public class MouseHandler extends MouseAdapter {
        @Override
        // Quando Move o Cusor do Mouse.
        public void mouseMoved(MouseEvent e) {
            int mouserX = e.getX();
            int mouserY = e.getY();

            switch (estadoJogo) {
                case MenuPrincipal:
                    // Botão Iniciar Jogo.
                    if (mouserX > botaoIniciarJogo.x
                            && mouserX < botaoIniciarJogo.x
                            + botaoIniciarJogo.width
                            && mouserY > botaoIniciarJogo.y
                            && mouserY < botaoIniciarJogo.y
                            + botaoIniciarJogo.height) {
                        botaoIniciarJogoSelecionado = true;
                    } else {
                        botaoIniciarJogoSelecionado = false;
                    }

                    // Botão Opções.
                    if (mouserX > botaoOpcoes.x
                            && mouserX < botaoOpcoes.x + botaoOpcoes.width
                            && mouserY > botaoOpcoes.y
                            && mouserY < botaoOpcoes.y + botaoOpcoes.height) {
                        botaoOpcoesSelecionado = true;
                    } else {
                        botaoOpcoesSelecionado = false;
                    }

                    // Botão Créditos.
                    if (mouserX > botaoCreditos.x
                            && mouserX < botaoCreditos.x + botaoCreditos.width
                            && mouserY > botaoCreditos.y
                            && mouserY < botaoCreditos.y + botaoCreditos.height) {
                        botaoCreditosSelecionado = true;
                    } else {
                        botaoCreditosSelecionado = false;
                    }

                    // Botão Recorde.
                    if (mouserX > botaoRecorde.x
                            && mouserX < botaoRecorde.x + botaoRecorde.width
                            && mouserY > botaoRecorde.y
                            && mouserY < botaoRecorde.y + botaoRecorde.height) {
                        botaoRecordeSelecionado = true;
                    } else {
                        botaoRecordeSelecionado = false;
                    }

                    // Botão Sair.
                    if (mouserX > botaoSair.x
                            && mouserX < botaoSair.x + botaoSair.width
                            && mouserY > botaoSair.y
                            && mouserY < botaoSair.y + botaoSair.height) {
                        botaoSairSelecionado = true;
                    } else {
                        botaoSairSelecionado = false;
                    }
                    break;

                case Opcoes:
                    // Botão Menu.
                    if (mouserX > botaoMenu.x
                            && mouserX < botaoMenu.x + botaoMenu.width
                            && mouserY > botaoMenu.y
                            && mouserY < botaoMenu.y + botaoMenu.height) {
                        botaoMenuSelecionado = true;
                    } else {
                        botaoMenuSelecionado = false;
                    }
                    break;

                case Recordes:
                    // Botão Menu.
                    if (mouserX > botaoMenu.x
                            && mouserX < botaoMenu.x + botaoMenu.width
                            && mouserY > botaoMenu.y
                            && mouserY < botaoMenu.y + botaoMenu.height) {
                        botaoMenuSelecionado = true;
                    } else {
                        botaoMenuSelecionado = false;
                    }
                    break;

                case Creditos:
                    // Botão Menu.
                    if (mouserX > botaoMenu.x
                            && mouserX < botaoMenu.x + botaoMenu.width
                            && mouserY > botaoMenu.y
                            && mouserY < botaoMenu.y + botaoMenu.height) {
                        botaoMenuSelecionado = true;
                    } else {
                        botaoMenuSelecionado = false;
                    }
                    break;

                case GAMEOVER:
                    // Botão Menu2.
                    if (mouserX > botaoMenu2.x
                            && mouserX < botaoMenu2.x + botaoMenu2.width
                            && mouserY > botaoMenu2.y
                            && mouserY < botaoMenu2.y + botaoMenu2.height) {
                        botaoMenu2Selecionado = true;
                    } else {
                        botaoMenu2Selecionado = false;
                    }

                    // Botão Novo Jogo.
                    if (mouserX > botaoNovoJogo.x
                            && mouserX < botaoNovoJogo.x + botaoNovoJogo.width
                            && mouserY > botaoNovoJogo.y
                            && mouserY < botaoNovoJogo.y + botaoNovoJogo.height) {
                        botaoNovoJogoSelecionado = true;
                    } else {
                        botaoNovoJogoSelecionado = false;
                    }
                    break;
            }
        }

        /**
         * Método Mouse Pressed (quando precionar o botão do mouse).
         */
        @Override
        public void mousePressed(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            switch (estadoJogo) {
                case MenuPrincipal:
                    // Condição quando precionar o botão Iniciar Jogo.
                    if (mouseX > botaoIniciarJogo.x
                            && mouseX < botaoIniciarJogo.x + botaoIniciarJogo.width
                            && mouseY > botaoIniciarJogo.y
                            && mouseY < botaoIniciarJogo.y
                            + botaoIniciarJogo.height) {

                        // Iniciar Novo Jogo.
                        novoJogo();
                    }

                    // Condição quando precionar o botão Opções.
                    if (mouseX > botaoOpcoes.x
                            && mouseX < botaoOpcoes.x + botaoOpcoes.width
                            && mouseY > botaoOpcoes.y
                            && mouseY < botaoOpcoes.y + botaoOpcoes.height) {

                        // Estado do jogo é definido para tela opções.
                        estadoJogo = EstadoJogo.Opcoes;
                    }
                    // Condição quando precionar o botão Créditos.
                    if (mouseX > botaoCreditos.x
                            && mouseX < botaoCreditos.x + botaoCreditos.width
                            && mouseY > botaoCreditos.y
                            && mouseY < botaoCreditos.y + botaoCreditos.height) {

                        // Estado do jogo é definido para tela Créditos.
                        estadoJogo = EstadoJogo.Creditos;
                    }
                    // Condição quando precionar o botão Recorde.
                    if (mouseX > botaoRecorde.x
                            && mouseX < botaoRecorde.x + botaoRecorde.width
                            && mouseY > botaoRecorde.y
                            && mouseY < botaoRecorde.y + botaoRecorde.height) {

                        // Estado do jogo é definido para tela Recordes.
                        estadoJogo = EstadoJogo.Recordes;
                    }
                    // Condição quando precionar o botão sair.
                    if (mouseX > botaoSair.x
                            && mouseX < botaoSair.x + botaoSair.width
                            && mouseY > botaoSair.y
                            && mouseY < botaoSair.y + botaoSair.height) {

                        // Sair do Jogo.
                        System.exit(0);
                    }
                    break;

                case Opcoes:
                    // Condição quando precionar o botão Menu.
                    if (mouseX > botaoMenu.x
                            && mouseX < botaoMenu.x + botaoMenu.width
                            && mouseY > botaoMenu.y
                            && mouseY < botaoMenu.y + botaoMenu.height) {

                        // Estado do jogo é definido para tela Menu Principal.
                        estadoJogo = EstadoJogo.MenuPrincipal;
                    }
                    break;

                case Creditos:
                    // Condição quando precionar o botão Menu.
                    if (mouseX > botaoMenu.x
                            && mouseX < botaoMenu.x + botaoMenu.width
                            && mouseY > botaoMenu.y
                            && mouseY < botaoMenu.y + botaoMenu.height) {

                        // Estado do jogo é definido para tela Menu Principal.
                        estadoJogo = EstadoJogo.MenuPrincipal;
                    }

                case Recordes:
                    // Condição quando precionar o botão Menu.
                    if (mouseX > botaoMenu.x
                            && mouseX < botaoMenu.x + botaoMenu.width
                            && mouseY > botaoMenu.y
                            && mouseY < botaoMenu.y + botaoMenu.height) {

                        // Estado do jogo é definido para tela Menu Principal.
                        estadoJogo = EstadoJogo.MenuPrincipal;
                    }
                    break;

                case GAMEOVER:
                    // Condição quando precionar o botão Menu2.
                    if (mouseX > botaoMenu2.x
                            && mouseX < botaoMenu2.x + botaoMenu2.width
                            && mouseY > botaoMenu2.y
                            && mouseY < botaoMenu2.y + botaoMenu2.height) {

                        // Estado do jogo é definido para tela Menu Principal.
                        estadoJogo = EstadoJogo.MenuPrincipal;
                    }

                    // Condição quando precionar o botão NovoJogo.
                    if (mouseX > botaoNovoJogo.x
                            && mouseX < botaoNovoJogo.x + botaoNovoJogo.width
                            && mouseY > botaoNovoJogo.y
                            && mouseY < botaoNovoJogo.y + botaoNovoJogo.height) {

                        // Reiniciar Jogo.
                        ReiniciarJogo();
                    }
                    break;
            }

        }
    }

}
