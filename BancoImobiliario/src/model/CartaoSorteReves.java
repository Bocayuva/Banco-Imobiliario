package model;

class CartaoSorteReves {

	private int numImage;
	private int valor;
	private String mensagem;
	private TipoCartaoSR tipo;
	private TipoAcaoCartaSR acao;
	
	protected enum TipoCartaoSR {
		sorte, reves
	}

	protected enum TipoAcaoCartaSR {
		pague, receba, recebeCadaJogador, prisao, pontoPartida
	}
	
	protected CartaoSorteReves () {
		this.numImage = 0;
		this.valor = 0;
		this.mensagem = null;
		this.tipo = null;
		this.acao = null;
	}
	
	protected CartaoSorteReves(TipoCartaoSR tipo, TipoAcaoCartaSR acao, int valor, String mensagem, int numImage) {
		this.mensagem = mensagem;
		this.tipo = tipo;
		this.acao = acao;
		this.valor = valor;
		this.numImage = numImage;
	}
	
	protected int getImagem () {
		return this.numImage;
	}
	
	protected int getValor () {
		return this.valor;
	}
	
	protected String getMensagem () {
		return this.mensagem;
	}
	
	protected TipoCartaoSR getTipoCartaoSR () {
		return this.tipo;
	}
	
	protected TipoAcaoCartaSR getAcaoCartaoSR () {
		return this.acao;
	}
}
	
