
package jogoOdisseia;

import gerenteImagens.GerenteImage;
import inimigos.InimigoSpikey;
import inimigos.InimigoUFO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import nave.Nave;
import recordes.Recorde;
import tempo.TempoCarregarJogo;
import armasInimigos.LeiserInimigo;
import armasNave.LeiserNave;
import armasNave.MissilNave;

import componentes.PowerUpLeiser;
import componentes.CaixaMissil;
import componentes.Vida;

import core.Framework;

import efeitoVisual.Efeito;
import efeitoVisual.EfFundoMovel;

import efeitoVisual.EfLeiser;
import efeitoVisual.EfMissil;

/**
 * @author Francisco Assis Souza Rodrigues
 * @version BETA Fevereiro/2014
 */

/**
 * 
 * Class Game. Esta class possue toda a logica do jogo.
 * 
 */
public class Game {

	/* ###### LISTAS ###### */
	// Lista de armas da Nave.
	private ArrayList<LeiserNave> ListaLeiserNave;
	private ArrayList<MissilNave> ListaMissilNave;
	// Lista de Inimigos.
	private ArrayList<InimigoUFO> ListaInimigosUFO;
	private ArrayList<InimigoSpikey> ListaInimigosSpikey;
	// Lista das Armas dos Inimigos.
	private ArrayList<LeiserInimigo> ListaLeiserInimigo;
	// Lista de Componentes.
	private ArrayList<PowerUpLeiser> ListaPoewrUpLeiser;
	private ArrayList<Vida> ListaVida;
	private ArrayList<CaixaMissil> ListaCaixaMissil;
	// Lista de Efeitos.
	private ArrayList<EfMissil> ListaEfeitoMissil;
	private ArrayList<Efeito> ListaExplosao;
	private ArrayList<EfLeiser> ListaEfLeiser;
	private ArrayList<Efeito> ListaEscudo;
	private ArrayList<Efeito> ListaEfImpacto;
	private ArrayList<Efeito> ListaEfAdd;

	/* ###### IMAGENS ###### */
	private BufferedImage imgCenario;
	private BufferedImage imgCosmo1;
	private BufferedImage imgCosmo2;
	private BufferedImage imgMontanhas;
	private BufferedImage imgTerra;
	private BufferedImage imgExplosao;
	private BufferedImage imgExplosao2;
	private BufferedImage imgEfLeiser;
	private BufferedImage imgEscudo;
	private BufferedImage imgEfImpacto;
	private BufferedImage imgEfAdd;
	private BufferedImage imgEfAdd2;
	private BufferedImage imgPainelTempo;
	private BufferedImage imgBarraVida;
	private BufferedImage imgIndicadorPontosGanhos;
	private BufferedImage imgIndicadorPontosPerdidos;

	// Imagens Utilizadas na classsificação (Recorde).
	private BufferedImage imgPrimeiroLugar;
	private BufferedImage imgSegundoLugar;
	private BufferedImage imgTerceiroLugar;
	private BufferedImage imgNaoClassificado;

	// Objetos de Imagens que serão Movimentadas.
	private EfFundoMovel moverCosmo1;
	private EfFundoMovel moverCosmo2;
	private EfFundoMovel moverMontanhas;
	private EfFundoMovel moverTerra;
	private EfFundoMovel moverCenario;

	/* ###### Outras Variaveis ###### */

	// Tempo do Jogo.
	static int segundos;
	static int minutos;

	// Números Aleatórios.
	private Random random;

	// Objeto da Class Nave.java.
	private Nave nave;

	// Objeto da Class Fonte.
	private Font font;

	// Objeto da Class (TempoCarregarJogo.java).
	private TempoCarregarJogo tempoCarregarJogo;

	// Estatísticas (Inimigos Destruídos, Inimigos Não Destruidos, Pontuação
	// Extra).
	private int ganhaPontos;
	private int perdePontos;
	public int totalPontos;

	// conta quantos inimigos foram destruidos para poder gera um powerUp.
	private int gerarPowerUp;
	private int gerarVida;
	private int gerarCaixaMissil;

	// Conta a quatidades de PowerUp adicionados a nave.
	private int contPowerUpAdd;
	private int contCaixaMissilAdd;

	// Ativadores de Armas.
	private boolean ativarMissil = false;

	// Conta quantidade de Vidas (CONTINUE).
	private int ContinueVida = 0;

	/* ####### RECORDES ###### */
	// Intancia de um objeto recorde da Class Recorde.java;
	Recorde recorde;

	// Variavel auxiliar que recebe o valor convertido de String para
	// Inteiro.
	private int auxPrimeiroRecorde;
	private int auxSegundoRecorde;
	private int auxTerceiroRecorde;
	private int auxNaoClassificado;

	// Variavel auxiliar que recebe o valor convertido de inteiro para
	// String.
	String auxTotalPontos;

	/**
	 * Construtor da Class.
	 */
	public Game() {
		// Instância um objeto da class Thread.
		Thread threadParaIniciaJogo = new Thread() {

			@Override
			public void run() {
				// Define variáveis ​​e objetos para o jogo.
				Inicializar();

				// Carrega Arquivos do Jogo (imagens, sons, ...).
				CarregarConteudo();

				// Instância o Objeto da Class (TempoCarregarJogo.java).
				// E espera o tempo definido na class para iniciar o jogo.
				setTempoCarregarJogo(new TempoCarregarJogo());
			}
		};
		// Inicia a Thread.
		threadParaIniciaJogo.start();
	}

	/**
	 * Definir variáveis ​​e objetos para o jogo.
	 */
	private void Inicializar() {
		recorde = new Recorde();

		// Instância objeto da Class Font e Define Fonte.
		font = new Font("Ubuntu", Font.BOLD, 18);

		// Instância objeto que gera numero aleatorios.
		random = new Random();

		// Intância objeto da Class Nave.java com Parametros.
		nave = new Nave(Framework.frameLargura / 4, Framework.frameAltura / 4);

		// Chama e Exibe Carregando Jogo.
		Framework.estadoJogo = Framework.EstadoJogo.CarregandoConteudo;

		// Instância Objetos das Listas.
		ListaLeiserNave = new ArrayList<LeiserNave>();
		ListaLeiserInimigo = new ArrayList<LeiserInimigo>();
		ListaMissilNave = new ArrayList<MissilNave>();
		ListaEfeitoMissil = new ArrayList<EfMissil>();
		ListaInimigosUFO = new ArrayList<InimigoUFO>();
		ListaInimigosSpikey = new ArrayList<InimigoSpikey>();
		ListaExplosao = new ArrayList<Efeito>();
		ListaEfLeiser = new ArrayList<EfLeiser>();
		ListaEscudo = new ArrayList<Efeito>();
		ListaEfImpacto = new ArrayList<Efeito>();
		ListaPoewrUpLeiser = new ArrayList<PowerUpLeiser>();
		ListaEfAdd = new ArrayList<Efeito>();
		ListaVida = new ArrayList<Vida>();
		ListaCaixaMissil = new ArrayList<CaixaMissil>();

		// Instância Objetos Imagens em Movimento.
		moverCosmo1 = new EfFundoMovel();
		moverCosmo2 = new EfFundoMovel();
		moverMontanhas = new EfFundoMovel();
		moverTerra = new EfFundoMovel();
		moverCenario = new EfFundoMovel();

		// Define Valor Inicial Estatísticas do Jogo.
		ganhaPontos = 0;
		perdePontos = 0;
	}

	/**
	 * Carrega os Arquivos do Jogo.
	 */
	private void CarregarConteudo() {
		try {
			// Cenário.
			imgCenario = GerenteImage.getInstance().loadImage(
					"images/cenarios/cenario1.png");

			// Cosmo1.
			imgCosmo1 = GerenteImage.getInstance().loadImage(
					"images/cosmos/cosmo3.png");

			// Cosmo2.
			imgCosmo2 = GerenteImage.getInstance().loadImage(
					"images/cosmos/cosmo4.png");

			// Montanhas.
			imgMontanhas = GerenteImage.getInstance().loadImage(
					"images/componentesCenario/mountains.png");

			// Terra.
			imgTerra = GerenteImage.getInstance().loadImage(
					"images/componentesCenario/ground.png");
			// Laiser.
			LeiserNave.imgLeiser = GerenteImage.getInstance().loadImage(
					"images/armas/leiser/leiser.png");

			// LeiserInimigo.
			LeiserInimigo.imgLeiserInimigo = GerenteImage.getInstance()
					.loadImage("images/armas/leiser/leiserInimigo.png");

			// Missil.
			MissilNave.imgMissil = GerenteImage.getInstance().loadImage(
					"images/armas/missil/missil.png");

			// Efeito Fumaça do Missil.
			EfMissil.imgEfeitoMissil = GerenteImage.getInstance().loadImage(
					"images/efeitosVisuais/efMissil.png");

			// Explosão.
			imgExplosao = GerenteImage.getInstance().loadImage(
					"images/efeitosVisuais/explosoes/explosao1.png");

			// Explosão2.
			imgExplosao2 = GerenteImage.getInstance().loadImage(
					"images/efeitosVisuais/explosoes/explosao2.png");

			// EfAdd.
			imgEfAdd = GerenteImage.getInstance().loadImage(
					"images/efeitosVisuais/efAdd/efAdd.png");

			// EfAdd2.
			imgEfAdd2 = GerenteImage.getInstance().loadImage(
					"images/efeitosVisuais/efAdd/efAdd2.png");

			// EfDisparo.
			imgEfLeiser = GerenteImage.getInstance().loadImage(
					"images/efeitosVisuais/efLeiser.png");

			// EfImpacto.
			imgEfImpacto = GerenteImage.getInstance().loadImage(
					"images/efeitosVisuais/efImpacto.png");

			// Escudo.
			imgEscudo = GerenteImage.getInstance().loadImage(
					"images/efeitosVisuais/escudo/escudo.png");

			// Painel Status do jogo.
			imgPainelTempo = GerenteImage.getInstance().loadImage(
					"images/painel/painelTempo.png");

			// Indicador Barra Progresso Vida.
			imgBarraVida = GerenteImage.getInstance().loadImage(
					"images/painel/barraVida.png");

			// Indicador de Pontos Ganhos.
			imgIndicadorPontosGanhos = GerenteImage.getInstance().loadImage(
					"images/painel/pontosGanhos.png");

			// Indicador de Pontos Perdidos.
			imgIndicadorPontosPerdidos = GerenteImage.getInstance().loadImage(
					"images/painel/pontosPerdidos.png");

			// do InimigoUFO.
			InimigoUFO.imgInimigoUFO = GerenteImage.getInstance().loadImage(
					"images/inimigos/ufo/ufo.png");

			InimigoUFO.imgPropusorInimigo = GerenteImage.getInstance()
					.loadImage("images/inimigos/ufo/propusorUFO.png");

			// do InimigoSpikey.
			InimigoSpikey.imgInimigoSpikey = GerenteImage.getInstance()
					.loadImage("images/inimigos/spikey/spikey.png");

			InimigoSpikey.imgGiroInimigoSpikey = GerenteImage.getInstance()
					.loadImage("images/inimigos/spikey/ef.png");

			// ADD PowerUp.
			PowerUpLeiser.imgPowerUpLeiser = GerenteImage.getInstance()
					.loadImage("images/powerUp/powerUP.png");

			// Vida.
			Vida.imgVida = GerenteImage.getInstance().loadImage(
					"images/vida/vida.png");

			// CaixaMissil.
			CaixaMissil.imgCaixaMissil = GerenteImage.getInstance().loadImage(
					"images/armas/missil/caixaMissil.png");

			// Estrelas classificação.
			imgPrimeiroLugar = GerenteImage.getInstance().loadImage(
					"images/imgPontuacao/primeiro.png");

			imgSegundoLugar = GerenteImage.getInstance().loadImage(
					"images/imgPontuacao/segundo.png");

			imgTerceiroLugar = GerenteImage.getInstance().loadImage(
					"images/imgPontuacao/terceiro.png");

			imgNaoClassificado = GerenteImage.getInstance().loadImage(
					"images/imgPontuacao/zero.png");

		} catch (IOException ex) {
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}

		// Inicializar as Imagens que seram Movimentadas com os
		// parametros(referência da imagem, velocidade e direção da imagem,
		// posição da imagem).
		moverCenario.Inicializar(imgCenario, -1, 0);
		moverCosmo1.Inicializar(imgCosmo1, -6, 0);
		moverCosmo2.Inicializar(imgCosmo2, -2, 0);

		moverMontanhas.Inicializar(imgMontanhas, -1, Framework.frameAltura
				- imgTerra.getHeight() - imgMontanhas.getHeight() + 40);

		moverTerra.Inicializar(imgTerra, -1.2,
				Framework.frameAltura - imgTerra.getHeight());
	}

	/**
	 * Reinicia Jogo - Redefinir algumas variáveis​​.
	 */
	public void ReiniciarJogo() {
		nave.Reiniciar(Framework.frameLargura / 4, Framework.frameAltura / 4);

		// Chama Metodo Reiniciar de cada Class correspondente.
		InimigoUFO.reiniciarInimigo();
		InimigoSpikey.reiniciarInimigo();
		PowerUpLeiser.reiniciar();
		Vida.reiniciar();
		CaixaMissil.reiniciar();

		// Reinicia Status de augumas Variaveis.
		LeiserNave.tempoUltimoLeiserCriado = 0;
		LeiserInimigo.tempoUltimoLeiserCriado = 0;
		MissilNave.tempoUltimoMissilCriado = 0;

		// Esvazia todas as Listas.
		ListaInimigosUFO.clear();
		ListaInimigosSpikey.clear();
		ListaLeiserNave.clear();
		ListaLeiserInimigo.clear();
		ListaMissilNave.clear();
		ListaEfeitoMissil.clear();
		ListaExplosao.clear();
		ListaEfLeiser.clear();
		ListaEscudo.clear();
		ListaEfImpacto.clear();
		ListaPoewrUpLeiser.clear();
		ListaEfAdd.clear();
		ListaVida.clear();
		ListaCaixaMissil.clear();

		// Redefine Estatísticas do Jogo (Zera Pontuação).
		ganhaPontos = 0;
		perdePontos = 0;
		gerarCaixaMissil = 0;
		gerarPowerUp = 0;
		gerarVida = 0;
	}

	/**
	 * Atualiza a Lógica do Jogo.
	 * 
	 * @param tempoJogo
	 *            O tempo de jogo decorrido em nanossegundos.
	 * @param mousePosition
	 *            posição atual do mouse.
	 */
	public void AtualizaJogo(long tempoJogo) {
		/* Nave */
		// Verifica se a Nave foi destruída, espera terminar todas as
		// animações e em seguida mostra a Estatistica do Jogo.
		if (!JogadorVivo() && ListaExplosao.isEmpty()) {
			Framework.estadoJogo = Framework.EstadoJogo.GAMEOVER;
			// Se o jogador for destruido não executa o restante do codigo
			// abaixo.
			return;
		}

		// Se o jogador (Nave) estiver vivo, Atualiza.
		if (JogadorVivo()) {
			verificarDisparoMissil(tempoJogo);
			verificarDisparoLeiserNave(tempoJogo);
			verificarDisparoLeiserInimigo(tempoJogo);
			nave.Mover();
			nave.Atualizar();
		}
		/* Atualizar Leiser da Nave */
		AtualizarLeiserNave(tempoJogo);

		/* Atualizar LeiserInimigo */
		AtualizarLeiserInimigo(tempoJogo);

		/* Atualizar Misseis Nave */
		AtualizarMissilNave(tempoJogo);

		// Verifica se há colisões (se qualquer um dos disparos
		// atingiram qualquer um dos inimigos).
		atualizarEfeitoFumacaMissil(tempoJogo);

		/* Inimigos */
		criaNovoInimigoUFO(tempoJogo);
		ColisaoNaveComInimigosUFO();

		criaNovoInimigoSpikey(tempoJogo);
		ColisaoNaveComInimigoSpikey();

		/* PowerUP */
		criaNovoPowerUp(tempoJogo);
		ColisaoNaveComPowerUp();

		/* Vida */
		criaNovaVida(tempoJogo);
		ColisaoNaveComVida();

		/* CaixaMissil */
		criaNovaCaixaMissil(tempoJogo);
		ColisaoNaveComCaixaMissil();

		/* Explosões */
		atualizarExplosoes();

		/* EfDisparo */
		atualizarEfLeiser();

		/* EfImpacto */
		atualizarEfImpacto();

		/* EfEscudo */
		atualizarEscudo();

		/* EfAdd */
		atualizarEfAdd();
	}

	/**
	 * Desenhe o jogo na tela.
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	public void Draw(Graphics2D g2d, long tempoJogo) {

		// Desenha a Imagem do Cenario.
		g2d.drawImage(imgCenario, 0, 0, Framework.frameLargura,
				Framework.frameAltura, null);

		// Desenha Imagens do Cenario em Movimento.
		moverCenario.Draw(g2d);
		moverMontanhas.Draw(g2d);
		moverTerra.Draw(g2d);
		moverCosmo2.Draw(g2d);

		// Condição para Desenhar Nave.
		if (JogadorVivo()) {
			nave.Draw(g2d);
		}
		// Desenha todos os InimigosUFO.
		for (int i = 0; i < ListaInimigosUFO.size(); i++) {
			ListaInimigosUFO.get(i).Draw(g2d);
		}
		// Desenha todos os InimigosSpikey.
		for (int i = 0; i < ListaInimigosSpikey.size(); i++) {
			ListaInimigosSpikey.get(i).Draw(g2d);
		}
		// Desenha todos os Leiser da Nave.
		for (int i = 0; i < ListaLeiserNave.size(); i++) {
			ListaLeiserNave.get(i).Draw(g2d);
		}
		// Desenha todos os LeiserInimigo.
		for (int i = 0; i < ListaLeiserInimigo.size(); i++) {
			ListaLeiserInimigo.get(i).Draw(g2d);
		}
		// Desenha todos os Misseis.
		for (int i = 0; i < ListaMissilNave.size(); i++) {
			ListaMissilNave.get(i).Draw(g2d);
		}
		// Desenha a Fumaça, Efeito de todos os Misseis.
		for (int i = 0; i < ListaEfeitoMissil.size(); i++) {
			ListaEfeitoMissil.get(i).Draw(g2d);
		}
		// Desenha todas as EfExplosões.
		for (int i = 0; i < ListaExplosao.size(); i++) {
			ListaExplosao.get(i).Draw(g2d);
		}
		// Desenha todos os EfDisparos.
		for (int i = 0; i < ListaEfLeiser.size(); i++) {
			ListaEfLeiser.get(i).Draw(g2d);
		}
		// Desenha todos os EfImpacto.
		for (int i = 0; i < ListaEfImpacto.size(); i++) {
			ListaEfImpacto.get(i).Draw(g2d);
		}
		// Desenha EfEscudo.
		for (int i = 0; i < ListaEscudo.size(); i++) {
			ListaEscudo.get(i).Draw(g2d);
		}
		// Desenha PowerUp.
		for (int i = 0; i < ListaPoewrUpLeiser.size(); i++) {
			ListaPoewrUpLeiser.get(i).Draw(g2d);
		}
		// Desenha EfAdd.
		for (int i = 0; i < ListaEfAdd.size(); i++) {
			ListaEfAdd.get(i).Draw(g2d);
		}
		// Desenha Vida.
		for (int i = 0; i < ListaVida.size(); i++) {
			ListaVida.get(i).Draw(g2d);
		}
		// Desenha CaixaMissil.
		for (int i = 0; i < ListaCaixaMissil.size(); i++) {
			ListaCaixaMissil.get(i).Draw(g2d);
		}

		/* ########## Desenha na Frente dos Objetos ########### */

		// *Desenha Estatisticas do Jogo*//
		// Define Fonte
		g2d.setFont(font);
		// Define Cor do Texto
		g2d.setColor(Color.WHITE);

		// Desenha Painel por traz dos Objetos.
		g2d.drawImage(imgPainelTempo, Framework.frameLargura / 2, 6, null);

		// Desenha Informação do Tempo Decorrido
		g2d.drawString(Tempo(tempoJogo), Framework.frameLargura / 2 + 60, 55);

		// Desenha Informação Imagem e Quantidade Inimigos Destruidos
		g2d.drawImage(imgIndicadorPontosGanhos, 60, 20, null);
		g2d.drawString("" + ganhaPontos, 80, 20);

		// Desenha Informação Imagem e Quantidade de Inimigos Não Abatidos
		g2d.drawImage(imgIndicadorPontosPerdidos, 150, 20, null);
		g2d.drawString("" + perdePontos, 180, 20);

		// Desenha Indicador Barra de Progresso Vida.
		g2d.drawImage(imgBarraVida, 10, 10, null);

		// Imagem em Movimento.
		// Desenha o Cosmo na frente.
		moverCosmo1.Draw(g2d);
	}

	/**
	 * Desenha Estatísticas do jogo, (GAMEOVER).
	 * 
	 * @param g2d
	 *            Graphics2D
	 * @param gameTime
	 *            Elapsed game time.
	 */
	public void DrawEstatisticas(Graphics2D g2d, long tempoJogo) {
		// Desenha (Define) Fonte.
		g2d.setFont(font);

		// Desenha (Define) Cor da Fonte
		g2d.setColor(Color.BLACK);

		// SubTitulo.
		g2d.drawString("ESTATÍSTICAS", 600, 200);

		// Tempo de Jogo.
		g2d.drawString("Tempo de Jogo: " + Tempo(tempoJogo), 550, 250);

		// Desenha Imagem e Quantidade de Pontos Ganhos (Inimigos Destruidos).
		g2d.drawImage(imgIndicadorPontosGanhos, 500, 280, null);
		g2d.drawString("Pontos Ganhos: " + ganhaPontos, 550, 300);

		// Desenha Imagem e Quantidade de Pontos Ganhos (Inimigos Não Abatidos).
		g2d.drawImage(imgIndicadorPontosPerdidos, 500, 310, null);
		g2d.drawString("Pontos Perdidos: " + perdePontos, 550, 330);

		// Desenha Total de Pontos.
		g2d.drawString("Total de Pontos: "
				+ (totalPontos = ganhaPontos - perdePontos), 500, 400);

		/******* RECORDE *******/
		// Verifica se o total de pontos feito pelo jogador é maior que o 1º
		// Lugar.
		if (totalPontos > auxPrimeiroRecorde) {

			// NOVO RECORDE.
			g2d.drawString("NOVO RECORDE!", 550, 450);
			g2d.drawImage(imgPrimeiroLugar, 550, 450, null);

			// Conver o valor que está na variavel totalPonto do tipo Inteiro
			// para String e a variavel auxTotalpontos recebe o valor
			// convertido.
			auxTotalPontos = String.valueOf(totalPontos);

			// passa o valor convertido que esta na variavel auxTotalPontos para
			// primeiroRecoder da Class Recorde.java.
			recorde.setPrimeiroRecorde(auxTotalPontos);
			recorde.setSegundoRecorde(String.valueOf(auxPrimeiroRecorde));
			recorde.setTerceiroRecorde(String.valueOf(auxSegundoRecorde));
			recorde.setNaoClassificou(String.valueOf(auxTerceiroRecorde));

			// chama o metodo para gravar o recorde.
			recorde.gravarRecorde();
		}

		// Verifica se o total de pontos feito pelo jogador é maior que o 2º
		// Lugar.
		if ((totalPontos < auxPrimeiroRecorde)
				&& (totalPontos > auxSegundoRecorde)) {

			// NOVO RECORDE.
			g2d.drawString("Bateu o 2º Lugar!", 550, 450);
			g2d.drawImage(imgSegundoLugar, 550, 450, null);

			// Conver o valor que está na variavel totalPonto do tipo Inteiro
			// para String e a variavel auxTotalpontos recebe o valor
			// convertido.
			auxTotalPontos = String.valueOf(totalPontos);

			// passa o valor convertido que esta na variavel auxTotalPontos para
			// primeiroRecoder da Class Recorde.java.
			recorde.setSegundoRecorde(auxTotalPontos);
			recorde.setTerceiroRecorde(String.valueOf(auxSegundoRecorde));
			recorde.setNaoClassificou(String.valueOf(auxTerceiroRecorde));

			// chama o metodo para gravar o recorde.
			recorde.gravarRecorde();
		}
		// Verifica se o total de pontos feito pelo jogador é maior que o 3º
		// Lugar.
		if ((totalPontos < auxSegundoRecorde)
				&& (totalPontos > auxTerceiroRecorde)) {

			// NOVO RECORDE.
			g2d.drawString("Bateu o 3º Lugar!", 550, 450);
			g2d.drawImage(imgTerceiroLugar, 550, 450, null);

			// Conver o valor que está na variavel totalPonto do tipo Inteiro
			// para String e a variavel auxTotalpontos recebe o valor
			// convertido.
			auxTotalPontos = String.valueOf(totalPontos);

			// passa o valor convertido que esta na variavel auxTotalPontos para
			// primeiroRecoder da Class Recorde.java.
			recorde.setTerceiroRecorde(auxTotalPontos);
			recorde.setNaoClassificou(String.valueOf(auxTerceiroRecorde));

			// chama o metodo para gravar o recorde.
			recorde.gravarRecorde();
		}
		// Verifica se o total de pontos é inferior aos Recordes.
		if ((totalPontos < auxNaoClassificado)
				&& (totalPontos > auxNaoClassificado)) {

			// NOVO RECORDE.
			g2d.drawString("Não Bateu Nenhum Recorde!", 550, 450);
			g2d.drawImage(imgNaoClassificado, 550, 450, null);

			// Conver o valor que está na variavel totalPonto do tipo Inteiro
			// para String e a variavel auxTotalpontos recebe o valor
			// convertido.
			auxTotalPontos = String.valueOf(totalPontos);

			// passa o valor convertido que esta na variavel auxTotalPontos para
			// primeiroRecoder da Class Recorde.java.
			recorde.setNaoClassificou(auxTotalPontos);

			// chama o metodo para gravar o recorde.
			recorde.gravarRecorde();
		}

	}

	/**
	 * Tempo do Jogo.
	 */
	private static String Tempo(long tempo) {
		// Dado o tempo em segundos.
		segundos = (int) (tempo / Framework.milisecInNanosec / 1000);

		// Dado o tempo em minutos e segundos.
		minutos = segundos / 60;
		segundos = segundos - (minutos * 60);

		String minString, secString;

		if (minutos <= 9)
			minString = "0" + Integer.toString(minutos);
		else
			minString = "" + Integer.toString(minutos);

		if (segundos <= 9)
			secString = "0" + Integer.toString(segundos);
		else
			secString = "" + Integer.toString(segundos);

		return minString + ":" + secString;
	}

	// * Os métodos para a atualização do jogo.*//

	/**
	 * Verifique se o jogador está vivo. Se Possue Continue. Se não, definir
	 * status game over.
	 * 
	 * @return True (Verdadeiro se o jogador está vivo, false caso destruido.
	 */
	private boolean JogadorVivo() {
		// Se a saudeAtual da nave for igual zero e ContinueVida maior zero.
		if ((nave.getSaudeAtual() <= 0) && (ContinueVida > 0)) {
			// Seta SaudeAtual da Nave para SaudeInicail.
			nave.setSaudeAtual(nave.getSaudeInicial());
			// Seta BarraSaudeAtual da Nave para BarraSaudeInicial.
			nave.setBarraSaudeAtual(nave.getBarraSaudeInicial());
			// Reduz Continue Vida.
			ContinueVida--;
		}

		// Se SaudeAtual da nave igual a zero e ContinueVida igual a zero.
		if (nave.getSaudeAtual() <= 0 && ContinueVida == 0) {

			/******* RECORDE *******/

			// Chamada do Método Busca o Recodes já armazenados.
			recorde.exibirRecordes();

			// Variaveis auxiliares armazenam os valor que será recebido dos
			// recordes salvos, do tipo String e converte para inteiro.
			auxPrimeiroRecorde = Integer.parseInt(recorde.getPrimeiroRecorde());
			auxSegundoRecorde = Integer.parseInt(recorde.getSegundoRecorde());
			auxTerceiroRecorde = Integer.parseInt(recorde.getTerceiroRecorde());
			auxNaoClassificado = Integer.parseInt(recorde.getNaoClassificou());

			// retorna falso, quando nave destruida.
			return false;
		}
		// retorna verdadeiro, enquanto a nave estiver intacta.
		return true;
	}

	/**
	 * Verifica se ouve disparo de LeiserNave e o cria. Verifica se a Leiser
	 * disponivel. Verifica PowerUp do Leiser. Adiciona Efeito no disparo do
	 * Leiser.
	 * 
	 * @param tempoJogo
	 *            Game tempo.
	 */
	private void verificarDisparoLeiserNave(long tempoJogo) {
		//
		if (nave.DispararLeiser(tempoJogo) && contPowerUpAdd == 0) {
			LeiserNave.tempoUltimoLeiserCriado = tempoJogo;

			// Instância do objeto da class Leiser.
			LeiserNave leiser = new LeiserNave();

			leiser.Inicializar(
					nave.xCoordenada + nave.getAjustaCoordenadaLeiserX(),
					nave.yCoordenada + nave.getAjustaCoordenadaLeiserY());

			// Adiciona leiser a lista de Leiser.
			ListaLeiserNave.add(leiser);

			// Adiciona EfLeiser.
			for (int efD = 0; efD < 3; efD++) {
				EfLeiser efDAnim = new EfLeiser(imgEfLeiser, 64, 64, 3, 0,
						false, nave.xCoordenada + 80, nave.yCoordenada + 5, efD);

				// Adiciona EfLeiser a Lista de EfLeiser.
				ListaEfLeiser.add(efDAnim);
			}
		}
		//
		if (nave.DispararLeiser(tempoJogo) && contPowerUpAdd == 1) {
			LeiserNave.tempoUltimoLeiserCriado = tempoJogo;

			// Instância do objeto da class Leiser.
			LeiserNave leiser = new LeiserNave();
			LeiserNave leiser2 = new LeiserNave();

			leiser.Inicializar(
					nave.xCoordenada + nave.getAjustaCoordenadaLeiserX(),
					nave.yCoordenada + nave.getAjustaCoordenadaLeiserY() - 25);

			leiser2.Inicializar(
					nave.xCoordenada + nave.getAjustaCoordenadaLeiserX(),
					nave.yCoordenada + nave.getAjustaCoordenadaLeiserY() + 25);

			// Adiciona leiser a lista de Leiser.
			ListaLeiserNave.add(leiser);
			ListaLeiserNave.add(leiser2);

			// Adiciona EfLeiser.
			for (int efD = 0; efD < 3; efD++) {
				EfLeiser efDAnim = new EfLeiser(imgEfLeiser, 64, 64, 3, 0,
						false, nave.xCoordenada + 80, nave.yCoordenada + 5, efD);

				// Adiciona EfLeiser a Lista de EfLeiser.
				ListaEfLeiser.add(efDAnim);
			}
		}
		//
		if (nave.DispararLeiser(tempoJogo) && contPowerUpAdd >= 2) {
			LeiserNave.tempoUltimoLeiserCriado = tempoJogo;

			// Instância do objeto da class Leiser.
			LeiserNave leiser = new LeiserNave();
			LeiserNave leiser2 = new LeiserNave();
			LeiserNave leiser3 = new LeiserNave();

			leiser.Inicializar(
					nave.xCoordenada + nave.getAjustaCoordenadaLeiserX(),
					nave.yCoordenada + nave.getAjustaCoordenadaLeiserY() - 25);

			leiser2.Inicializar(
					nave.xCoordenada + nave.getAjustaCoordenadaLeiserX(),
					nave.yCoordenada + nave.getAjustaCoordenadaLeiserY() + 0);

			leiser3.Inicializar(
					nave.xCoordenada + nave.getAjustaCoordenadaLeiserX(),
					nave.yCoordenada + nave.getAjustaCoordenadaLeiserY() + 25);

			// Adiciona leiser a lista de Leiser.
			ListaLeiserNave.add(leiser);
			ListaLeiserNave.add(leiser2);
			ListaLeiserNave.add(leiser3);

			// Adiciona EfLeiser.
			for (int efD = 0; efD < 3; efD++) {
				EfLeiser efDAnim = new EfLeiser(imgEfLeiser, 64, 64, 3, 0,
						false, nave.xCoordenada + 80, nave.yCoordenada + 5, efD);

				// Adiciona EfLeiser a Lista de EfLeiser.
				ListaEfLeiser.add(efDAnim);
			}
		}
	}

	/**
	 * Cria Disparo de LeiserInimigo. Verifica se a LeiserInimgo disponivel.
	 * Adiciona Efeito no Disparo do Leiser Inimigo.
	 * 
	 * @param tempoJogo
	 *            Game tempo.
	 */
	private void verificarDisparoLeiserInimigo(long tempoJogo) {
		for (int i = 0; i < ListaInimigosUFO.size(); i++) {

			InimigoUFO inimigoUFO = ListaInimigosUFO.get(i);

			if (inimigoUFO.DispararLeiser(tempoJogo)) {
				LeiserInimigo.tempoUltimoLeiserCriado = tempoJogo;

				// Instância do objeto da class Leiser.
				LeiserInimigo leiserInimigo = new LeiserInimigo();

				leiserInimigo.Inicializar(inimigoUFO.xCoordenada
						+ inimigoUFO.ajustaCoordenadaLeiserX,
						inimigoUFO.yCoordenada
								+ inimigoUFO.ajustaCoordenadaLeiserY);

				// Adiciona leiser a lista de Leiser.
				ListaLeiserInimigo.add(leiserInimigo);

				// Adiciona EfLeiserInimigo (tamanho da posição da Imagem, e
				// suas respectivas coordenadas).
				for (int efD = 0; efD < 3; efD++) {
					EfLeiser efDAnim = new EfLeiser(imgEfLeiser, 64, 64, 3, 0,
							false, inimigoUFO.xCoordenada - 60,
							inimigoUFO.yCoordenada - 20, efD);

					// Adiciona EfLeiser a Lista de EfLeiser.
					ListaEfLeiser.add(efDAnim);
				}
			}
		}
	}

	/**
	 * Verifica se ouve disparo de Missil e o cria. Verifica se a Missil
	 * disponivel.
	 * 
	 * @param tempoJogo
	 *            Game tempo.
	 */
	private void verificarDisparoMissil(long tempoJogo) {

		if (nave.DispararMissil(tempoJogo) && (ativarMissil == true)) {

			MissilNave.tempoUltimoMissilCriado = tempoJogo;

			// Intância dois objetos da mesma Class (Missil.java).
			MissilNave missil = new MissilNave();
			MissilNave missil2 = new MissilNave();

			// Chama o metodo Inicializar da Class (Missil.java), para cada
			// objeto com passagem de parametros.
			missil.Inicializar(
					nave.xCoordenada + nave.getAjustaCoordenadaMissilX(),
					nave.yCoordenada + nave.getAjustaCoordenadaMissilY());

			missil2.Inicializar(
					nave.xCoordenada + nave.getAjustaCoordenadaMissilX(),
					nave.yCoordenada + nave.getAjustaCoordenadaMissilY() + 60);

			// Adiciona os objetos a lista.
			ListaMissilNave.add(missil);
			ListaMissilNave.add(missil2);
		}
	}

	/**
	 * Cria um novo InimigoUFO, em um determinado tempo.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void criaNovoInimigoUFO(long tempoJogo) {
		if (tempoJogo - InimigoUFO.tempoPassadoCriouInimigos >= InimigoUFO.tempoEntreNovosInimigos) {

			// Instância um objeto da Class InimigoUFO.java
			InimigoUFO inimigoUFO = new InimigoUFO();

			int xCoordinate = Framework.frameLargura;

			// Define a Coordenada Aleatória em que será criado.
			int yCoordinate = random.nextInt(Framework.frameAltura
					- InimigoUFO.imgInimigoUFO.getHeight());

			inimigoUFO.Inicializar(xCoordinate, yCoordinate);

			// Adicionar inimigo criado para a lista de inimigos.
			ListaInimigosUFO.add(inimigoUFO);

			// Acelerar a velocidade do inimigo e aperece.
			InimigoUFO.speedUp();

			// Define novo horário para último inimigo criado.
			InimigoUFO.tempoPassadoCriouInimigos = tempoJogo;
		}
	}

	/**
	 * Cria um novo InimigoSpikey, em um determinado tempo.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void criaNovoInimigoSpikey(long tempoJogo) {
		if (minutos >= 1) {
			if (tempoJogo - InimigoSpikey.tempoPassadoCriouInimigos >= InimigoSpikey.tempoEntreNovosInimigos) {

				// Instância um objeto da Class InimigoSpikey.java
				InimigoSpikey inimigoSpikey = new InimigoSpikey();

				int xCoordinate = Framework.frameLargura;

				// Define a Coordenada Aleatória em que será criado.
				int yCoordinate = random.nextInt(Framework.frameAltura
						- InimigoSpikey.imgInimigoSpikey.getHeight());

				inimigoSpikey.Inicializar(xCoordinate, yCoordinate);

				// Adicionar inimigo criado para a lista de inimigos.
				ListaInimigosSpikey.add(inimigoSpikey);

				// Acelerar a velocidade do inimigo e aperece.
				InimigoSpikey.speedUp();

				// Define novo horário para último inimigo criado.
				InimigoSpikey.tempoPassadoCriouInimigos = tempoJogo;
			}
		}
	}

	/**
	 * Cria um novo PowerUp, em um determinado tempo.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void criaNovoPowerUp(long tempoJogo) {
		// Condição para criar PowerUp.
		if (gerarPowerUp >= 15 && contPowerUpAdd <= 30 && contPowerUpAdd == 0) {

			// Instância um objeto da Class PowerUp.java
			PowerUpLeiser powerUp = new PowerUpLeiser();

			int xCoordenada = Framework.frameLargura;

			// Define a Coordenada Aleatória em que será criado.
			int yCoordenada = random.nextInt(Framework.frameAltura
					- PowerUpLeiser.imgPowerUpLeiser.getHeight());

			powerUp.Inicializar(xCoordenada, yCoordenada);

			// Adicionar PowerUp criado para a lista de PowerUp.
			ListaPoewrUpLeiser.add(powerUp);

			// Acelerar a velocidade do inimigo e aperece.
			PowerUpLeiser.speedUp();

			// Define novo horário para último inimigo criado.
			PowerUpLeiser.tempoPassadoCriouPowerUpLeiser = tempoJogo;

			// Reinicia PowerUp.
			gerarPowerUp = 0;
		}
		if (gerarPowerUp > 30 && contPowerUpAdd <= 60 && contPowerUpAdd == 1) {

			// Instância um objeto da Class PowerUp.java
			PowerUpLeiser powerUp = new PowerUpLeiser();

			int xCoordenada = Framework.frameLargura;

			// Define a Coordenada Aleatória em que será criado.
			int yCoordenada = random.nextInt(Framework.frameAltura
					- PowerUpLeiser.imgPowerUpLeiser.getHeight());

			powerUp.Inicializar(xCoordenada, yCoordenada);

			// Adicionar PowerUp criado para a lista de PowerUp.
			ListaPoewrUpLeiser.add(powerUp);

			// Acelerar a velocidade do inimigo e aperece.
			PowerUpLeiser.speedUp();

			// Define novo horário para último inimigo criado.
			PowerUpLeiser.tempoPassadoCriouPowerUpLeiser = tempoJogo;

			// Reinicia PowerUp.
			gerarPowerUp = 0;
		}
		if (gerarPowerUp > 60 && contPowerUpAdd >= 2) {

			// Instância um objeto da Class PowerUp.java
			PowerUpLeiser powerUp = new PowerUpLeiser();

			int xCoordenada = Framework.frameLargura;

			// Define a Coordenada Aleatória em que será criado.
			int yCoordenada = random.nextInt(Framework.frameAltura
					- PowerUpLeiser.imgPowerUpLeiser.getHeight());

			powerUp.Inicializar(xCoordenada, yCoordenada);

			// Adicionar PowerUp criado para a lista de PowerUp.
			ListaPoewrUpLeiser.add(powerUp);

			// Acelerar a velocidade do inimigo e aperece.
			PowerUpLeiser.speedUp();

			// Define novo horário para último inimigo criado.
			PowerUpLeiser.tempoPassadoCriouPowerUpLeiser = tempoJogo;

			// Reinicia PowerUp.
			gerarPowerUp = 0;
		}
	}

	/**
	 * Cria uma nova Vida, em um determinado tempo.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void criaNovaVida(long tempoJogo) {
		// Condição para criar Vida.
		if (gerarVida >= 100 && ContinueVida == 0) {
			// Instância um objeto da Class PowerUp.java
			Vida vida = new Vida();

			int xCoordenada = Framework.frameLargura;

			// Define a Coordenada Aleatória em que será criado.
			int yCoordenada = random.nextInt(Framework.frameAltura
					- Vida.imgVida.getHeight());

			vida.Inicializar(xCoordenada, yCoordenada);

			// Adicionar Vida criado para a lista de Vida.
			ListaVida.add(vida);

			// Acelerar a velocidade do inimigo e aperece.
			Vida.speedUp();

			// Define novo horário para último inimigo criado.
			Vida.tempoPassadoCriouVida = tempoJogo;

			// Reinicia Variavel gerarVida.
			gerarVida = 0;
		}
		// Condição para criar Vida.
		if (gerarVida >= 200 && ContinueVida >= 1) {
			// Instância um objeto da Class PowerUp.java
			Vida vida = new Vida();

			int xCoordenada = Framework.frameLargura;

			// Define a Coordenada Aleatória em que será criado.
			int yCoordenada = random.nextInt(Framework.frameAltura
					- Vida.imgVida.getHeight());

			vida.Inicializar(xCoordenada, yCoordenada);

			// Adicionar Vida criado para a lista de Vida.
			ListaVida.add(vida);

			// Acelerar a velocidade do inimigo e aperece.
			Vida.speedUp();

			// Define novo horário para último inimigo criado.
			Vida.tempoPassadoCriouVida = tempoJogo;

			// Reinicia Variavel gerarVida.
			gerarVida = 0;
		}
	}

	/**
	 * Cria uma nova CaixaMissil, em um determinado tempo.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void criaNovaCaixaMissil(long tempoJogo) {
		// Condição para criar CaixaMissil.
		if (gerarCaixaMissil >= 1 && contCaixaMissilAdd <= 30
				&& contCaixaMissilAdd == 0) {

			// Instância um objeto da Class CaixaMissil.java
			CaixaMissil caixaMissil = new CaixaMissil();

			int xCoordenada = Framework.frameLargura;

			// Define a Coordenada Aleatória em que será criado.
			int yCoordenada = random.nextInt(Framework.frameAltura
					- CaixaMissil.imgCaixaMissil.getHeight());

			caixaMissil.Inicializar(xCoordenada, yCoordenada);

			// Adicionar CAixaMissil criado para a lista de CaixaMissil.
			ListaCaixaMissil.add(caixaMissil);

			// Acelerar a velocidade do inimigo e aperece.
			CaixaMissil.speedUp();

			// Define novo horário para último inimigo criado.
			CaixaMissil.tempoPassadoCriouCaixaMissil = tempoJogo;

			// Reinicia CaixaMissil.
			gerarCaixaMissil = 0;
		}
		if (gerarCaixaMissil > 30 && contCaixaMissilAdd <= 60
				&& contCaixaMissilAdd == 1) {

			// Instância um objeto da Class CaixaMissil.java
			CaixaMissil caixaMissil = new CaixaMissil();

			int xCoordenada = Framework.frameLargura;

			// Define a Coordenada Aleatória em que será criado.
			int yCoordenada = random.nextInt(Framework.frameAltura
					- CaixaMissil.imgCaixaMissil.getHeight());

			caixaMissil.Inicializar(xCoordenada, yCoordenada);

			// Adicionar CAixaMissil criado para a lista de CaixaMissil.
			ListaCaixaMissil.add(caixaMissil);

			// Acelerar a velocidade do inimigo e aperece.
			CaixaMissil.speedUp();

			// Define novo horário para último inimigo criado.
			CaixaMissil.tempoPassadoCriouCaixaMissil = tempoJogo;

			// Reinicia CaixaMissil.
			gerarCaixaMissil = 0;
		}
		if (gerarCaixaMissil > 60 && contCaixaMissilAdd >= 2) {

			// Instância um objeto da Class CaixaMissil.java
			CaixaMissil caixaMissil = new CaixaMissil();

			int xCoordenada = Framework.frameLargura;

			// Define a Coordenada Aleatória em que será criado.
			int yCoordenada = random.nextInt(Framework.frameAltura
					- CaixaMissil.imgCaixaMissil.getHeight());

			caixaMissil.Inicializar(xCoordenada, yCoordenada);

			// Adicionar CAixaMissil criado para a lista de CaixaMissil.
			ListaCaixaMissil.add(caixaMissil);

			// Acelerar a velocidade do inimigo e aperece.
			CaixaMissil.speedUp();

			// Define novo horário para último inimigo criado.
			CaixaMissil.tempoPassadoCriouCaixaMissil = tempoJogo;

			// Reinicia CaixaMissil.
			gerarCaixaMissil = 0;
		}
	}

	/**
	 * Atualiza todos os inimigosUFO. Atualizações animações componentes da
	 * nave. Verifica se inimigo foi destruído. Verifica se ouve qualquer
	 * colisão do inimigo com o jogador.
	 */
	private void ColisaoNaveComInimigosUFO() {
		for (int i = 0; i < ListaInimigosUFO.size(); i++) {

			// Instância um objeto da Class InimigoUFO.
			InimigoUFO inimigoUFO = ListaInimigosUFO.get(i);

			// Atualizar InimigoUFO.
			inimigoUFO.Atualizar();

			// Instância um objeto da class Rectangle, com parametros do objeto
			// nave.
			Rectangle naveRectangel = new Rectangle(nave.xCoordenada,
					nave.yCoordenada, nave.getImgNave().getWidth(), nave
							.getImgNave().getHeight());

			// Instância um objeto da class Rectangle, com parametros do objeto
			// inimigoUFO.
			Rectangle inimigoUFORectangel = new Rectangle(
					inimigoUFO.xCoordenada, inimigoUFO.yCoordenada,
					InimigoUFO.imgInimigoUFO.getWidth(),
					InimigoUFO.imgInimigoUFO.getHeight());

			// Se a Nave Colidio com o Inimigo.
			if (naveRectangel.intersects(inimigoUFORectangel)) {
				// Reduz Saúde da Nave.
				nave.setSaudeAtual(nave.getSaudeAtual()
						- inimigoUFO.danoColisao);

				// Reduz Tamanho da Barra de Saúde da Nave.
				nave.setBarraSaudeAtual(nave.getBarraSaudeAtual()
						- inimigoUFO.danoColisao);

				// Remover inimigo da lista.
				ListaInimigosUFO.remove(i);

				// Reduz Pontuação.
				perdePontos++;

				// Remove PowerUp.
				if (contPowerUpAdd >= 1) {
					contPowerUpAdd--;
				}
				// Adicionar Explosão na Nave.
				if (nave.getSaudeAtual() <= 0) {
					for (int exNum = 0; exNum < 3; exNum++) {
						Efeito expAnim = new Efeito(imgExplosao2, 130, 130, 8,
								45, false, nave.xCoordenada + exNum * 60,
								nave.yCoordenada - random.nextInt(100), exNum
										* 200 + random.nextInt(100));
						ListaExplosao.add(expAnim);
					}
				}
				// Explosão no InimigoUFO.
				for (int exNum = 0; exNum < 3; exNum++) {
					Efeito expAnim = new Efeito(imgExplosao2, 130, 130, 8, 45,
							false, inimigoUFO.xCoordenada + exNum * 60,
							inimigoUFO.yCoordenada - random.nextInt(100), exNum
									* 200 + random.nextInt(100));
					ListaExplosao.add(expAnim);
				}

				// Nave Colidio com Inimigo e Escudo Danificado jogo GAMEOVER,
				// não precisa verificar outros inimigos.
				break;
			}
			// Verifique Saúde do InimigoUFO (Inimigo Destruido).
			if (inimigoUFO.saude <= 0) {
				// Adicionar explosão.
				Efeito expAnim = new Efeito(imgExplosao, 134, 134, 12, 45,
						false, inimigoUFO.xCoordenada, inimigoUFO.yCoordenada
								- imgExplosao.getHeight() / 3, 0);

				// Adiciona EfExplosão a Lista de Explosão.
				ListaExplosao.add(expAnim);

				// Remove Inimigo da Lista
				ListaInimigosUFO.remove(i);

				// Ganha Pontos (Inimigo Destruido).
				ganhaPontos++;

				// Conta inimigos mortos para poder liberar powerUp.
				gerarPowerUp++;

				// Conta inimigos mortos para poder liberar powerUp.
				gerarCaixaMissil++;

				// Conta inimigos mortos para poder gerar vida.
				gerarVida++;

				// Inimigo foi destruído passa para o próximo Inimigo.
				continue;
			}
			// Se o Inimigo atual não está na tela, remove-o da lista e
			// atualizar a variável InimigosNaoAbatidos.
			if (inimigoUFO.isLeftScreen()) {
				ListaInimigosUFO.remove(i);
				// Perde Pontos
				perdePontos++;
			}
		}
	}

	/**
	 * Atualiza todos os inimigosSpikey. Atualizações animações componentes da
	 * nave. Verifica se inimigo foi destruído. Verifica se ouve qualquer
	 * colisão do inimigo com o jogador.
	 */
	private void ColisaoNaveComInimigoSpikey() {
		for (int i = 0; i < ListaInimigosSpikey.size(); i++) {

			// Intância um objeto da Class InimigoSpeikey.
			InimigoSpikey inimigoSpikey = ListaInimigosSpikey.get(i);

			// Atualizar InimigoSpikey.
			inimigoSpikey.Atualizar();

			// Instância um objeto da class Rectangle, com parametros do objeto
			// nave.
			Rectangle naveRectangel = new Rectangle(nave.xCoordenada,
					nave.yCoordenada, nave.getImgNave().getWidth(), nave
							.getImgNave().getHeight());

			// Instância um objeto da class Rectangle, com parametros do objeto
			// inimigoSpikey.
			Rectangle inimigoSpikeyRectangel = new Rectangle(
					inimigoSpikey.xCoordenada, inimigoSpikey.yCoordenada,
					InimigoSpikey.imgInimigoSpikey.getWidth(),
					InimigoSpikey.imgInimigoSpikey.getHeight());

			// Nave Colidio com o InimigoSpikey.
			if (naveRectangel.intersects(inimigoSpikeyRectangel)) {
				// Reduz Saúde da Nave.
				nave.setSaudeAtual(nave.getSaudeAtual()
						- inimigoSpikey.danoColisao);

				// Reduz Tamanho da Barra de Saúde da Nave.
				nave.setBarraSaudeAtual(nave.getBarraSaudeAtual()
						- inimigoSpikey.danoColisao);

				// Remover inimigo da lista.
				ListaInimigosSpikey.remove(i);

				// Reduz Pontuação.
				perdePontos++;

				// Remove powerUp;
				if (contPowerUpAdd >= 1) {
					contPowerUpAdd--;
				}

				// Condição para Adicionar Explosão na Nave.
				if (nave.getSaudeAtual() <= 0) {
					for (int exNum = 0; exNum < 3; exNum++) {
						Efeito expAnim = new Efeito(imgExplosao, 134, 134, 12,
								45, false, nave.xCoordenada + exNum * 60,
								nave.yCoordenada - random.nextInt(100), exNum
										* 200 + random.nextInt(100));
						ListaExplosao.add(expAnim);
					}
				}

				// Adicionar Escudo na Nave.
				if (nave.getSaudeAtual() > 0) {
					for (int exNum = 0; exNum < 3; exNum++) {
						Efeito expAnim = new Efeito(imgEscudo, 145, 145, 10,
								45, false, nave.xCoordenada, nave.yCoordenada,
								exNum);
						ListaEscudo.add(expAnim);
					}
				}

				// Adicionar Explosão no InimigoSpikey.
				for (int exNum = 0; exNum < 3; exNum++) {
					Efeito expAnim = new Efeito(imgExplosao, 134, 134, 12, 45,
							false, inimigoSpikey.xCoordenada + exNum * 60,
							inimigoSpikey.yCoordenada - random.nextInt(100),
							exNum * 200 + random.nextInt(100));
					ListaExplosao.add(expAnim);
				}

				// Nave Colidio com Inimigo e Escudo Danificado jogo GAMEOVER,
				// não precisa verificar outros inimigos.
				break;
			}

			// Verifique Saúde do InimigoSpikey.
			if (inimigoSpikey.saude <= 0) {
				// Adicionar explosão. Subseqüência 1/3 explosão altura da
				// imagem (explosionAnimImg.getHeight () / 3), de modo que
				// explosão é desenhado mais no centro.
				Efeito expAnim = new Efeito(
						imgExplosao,
						134,
						134,
						12,
						45,
						false,
						inimigoSpikey.xCoordenada,
						inimigoSpikey.yCoordenada - imgExplosao.getHeight() / 3,
						0);

				// Adiciona EfExplosão a Lista de Explosão.
				ListaExplosao.add(expAnim);

				// Remove Inimigo da Lista
				ListaInimigosSpikey.remove(i);

				// Ganha Pontos (quando inimigo é destruido).
				ganhaPontos++;

				// Conta inimigos destridos para poder gerar powerUp.
				gerarPowerUp++;

				// Conta inimigos destridos para poder gerar vida.
				gerarVida++;

				// Inimigo foi destruído para que possa passar para o
				// próximo Inimigo.
				continue;
			}

			// Se o Inimigo atual não está na tela, remove da lista e atualizar
			// a variável InimigosNaoAbatidos.
			if (inimigoSpikey.isLeftScreen()) {
				ListaInimigosSpikey.remove(i);

				// Perde Pontos.
				perdePontos++;
			}
		}
	}

	/**
	 * Atualiza todos os powerUp. Atualizações animações componentes da nave.
	 * Verifica se powerUp foi intercepetado.
	 */
	private void ColisaoNaveComPowerUp() {
		for (int i = 0; i < ListaPoewrUpLeiser.size(); i++) {

			// Intância um objeto da Class PowerUp.java
			PowerUpLeiser powerUp = ListaPoewrUpLeiser.get(i);

			// Atualizar PowerUp.
			powerUp.Atualizar();

			// Instância um objeto da class Rectangle, com parametros do objeto
			// nave.
			Rectangle naveRectangel = new Rectangle(nave.xCoordenada,
					nave.yCoordenada, nave.getImgNave().getWidth(), nave
							.getImgNave().getHeight());

			// Instância um objeto da class Rectangle, com parametros do objeto
			// powerUp.
			Rectangle powerUpRectangel = new Rectangle(powerUp.xCoordenada,
					powerUp.yCoordenada,
					PowerUpLeiser.imgPowerUpLeiser.getWidth(),
					PowerUpLeiser.imgPowerUpLeiser.getHeight());

			// Se a Nave Interceptou com o PowerUp.
			if (naveRectangel.intersects(powerUpRectangel)) {
				// Aumenta PowerUp do Leiser da Nave.

				// Remover PowerUp da lista.
				ListaPoewrUpLeiser.remove(i);

				// Adicionar EfAdd PowerUp na Nave.
				for (int exNum = 0; exNum < 3; exNum++) {
					Efeito expAnim = new Efeito(imgEfAdd, 200, 200, 10, 45,
							false, nave.xCoordenada - 50,
							nave.yCoordenada - 50, exNum);
					ListaEfAdd.add(expAnim);
				}

				// Incrementa PowerUp ao liser da Nave.
				contPowerUpAdd++;

				// Ganha Pontuação Extra qundo pega powerUp.
				ganhaPontos = ganhaPontos + 5;

				break;
			}
			// Se o PowerUp atual não está na tela, remove-o da lista e
			// atualizar.
			if (powerUp.isLeftScreen()) {
				// Remover PowerUp da lista.
				ListaPoewrUpLeiser.remove(i);
			}
		}
	}

	/**
	 * Atualizações - Verifica se vida foi intercepetado.
	 */
	private void ColisaoNaveComVida() {
		for (int i = 0; i < ListaVida.size(); i++) {

			// Intância um objeto da Class Vida.java
			Vida vida = ListaVida.get(i);

			// Atualizar Vida.
			vida.Atualizar();

			// Instância um objeto da class Rectangle, com parametros do objeto
			// nave.
			Rectangle naveRectangel = new Rectangle(nave.xCoordenada,
					nave.yCoordenada, nave.getImgNave().getWidth(), nave
							.getImgNave().getHeight());

			// Instância um objeto da class Rectangle, com parametros do objeto
			// vida.
			Rectangle vidaRectangel = new Rectangle(vida.xCoordenada,
					vida.yCoordenada, Vida.imgVida.getWidth(),
					Vida.imgVida.getHeight());

			// Se a Nave Interceptou com a Vida.
			if (naveRectangel.intersects(vidaRectangel)) {
				// Aumenta vida da Nave.

				// Remover vida da lista.
				ListaVida.remove(i);

				// Adicionar EfAdd Vida na Nave.
				for (int exNum = 0; exNum < 3; exNum++) {
					Efeito expAnim = new Efeito(imgEfAdd2, 128, 300, 5, 30,
							false, nave.xCoordenada, nave.yCoordenada - 50,
							exNum);
					ListaEfAdd.add(expAnim);
				}

				// Incrementa Vida.
				if (nave.getSaudeAtual() == 100) {
					// Incrementa Vida Extra.
					ContinueVida++;
				} else {
					nave.setSaudeAtual(100);
				}

				// Ganha Pontuação Extra.
				ganhaPontos = ganhaPontos + 10;

				// Nave Interceptor Vida e Escudo Danificado jogo GAMEOVER,
				// não precisa verificar outros inimigos.
				break;
			}

			// Se o Vida atual não está na tela, remove da lista e atualizar.
			if (vida.isLeftScreen()) {
				ListaVida.remove(i);
			}
		}
	}

	/**
	 * Atualiza todos as CaixasMissil. Atualizações animações componentes da
	 * nave. Verifica se caixaMissil foi intercepetada.
	 */
	private void ColisaoNaveComCaixaMissil() {
		for (int i = 0; i < ListaCaixaMissil.size(); i++) {

			// Intância um objeto da Class CaixaMissil.java
			CaixaMissil caixaMissil = ListaCaixaMissil.get(i);

			// Atualizar CaixaMissil.
			caixaMissil.Atualizar();

			// Instância um objeto da class Rectangle, com parametros do objeto
			// nave.
			Rectangle naveRectangel = new Rectangle(nave.xCoordenada,
					nave.yCoordenada, nave.getImgNave().getWidth(), nave
							.getImgNave().getHeight());

			// Instância um objeto da class Rectangle, com parametros do objeto
			// caixaMissil.
			Rectangle caixaMissilRectangel = new Rectangle(
					caixaMissil.xCoordenada, caixaMissil.yCoordenada,
					CaixaMissil.imgCaixaMissil.getWidth(),
					CaixaMissil.imgCaixaMissil.getHeight());

			// Se a Nave Interceptou com o CaixaMissil.
			if (naveRectangel.intersects(caixaMissilRectangel)) {
				// Ativa Missil da Nave.

				// Remover CaixaMissil da lista.
				ListaCaixaMissil.remove(i);

				// Adicionar EfAdd CaixaMissil na Nave.
				for (int exNum = 0; exNum < 3; exNum++) {
					Efeito expAnim = new Efeito(imgEfAdd, 200, 200, 10, 45,
							false, nave.xCoordenada - 50,
							nave.yCoordenada - 50, exNum);
					ListaEfAdd.add(expAnim);
				}

				// Incrementa.
				contCaixaMissilAdd++;

				// Ganha Pontuação Extra qundo pega powerUp.
				ganhaPontos = ganhaPontos + 25;

				// Ativa Missil
				ativarMissil = true;

				break;
			}
			// Se o CaixaMissil atual não está na tela, remove-o da lista e
			// atualizar.
			if (caixaMissil.isLeftScreen()) {
				// Remover CaixaMissil da lista.
				ListaCaixaMissil.remove(i);
			}
		}
	}

	/**
	 * Atualize Leiser. Ele se move de Leiser e adicionar fumaça por trás dele.
	 * Verifica se o Leiser é deixou a tela. Verifica se algum Leiser é bateu
	 * qualquer inimigo.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void AtualizarLeiserNave(long tempoJogo) {
		for (int i = 0; i < ListaLeiserNave.size(); i++) {

			// Instância um objeto da Class Leiser.
			LeiserNave leiser = ListaLeiserNave.get(i);

			// Move o Leiser.
			leiser.Atualizar();

			// Verifica quando sai da tela.
			if (leiser.saiuDaTela()) {
				ListaLeiserNave.remove(i);

				// Leiser saiu da tela para que o remova da lista e agora
				// pode dispara o próximo Leiser.
				continue;
			}

			// Verifica se Leiser atual colidio com qualquer inimigo.
			if (ColizaoLeiserComInimigos(leiser))
				// Leiser também foi destruído e remove.
				ListaLeiserNave.remove(i);
		}
	}

	/**
	 * Atualize Leiser Inimigo. Move Leiser. Verifica se o Leiser é saiu da
	 * tela. Verifica se algum Leiser é colidiu com a nave.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void AtualizarLeiserInimigo(long tempoJogo) {
		for (int i = 0; i < ListaLeiserInimigo.size(); i++) {

			// Instância um objeto da Class Leiser.
			LeiserInimigo leiserInimigo = ListaLeiserInimigo.get(i);

			// Move o LeiserInimigo.
			leiserInimigo.Atualizar();

			// Verifica quando sai da tela.
			if (leiserInimigo.saiuDaTela()) {
				ListaLeiserInimigo.remove(i);

				// LeiserInimigo saiu da tela para que o remova da lista e agora
				// pode dispara o próximo LeiserInimigo.
				continue;
			}

			// Verifica se LeiserInimigo atual colidio com qualquer inimigo.
			if (ColizaoLeiserInimigoComNave(leiserInimigo))
				// LeiserInimigo também foi destruído e remove.
				ListaLeiserInimigo.remove(i);
		}
	}

	/**
	 * Atualize Misseis. Ele se move de foguetes e adicionar fumaça por trás
	 * dele. Verifica se o foguete é deixado na tela. Verifica se algum foguete
	 * é bater qualquer inimigo.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void AtualizarMissilNave(long tempoJogo) {
		for (int i = 0; i < ListaMissilNave.size(); i++) {
			MissilNave missil = ListaMissilNave.get(i);

			// Move o Missil.
			missil.Atualizar();

			// Verifica o Missil saiu da tela.
			if (missil.saiudaTela()) {
				ListaMissilNave.remove(i);

				// Missil saiu da tela para que o remova da lista e agora
				// pode dispara o próximo Missil.
				continue;
			}

			// Cria uma fumaça efeito para Missil.
			EfMissil rs = new EfMissil();

			// Subtrair o tamanho da imagem fumaça do Missil
			// (rocketSmokeImg.getWidth ()), de modo que a fumaça não é
			// desenhada sob / por trás da imagem de foguete.
			int xCoordinate = missil.xCoordenada
					- EfMissil.imgEfeitoMissil.getWidth();

			// Subtrair 5 de modo que fum estará no meio do foguete em y de
			// coordenadas. Nós rendomly adicionar um número entre 0 e 6, de
			// modo que a linha de fumaça não é linha reta.
			int yCoordinte = missil.yCoordenada - 5 + random.nextInt(6);

			rs.Initialize(xCoordinate, yCoordinte, tempoJogo,
					missil.tempoAtualDaFumaca);
			ListaEfeitoMissil.add(rs);

			// Porque o foguete está rapidamente chegarmos espaço vazio entre
			// fumaças de modo
			// Precisamos adicionar mais fumaça.
			// Quanto maior é a velocidade dos foguetes, maiores são espaços
			// vazios.
			// Vamos tirar essa fumaça um pouco à frente do que nós desenhar
			// Antes.
			int smokePositionX = 5 + random.nextInt(8);

			rs = new EfMissil();

			xCoordinate = missil.xCoordenada
					- EfMissil.imgEfeitoMissil.getWidth() + smokePositionX;
			// Aqui nós precisamos adicionar para que a fumaça não vai estar na
			// mesma x
			// Coordenada como fumaça anterior. Primeiro, precisamos adicionar 5
			// porque nós
			// Adiciona número aleatório de 0 a 8 e se o número aleatório é 0
			// que
			// Seria na mesma coordenada como fumaça antes.

			// Subtrair 5 de modo que fum estará no meio do foguete em y
			// Coordenadas. Nós rendomly adicionar um número entre 0 e 6, de
			// modo que o Linha fumo não é linha reta.
			yCoordinte = missil.yCoordenada - 5 + random.nextInt(6);
			rs.Initialize(xCoordinate, yCoordinte, tempoJogo,
					missil.tempoAtualDaFumaca);
			ListaEfeitoMissil.add(rs);

			// Aumente o tempo de vida para a próxima peça de fumaça de
			// foguetes.
			missil.tempoAtualDaFumaca *= 1.02;

			// Verifica se foguete atual bater qualquer inimigo.
			if (ColizaoMissilComInimigos(missil))
				// Foguete também foi destruída para que removê-lo.
				ListaMissilNave.remove(i);
		}
	}

	/**
	 * Verifica se o Leiser atingiu qualquer um dos inimigos.
	 * 
	 * @param LeiserInimigo
	 *            Leiser to check.
	 * @return True se ele colidir com qualquer um dos inimigos, false caso
	 *         contrário.
	 */
	private boolean ColizaoLeiserComInimigos(LeiserNave leiser) {
		boolean didItHitEnemy = false;

		// Retângulo Liser atual.
		Rectangle leiserRectangle = new Rectangle(leiser.xCoordenada,
				leiser.yCoordenada, 2, LeiserNave.imgLeiser.getHeight());

		// Percorre todos os inimigosUFO.
		for (int j = 0; j < ListaInimigosUFO.size(); j++) {
			InimigoUFO inimigoUFO = ListaInimigosUFO.get(j);

			// Retângulo atual inimigoUFO.
			Rectangle InimigoUFORectangel = new Rectangle(
					inimigoUFO.xCoordenada, inimigoUFO.yCoordenada,
					InimigoUFO.imgInimigoUFO.getWidth(),
					InimigoUFO.imgInimigoUFO.getHeight());

			// Se Leiser Interceptou Inimigo.
			if (leiserRectangle.intersects(InimigoUFORectangel)) {
				didItHitEnemy = true;

				// Leiser quando acerta o inimigo, reduzir a saúde do inimigo.
				inimigoUFO.saude -= LeiserNave.danoDeEnergia;

				// Adicionar EfImpacto no Inimigo.
				for (int efI = 0; efI < 8; efI++) {
					Efeito efIAnim = new Efeito(imgEfImpacto, 64, 64, 8, 0,
							false, inimigoUFO.xCoordenada - 40,
							inimigoUFO.yCoordenada, efI);

					// Adiciona EfLeiser a Lista de EfLeiser.
					ListaEfImpacto.add(efIAnim);
				}

				// Leiser atingiu inimigo, não precisa verificar outros
				// inimigos.
				break;
			}
		}
		// Percorre todos os inimigosSpikey.
		for (int j = 0; j < ListaInimigosSpikey.size(); j++) {
			InimigoSpikey inimigoSpikey = ListaInimigosSpikey.get(j);

			// Retângulo atual inimigoSpikey.
			Rectangle spikeyRectangel = new Rectangle(
					inimigoSpikey.xCoordenada, inimigoSpikey.yCoordenada,
					InimigoSpikey.imgInimigoSpikey.getWidth(),
					InimigoSpikey.imgInimigoSpikey.getHeight());

			// Se Leiser Interceptou Inimigo.
			if (leiserRectangle.intersects(spikeyRectangel)) {
				didItHitEnemy = true;

				// Leiser quando acerta o inimigo, reduzir a sua saúde.
				inimigoSpikey.saude -= LeiserNave.danoDeEnergia;

				// Adicionar EfImpacto no Inimigo.
				for (int efI = 0; efI < 8; efI++) {
					Efeito efIAnim = new Efeito(imgEfImpacto, 64, 64, 8, 0,
							false, inimigoSpikey.xCoordenada - 40,
							inimigoSpikey.yCoordenada, efI);

					// Adiciona EfLeiser a Lista de EfLeiser.
					ListaEfImpacto.add(efIAnim);
				}

				// Leiser atingiu inimigoSpikey, não precisa verificar outros
				// inimigos.
				break;
			}
		}

		return didItHitEnemy;
	}

	/**
	 * Verifica se o LeiserInimigo atingiu a Nave.
	 * 
	 * @param LeiserInimigo
	 *            Leiser to check.
	 * @return True se LeiserInimigo colidir com a Nave, false caso contrário.
	 */
	private boolean ColizaoLeiserInimigoComNave(LeiserInimigo leiser) {

		boolean didItHitEnemy = false;

		// Retângulo LeiserInimigo.
		Rectangle leiserInimigoRectangle = new Rectangle(leiser.xCoordenada,
				leiser.yCoordenada, 2,
				LeiserInimigo.imgLeiserInimigo.getHeight());

		// Percorre todos os inimigos.
		for (int j = 0; j < ListaInimigosUFO.size(); j++) {
			InimigoUFO inimigoUFO = ListaInimigosUFO.get(j);

			// Retângulo da Nave.
			Rectangle naveRectangel = new Rectangle(nave.xCoordenada,
					nave.yCoordenada, nave.getImgNave().getWidth(), nave
							.getImgNave().getHeight());

			// Se LeiserInimigo Interceptou Nave.
			if (leiserInimigoRectangle.intersects(naveRectangel)) {
				didItHitEnemy = true;

				// LeiserInimigo quando acerta a Nave, reduzir a saúde da Nave.
				nave.setSaudeAtual(nave.getSaudeAtual()
						- LeiserInimigo.danoDeEnergia);

				// Reduz Tamanho da Barra de Saúde da Nave.
				nave.setBarraSaudeAtual(nave.getBarraSaudeAtual()
						- LeiserInimigo.danoDeEnergia);

				// Adicionar EfImpacto na Nave.
				for (int i = 0; i < 8; i++) {
					Efeito ipAnim = new Efeito(imgEfImpacto, 64, 64, 8, 0,
							false, nave.xCoordenada + 50, nave.yCoordenada, i);

					// Adiciona EfLeiser a Lista de EfLeiser.
					ListaEfImpacto.add(ipAnim);
				}

				// LeiserInimigo atingiu Nave, não precisa verificar outros
				// inimigos.
				break;
			}
		}
		return didItHitEnemy;
	}

	/**
	 * Verifica se o Missil atingiu qualquer um dos inimigos.
	 * 
	 * @param MissilNave
	 *            Missil to check.
	 * @return True se ele bater qualquer um dos helicópteros inimigos, false
	 *         caso contrário.
	 */
	private boolean ColizaoMissilComInimigos(MissilNave missil) {
		boolean didItHitEnemy = false;

		// Retângulo Missiel atual.
		Rectangle missilRectangle = new Rectangle(missil.xCoordenada,
				missil.yCoordenada, 2, MissilNave.imgMissil.getHeight());

		// Percorra todos os InimigosUFO.
		for (int j = 0; j < ListaInimigosUFO.size(); j++) {
			InimigoUFO UFO = ListaInimigosUFO.get(j);

			// Retângulo Inimigo atual.
			Rectangle enemyRectangel = new Rectangle(UFO.xCoordenada,
					UFO.yCoordenada, InimigoUFO.imgInimigoUFO.getWidth(),
					InimigoUFO.imgInimigoUFO.getHeight());

			// Missil Colidio com InimigoUFO.
			if (missilRectangle.intersects(enemyRectangel)) {
				didItHitEnemy = true;

				// Reduz Saúde do inimigoUFO.
				UFO.saude -= MissilNave.damagePower;

				// Foguete atingiu inimigoUFO de modo que não precisa verificar
				// outros inimigosUFO.
				break;
			}
		}

		// Percorre todos os InimigosSpikey.
		for (int j = 0; j < ListaInimigosSpikey.size(); j++) {
			InimigoSpikey sprikey = ListaInimigosSpikey.get(j);

			// Retângulo atual inimigoSpikey.
			Rectangle inimigoSpikeyRectangel = new Rectangle(
					sprikey.xCoordenada, sprikey.yCoordenada,
					InimigoSpikey.imgInimigoSpikey.getWidth(),
					InimigoSpikey.imgInimigoSpikey.getHeight());

			// Missil Colidio com InimigoSpikey.
			if (missilRectangle.intersects(inimigoSpikeyRectangel)) {
				didItHitEnemy = true;

				// Reduz Saúde do inimigoSpikey.
				sprikey.saude -= MissilNave.damagePower;

				// Missil atingiu inimigo, não precisa verificar outros
				// inimigos.
				break;
			}
		}
		return didItHitEnemy;
	}

	/**
	 * Atualizações Efeito Fumaça de todos os Misseis. Se o tempo de vida do
	 * fumaça é sobre Então excluí-lo da lista. Também muda a transparência de
	 * uma fumaça Imagem, para que a fumaça desapareça lentamente.
	 * 
	 * @param gameTime
	 *            Game time.
	 */
	private void atualizarEfeitoFumacaMissil(long tempoJogo) {
		for (int i = 0; i < ListaEfeitoMissil.size(); i++) {
			EfMissil efMissil = ListaEfeitoMissil.get(i);

			// É hora de remover a Efeito Fumaça Missil.
			if (efMissil.didSmokeDisapper(tempoJogo))
				ListaEfeitoMissil.remove(i);

			// Definir nova transparência imagem Fumaça do Missil.
			efMissil.updateTransparency(tempoJogo);
		}
	}

	/**
	 * Atualizações todas as animações e explosão, remover a animação quando
	 * Acabou.
	 */
	private void atualizarExplosoes() {
		for (int i = 0; i < ListaExplosao.size(); i++) {
			// Se a animação é mais que removê-lo da lista.
			if (!ListaExplosao.get(i).active)
				ListaExplosao.remove(i);
		}
	}

	/**
	 * Atualizações todas as animações do Disparo do Leiser, remover a animação
	 * quando Acabou.
	 */
	private void atualizarEfLeiser() {
		for (int i = 0; i < ListaEfLeiser.size(); i++) {
			// Se a animação é mais que removê-lo da lista.
			if (!ListaEfLeiser.get(i).active)
				ListaEfLeiser.remove(i);
		}
	}

	/**
	 * Atualizações todas as animações de Efeitos de Impactos, remover a
	 * animação quando Acabou.
	 */
	private void atualizarEfImpacto() {
		for (int i = 0; i < ListaEfImpacto.size(); i++) {
			// Se a animação é mais que removê-lo da lista.
			if (!ListaEfImpacto.get(i).active)
				ListaEfImpacto.remove(i);
		}
	}

	/**
	 * Atualizações todas as animações de Escudo, remover a animação quando
	 * acabar.
	 */
	private void atualizarEscudo() {
		for (int i = 0; i < ListaEscudo.size(); i++) {
			// Se a animação é mais que removê-lo da lista.
			if (!ListaEscudo.get(i).active)
				ListaEscudo.remove(i);
		}
	}

	/**
	 * Atualizações todas as animações de EfAdd, remover a animação quando
	 * acabar.
	 */
	private void atualizarEfAdd() {
		for (int i = 0; i < ListaEfAdd.size(); i++) {
			// Se a animação é mais que removê-lo da lista.
			if (!ListaEfAdd.get(i).active)
				ListaEfAdd.remove(i);
		}
	}

	/* #########(METODOS DE ACESSO)########### */

	/**
	 * @return the tempoCarregarJogo
	 */
	public TempoCarregarJogo getTempoCarregarJogo() {
		return tempoCarregarJogo;
	}

	/**
	 * @param tempoCarregarJogo
	 *            the tempoCarregarJogo to set
	 */
	public void setTempoCarregarJogo(TempoCarregarJogo tempoCarregarJogo) {
		this.tempoCarregarJogo = tempoCarregarJogo;
	}

	// Adicionar Escudo na Nave.
	// if (nave.getSaudeAtual() > 0) {
	// for (int exNum = 0; exNum < 3; exNum++) {
	// EfEscudo expAnim = new EfEscudo(imgEscudo, 145, 145,
	// 10, 45, false, nave.xCoordenada,
	// nave.yCoordenada, exNum);
	// ListaEscudo.add(expAnim);
	// }
	// }

}/* FIM Class */

