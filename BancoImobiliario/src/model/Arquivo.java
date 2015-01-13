//package model;
//
//
//public class Arquivo {
//
//}
//
//}
//
//public boolean verificaJogadorPropriedadeGrupoTodo (Jogador jogador, GrupoPropriedade grupo) {
//	
//	Terreno tmp;
//	
//	for(int i=0 ; i < this.tabuleiro.length ; i++) {
//		if(this.tabuleiro[i].getTipoDaCasaTabuleiro() == TipoCasa.terreno) {
//			tmp = (Terreno) this.tabuleiro[i];
//			if(tmp.getCor() == grupo && tmp.getProprietario() == jogador)
//				return true;
//			else
//				return false;
//		}
//	}
//	return false;
//}

package model;

import java.util.LinkedList;

class Arquivo {
	
	CasaTabuleiro[] tabuleiro;
	LinkedList<Object> listJogador;
	
	protected Arquivo () {
		tabuleiro = null;
		listJogador = null;
	}
	
	protected static Boolean gravarJogo (CasaTabuleiro[] tabuleiro, LinkedList<Object> listJogador) {
		return true;
	}

	protected static Arquivo carregarJogo (CasaTabuleiro[] tabuleiro, LinkedList<Object> listJogador) {
		Arquivo tmp = new Arquivo();
		tmp.tabuleiro = tabuleiro;
		tmp.listJogador = listJogador;
	
		return tmp;
	}
}