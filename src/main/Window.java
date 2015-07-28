package main;

import core.Framework;

import javax.swing.*;
import java.awt.*;

/**
 * @author Francisco Assis Souza Rodrigues
 * @version BETA Fevereiro/2014
 */

/**
 * Class Window.
 */
public class Window extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Método Principal que cria a aplicação.
     *
     * @param args
     */
    public static void main(String[] args) {
        // Use o fio de envio de eventos para construir a interface do usuário
        // para thread.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }

    /**
     * Construtor da class.
     */
    @SuppressWarnings("unused")
    private Window() {
        /* Título do jogo */
        this.setTitle("Odisseia");

		/* Tamanho da janela, Modo de tela cheia */
        if (true) {
			/* Desativa decorações da janela */
            this.setUndecorated(true);

			/* Coloca a janela em tela cheia */
            this.setExtendedState(Frame.MAXIMIZED_BOTH);

			/* Icone da Aplicação */
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                    Window.class.getResource("/logo.png")));
        }
		/* Modo da janela */
        else {
			/* Tamanho da janela */
            this.setSize(1024, 768);

			/* Coloca a janela no centro da tela */
            this.setLocationRelativeTo(null);

			/*
			 * Define que, essa janela não pode ser redimensionável ​​pelo
			 * usuário
			 */
            this.setResizable(false);
        }
		/* Sair da aplicação */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Cria a instância da class (Framework.java) que extende da class
		 * (Canvas.java) e coloca-lo na janela
		 */
        this.setContentPane(new Framework());

		/* Define que a janela é visivel */
        this.setVisible(true);

    }/* Fim do Construtor */

}/* Fim da Class */
