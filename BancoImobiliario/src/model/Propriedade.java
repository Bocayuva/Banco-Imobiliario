package model;

abstract class Propriedade extends CasaTabuleiro{
	
	/***************************************************************************
	*** Vareaveis Locais de Propriedade
	***************************************************************************/
	
	boolean hipotecado;
	protected boolean aVendaNoMercado;
	protected int valorDeVendaNoMercado;
	protected int valorNominal;	
	protected int valorhipoteca;
	protected Jogador proprietario;
	protected TipoPropriedade tipoPropriedade;
	
	/***************************************************************************
	*** Estrutura de Apoio a Propriedade
	***************************************************************************/
	
	protected enum TipoPropriedade {
		terreno, empressa
	}
	
	/***************************************************************************
	*** Funcoes de Propriedade
	***************************************************************************/
	////////////////////////////////////////////////////////////////////////////
	///// Funcoes de Propriedade - Tipo GET
	protected int getValorNominal () {
		return this.valorNominal;
	}
	protected int getValorDeVenda () {
		return this.valorDeVendaNoMercado;
	}
	protected int getValorHipoteca() {
		return this.valorhipoteca;
	}
	protected boolean getPropriedadeEstaHipotecada() {
		return this.hipotecado;
	}
	protected boolean getStatosVendaNoMercado() {
		return this.aVendaNoMercado;
	}
	protected TipoPropriedade getTipoPropriedade() {
		return tipoPropriedade;
	}
	protected Jogador getProprietario() {
		return this.proprietario;
	}
	
	////////////////////////////////////////////////////////////////////////////
	///// Funcoes de Propriedade - Tipo SET
	protected void 		setTipoPropriedade(TipoPropriedade tipoPropriedade) {
		this.tipoPropriedade = tipoPropriedade;
	}
	protected void 		setListarPropriedadeMercado(int valor) {		
		this.aVendaNoMercado = true;
		this.valorDeVendaNoMercado = valor;
	}
	protected void 		setRemoverPropriedadeMercado( ) {		
		this.aVendaNoMercado = false;
		this.valorDeVendaNoMercado = 0;
	}
	protected boolean 	setVenderPropriedade(Jogador jogador) {
			
		if(this.hipotecado == false)
		{
			jogador.setAdicionarDinheiro( this.valorNominal);
			this.proprietario = null;
			this.valorDeVendaNoMercado = 0;
			this.aVendaNoMercado = false;
		}
		else {
			return false;
		}

		return true;
	}
	protected void 	setComprarPropriedade(Jogador jogador) {
		if(jogador == null) 
			throw new IllegalArgumentException("Jogador comprador nao encontrado");
		
		if(this.proprietario == null) {
			// Comprar do Banco
			if( jogador.getDinheiro() - this.valorNominal  < 0)
				throw new IllegalArgumentException("Jogador nao possui dinheiro o suficiente");
			
			jogador.setRemoverDinheiro(this.valorNominal);
			this.proprietario = jogador;
		}
		else if( this.aVendaNoMercado == true ){
			// Comprar do Vendedor
			if( jogador.getDinheiro() - this.valorDeVendaNoMercado  < 0)
				throw new IllegalArgumentException("Jogador nao possui dinheiro suficiente");
			jogador.setRemoverDinheiro(this.valorDeVendaNoMercado);
			this.proprietario.setAdicionarDinheiro(valorDeVendaNoMercado);
			this.proprietario = jogador;
			this.aVendaNoMercado = false;
			this.valorDeVendaNoMercado = 0;
		}
		else 
			throw new IllegalArgumentException("ERRO, nao esta a venda");
	}
	protected void 		setPagarHipoteca() {			
		this.hipotecado = false;
	}
	protected void		setHipotecarPropriedade() {
		proprietario.setAdicionarDinheiro(valorhipoteca);
		this.hipotecado = true;
	}
}
