package model;

import model.Jogador.StatusJogador;

class Empresa extends Propriedade {

	private int taxa;
	private TipoCompanhia companhia;

	protected Empresa(int taxa, int valorNominal, int valorhipoteca,
			TipoCompanhia companhia) {
		this.tipoCasaTabuleiro = TipoCasaTabuleiro.propriedade;
		this.tipoPropriedade = TipoPropriedade.empressa;
		this.companhia = companhia;
		this.taxa = taxa;
		this.valorhipoteca = valorhipoteca;
		this.valorNominal = valorNominal;
		this.hipotecado = false;
		this.aVendaNoMercado = false;
		this.proprietario = null;
	}

	protected enum TipoCompanhia {
		taxi, ferroviaria, onibus, navegacao, aviacao, taxiAereo
	}

	protected int getTaxaNominal() {
		return this.taxa;
	}
	
	protected int getHipoteca() {
		return this.valorhipoteca;
	}

	protected TipoCompanhia getTipoCompanhia() {
		return this.companhia;
	}
	
	protected int getTaxaAluguel(Jogador joggadorApagar, int numDados) {
		if(proprietario == joggadorApagar || (this.proprietario != null && this.proprietario.getStatusJogador() == StatusJogador.Preso) || this.hipotecado == true)
			return 0;
		return this.taxa*numDados;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected void	setRetornaParaBanco () {
		this.proprietario = null;
		this.aVendaNoMercado = false;
		this.hipotecado = false;
	}
}
