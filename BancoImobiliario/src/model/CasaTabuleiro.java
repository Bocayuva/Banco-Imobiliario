package model;

abstract class CasaTabuleiro {
	
	protected TipoCasaTabuleiro tipoCasaTabuleiro;
	
	protected CasaTabuleiro () {
		this.tipoCasaTabuleiro = null;
	}
	
	public TipoCasaTabuleiro getTipoDaCasaTabuleiro(int posicaoTabuleiro) {
		if(posicaoTabuleiro < 0 || posicaoTabuleiro > 40)
			throw new IllegalArgumentException("Posicao do Tabuleiro Invalida");
		return this.tipoCasaTabuleiro;
	}
	
	public enum TipoCasaTabuleiro {
		propriedade, sorteReves, lucroDividendo, impostoRenda, prisao, vaiParaPrisao, pontoDePartida, paradaLivre;
	}
}
