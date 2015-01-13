package model;

import model.Aluguel.TipoAluguel;
import model.Jogador.StatusJogador;

class Terreno extends Propriedade {

	/***************************************************************************
	 *** Vareaveis Locais de Terreno
	 ***************************************************************************/

	private String nome;
	private GrupoPropriedade cor;
	private Aluguel aluguel;
	private int precoCasa;
	private int precoHotel;
	private int nEdificios; /* 0 terreno, 1~4 numero de casas, 5 Hotel */

	/***************************************************************************
	 *** Estrutura de Apoio de Terreno
	 ***************************************************************************/

	protected enum GrupoPropriedade {
		rosa, azul, roxo, laranja, vermelho, amarelo, verde, cinza
	}

	/***************************************************************************
	 *** Construtor Default Locais de Terreno
	 ***************************************************************************/

	protected Terreno(String nome, GrupoPropriedade cor, int valorNominal,
			int precoCasa, int precoHotel, int precohipoteca,
			int aluguelTerreno, int aluguelCasa1, int aluguelCasa2,
			int aluguelCasa3, int aluguelCasa4, int aluguelHotel) {
		this.nEdificios = 0;
		this.nome = nome;
		this.proprietario = null;
		this.tipoCasaTabuleiro = TipoCasaTabuleiro.propriedade;
		this.tipoPropriedade = TipoPropriedade.terreno;
		this.cor = cor;
		this.valorNominal = valorNominal;
		this.valorhipoteca = precohipoteca;
		this.aVendaNoMercado = false;
		this.hipotecado = false;
		this.precoCasa = precoCasa;
		this.precoHotel = precoHotel;
		this.aluguel = new Aluguel(aluguelTerreno, aluguelCasa1, aluguelCasa2,
				aluguelCasa3, aluguelCasa4, aluguelHotel);
	}

	/***************************************************************************
	 *** Funcoes de Terreno
	 ***************************************************************************/
	// //////////////////////////////////////////////////////////////////////////
	// // Funcoes de GET
	protected int getValorAluguel(Jogador jogador) {
		if (this.hipotecado == true || this.proprietario == null
				|| this.proprietario == jogador || this.proprietario.getStatusJogador() == StatusJogador.Preso)
			return 0;
		else if (nEdificios == 0)
			return aluguel.getAluguel(TipoAluguel.terreno, 0);
		else if (nEdificios > 0 && nEdificios < 5)
			return aluguel.getAluguel(TipoAluguel.casa, nEdificios);
		else
			return aluguel.getAluguel(TipoAluguel.hotel, nEdificios);
	}

	protected int getNumEdificios() {
		return this.nEdificios;
	}

	protected int getPrecoCasa() {
		return this.precoCasa;
	}

	protected int getPrecoHotel() {
		return this.precoHotel;
	}

	protected int getPrecoHipoteca() {
		return this.valorhipoteca;
	}

	protected String getNome() {
		return this.nome;
	}

	protected Aluguel getAluguel() {
		return this.aluguel;
	}

	protected Jogador getProprietario() {
		return this.proprietario;
	}

	protected GrupoPropriedade getCor() {
		return this.cor;
	}

	// //////////////////////////////////////////////////////////////////////////
	// // Funcoes de SET
	protected boolean setAdicionarEdificio() {
		if (this.nEdificios >= 0 && this.nEdificios < 5) {
			this.nEdificios++;
			return true;
		} 
		else
			return false;

	}

	protected boolean setPorAbaixoEdificios() {
		this.nEdificios = 0;
		return true;
	}

	protected void setRetornaParaBanco() {
		this.proprietario = null;
		this.aVendaNoMercado = false;
		this.hipotecado = false;
		this.nEdificios = 0;
	}

	protected void setPorAbaixoEdificios(int qtd) {
		int numTmp = this.nEdificios - qtd;
		if (numTmp >= 0)
			this.nEdificios = numTmp;
		else
			throw new IllegalArgumentException(
					"ERRO, Numero de solicitadas para demulicao e superior ao numero construido");
	}
}