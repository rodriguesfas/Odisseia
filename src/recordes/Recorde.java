package recordes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

/**
 * @author Francisco de Assis de Souza Rodrigues. 27/02/2014
 */

public class Recorde {

	private String primeiroRecorde;
	private String segundoRecorde;
	private String terceiroRecorde;
	private String naoClassificou;

	private BufferedReader br;

	public Recorde() {
	}

	/* Gravar Recorede */
	public void gravarRecorde() {
		try {
			/*
			 * Instância de um Objeto da Class Java(PrintWriter
			 * "para Gravação do Arquivo"). Define o nome e a extensão do
			 * arquivo que deseja criar.
			 */
			PrintWriter out = new PrintWriter("arquivoRecorde" + ".txt");

			/* Captura os valores */
			out.println(getPrimeiroRecorde());
			out.println(getSegundoRecorde());
			out.println(getTerceiroRecorde());
			out.println(getNaoClassificou());

			/* Fecha Conexão */
			out.close();

		} catch (IOException Erro) {
			JOptionPane.showMessageDialog(null,
					"Erro! Não foi Possivel salvar seu Recorde." + Erro);
		}

	}

	/**/
	public void exibirRecordes() {
		try {
			/*
			 * Instância de um Objeto da Class java(BufferedReader
			 * "Para Leitura do Arquivo"), que Instância um Objeto da class
			 * java(FileReader) referenciando o arquivo a ser aberto.
			 */
			br = new BufferedReader(new FileReader("arquivoRecorde" + ".txt"));

			/* Ler o conteúdo do arquivo e repassa para as variaveis */
			setPrimeiroRecorde(br.readLine());
			setSegundoRecorde(br.readLine());
			setTerceiroRecorde(br.readLine());
			setNaoClassificou(br.readLine());

		} catch (IOException Erro) {
			JOptionPane.showMessageDialog(null, "Erro! ao Acessar Recordes."
					+ Erro);
		}
	}

	/**
	 * @return the primeiroRecorde
	 */
	public String getPrimeiroRecorde() {
		return primeiroRecorde;
	}

	/**
	 * @param primeiroRecorde
	 *            the primeiroRecorde to set
	 */
	public void setPrimeiroRecorde(String primeiroRecorde) {
		this.primeiroRecorde = primeiroRecorde;
	}

	/**
	 * @return the segundoRecorde
	 */
	public String getSegundoRecorde() {
		return segundoRecorde;
	}

	/**
	 * @param segundoRecorde
	 *            the segundoRecorde to set
	 */
	public void setSegundoRecorde(String segundoRecorde) {
		this.segundoRecorde = segundoRecorde;
	}

	/**
	 * @return the terceiroRecorde
	 */
	public String getTerceiroRecorde() {
		return terceiroRecorde;
	}

	/**
	 * @param terceiroRecorde
	 *            the terceiroRecorde to set
	 */
	public void setTerceiroRecorde(String terceiroRecorde) {
		this.terceiroRecorde = terceiroRecorde;
	}

	/**
	 * @return the naoClassificou
	 */
	public String getNaoClassificou() {
		return naoClassificou;
	}

	/**
	 * @param naoClassificou
	 *            the naoClassificou to set
	 */
	public void setNaoClassificou(String naoClassificou) {
		this.naoClassificou = naoClassificou;
	}
}
