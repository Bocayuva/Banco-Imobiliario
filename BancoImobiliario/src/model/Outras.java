package model;

import model.Jogador.StatusJogador;

class Outras extends CasaTabuleiro{
	
	private final static int bonusRodada = 200;
	private final static int impostoRenda = 200;
	private final static int lucroDividadendos = 200;
	
	protected Outras (TipoCasaTabuleiro tipo){
		this.tipoCasaTabuleiro = tipo;
	}
	
	protected static void pontoDePartida(Jogador jogador) {
		if(jogador == null)
			throw new IllegalArgumentException("Jogador nao encontrado");
		jogador.setAdicionarDinheiro(bonusRodada);
	}
	
	protected static void LucroDividendo(Jogador jogador) {
		if(jogador == null)
			throw new IllegalArgumentException("Jogador nao encontrado");
		jogador.setAdicionarDinheiro(lucroDividadendos);
	}
	
	protected static void ImpostoRenda(Jogador jogador) {
		if(jogador == null)
			throw new IllegalArgumentException("Jogador nao encontrado");
		jogador.setRemoverDinheiro(impostoRenda);
	}
	
	protected static void saiDaPrisao(Jogador jogador) {
		if(jogador == null)
			throw new IllegalArgumentException("Jogador nao encontrado");
		jogador.setSaiPrisao();
	}
	
	protected static void vaiParaPrisao(Jogador jogador) {
		if(jogador == null)
			throw new IllegalArgumentException("Jogador nao encontrado");
		else if(jogador.getStatusJogador() == StatusJogador.Preso)
			throw new IllegalArgumentException("Jogador ja esta preso");
		else if(jogador.getCartaoSaidaLivrePrisao() == true) {
			jogador.setCartaoSaidaLivrePrisao();
			SorteReves.retornoAoBaralhoCartaoSaidaLivrePrisao();
			throw new IllegalArgumentException("Jogador usou sua cartao Libera da Prisao");
		}
		else
			jogador.setVaiPrisao();
	}
}
