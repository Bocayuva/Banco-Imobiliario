package model;

import java.util.LinkedList;
import java.util.Observable;

import model.CartaoSorteReves.TipoCartaoSR;
import model.Propriedade.TipoPropriedade;
import model.CasaTabuleiro.TipoCasaTabuleiro;
import model.Empresa.TipoCompanhia;
import model.Jogador.StatusJogador;
import model.Terreno.GrupoPropriedade;

public class Jogo extends Observable {

	/***************************************************************************
	 *** Vareavies Globais relacionadas Classe Jogo
	 ***************************************************************************/
	// //////////////////////////////////////////////////////////////////////////
	// /// Singleton da clase jogo
	private static Jogo jogo = null; // atributo estático para o singleton

	// //////////////////////////////////////////////////////////////////////////
	// /// Constantes dos Jogo
	public final static int qtdTotolCasasTabuleiro = 40;/*
														 * numero total de casa
														 * no tabuleiro
														 */
	public final static int titulosPropriedades = 28; /*
													 * numero total de
													 * propriedade no tabuleiro
													 */
	public final static int qtdToTalCasas = 40; /*
												 * numero maximo de casas no
												 * jogo
												 */
	public final static int qtdTotalHoteis = 12; /*
												 * numero maximo de hoteis no
												 * jogo
												 */
	public final static int numMaxJogadores = 6; /*
												 * numero maximo de jogadores no
												 * jogo
												 */

	// //////////////////////////////////////////////////////////////////////////
	// /// Estruturas do Jogo
	private static CasaTabuleiro[] tabuleiro; /* estrutura aloca o tabulueiro */
	private static LinkedList<Object> listJogador; /*
													 * estrutura aloca os
													 * jogadores
													 */

	// //////////////////////////////////////////////////////////////////////////
	// /// Vareaveis Auxiliares de controle Jogo
	// /// Relacionadas ao jogo
	private static int quantidadeJogadoresNaPartida; /*
													 * total de jogadores na
													 * partida
													 */
	private static int quantidadeJogadoresFalidos; /*
													 * total de jogadores na
													 * partida
													 */
	private static int quantidadeDisponivelCasas; /*
												 * total de casas no tabuleiro
												 * do jgo
												 */
	private static int quantidadeDisponivelHotel; /*
												 * total de hoteis no tabuleiro
												 * do jogo
												 */
	// /// Relacionadas ao jogador da rodada
	private static int numDadosJogadorDaRodada; /*
												 * Vareavel do somatorio dos
												 * dados do Jogador da Rodada
												 */
	private static int contQtdSeguidaDadosIquais; /*
												 * Vareavel de controle de
												 * numero de vezes o Jogador da
												 * Rodada se repete seguidamente
												 */
	private static boolean dadosIquaisJogadorDaRodada; /*
														 * Vareavel de iqualdade
														 * dos dados do Jogador
														 * da Rodada - TRUE:
														 * iquais FALSE:
														 * diferente
														 */
	private static Jogador jogadorDaRodada; /* Referencia do jogador da rodada */
	private static CartaoSorteReves sorteRevesDaRodada; /*
														 * Referencia ao cartao
														 * pego pelo Jogador da
														 * Rodada
														 */

	/***************************************************************************
	 *** Funcoes relacionadas Classe Jogo
	 ***************************************************************************/
	// //////////////////////////////////////////////////////////////////////////
	// /// Construtor Default
	public Jogo() {
	}

	// //////////////////////////////////////////////////////////////////////////
	// /// Singleton da clase Jogo
	public static Jogo getInstance() {
		if (jogo == null) {
			jogo = new Jogo();
			new Dado();
			new SorteReves();
			setGAMEiniciarJogo();
		}
		return jogo;
	}

	// //////////////////////////////////////////////////////////////////////////
	// /// Funcao de notificacao de Observable
	public static void setGAMEAtualiza() {
		jogo.setChanged();
		jogo.notifyObservers();
	}

	/***************************************************************************
	 *** Funcoes relacionadas ao Jogo
	 ***************************************************************************/
	// //////////////////////////////////////////////////////////////////////////
	// /// Estruturas de Apoio em Jogo
	public enum JogoTipoCasa {
		terreno, empresa, sorteReves, lucroDividendo, impostoRenda, 
		prisao, vaiParaPrisao, pontoDePartida, paradaLivre
	}

	public enum JogoTipoAcao {
		// Propriedade
		leiluarPropriedade, hipotecarPropriedade, venderPropriedadeParaBanco,
		colocarVendaPropriedade, comprarPropriedadeDoBanco, comprarPropriedadeDoJogador,
		trocaPropriedade,
		// Receber
		receberDosJogadores, receberDoSorte, receberLucroDividendo, 
		receberDoPontoDePartida, recebeCartaoLivraDaPrisao,
		// Pagar
		pagarAluguel, pagarAoBancoPrisao, pagarAoBancoReves, 
		pagarImpostoRenda, pagarHipoteca,
		// Residencia
		comprarResidencias, venderResidencias,
		// Acao de jogo
		sairDaPrisao, vaiParaPrisao, irParaPontoPartida, jogarDados
	}

	// //////////////////////////////////////////////////////////////////////////
	// /// Funcos de Apoio a Jogo - Tipo SET
	public static void setGAMEiniciarJogo() {
		quantidadeJogadoresNaPartida = 0;
		quantidadeJogadoresFalidos = 0;
		contQtdSeguidaDadosIquais = 0;
		quantidadeDisponivelCasas = qtdToTalCasas;
		quantidadeDisponivelHotel = qtdTotalHoteis;
		dadosIquaisJogadorDaRodada = false;
		sorteRevesDaRodada = null;
		jogadorDaRodada = null;
		Tabuleiro tmp = new Tabuleiro();
		tabuleiro = tmp.getTabuleiro();
		listJogador = new LinkedList<Object>();
	}

	public static void setGAMEsalvarJogo() {
		Arquivo.gravarJogo(tabuleiro, listJogador);
	}

	public static void setGAMEcarregaJogo() {
		Arquivo tmp = Arquivo.carregarJogo(tabuleiro, listJogador);
		tabuleiro = tmp.tabuleiro;
		listJogador = tmp.listJogador;
	}

	public static void setGAMEOrdenarJogadores(int jogNDados[]) {
		// Inicializa vetores de apoio
		int ordemDoJogo[] = new int[getGAMEquantidadeJogador()];
		int posicaoN[] = new int[getGAMEquantidadeJogador()];
		// Ordena de posicao dos jogadores
		for (int i = 0; i < getGAMEquantidadeJogador(); i++) {
			for (int j = i + 1; j < getGAMEquantidadeJogador(); j++) {
				if (jogNDados[j] > jogNDados[i])
					posicaoN[i]++;
				else
					posicaoN[j]++;
			}
			ordemDoJogo[posicaoN[i]] = i + 1;
		}
		// Atualiza a ordem de jogadores na classe jogador
		for (int i = 0; i < ordemDoJogo.length; i++) {
			Jogador tmp = getJogador(ordemDoJogo[i]);
			tmp.setPosicaoNaRodada(i + 1);
		}
		// Inicializa o jogador da rodada
		jogadorDaRodada = getJogadorRodada(1);
		jogo.setChanged();
		jogo.notifyObservers();
	}

	public static void setGAMEquantidadeJogadoresNaPartida(int numTotalJogadores) {
		quantidadeJogadoresNaPartida = numTotalJogadores;
		jogo.setChanged();
		jogo.notifyObservers();
	}

	public static void setGAMEadicionarJogadorNoJogo(int numJogador,
			String nomeLogo) {
		if (getGAMEquantidadeJogador() > numMaxJogadores)
			throw new IllegalArgumentException(
					"Adicao INVALIDA!!! Limite de jogadores SUPERADO");
		int posicaoOrdemJogo = numJogador;
		Jogador tmp = new Jogador(posicaoOrdemJogo, numJogador, nomeLogo);
		listJogador.add(tmp);
	}

	public static boolean setGAMEproximaRodada() {

		// Trata condicao para jogador joga novamente, caso dados sejam iquais.
		if (dadosIquaisJogadorDaRodada == false) {
			contQtdSeguidaDadosIquais = 0;
			// Atualiza o jogador da rodada
			boolean atualizouSucesso = setAtualizaJogadorDaRodada();
			/*
			 * Trata condicao fim de jogo, onde atualizouSucesso e false, ja que
			 * o jogador anterior e atual da rodada sao os mesmos, logo so
			 * existe um jogador no jogo.
			 */
			if (atualizouSucesso == false)
				return false;
		}// IF

		if (contQtdSeguidaDadosIquais == 3) {
			setJOGRODvaiParaPrisao();
			dadosIquaisJogadorDaRodada = false;
			throw new IllegalArgumentException(
					"Jogador vai para prisao, sortudo demais, 4 vezes joga novamente");
		}

		// Atualiza Rodada para o estado inicial.
		contQtdSeguidaDadosIquais++;
		numDadosJogadorDaRodada = 0;
		dadosIquaisJogadorDaRodada = false;
		sorteRevesDaRodada = null;
		return true;
	}

	// /// Funcos de Apoio a Jogo - Tipo GET
	public static int getGAMEquantidadeJogador() {
		return quantidadeJogadoresNaPartida;
	}

	public static int getGAMEquantidadeDisponivelCasas() {
		return quantidadeDisponivelCasas;
	}

	public static int getGAMEquantidadeDisponivelHotel() {
		return quantidadeDisponivelHotel;
	}

	/***************************************************************************
	 *** Funcoes relacionadas ao Jogador
	 ***************************************************************************/
	// //////////////////////////////////////////////////////////////////////////
	// /// Funcoes do Jogador da Rodada - Tipo SET
	public static void setJOGRODfuturaPosicao() {
		Jogador jogador = null;
		for (int i = 0; i < listJogador.size(); i++) {
			jogador = (Jogador) listJogador.get(i);
			if (jogador.getID() == jogadorDaRodada.getID())
				break;
		}
		int posicaoAtual = getJOGposicaoJogadorTabuleiro(jogadorDaRodada
				.getID());
		int posicaoFutura = posicaoAtual + numDadosJogadorDaRodada;
		posicaoFutura %= 40;

		jogador.setPosicaNoTabuleiro(posicaoFutura);
	}

	public static void setJOGRODvaiParaPrisao() {
		Outras.vaiParaPrisao(jogadorDaRodada);
	}

	public static void setJOGRODdadoSairDaPrisao() {
		Outras.saiDaPrisao(jogadorDaRodada);
	}

	public static void setJOGRODpagaSairPrisao() {
		if (jogadorDaRodada.getStatusJogador() != StatusJogador.Preso)
			throw new IllegalArgumentException("Jogador nao esteva preso");
		else if (jogadorDaRodada.getRodadasPrisao() < 4)
			throw new IllegalArgumentException(
					"Jogador nao pode pagar para sair da prisao, somente a partir da 4 tentativa");
		jogadorDaRodada.setRemoverDinheiro(50);
		Outras.saiDaPrisao(jogadorDaRodada);
	}

	public static void setJOGRODpagarImpostoDeRenda() {
		Outras.ImpostoRenda(jogadorDaRodada);
	}

	public static void setJOGRODpagarAluguel(int posicao) {
		Propriedade casa = (Propriedade) tabuleiro[getJOGposicaoJogadorTabuleiro(jogadorDaRodada
				.getID())];

		int taxaApagar = 0;
		if (casa.tipoPropriedade == TipoPropriedade.empressa) {
			Empresa e = (Empresa) tabuleiro[getJOGposicaoJogadorTabuleiro(jogadorDaRodada
					.getID())];
			taxaApagar = e.getTaxaAluguel(jogadorDaRodada,
					numDadosJogadorDaRodada);
		} else {
			Terreno t = (Terreno) tabuleiro[getJOGposicaoJogadorTabuleiro(jogadorDaRodada
					.getID())];
			taxaApagar = t.getValorAluguel(jogadorDaRodada);
		}
		if (taxaApagar != 0) {
			jogadorDaRodada.setRemoverDinheiro(taxaApagar);
			casa.proprietario.setAdicionarDinheiro(taxaApagar);
		}
	}

	public static void setJOGRODpagarHipoteca(int posicao, int numJogador) {
		if (tabuleiro[posicao].tipoCasaTabuleiro != TipoCasaTabuleiro.propriedade)
			throw new IllegalArgumentException(
					"ERRO, localizicao invalida, nao e uma propriedade");

		Propriedade propTemp = (Propriedade) tabuleiro[posicao];
		Jogador jogTemp = getJogador(numJogador);

		if (propTemp.getPropriedadeEstaHipotecada() == false)
			throw new IllegalArgumentException("Terreno não está hipotecado");
		else if (jogTemp != propTemp.getProprietario())
			throw new IllegalArgumentException(
					"Jogador não é o proprietário desse terreno");
		else {
			int hipotecaMaisJuros = (int) 1.2 * propTemp.getValorHipoteca();
			if (hipotecaMaisJuros > jogTemp.getDinheiro())
				throw new IllegalArgumentException(
						"Jogador não têm dinheiro o suficiente");
			else {
				jogTemp.setRemoverDinheiro(hipotecaMaisJuros);
				propTemp.setPagarHipoteca();
			}
		}
	}

	public static void setJOGRODreceberLucroDividendo() {
		Outras.LucroDividendo(jogadorDaRodada);
	}

	public static void setJOGRODreceberHonorarios() {
		Outras.pontoDePartida(jogadorDaRodada);
	}

	public static void setJOGRODdeclarFalencia() {
		// retorna carta sorte reves
		if (jogadorDaRodada.getCartaoSaidaLivrePrisao() == true)
			SorteReves.retornoAoBaralhoCartaoSaidaLivrePrisao();

		// limpa proprietario da propriedade em tabuleiro
		Terreno terrTmp = null;
		Empresa empTmp = null;
		for (int index = 0; index < jogadorDaRodada.getSizeListaPropriedade(); index++) {
			Propriedade tmp = (Propriedade) jogadorDaRodada
					.getPropriedade(index);
			if (tmp.tipoPropriedade == TipoPropriedade.terreno) {
				terrTmp = (Terreno) tmp;
				terrTmp.setRetornaParaBanco();
			} else {
				empTmp = (Empresa) tmp;
				empTmp.setRetornaParaBanco();
			}
		}

		// remove jogador
		jogadorDaRodada.setFalido();
		quantidadeJogadoresFalidos++;
	}

	public static void setJOGRODcomprarEdificio(int posicao, int qtd) {
		// Verifica se o jogador esta na casa do tabuleiro
		if (posicao != jogadorDaRodada.getPosicaNoTabuleiro())
			throw new IllegalArgumentException(
					"ERRO, localizicao Jogador invalida");
		// Verifica se e uma propriedade
		if (tabuleiro[posicao].tipoCasaTabuleiro != TipoCasaTabuleiro.propriedade)
			throw new IllegalArgumentException(
					"ERRO, localizacao nao e propriedade");
		// Verifica se e um Terreno
		Propriedade p = (Propriedade) tabuleiro[posicao];
		if (p.tipoPropriedade != TipoPropriedade.terreno)
			throw new IllegalArgumentException("ERRO, propriedade invalida");
		// Verifica se e ainda pode Construir
		Terreno t = (Terreno) tabuleiro[posicao];
		if (t.getNumEdificios() == 5)
			throw new IllegalArgumentException(
					"ERRO, Já fora construido o numero maximo de edificios.");
		// Verifica se e o jogador e proprietario de todo grupo
		if (getJOGRODproprietarioDeTodoGrupo(posicao) != true)
			throw new IllegalArgumentException(
					"ERRO, Jogador nao possui todas as propriedades do grupo");
		// Verifica se pode construir a qtd de edificios, respeitando o limite
		// junto as outras propriedade do grupo
		int grupoTerreno[] = getTERRterrenosDoGrupo(posicao);
		int numMaxCasas = 0;
		int numMinCasas = 6;
		int numMaxCompra;
		for (int i = 0; i < grupoTerreno.length; i++) {
			Terreno tt = (Terreno) tabuleiro[grupoTerreno[i]];
			System.out.println( "Terreno = " + tt.getNome() + " casas = " +tt.getNumEdificios());  
			if (tt.getNumEdificios() < numMinCasas)
				numMinCasas = tt.getNumEdificios();
			else if(tt.getNumEdificios() > numMaxCasas)
				numMaxCasas = tt.getNumEdificios();
		}
		
		System.out.println("Numero minimo = " + numMinCasas);
		System.out.println("Numero maximo = " + numMaxCasas);
		System.out.println("Quantidade = " + qtd);
		
		if(numMinCasas == numMaxCasas)
			numMaxCompra = 1;
		else if(t.getNumEdificios() != numMaxCasas )
			numMaxCompra = numMaxCasas - t.getNumEdificios();
		else
			numMaxCompra = 0;
		
		boolean resp = false;
		if (qtd <= numMaxCompra)
		{
			if(jogadorDaRodada.getDinheiro() - qtd * t.getPrecoCasa() > 0)
				for (int i = 0; i < qtd; i++)
				{
					resp = t.setAdicionarEdificio();
					if(resp == true)
						jogadorDaRodada.setRemoverDinheiro(t.getPrecoCasa());
					else
						throw new IllegalArgumentException(
								"ERRO, Falha na construcao do edificio");
				}
			else
				throw new IllegalArgumentException(
						"Jogador não possui dinheiro o suficiente");
		}
		else
			throw new IllegalArgumentException(
					"ERRO!Não é possivel comprar " + qtd + " casas. O limite de compra é " + numMaxCompra);
				
	}

	// /// Funcoes do Jogador da Rodada - Tipo GET
	public static int getJOGRODid() {
		return jogadorDaRodada.getID();
	}

	public static int getJOGRODlocalizacao() {
		return jogadorDaRodada.getPosicaNoTabuleiro();
	}

	public static JogoTipoCasa getJOGRODtipoCasaTabuleiro() {
		int posicao = jogadorDaRodada.getPosicaNoTabuleiro();
		switch (tabuleiro[posicao].tipoCasaTabuleiro) {
		case propriedade:
			Propriedade tmp = (Propriedade) tabuleiro[posicao];
			if (tmp.tipoPropriedade == TipoPropriedade.empressa)
				return JogoTipoCasa.empresa;
			else
				return JogoTipoCasa.terreno;
		case impostoRenda:
			return JogoTipoCasa.impostoRenda;
		case lucroDividendo:
			return JogoTipoCasa.lucroDividendo;
		case paradaLivre:
			return JogoTipoCasa.paradaLivre;
		case pontoDePartida:
			return JogoTipoCasa.pontoDePartida;
		case prisao:
			if (jogadorDaRodada.getStatusJogador() == Jogador.StatusJogador.Preso)
				return JogoTipoCasa.prisao;
			else
				return JogoTipoCasa.paradaLivre;
		case sorteReves:
			return JogoTipoCasa.sorteReves;
		case vaiParaPrisao:
			return JogoTipoCasa.vaiParaPrisao;
		}
		return null;
	}

	public static JogoTipoAcao getJOGRODacaoDoJogoAo() {
		switch (getJOGRODtipoCasaTabuleiro()) {
		case impostoRenda:
			return Jogo.getDescobreAcaoImpostoRenda();
		case lucroDividendo:
			return Jogo.getDescobreAcaoLucroDividendo();
		case paradaLivre:
			return Jogo.getDescobreAcaoParadaLivre();
		case pontoDePartida:
			return Jogo.getDescobreAcaoPontoDePartida();
		case prisao:
			return Jogo.getDescobreAcaoPrisao();
		case sorteReves:
			return Jogo.getDescobreAcaoSorteReves();
		case vaiParaPrisao:
			return Jogo.getDescobreAcaoVaiParaPrisao();
		case terreno:
			return Jogo.getDescobreAcaoPropriedade();
		case empresa:
			return Jogo.getDescobreAcaoPropriedade();
		}
		return null;
	}

	public static boolean getJOGRODproprietarioDeTodoGrupo(int posicao) {
		boolean resposta = true;
		Terreno t = (Terreno) tabuleiro[posicao];
		GrupoPropriedade grupo = t.getCor();

		// Localizar a coluna do tabuleiro, onde se encontra todas as
		// propriedades do mesmo grupo
		int iPosCmp = -1;
		if (posicao >= 1 && posicao <= 9)
			iPosCmp = 1;
		else if (posicao >= 11 && posicao <= 19)
			iPosCmp = 11;
		else if (posicao >= 21 && posicao <= 29)
			iPosCmp = 21;
		else if (posicao >= 31 && posicao <= 39)
			iPosCmp = 31;

		// Guarda casas do grupo desejado
		for (int i = 0, idxCasa = iPosCmp; i < 9; i++, idxCasa++) {
			// Verifica se e uma propriedade
			if (TipoCasaTabuleiro.propriedade == tabuleiro[idxCasa].tipoCasaTabuleiro) {
				Propriedade prop = (Propriedade) tabuleiro[idxCasa];
				// Verifica se e um propriedade do tipo terreno
				if (prop.tipoPropriedade == TipoPropriedade.terreno) {
					Terreno terr = (Terreno) tabuleiro[idxCasa];
					// Verifica se o terreno e do grupo desejado
					if (terr.getCor() == grupo) {
						// Verifica o proprietario
						if (terr.getProprietario() != jogadorDaRodada)
							resposta = false;
					}// IF
				}// IF
			}// IF
		}// FOR
		return resposta;
	}

	// //////////////////////////////////////////////////////////////////////////
	// /// Funcoes do Jogador - Tipo GET
	public static boolean getJOGjogadorNaPrisao(int numJogador) {
		Jogador jogador = getJogador(numJogador);
		if (jogador.getStatusJogador() == StatusJogador.Preso)
			return true;
		else
			return false;
	}

	public static boolean getJOGcartaoSaidaLivrePrisao(int numJogador) {
		Jogador jogador = getJogador(numJogador);
		return jogador.getCartaoSaidaLivrePrisao();
	}

	public static String getJOGlogoJogador(int numJogador) {
		for (int i = 0; i < listJogador.size(); i++) {
			Jogador jogador = (Jogador) listJogador.get(i);
			if (jogador.getID() == numJogador)
				return jogador.getLogo();
		}
		return "";
	}

	public static String getJOGstatusJogadorNoJogo(int numJogador) {
		Jogador jogador = null;
		jogador = getJogador(numJogador);
		if (jogador.getStatusJogador() == StatusJogador.Falido)
			return "Falido";
		if (jogador.getStatusJogador() == StatusJogador.Preso)
			return "Preso";
		else
			return "Jogando";
	}

	public static String getJOGlocalizacaoJogadorTabuleiro(int numJogador) {
		Jogador jogador = null;
		jogador = getJogador(numJogador);
		int posicao = jogador.getPosicaNoTabuleiro();
		switch (tabuleiro[posicao].getTipoDaCasaTabuleiro(posicao)) {
		case impostoRenda:
			return "impostoRenda";
		case lucroDividendo:
			return "lucroDividendo";
		case paradaLivre:
			return "paradaLivre";
		case pontoDePartida:
			return "pontoDePartida";
		case prisao:
			return "prisão";
		case sorteReves:
			return "sorteReves";
		case vaiParaPrisao:
			return "VaiParaPrisao";
		case propriedade:
			Propriedade casa = (Propriedade) tabuleiro[posicao];
			if (casa.getTipoPropriedade() == TipoPropriedade.terreno) {
				Terreno t = (Terreno) tabuleiro[posicao];
				return t.getNome();
			} else if (casa.getTipoPropriedade() == TipoPropriedade.empressa) {
				Empresa e = (Empresa) tabuleiro[posicao];
				switch (e.getTipoCompanhia()) {
				case aviacao:
					return "aviacao";
				case ferroviaria:
					return "ferroviaria";
				case navegacao:
					return "navegacao";
				case onibus:
					return "onibus";
				case taxi:
					return "taxi";
				case taxiAereo:
					return "taxiAereo";
				}
			}
		}
		return "";
	}

	public static int getJOGposicaoJogadorTabuleiro(int numJogador) {
		Jogador jog = getJogador(numJogador);
		return jog.getPosicaNoTabuleiro();
	}

	public static int getJOGdinheiroJogador(int numJogador) {
		Jogador jogador = getJogador(numJogador);
		return jogador.getDinheiro();
	}

	public static int getJOGSizeListaPropriedade(int numJogador) {
		Jogador jog = getJogador(numJogador);
		return jog.getSizeListaPropriedade();
	}

	public static int getJOGpropriedadeListaPropriedade(int numJogador,
			int index) {
		Jogador jog = getJogador(numJogador);
		Propriedade prop = (Propriedade) jog.getPropriedade(index);
		for (int i = 0; i < tabuleiro.length; i++)
			if (prop == tabuleiro[i]) {
				return i;
			}
		return -1;
	}

	/***************************************************************************
	 *** Funcoes relacionadas ao Dado
	 ***************************************************************************/

	public static int setDADOjogaDado() {
		Dado.jogarDados();
		return Dado.getNumDado();
	}

	public static void setDADOnumDadosJogadorRodada(int numDado1, int numDado2) {
		if (numDado1 == numDado2)
			dadosIquaisJogadorDaRodada = true;
		else
			dadosIquaisJogadorDaRodada = false;
		numDadosJogadorDaRodada = numDado1 + numDado2;
	}

	public static boolean setDADOnumDadosJogadorRodadaSaiPrisao(int numDado1,
			int numDado2) {
		if (numDado1 == numDado2)
			dadosIquaisJogadorDaRodada = true;
		if (dadosIquaisJogadorDaRodada == true) {
			return true;
		}
		jogadorDaRodada.setAdicionaRodadaPrisao();
		return false;
	}

	/***************************************************************************
	 *** Funcoes relacionadas a Sorte Reves
	 ***************************************************************************/
	// /// Set Sorte Reves, executa a acao de sorte ou reves, junto ao jogador
	// da rodada
	public static void setCSRexecutarCartaoSorteReves() {

		if (sorteRevesDaRodada == null)
			throw new IllegalArgumentException("Jogador nao pegou carta de sorte reves");

		if (sorteRevesDaRodada.getAcaoCartaoSR() == CartaoSorteReves.TipoAcaoCartaSR.pague) {
			jogadorDaRodada.setRemoverDinheiro(sorteRevesDaRodada.getValor());
		} else if (sorteRevesDaRodada.getAcaoCartaoSR() == CartaoSorteReves.TipoAcaoCartaSR.pontoPartida) {
			Outras.pontoDePartida(jogadorDaRodada);
			jogadorDaRodada.setVaiParaPontoPartida();
		} else if (sorteRevesDaRodada.getAcaoCartaoSR() == CartaoSorteReves.TipoAcaoCartaSR.receba) {
			jogadorDaRodada.setAdicionarDinheiro(sorteRevesDaRodada.getValor());
		} else if (sorteRevesDaRodada.getAcaoCartaoSR() == CartaoSorteReves.TipoAcaoCartaSR.recebeCadaJogador) {
			Jogador j = null;
			int cont = 0;
			int addMoney = 0;
			while (  cont < listJogador.size()) {
				j = (Jogador) listJogador.get(cont);
				if (j != jogadorDaRodada) {
					if (j.getDinheiro() >= 50) { // Se tem dinheiro, paga
						j.setRemoverDinheiro(sorteRevesDaRodada.getValor());
						addMoney++;
						cont++;
					} else { // Se nao tem dinheiro, segue para o proximo
						cont++;
					}
				} else {
					cont++;
				}
			}
			for (int i = 0; i < addMoney; i++) {
				jogadorDaRodada.setAdicionarDinheiro(sorteRevesDaRodada.getValor());
			}
		}
	}

	public static String getCSRtipoCartaoSorteReves() {
		if (sorteRevesDaRodada == null)
			throw new IllegalArgumentException(
					"Jogador nao pegou carta de sorte reves");
		if (sorteRevesDaRodada.getTipoCartaoSR() == TipoCartaoSR.sorte)
			return "Sorte";
		else
			return "Reves";
	}

	public static String getCSRmensagemCartaoSorteReves() {
		if (sorteRevesDaRodada == null)
			throw new IllegalArgumentException(
					"Jogador nao pegou carta de sorte reves");
		else
			return sorteRevesDaRodada.getMensagem();
	}

	public static String getCSRacaoCartaoSorteReves() {
		if (sorteRevesDaRodada == null)
			throw new IllegalArgumentException(
					"Jogador nao pegou carta de sorte reves");
		else if (sorteRevesDaRodada.getAcaoCartaoSR() == CartaoSorteReves.TipoAcaoCartaSR.pague)
			return "Pagua";
		else if (sorteRevesDaRodada.getAcaoCartaoSR() == CartaoSorteReves.TipoAcaoCartaSR.pontoPartida)
			return "pontoPartida";
		else if (sorteRevesDaRodada.getAcaoCartaoSR() == CartaoSorteReves.TipoAcaoCartaSR.prisao)
			return "prisao";
		else if (sorteRevesDaRodada.getAcaoCartaoSR() == CartaoSorteReves.TipoAcaoCartaSR.receba)
			return "Receba";
		return "recebeCadaJogador";
	}

	public static int getCSRvalorCartaoSorteReves() {
		if (sorteRevesDaRodada == null)
			throw new IllegalArgumentException(
					"Jogador nao pegou carta de sorte reves");
		else
			return sorteRevesDaRodada.getValor();
	}

	public static int getCSRimagemCartaoSorteReves() {
		return sorteRevesDaRodada.getImagem();
	}

	/***************************************************************************
	 *** Funcoes relacionadas a Propriedades
	 ***************************************************************************/
	// //////////////////////////////////////////////////////////////////////////
	// /// Funcoes de Propriedade - Tipo GET
	// Status
	public static boolean getPROPpropriedadeEstaHipotecada(int posicao) {
		Propriedade p = (Propriedade) tabuleiro[posicao];
		if (p.getPropriedadeEstaHipotecada() == true)
			return true;
		else
			return false;
	}

	public static boolean getPROPvalidaCasaTabuleiroPropriedade(int posicao) {
		if (tabuleiro[posicao].tipoCasaTabuleiro == TipoCasaTabuleiro.propriedade) {
			return true;
		}
		return false;
	}

	public static boolean getPROPstatusAVendaPeloJogador(int posicao) {

		if (tabuleiro[posicao].tipoCasaTabuleiro != TipoCasaTabuleiro.propriedade)
			throw new IllegalArgumentException("ERRO, localizicao invalida");

		Propriedade tmp = (Propriedade) tabuleiro[posicao];
		if (tmp.getStatosVendaNoMercado() == true)
			return true;
		else
			return false;
	}

	// Valor
	public static int getPROPvalorNominalPropriedade(int posicao) {

		if (tabuleiro[posicao].tipoCasaTabuleiro != TipoCasaTabuleiro.propriedade)
			throw new IllegalArgumentException("ERRO, localizicao invalida");

		Propriedade tmp = (Propriedade) tabuleiro[posicao];
		return tmp.valorNominal;
	}

	public static int getPROPvalorHipotecaPropriedade(int posicao) {
		if (tabuleiro[posicao].tipoCasaTabuleiro != TipoCasaTabuleiro.propriedade)
			throw new IllegalArgumentException(
					"Jogador solicitacao de ERRO, localizicao invalida");

		Propriedade tmp = (Propriedade) tabuleiro[posicao];
		return tmp.valorhipoteca;
	}

	public static int getPROPvalorVendaDaPropriedadeMercado(int posicao) {
		if (tabuleiro[posicao].tipoCasaTabuleiro != TipoCasaTabuleiro.propriedade)
			throw new IllegalArgumentException("ERRO, localizicao invalida");
		Propriedade tmp = (Propriedade) tabuleiro[posicao];
		return tmp.valorDeVendaNoMercado;
	}

	public static int getPROPvalorDoAluguel(int posicao) {
		Propriedade casa = (Propriedade) tabuleiro[posicao];
		int taxaApagar = 0;
		if (casa.tipoPropriedade == TipoPropriedade.empressa) {
			Empresa e = (Empresa) tabuleiro[posicao];
			taxaApagar = e.getTaxaAluguel(jogadorDaRodada,
					numDadosJogadorDaRodada);
		} else {
			Terreno t = (Terreno) tabuleiro[posicao];
			taxaApagar = t.getValorAluguel(jogadorDaRodada);
		}
		return taxaApagar;
	}

	// Proprietario
	public static int getPROPproprietario(int posicao) {
		if (tabuleiro[posicao].tipoCasaTabuleiro != TipoCasaTabuleiro.propriedade)
			throw new IllegalArgumentException(
					"Jogador solicitacao de ERRO, localizicao invalida");

		Propriedade tmp = (Propriedade) tabuleiro[posicao];
		if (tmp.proprietario == null) {
			return 0;
		} else {
			return tmp.proprietario.getID();
		}
	}

	public static int[] getPROPpropriedadeDoJogador(int numJogador) {

		// descobrir quantas propriedades o jogador possui
		int TAM = 0;
		for (int w = 0; w < 40; w++) {
			if (TipoCasaTabuleiro.propriedade == tabuleiro[w].tipoCasaTabuleiro) {
				Propriedade prop = (Propriedade) tabuleiro[w];
				if (null != prop.getProprietario()	&& numJogador == prop.getProprietario().getID()) {
					TAM++;
				}
			}
		}
		// cria vetor com o tam exato da quantidade de propriedades do jogador
		if (TAM == 0)
			return null;

		int[] grupoCasa = new int[TAM];
		// Guarda posicao das propriedade no veto
		for (int i = 0, w=0; i < tabuleiro.length; i++) {
			if (tabuleiro[i].tipoCasaTabuleiro == TipoCasaTabuleiro.propriedade) {
				Propriedade prop = (Propriedade) tabuleiro[i];
				// Verifica se e um propriedade do tipo terreno
				if (prop.getProprietario() != null && numJogador == prop.getProprietario().getID()) {	
					grupoCasa[w] = i;
					w++;
				}
			}
		}// FOR
		
		return grupoCasa;
	}

	// Propriedade
	public static String getPROPtipoPropriedade(int posicao) {
		Propriedade tmp = (Propriedade) tabuleiro[posicao];
		if (tmp.tipoPropriedade == TipoPropriedade.empressa)
			return "Empresa";
		return "Terreno";
	}

	// /// Funcoes de Propriedade - Tipo SET
	public static void setPROPcomprarPropriedade(int posicao, int numJogador) {
		Propriedade tmpProp = (Propriedade) tabuleiro[posicao];
		Jogador j = getJogador(numJogador);
		
		// Compra a propriedade
		tmpProp.setComprarPropriedade(j); // set no tabuleiro
		j.setAdicionarListaPropriedade(tmpProp); // set no jogador
	}

	public static void setPROPvenderPropriedadeParaBanco(int posicao, int jogador) {

		Propriedade tmpProp = (Propriedade) tabuleiro[posicao];

		if (tmpProp.getProprietario() == null)
			return;

		Jogador jogTemp = getJogador(jogador);
		
		// Verifica se tem edificio e os vende caso exista
		if (tmpProp.getTipoPropriedade() == Propriedade.TipoPropriedade.terreno) {
			Terreno tmp = (Terreno) tabuleiro[posicao];
			int numEdificios = tmp.getNumEdificios();
			if (tmp.getNumEdificios() != 0) {// Significa que ele tem casa(s) /
												// Hotel
				int valor = numEdificios % 5 * tmp.getPrecoCasa()
						+ numEdificios / 5 * tmp.getPrecoHotel();
				jogTemp.setAdicionarDinheiro(valor / 2);
				tmp.setPorAbaixoEdificios();
			}
		}

		
		// Vende propriedade para banco
		tmpProp.setVenderPropriedade(getJogador(jogador)); // set no tabuleiro
		jogadorDaRodada.setRemoverListaPropriedade(tmpProp);// set no jogador
	
	}
	
	public static void PROPtrocaPropriedadeEntreJogadores(int posicao, int posicaoDesejada) {
		Propriedade prop1=null, prop2=null;
		prop1 = (Propriedade) tabuleiro[posicao];
		prop2 = (Propriedade) tabuleiro[posicaoDesejada];
		
		if(prop1.getProprietario() == null || prop2.getProprietario() == null)
			throw new IllegalArgumentException("ERRO, proprietario invalida");
		
		prop1.setRemoverPropriedadeMercado();
		prop2.setRemoverPropriedadeMercado();
		
		int tmp = prop1.getProprietario().getID();
		prop1.proprietario = prop2.getProprietario();
		prop2.proprietario = getJogador(tmp);
	}

	// //////////////////////////////////////////////////////////////////////////
	// /// Funcoes com o Hipoteca - Tipo SET
	public static void setPROPhipotecarPropriedade(int posicao, int numJogador) {
		if (tabuleiro[posicao].tipoCasaTabuleiro != TipoCasaTabuleiro.propriedade)
			throw new IllegalArgumentException("ERRO, localizicao invalida");
		Propriedade propTemp = (Propriedade) tabuleiro[posicao];
		Jogador jogTemp = getJogador(numJogador);
		if (propTemp.getPropriedadeEstaHipotecada() == true)
			throw new IllegalArgumentException("Propriedade ja está hipotecada");
		if (jogTemp != propTemp.getProprietario())
			throw new IllegalArgumentException("Jogador não é o proprietário");
		propTemp.setHipotecarPropriedade();
	}

	// //////////////////////////////////////////////////////////////////////////
	// /// Funcoes de Mercado - Tipo SET
	public static void setPROPListarPropriedadeMercado(int numJogador,
			int posicao, int valor) {
		if (valor <= 0)
			throw new IllegalArgumentException("ERRO, valor invalido.");
		Propriedade propTemp = (Propriedade) tabuleiro[posicao];
		Jogador jogTemp = getJogador(numJogador);
		if (jogTemp != propTemp.getProprietario())
			throw new IllegalArgumentException("Jogador nao e o proprietario");
		propTemp.setListarPropriedadeMercado(valor);
	}

	public static void setPROPremoverPropriedadeMercado(int numJogador,
			int posicao) {
		Propriedade propTemp = (Propriedade) tabuleiro[posicao];
		Jogador jogTemp = getJogador(numJogador);
		if (jogTemp != propTemp.getProprietario())
			throw new IllegalArgumentException("Jogador nao e o proprietario");
		propTemp.setRemoverPropriedadeMercado();
	}

	/***************************************************************************
	 *** Funcoes relacionadas a propriedade Terreno
	 ***************************************************************************/
	// //////////////////////////////////////////////////////////////////////////
	// /// Funcoes de Terreno
	// Tipo GET
	public static String getTERRnomeTerreno(int posicao) {
		Terreno t = (Terreno) tabuleiro[posicao];
		return t.getNome();
	}

	public static String getTERRgrupoDoTerreno(int posicao) {

		Terreno t = (Terreno) tabuleiro[posicao];

		if (t.getCor() == GrupoPropriedade.amarelo)
			return "Amarelo";
		else if (t.getCor() == GrupoPropriedade.azul)
			return "Azul";
		else if (t.getCor() == GrupoPropriedade.cinza)
			return "Cinza";
		else if (t.getCor() == GrupoPropriedade.laranja)
			return "Laranja";
		else if (t.getCor() == GrupoPropriedade.rosa)
			return "Rosa";
		else if (t.getCor() == GrupoPropriedade.roxo)
			return "Roxo";
		else if (t.getCor() == GrupoPropriedade.verde)
			return "Verde";
		else
			return "Vermelho";
	}

	public static String getTERRtipoEdificio(int posicao) {

		Terreno t = (Terreno) tabuleiro[posicao];
		int numEdificios = t.getNumEdificios();
		if (numEdificios < 5) // 1 ~ 4 - Possui casas
			return "Casa";
		else
			return "Hotel";
	}

	public static int getTERRqtdEdificio(int posicao) {
		Terreno t = (Terreno) tabuleiro[posicao];
		return t.getNumEdificios();
	}

	public static int getTERRprecoComprarEdificio(int posicao, int qtd) {
		Terreno t = (Terreno) tabuleiro[posicao];
		if (t.getNumEdificios() == 4)
			return t.getPrecoHotel();
		return t.getPrecoCasa();
	}

	public static int[] getTERRterrenosDoGrupo(int posicao) {
		// identifica grupo desejado
		Terreno t = (Terreno) tabuleiro[posicao];
		GrupoPropriedade grupo = t.getCor();

		// Localizar a coluna do tabuleiro, onde se encontra todas as
		// propriedades do mesmo grupo
		int iPosCmp = -1;
		int iPosCmpMax = -1;
		if (posicao >= 1 && posicao <= 10) {
			iPosCmp = 1;
			iPosCmpMax = 10;
		} else if (posicao >= 11 && posicao <= 19) {
			iPosCmp = 11;
			iPosCmpMax = 19;
		} else if (posicao >= 21 && posicao <= 29) {
			iPosCmp = 21;
			iPosCmpMax = 29;
		} else if (posicao >= 31 && posicao <= 39) {
			iPosCmp = 31;
			iPosCmpMax = 39;
		}

		// Inicializa o vetor de retorno, com as posicoes dos terrenos do mesmo
		// grupo
		int[] grupoCasa = new int[getNumPropriedadeDoGrupo(grupo)];

		// Guarda casas do grupo desejado
		int qntCasasAchadas = 0;
		for (int i = 0, idxCasa = iPosCmp; i <= iPosCmpMax - iPosCmp; i++, idxCasa++) {
			// Verifica se é uma propriedade e se já não achamos todas os lotes
			if (TipoCasaTabuleiro.propriedade == tabuleiro[idxCasa].tipoCasaTabuleiro
					&& qntCasasAchadas < grupoCasa.length) {
				Propriedade prop = (Propriedade) tabuleiro[idxCasa];
				// Verifica se e um propriedade do tipo terreno
				if (prop.tipoPropriedade == TipoPropriedade.terreno) {
					Terreno terr = (Terreno) tabuleiro[idxCasa];
					// Verifica se o terreno e do grupo desejado
					if (terr.getCor() == grupo) {
						// Guarda terreno do grupo no vetor
						grupoCasa[qntCasasAchadas] = idxCasa;
						qntCasasAchadas++;
					}// IF
				}// IF
			}// IF
		}// FOR
		return grupoCasa;
	}

	// Tipo SET
	public static void setTERRvenderEdificio(int numJogador, int posicao,
			int qtd) {
		if (tabuleiro[posicao].tipoCasaTabuleiro != TipoCasaTabuleiro.propriedade)
			throw new IllegalArgumentException("ERRO, localizicao invalida");

		Propriedade tmpProp = (Propriedade) tabuleiro[posicao];
		Jogador jogTemp = getJogador(numJogador);

		if (jogTemp != tmpProp.getProprietario())
			throw new IllegalArgumentException(
					"Jogador nao e o dono da propriedade");

		// Verifica se tem edificio e os vende caso exista
		if (tmpProp.getTipoPropriedade() == Propriedade.TipoPropriedade.terreno) {
			Terreno tmp = (Terreno) tabuleiro[posicao];
			if (tabuleiro[posicao].tipoCasaTabuleiro == TipoCasaTabuleiro.propriedade) {
				if (tmp.getNumEdificios() != 0) {// Significa que ele tem
													// casa(s) / Hotel
					int valor = qtd % 5 * tmp.getPrecoCasa() + qtd / 5
							* tmp.getPrecoHotel();
					jogTemp.setAdicionarDinheiro(valor / 2);
					tmp.setPorAbaixoEdificios(qtd);
				}
			}
		}
	}

	/***************************************************************************
	 *** Funcoes relacionadas a propriedade Empresa
	 ***************************************************************************/
	// //////////////////////////////////////////////////////////////////////////
	// /// Funcoes de Empresa - Tipo GET
	public static int getEMPtaxaCompanhia(int posicao) {
		if (tabuleiro[posicao].tipoCasaTabuleiro != TipoCasaTabuleiro.propriedade)
			throw new IllegalArgumentException("ERRO, localizicao invalida");
		Empresa tmp = (Empresa) tabuleiro[posicao];
		return tmp.getTaxaNominal();
	}

	public static String getEMPtipoCompanhia(int posicao) {
		if (tabuleiro[posicao].tipoCasaTabuleiro != TipoCasaTabuleiro.propriedade)
			throw new IllegalArgumentException("ERRO, localizicao invalida");
		Empresa tmp = (Empresa) tabuleiro[posicao];
		if (tmp.getTipoCompanhia() == TipoCompanhia.aviacao)
			return "aviacao";
		if (tmp.getTipoCompanhia() == TipoCompanhia.ferroviaria)
			return "navegacao";
		if (tmp.getTipoCompanhia() == TipoCompanhia.navegacao)
			return "navegacao";
		if (tmp.getTipoCompanhia() == TipoCompanhia.onibus)
			return "taxiAereo";
		if (tmp.getTipoCompanhia() == TipoCompanhia.taxi)
			return "taxi";
		else
			return "taxiAereo";
	}

	/***************************************************************************
	 *** Funcoes de Apoio a Classe Jogo
	 ***************************************************************************/
	// //////////////////////////////////////////////////////////////////////////
	// /// Funcoes de apoio sobre Propriedade
	private static int getNumPropriedadeDoGrupo(GrupoPropriedade grupo) {

		// Inicializa o tamanho do Maior grupo, tendo a quantidade de terrenos
		// pertencente a este grupo
		int numPropriedadeDoMaiorGrupo = 0;

		// Guarda casas do grupo desejado
		for (int i = 0; i < qtdToTalCasas; i++) {
			// Verifica se e uma propriedade
			if (TipoCasaTabuleiro.propriedade == tabuleiro[i].tipoCasaTabuleiro) {
				Propriedade prop = (Propriedade) tabuleiro[i];
				// Verifica se e um propriedade do tipo terreno
				if (prop.tipoPropriedade == TipoPropriedade.terreno) {
					Terreno terr = (Terreno) tabuleiro[i];
					// Verifica se o terreno e do grupo desejado
					if (terr.getCor() == grupo) {
						// Guarda terreno do grupo no vetor
						numPropriedadeDoMaiorGrupo++;
					}// IF
				}// IF
			}// IF
		}// FOR
		return numPropriedadeDoMaiorGrupo;
	}

	// //////////////////////////////////////////////////////////////////////////
	// /// Funcoes de apoio a jogador da rodada
	// Tipo SET
	private static boolean setAtualizaJogadorDaRodada() {
		do {
			// Descobre o prox posicao da rodada
			int novaPosicaoNaRodada = jogadorDaRodada.getPosicaoNaRodada() + 1;

			// Tratar condicao final de rodada geral, volta para o primeiro
			// jogador
			if (novaPosicaoNaRodada > getGAMEquantidadeJogador())
				novaPosicaoNaRodada = 1;

			// Busca o jogador da respectiva nova posicao na rodada
			jogadorDaRodada = getJogadorRodada(novaPosicaoNaRodada);
		} while (jogadorDaRodada.getStatusJogador() == StatusJogador.Falido);

		if (quantidadeJogadoresNaPartida - quantidadeJogadoresFalidos == 1)
			return false;
		return true;
	}

	public static void setPegarCartaoSorteSorteRevesJogadorDaRodada() {
		if (tabuleiro[jogadorDaRodada.getPosicaNoTabuleiro()]
				.getTipoDaCasaTabuleiro(jogadorDaRodada.getPosicaNoTabuleiro()) != TipoCasaTabuleiro.sorteReves)
			throw new IllegalArgumentException(
					"Jogador nao se encontra em Sorte e Reves, para pegar o cartao");
		sorteRevesDaRodada = SorteReves.getCartaoSorteReves(jogadorDaRodada);
	}

	// Tipo GET
	private static Jogador getJogador(int numJogador) {
		for (int i = 0; i < listJogador.size(); i++) {
			Jogador jogador = (Jogador) listJogador.get(i);
			if (jogador.getID() == numJogador) {
				return jogador;
			}
		}
		return null;
	}

	private static Jogador getJogadorRodada(int posicaoNaRodada) {
		for (int i = 0; i < listJogador.size(); i++) {
			Jogador jogador = (Jogador) listJogador.get(i);
			if (jogador.getPosicaoNaRodada() == posicaoNaRodada) {
				return jogador;
			}
		}
		return null;
	}

	// /// Funcoes de apoio a acao ao jogador da rodada
	private static JogoTipoAcao getDescobreAcaoPropriedade() {
		// executa funcoes auxiliares, para descobrir a acao e prepara estado de
		// jogo, para que jogador execute a acha principal
		Propriedade propriedade = (Propriedade) tabuleiro[jogadorDaRodada
				.getPosicaNoTabuleiro()];
		if (propriedade.proprietario == null)
			return JogoTipoAcao.comprarPropriedadeDoBanco;
		else if (propriedade.proprietario == jogadorDaRodada) {
			if (propriedade.getPropriedadeEstaHipotecada() == true)
				return JogoTipoAcao.pagarHipoteca;
			return JogoTipoAcao.venderPropriedadeParaBanco;
		} else
			return JogoTipoAcao.pagarAluguel;
	}

	private static JogoTipoAcao getDescobreAcaoSorteReves() {
		// Preparei Jogo para Sorte reves
		setPegarCartaoSorteSorteRevesJogadorDaRodada();
		// Descobrir a acao
		switch (sorteRevesDaRodada.getAcaoCartaoSR()) {
		case pague:
			return JogoTipoAcao.pagarAoBancoReves; // Retorna o tipo a ser
													// tratado na controle,
													// junto a frame mensagem
		case pontoPartida:
			return JogoTipoAcao.irParaPontoPartida;
		case prisao:
			if (sorteRevesDaRodada.getTipoCartaoSR() == TipoCartaoSR.sorte)
				return JogoTipoAcao.recebeCartaoLivraDaPrisao;
			return JogoTipoAcao.vaiParaPrisao;
		case receba:
			return JogoTipoAcao.receberDoSorte;
		case recebeCadaJogador:
			return JogoTipoAcao.receberDosJogadores;
		}
		return null;
	}

	private static JogoTipoAcao getDescobreAcaoPrisao() {
		if (jogadorDaRodada.getRodadasPrisao() < 4)
			return JogoTipoAcao.jogarDados;
		else if (jogadorDaRodada.getStatusJogador() != StatusJogador.Preso)
			return null;
		else
			return JogoTipoAcao.pagarAoBancoPrisao;
	}

	private static JogoTipoAcao getDescobreAcaoPontoDePartida() {
		return JogoTipoAcao.receberDoPontoDePartida;
	}

	private static JogoTipoAcao getDescobreAcaoParadaLivre() {
		return null;
	}

	private static JogoTipoAcao getDescobreAcaoLucroDividendo() {
		return JogoTipoAcao.receberLucroDividendo;
	}

	private static JogoTipoAcao getDescobreAcaoImpostoRenda() {
		return JogoTipoAcao.pagarImpostoRenda;
	}

	private static JogoTipoAcao getDescobreAcaoVaiParaPrisao() {
		return JogoTipoAcao.vaiParaPrisao;
	}
}