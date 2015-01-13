package model;

import java.util.ArrayList;
import java.util.Collections;

import model.CartaoSorteReves.TipoAcaoCartaSR;
import model.CartaoSorteReves.TipoCartaoSR;

class SorteReves extends CasaTabuleiro{

	/***************************************************************************
	*** Vareavies locais de SorteReves
	***************************************************************************/
	
	private static int rodada;
	private static ArrayList<Object> baralho = new ArrayList<Object>();
	protected static  boolean saidaLivrePrisao;
	
	/***************************************************************************
	*** Funcoes de SorteReves
	***************************************************************************/
	
	protected static void retornoAoBaralhoCartaoSaidaLivrePrisao () {
			saidaLivrePrisao = false;
	}
	
	protected static CartaoSorteReves getCartaoSorteReves(Jogador jogador) {

		CartaoSorteReves envio = null;
		
		if (saidaLivrePrisao == true && true == equalsSaidaLivrePrisao((CartaoSorteReves)baralho.get(0))) {
			envio = (CartaoSorteReves)baralho.get(1);
		} else { 
			if(equalsSaidaLivrePrisao((CartaoSorteReves)baralho.get(0)) == true) {
			saidaLivrePrisao = true;
			jogador.setCartaoSaidaLivrePrisao();
			}
			envio = (CartaoSorteReves)baralho.get(0);
		}
		
		atualizaBaralho();
		rodada++;
		if (rodada == baralho.size()) {
			rodada = 0;
			embaralhaCartoes();
		}
		return envio; 
	}
	
	private static void atualizaBaralho() {
		CartaoSorteReves tmp = (CartaoSorteReves)baralho.get(0);
		baralho.remove(0);
		baralho.add(tmp);
	}

	private static void embaralhaCartoes() {
		Collections.shuffle(baralho);
	}

	private static boolean equalsSaidaLivrePrisao(CartaoSorteReves c) {
		if (c.getTipoCartaoSR() == TipoCartaoSR.sorte && c.getAcaoCartaoSR() == TipoAcaoCartaSR.prisao)
			return true;
		return false;
	}
	
	/***************************************************************************
	*** Constructor Default SorteReves
	***************************************************************************/
	
	protected SorteReves() {
		tipoCasaTabuleiro = TipoCasaTabuleiro.sorteReves;
		rodada = 0;
		saidaLivrePrisao = false;
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.receba,			 80,"Um amigo tinha lhe pedido um emprestimo e se esqueceu de devolver. Ele acaba de se lembrar.", 		  0));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.receba,			150,"Houve um assalto a sua loja, mas voce estava segurado.", 											  1));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.receba,			 20,"Voce jogou na Loteria Esportiva com um grupo de amigos. Ganharam!!!", 								  2));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.receba,			100,"Voce tirou o primeiro lugar no Torneio de Tenis do seu clube. Parabens!", 							  3));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.receba,			 50,"Voce trocou seu carro usado com um amigo e ainda saiu lucrando.", 									  4));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.receba,			100,"O seu cachorro policial tirou o 1 premio na exposicao do Kennel Club.", 							  5));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.pontoPartida,		  0,"Avance ate o ponto de partida e...", 															  	  6));//ESPECIAL
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.receba,		 	 25,"A prefeitura mandou abrir uma nova avenida, para o que desapropriou varios predios. Em consequencia seu terreno valorizou.", 7));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.receba,			100,"Inesperadamente voce recebeu uma heranca que ja estava esquecida.", 								  8));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.receba,			 50,"Voce acaba de receber uma parcela do seu 13 salario.",												  9));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.receba,			 45,"Voce saiu de ferias e se hospedou na casa de um amigo. Voce economizou o hotel.", 					  10));	
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.receba,			200,"Voce esta com sorte. Suas acoes na Bolsa de Valores estao em alta.", 								  11));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.receba,			100,"Voce foi promovido a diretor da sua empresa.", 													  12));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.recebeCadaJogador,  50,"Voce apostou com os parceiros deste jogo e ganhou.", 										  13));//ESPECIAL
		baralho.add(new CartaoSorteReves(TipoCartaoSR.sorte, TipoAcaoCartaSR.prisao,			  0,"Saida livre da prisao. Conserve este cartao para quando lhe for preciso ou negocie-o como desejar.", 14)); //ESPECIAL
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague,				 15,"Um amigo pediu-lhe um emprestimo. Voce nao pode recusar.", 										  15));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague,				 25,"Voce vai casar e esta comprando um apartamento.", 													  16));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague,				 25,"Seu clube esta ampliando as piscinas. Os socios devem contribuir", 								  17));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague,				 30,"Voce estacionou seu carro em lugar proibido e entrou na contra mao.", 								  18));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague, 			 30,"Renove a tempo a licenca do seu automovel", 														  19));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague, 			 30,"Voce achou interessante assistir a estreia da temporada de ballet. Compre os ingressos.", 			  20));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague,				 40,"Papai os livros do ano passado nao servem mais, preciso de livros novos.",  						  21));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague, 			 45,"O medico lhe recomendou repouso num bom hotel de montanha.", 										  22));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague,				 45,"Seus parentes do interior vieram passar umas ferias na sua casa.", 								  23));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague, 			 50,"A geada prejuducou a sua safra de cafe.", 															  24));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague, 	 		 50,"Seus filhos ja vao para a escola. Pague a primeira mensalidade.", 									  25));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague,				 50,"Voce acaba de receber a comunicacao do Imposto de Renda.",											  26));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague, 			100,"Parabens!!! Voce convidou seus amigos para festejar o aniversario.", 								  27));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.pague, 			100,"Voce e papai outra vez!!! Despesas de maternidade.", 												  28));
		baralho.add(new CartaoSorteReves(TipoCartaoSR.reves, TipoAcaoCartaSR.prisao, 			  0,"Voce vai para a prisao sem receber nada.", 														  29));//ESPECIAL
		embaralhaCartoes();
		embaralhaCartoes();
	}
}