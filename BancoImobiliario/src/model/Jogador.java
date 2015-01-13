package model;

import java.util.LinkedList;

class Jogador {
	/***************************************************************************
	*** Vareavies locais de Jogador
	***************************************************************************/
	
	private	  String logo;
	private	  int idJogador;
	private   int dinheiro;
	private	  int posicaoNaRodada;
	private   int localizacaoNoTabuleiro;
	private	  int rodadasPrisao;
	private	  int cartaoSaidaLivrePrisaoVenda;
	protected boolean cartaoSaidaLivrePrisao;
	private   StatusJogador statusNaPartida;
	private   LinkedList<Object> listPropriedade;
	
	/***************************************************************************
	*** Estrutura de Apoio a Jogador
	***************************************************************************/

	protected enum StatusJogador {
		Jogando, Falido, Preso
	}
	
	/***************************************************************************
	*** Construtor Default
	***************************************************************************/
	
	public Jogador (int posicaoNaRodada, int idJogador, String logo) {
		this.posicaoNaRodada = posicaoNaRodada;
		this.logo = logo;
		this.rodadasPrisao = 0;
		this.localizacaoNoTabuleiro = 0;
		this.idJogador = idJogador;
		this.dinheiro = 1500;
		this.statusNaPartida = StatusJogador.Jogando;
		this.cartaoSaidaLivrePrisao = false;
		this.cartaoSaidaLivrePrisaoVenda = 0;
		this.listPropriedade = new LinkedList<Object>();
	}
	
	/***************************************************************************
	*** Funcoes relacionadas a Jogador
	***************************************************************************/
	////////////////////////////////////////////////////////////////////////////
	///// Funcoes de Jogador - Tipo GET
	protected int 			getID								() 
	{
		return this.idJogador;
	}
	protected int 			getPosicaoNaRodada 					() 
	{
		return this.posicaoNaRodada;
	}
	protected int 			getDinheiro 						() 
	{
		return this.dinheiro;
	}	
	protected int 			getRodadasPrisao 					() 
	{
		return this.rodadasPrisao;
	}
	protected int 			getPosicaNoTabuleiro 				() 
	{
		return this.localizacaoNoTabuleiro;
	}
	protected int 			getValorDeVendaCartaoLivraPrisao 	() 
	{
		return this.cartaoSaidaLivrePrisaoVenda;
	}
	protected boolean 		getCartaoSaidaLivrePrisao 			() 
	{
		return this.cartaoSaidaLivrePrisao;
	}
	protected String 		getLogo 							() 
	{
		return this.logo;
	}
	protected StatusJogador getStatusJogador 					() 
	{
		return this.statusNaPartida;
	}
	
	////////////////////////////////////////////////////////////////////////////
	///// Funcoes de Jogador - Tipo SET
	protected void setFalido				() 
	{
		statusNaPartida = StatusJogador.Falido;
		this.dinheiro = 0;
		this.localizacaoNoTabuleiro=0;
		this.rodadasPrisao = 0;
		this.localizacaoNoTabuleiro = 0;
		this.cartaoSaidaLivrePrisao = false;
		this.cartaoSaidaLivrePrisaoVenda = 0;
		while(listPropriedade.isEmpty()==false)
			listPropriedade.remove();
	}
	
	///// Sobre Posicao
	protected void setVaiParaPontoPartida 	() 
	{
		this.localizacaoNoTabuleiro = 0;
	}
	protected void setPosicaoNaRodada 		(int posicaoNaRodada) 
	{
		this.posicaoNaRodada = posicaoNaRodada;
	}
	protected void setPosicaNoTabuleiro 	(int numCasaTabuleiro) 
	{
		this.localizacaoNoTabuleiro = numCasaTabuleiro;
	}

	
	///// Sobre Dinheiro
	protected void setAdicionarDinheiro 	(int dinheiro)
	{
		if(dinheiro <= 0)
			throw new IllegalArgumentException("Valor adicionado invalido");
		this.dinheiro += dinheiro;
	}
	protected void setRemoverDinheiro 		(int dinheiro) 
	{
		int valor = this.dinheiro - dinheiro;
		if(dinheiro <= 0)
			throw new IllegalArgumentException("Valor invalido");
		else if( valor <= 0)
			throw new IllegalArgumentException("Jogador nao tem dinheiro suficiente para pagar o imposto");
		this.dinheiro -= dinheiro;
	}
	
	///// Sobre Prisao
	protected void setVaiPrisao 			() 
	{
		if(this.statusNaPartida == StatusJogador.Jogando) {
			this.statusNaPartida = StatusJogador.Preso;
			this.localizacaoNoTabuleiro = 10;
			this.rodadasPrisao = 0;
		}
	}
	protected void setSaiPrisao 			() 
	{
		if(this.statusNaPartida == StatusJogador.Preso) {
			this.statusNaPartida = StatusJogador.Jogando;
			this.rodadasPrisao = 0;
		}
	}
	protected void setAdicionaRodadaPrisao	() 
	{
		this.rodadasPrisao++ ;
	}

	///// Sobre Cartao Prisao
	protected void setCartaoSaidaLivrePrisao 	() 
	{
		if(this.cartaoSaidaLivrePrisao == true)
			this.cartaoSaidaLivrePrisao = false;
		else
			this.cartaoSaidaLivrePrisao = true;
		this.cartaoSaidaLivrePrisaoVenda = 0;
	}
	protected void setRemoverCartaoSaidaLivrePrisao 	() 
	{
		if(this.cartaoSaidaLivrePrisao == false)
			throw new IllegalArgumentException("ERRO, O jogador nao possui o cartao");
		
		this.cartaoSaidaLivrePrisao = true;
		this.cartaoSaidaLivrePrisaoVenda = 0;
	}
	protected void setPorVendaCartaoLivraPrisao (int valorDeVenda) 
	{
		if(dinheiro <= 0)
			throw new IllegalArgumentException("Valor adicionado invalido");
		this.cartaoSaidaLivrePrisaoVenda = valorDeVenda;
	}
	protected void setComproCartaoLivraPrisao 	(Jogador jogadorVendedor)
	{
		if(jogadorVendedor == null)
			throw new IllegalArgumentException("Jogador nao encontrado");
		else if(jogadorVendedor.getCartaoSaidaLivrePrisao() == false)
			throw new IllegalArgumentException("Jogador nao possui o cartao");

		int valor = jogadorVendedor.getValorDeVendaCartaoLivraPrisao();
		this.setRemoverDinheiro(valor);
		this.setCartaoSaidaLivrePrisao();
		jogadorVendedor.setAdicionarDinheiro(valor);
		jogadorVendedor.setCartaoSaidaLivrePrisao();
	}

	////////////////////////////////////////////////////////////////////////////
	///// Sobre Listagem de Propriedade
	// GET
	protected int  			getSizeListaPropriedade		() 
	{
		return listPropriedade.size();
	}
	protected Propriedade 	getPropriedade				(int index) 
	{
		@SuppressWarnings("unused")
		Propriedade prop = (Propriedade)listPropriedade.get(index);
		return (Propriedade) listPropriedade.get(index);
	}
	// SET
	protected void 			setAdicionarListaPropriedade(Propriedade propriedade) 
	{		
		if(propriedade == null)
			throw new IllegalArgumentException("ERRO, propriedade e NULL");
		if(propriedade.getProprietario() != this)
			throw new IllegalArgumentException("ERRO, Jogador nao e o proprietario");
		
		listPropriedade.add(propriedade);
	}
	protected void 			setRemoverListaPropriedade	(Propriedade propriedade) 
	{
		if(listPropriedade.isEmpty() == true)
			throw new IllegalArgumentException("ERRO, listagem de propriedade esta vazia");
		
		// Pecorre a listagem para encontrar a propriedade
		for(int index=0; index< listPropriedade.size() ; index++) {
			// Armazena temporariamente a propriedade da posicao (index) da lista
			Propriedade tmp = (Propriedade) listPropriedade.get(index);
			// Compara se a propriedade da listagem e a propriedade desejada
			if(tmp == propriedade)
				// Propriedade identificada, remove, remove da listagem
				listPropriedade.remove(index);
		}
	}
	
}
