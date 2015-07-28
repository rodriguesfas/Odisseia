package tempo;

import core.Framework;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Francisco Assis Souza Rodrigues
 */

public class TempoCarregarJogo extends Thread {
    public TempoCarregarJogo() {
        start();
    }

    public void run() {
        int cont = 3;
        int valor = 3;

        while (cont >= 0) {
            try {
                Thread.sleep(1000);
                System.out.println(valor);
                valor--;
            } catch (InterruptedException Erro) {
                Logger.getLogger(TempoCarregarJogo.class.getName()).log(
                        Level.SEVERE, null, Erro);
            }
            cont--;
        }
        /*Define estado do Jogo para (Jogando)*/
        Framework.estadoJogo = Framework.EstadoJogo.Jogando;
    }
}
