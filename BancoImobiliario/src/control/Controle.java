package control;

import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Jogo;
import model.Jogo.JogoTipoAcao;
import model.Jogo.JogoTipoCasa;
import view.frame.Frame_Dado;
import view.frame.Frame_Dado.TipoDado;
import view.frame.Frame_Jogador;
import view.frame.Frame_Mensagem;
import view.frame.Frame_Mensagem.TipoMensagemFrame;
import view.frame.Frame_Mercado;
import view.frame.Frame_Principal;
import view.frame.Frame_Principal.TipoPanel;
import view.frame.Frame_Propriedade;
import view.frame.Frame_Propriedade.TipoPropriedadeApoio;
import view.panel.Panel_Dado;
import view.panel.Panel_JogadorBox;
import view.panel.Panel_PropriedadeApoioEdificio.TipoAcaoEdifico;

public class Controle {
	
	/***************************************************************************
	**** Vareaveis Globais da Classe Controle
	***************************************************************************/
	////////////////////////////////////////////////////////////////////////////
	//// Vareaveis de Sginton
	////////////////////////////////////////////////////////////////////////////
	private static Controle controle = null;
	////////////////////////////////////////////////////////////////////////////
	//// Vareaveis de FRAME
	////////////////////////////////////////////////////////////////////////////
	private static Frame_Jogador jogadorRodadaFrame = null;
	private static Frame_Dado dadoFrame = null;
	private static Frame_Mensagem mensagemFrame = null;
	private static Frame_Mercado mercadoFrame = null;
	private static LinkedList<Object> listPropriedadeFrame = null;
	////////////////////////////////////////////////////////////////////////////
	//// Vareaveis de Controle
	////////////////////////////////////////////////////////////////////////////
	private static boolean jogouDados = false;
	private static boolean jogouDadoPrisao = false;
	private static boolean receberHonorarios = false;

	/***************************************************************************
	**** Funcoes relacionada a Classe Controle
	***************************************************************************/
	
	public Controle() {	}
	private static Controle getInstance() {
		if (controle == null) {
			controle = new Controle();
			listPropriedadeFrame = new LinkedList<Object>();
			Jogo.getInstance();
			Frame_Principal.getInstance();
		}
		return controle;
	}

	/***************************************************************************
	**** Funcoes relacionada a Frame Principal
	***************************************************************************/
	////////////////////////////////////////////////////////////////////////////
	//// Funcoes do menu principal
	// Tipo SET
	public static void setEncerrarPrograma() {
		System.exit(0);
	}/* FIM: setEncerrarPrograma */
	public static void setNovoJogo() {
		Jogo.setGAMEiniciarJogo();
		Frame_Principal.setContentPanel(TipoPanel.adicionarJogadores);
	}

	// Tipo GET
	public static void getJogadorFrame(int numJogador) {
		@SuppressWarnings("unused")
		Frame_Jogador frame = new Frame_Jogador(numJogador);
	}
	public static void getMercadoFrame() {
		if(mercadoFrame != null) {
			mercadoFrame.removeAll();
			mercadoFrame = null;
		}
		mercadoFrame = new Frame_Mercado();
	}

	////////////////////////////////////////////////////////////////////////////
	//// Funcoes do Panel AdicionarJogador
	public static void setVoltarParaInicio() {
		Frame_Principal.setContentPanel(TipoPanel.inicial);
	}
	public static void setConfirmaQtdTotalJogadores(int numQtdTotalJogadores) {
		Jogo.setGAMEquantidadeJogadoresNaPartida(numQtdTotalJogadores);
	}
	public static void setConfirmaAdicionarJogadores(Panel_JogadorBox[] jogadorBox) {
		// verifica a quantidade de jogadores, a serem adicionados
		int qtdJogadorestmp = 0;
		for (int i = 0; i < 6; i++) {
			if (jogadorBox[i].getName() != "")
				qtdJogadorestmp++;
		}
		// verifica se existe pelo menos 2 jogadores
		if (qtdJogadorestmp < 2) {
			JOptionPane.showMessageDialog(null," Erro! Escolha pelo menos dois jogadores diferentes!");
			return;
		}
		// verifica se os jogadores possuem simbulos diferentes
		for (int x = 0; x < jogadorBox.length; x++)
			for (int y = 0; y < jogadorBox.length; y++)
				if (x != y
						&& jogadorBox[x].getName() == jogadorBox[y].getName()
						&& jogadorBox[x].getName() != ""
						&& jogadorBox[y].getName() != "") {
					JOptionPane.showMessageDialog(null," Erro! Escolha personagens diferentes!");
					return;
				}
		// verifica se todos os jogadores selecionaram seu personagem
		for (int w = 0; w < qtdJogadorestmp; w++) {
			if (jogadorBox[w].getName() == "") {
				JOptionPane.showMessageDialog(null," Erro! Algum jogador nao foi selecionado");
				return;
			}
		}
		// adicionando jogadoredes no jogo
		for (int w = 0; w < jogadorBox.length; w++) {
			if (jogadorBox[w].getName() != "") {
				Jogo.setGAMEadicionarJogadorNoJogo(w + 1,
						jogadorBox[w].getName());
			}
		}
		// atualiza principalFrame para tabuleiro
		Frame_Principal.setContentPanel(TipoPanel.tabuleiro);
		// Chama a dadoframe, para determinar a ordem de jogadores
		dadoFrame = new Frame_Dado(TipoDado.dadoDeOrdenacao);
	}

	/**************************************************************************
	**** Funcoes relacionadas a frame Jogador
	***************************************************************************/

	////////////////////////////////////////////////////////////////////////////
	//// Funcoes em menu jogador
	public static void setJogarDados() {
		if (jogouDados == false) {
			dadoFrame = new Frame_Dado(TipoDado.dadoDeJogo);
			jogouDados = true;
		}
	}
	public static void setTerminarRodada() {
		// Vareavel de controle, para manter da interface corretamente.
		jogouDados = false;
		
		// Fecha todas as janelas relacionadas ao jogador da rodada.
		fecharPropriedadeFrameDoJogador(Jogo.getJOGRODid());
		
		// Atualiza jogo para a proxima rodada
		boolean jogoContinua = true;
		try
		{
			jogoContinua = Jogo.setGAMEproximaRodada();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			Frame_Principal.setAtualizaTabuleiroPanel();
			jogadorRodadaFrame.setTrueJogarDado();
			Jogo.setGAMEAtualiza();
			return;
		}
		// Trata condicao de fim de jogo
		if(jogoContinua == false) {
			Jogo.setGAMEAtualiza();
			jogadorRodadaFrame.setFalseMenuJogador();
			int resposta = JOptionPane.showConfirmDialog(
				null,"PARABENS JOGADOR "+Jogo.getJOGRODid()+
				", \n!!! VOCE GANHO A JOGO !!!",
				"!!!!! Parabens Vencedor !!!!!",
				JOptionPane.DEFAULT_OPTION);
			if (resposta == JOptionPane.YES_OPTION) {
				// Termina o jogo, apaguando jogo antigo.
				Jogo.getInstance().deleteObservers();
				Jogo.setGAMEiniciarJogo();
				jogadorRodadaFrame.setCloseFrame();
				Frame_Principal.setContentPanel(TipoPanel.inicial);
			}
		}
		// O jogo continua
		// Trata condicao de jogador na prisao.
		else if(Jogo.getJOGjogadorNaPrisao(Jogo.getJOGRODid())==true) {
			if(jogouDadoPrisao == false)
				apoioDecisao();
		} else {
			jogouDadoPrisao = false;
			jogadorRodadaFrame.setTrueJogarDado();
		}

		Jogo.setGAMEAtualiza();
	}
	public static void setFalencia() {
		// Fecha todas as janelas relacionadas ao jogador da rodada e prepara para falencia
		jogadorRodadaFrame.setFalseJogarDado();
		fecharPropriedadeFrameDoJogador(Jogo.getJOGRODid());
		
		// Atualiza status jogador
		Jogo.setJOGRODdeclarFalencia();
		
		// Atualiza o tabuleiro
		Frame_Principal.setAtualizaTabuleiroPanel();
		
		Jogo.setGAMEAtualiza();
		
		// Atualiza o jogador da rodada
		setTerminarRodada();
	}

	////////////////////////////////////////////////////////////////////////////
	//// Funcoes em panel Jogador 
	public static void getPropriedade(int posicao, int numJogador) {
		//if(existePropriedadeFrame(posicao)==false) { //True, nao deixa criar a janela novamente
			Frame_Propriedade propriedadeFrame = new Frame_Propriedade(posicao,numJogador);
			listPropriedadeFrame.add(propriedadeFrame);
		//}
	}
	
	/**************************************************************************
	**** Funcoes relacionadas a frame Dado
	***************************************************************************/
	////////////////////////////////////////////////////////////////////////////
	//// Funcoes relacionada definicao da ordem de jogo entre os jogadores
	public static void setConfirmarOrdemJogadores(Panel_Dado jogNDado[]) {
		boolean status = false;
		// Valida se todos os jogadores ja clicaram parar os dados
		for (int i = 0; i < Jogo.getGAMEquantidadeJogador(); i++) {
			status = jogNDado[i].getJaClicado();
			if (status == false)
				break;
			// Caso todos os jogadores ja tenham seu valor de dado, status =
			// true
		}
		if (status != false) {
			// prepara vetor para ser enviado ao jogo
			int valorDados[] = new int[Jogo.getGAMEquantidadeJogador()];
			for (int i = 0; i < Jogo.getGAMEquantidadeJogador(); i++)
				valorDados[i] = jogNDado[i].getSomaDados();
			// Por usarmos as mesmas panels que são utilizadas para movimentar
			// os jogadores
			// caso qualquer um dos jogadores tire dados iguais no sorteio.
			// Automaticamente
			// o primeiro jogador a jogar jogaria duas vezes. Logo para evitar
			// isso jogaremos
			// mais uma vez, para desativar essa repetição
			Jogo.setDADOnumDadosJogadorRodada(0, 1);
			// Prepara jogo para o jogador da rodada
			Jogo.setGAMEOrdenarJogadores(valorDados);
			// Cria panel do jogador da Rodada
			jogadorRodadaFrame = new Frame_Jogador(0);
			// Fecha frame de dados para ordenacao
			dadoFrame.dispose();
		} else {
			JOptionPane.showMessageDialog(null,"Todos os jogadores precisam lançar seus dados");
			return;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////
	//// Funcoes relacionada jogador da rodada
	// Funcoes dado da rodada
	public static void setMoverPeca() {
		if (receberHonorarios == true || Jogo.getJOGRODlocalizacao() == 0) { // O jogador passou direto pelo ponto de partida
			receberHonorarios = false;
			mensagemFrame = new Frame_Mensagem(TipoMensagemFrame.pontoDePartida);
		} else { // O jogador apenas segue normalmente
			apoioDecisao();
		}
		Frame_Principal.setAtualizaTabuleiroPanel();
		dadoFrame.dispose();
		Jogo.setGAMEAtualiza();
	}
	public static void setPararDadosRodada() {
		int posAntes = Jogo.getJOGposicaoJogadorTabuleiro(Jogo.getJOGRODid());
		try {
		Jogo.setJOGRODfuturaPosicao();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());}
		int posDepois = Jogo.getJOGposicaoJogadorTabuleiro(Jogo.getJOGRODid());
		if (posDepois != 0 && posDepois < posAntes)
			receberHonorarios = true;
		dadoFrame.repaint();
	}
	
	// Funcoes de Prisao
	public static void setPagarSairDaPrisao() {
		try {
			Jogo.setJOGRODpagaSairPrisao();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());}
		Jogo.setGAMEAtualiza();
		setMoverPeca();
	}
	public static void setDadoSairDaPrisao() {
		try {
			Jogo.setJOGRODdadoSairDaPrisao();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());}
		Jogo.setGAMEAtualiza();
		setMoverPeca();
	}
	public static void setFecharDadoFrame() {
		jogouDadoPrisao = false;
		dadoFrame.dispose();
		Jogo.setGAMEAtualiza();
		jogadorRodadaFrame.setTrueTerminaRodadaBotao();
	}
	
	/***************************************************************************
	**** Funcoes relacionadas a frame Mercado
	***************************************************************************/

	public static void setComprarPropriedadeDoJogador (int numPropriedade, int numJogador) {
			try 
			{
				Jogo.setPROPcomprarPropriedade(numPropriedade, numJogador);
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null,e.getMessage());
				return;
			}
			mercadoFrame.dispose();
			Jogo.setGAMEAtualiza();
	}

	/***************************************************************************
	**** Funcoes relacionadas a frame Propriedade
	***************************************************************************/
	
	public static void setEdificio(final TipoAcaoEdifico tipoAcaoEdifico, int posicao, int numJogador) {
		Frame_Propriedade propriedadeApoio = new Frame_Propriedade(TipoPropriedadeApoio.edificio, tipoAcaoEdifico,posicao,numJogador);
		listPropriedadeFrame.add(propriedadeApoio);
	}
	public static void getListagemPropriedadeFrame(int posicao, int numJogador) {
		Frame_Propriedade propriedadeApoio = new Frame_Propriedade(TipoPropriedadeApoio.porVendaPropriedade,posicao,numJogador);
		listPropriedadeFrame.add(propriedadeApoio);
	}

	/***************************************************************************
	**** Funcoes relacionadas a frame Propriedade Apoio
	***************************************************************************/

	///// Fechar Frame de Apoio
	public static void setFecharFrameApoio(Frame_Propriedade frame) {
		frame.dispose();
	}
	///// Comprar Casa
	public static void setComprarCasa(int posicao, int qtd) {
		try {
			Jogo.setJOGRODcomprarEdificio(posicao, qtd);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			return;
		}
	}
	///// Vender Casa
	public static void setVenderCasa(int numJogador, int posicao,  int qtd) {
		try {
			Jogo.setTERRvenderEdificio(numJogador, posicao, qtd);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());}
	}
	///// Listar Propriedade
	public static void setListarPropriedade(JFrame frame, int numJogador, int posicao, int valor) {
		frame.dispose();
		try {
			Jogo.setPROPListarPropriedadeMercado(numJogador, posicao, valor);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
		Jogo.setGAMEAtualiza();
		if(mercadoFrame != null)
			mercadoFrame.dispose(); // problema de atualizacao, entao fecha
	}
	///// Retirar Propriedade Mercado
	public static void setRetirarPropriedadeMercado(int posicao, int numJogador) {
		try {
			Jogo.setPROPremoverPropriedadeMercado(numJogador, posicao);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
		Jogo.setGAMEAtualiza();
	}
	///// Propor Troca Propriedade
	public static void setProporTrocaPropriedade(int posicao, int numJogador) {
		Frame_Propriedade prop = new Frame_Propriedade(TipoPropriedadeApoio.trocaPropriedade, posicao, numJogador);
		listPropriedadeFrame.add(prop);
	}
	///// Enviar Propor Troca Propriedade
	public static void setEnviarProporTrocaPropriedade(int posicao,	int posicaoDesejada) {
		Frame_Propriedade prop = new Frame_Propriedade(TipoPropriedadeApoio.respostaTrocaPropriedade, posicao, posicaoDesejada);
		listPropriedadeFrame.add(prop);
	}
	///// Troca Propriedade
	public static void setTrocaPropriedade(int posicao,	int posicaoDesejada) {
		try
		{
		Jogo.PROPtrocaPropriedadeEntreJogadores( posicao, posicaoDesejada);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			return;
		}
		Jogo.setGAMEAtualiza();
	}
	
	/***************************************************************************
	**** Funcoes relacionadas a frame Mensagem
	***************************************************************************/

	///// Acao Receber
	public static void setAcaoReceber(JogoTipoAcao jogoTipoAcao) {
		try {
			if (jogoTipoAcao == JogoTipoAcao.receberDoSorte) {
				Jogo.setCSRexecutarCartaoSorteReves();
				mensagemFrame.dispose();
				jogadorRodadaFrame.setTrueTerminaRodadaBotao();
			} else if (jogoTipoAcao == JogoTipoAcao.receberDoPontoDePartida) {
				Jogo.setJOGRODreceberHonorarios();
				mensagemFrame.dispose();
				apoioDecisao(); /*Chama o apoio no caso de passar pelo ponto de partida, pois para em outra casa para execucao de uma acao*/
			} else if (jogoTipoAcao == JogoTipoAcao.receberLucroDividendo) {
				Jogo.setJOGRODreceberLucroDividendo();
				mensagemFrame.dispose();
				jogadorRodadaFrame.setTrueTerminaRodadaBotao();
			} else if (jogoTipoAcao == JogoTipoAcao.receberDosJogadores) {
				Jogo.setCSRexecutarCartaoSorteReves();
				mensagemFrame.dispose();
				jogadorRodadaFrame.setTrueTerminaRodadaBotao();
			} else if (jogoTipoAcao == JogoTipoAcao.irParaPontoPartida) {
				Jogo.setCSRexecutarCartaoSorteReves();
				mensagemFrame.dispose();
				Frame_Principal.setAtualizaTabuleiroPanel();
				jogadorRodadaFrame.setTrueTerminaRodadaBotao();
			} else if (jogoTipoAcao == JogoTipoAcao.recebeCartaoLivraDaPrisao) {
				Jogo.setCSRexecutarCartaoSorteReves();
				mensagemFrame.dispose();
				jogadorRodadaFrame.setTrueTerminaRodadaBotao();
			} 
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());}
		Jogo.setGAMEAtualiza();
	}
	///// Acao Pagar
	public static void setAcaoPagar(JogoTipoAcao jogoTipoAcao) {
		try {
			if (jogoTipoAcao == JogoTipoAcao.pagarAoBancoReves) {
				Jogo.setCSRexecutarCartaoSorteReves();
			} else if (jogoTipoAcao == JogoTipoAcao.pagarImpostoRenda) {
				Jogo.setJOGRODpagarImpostoDeRenda();
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			return;
		}
		mensagemFrame.dispose();
		jogadorRodadaFrame.setTrueTerminaRodadaBotao();
		Jogo.setGAMEAtualiza();
	}
	///// Acao Prisao
	public static void setPrisao(JogoTipoAcao jogoTipoAcao) {
		try {
			if (jogoTipoAcao == JogoTipoAcao.vaiParaPrisao) {
				mensagemFrame.dispose();
				jogadorRodadaFrame.setTrueTerminaRodadaBotao();
				Jogo.setJOGRODvaiParaPrisao();
				Frame_Principal.setAtualizaTabuleiroPanel();
				
				Jogo.setGAMEAtualiza();

				
			} else if (jogoTipoAcao == JogoTipoAcao.pagarAoBancoPrisao) {
				
				Jogo.setJOGRODpagaSairPrisao();
				mensagemFrame.dispose();
				Jogo.setGAMEAtualiza();
				dadoFrame = new Frame_Dado(TipoDado.dadoDeJogo); 
			}	
			else { // if(tipoAcao == JogoTipoAcao.jogarDados)
				
				mensagemFrame.dispose();
				jogouDadoPrisao = true;
				dadoFrame = new Frame_Dado(TipoDado.dadoDePrisao);
				
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());}
	}
	///// Acao Hipoteca
	public static void setHipoteca(JogoTipoAcao jogoTipoAcao, int posicao, int numJogador) {

		if (jogoTipoAcao == JogoTipoAcao.pagarHipoteca) {
				try
				{
					Jogo.setJOGRODpagarHipoteca(Jogo.getJOGRODlocalizacao(),Jogo.getJOGRODid());
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
					return;
				}
				jogadorRodadaFrame.setTrueTerminaRodadaBotao();
				mensagemFrame.dispose();
			} else if (jogoTipoAcao == JogoTipoAcao.hipotecarPropriedade) {
				try
				{
				Jogo.setPROPhipotecarPropriedade(posicao, numJogador);
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
					return;
				}
			}
		Jogo.setGAMEAtualiza();
	}
	///// Acao Propriedade
	public static void setPropriedade(JogoTipoAcao jogoTipoAcao, JFrame frame, int posicao) {
			if (jogoTipoAcao == JogoTipoAcao.comprarPropriedadeDoBanco) {
				try 
				{
					Jogo.setPROPcomprarPropriedade(Jogo.getJOGRODlocalizacao(),Jogo.getJOGRODid());
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
					return;
				}
					mensagemFrame.dispose();
					jogadorRodadaFrame.setTrueTerminaRodadaBotao();
			} else if (jogoTipoAcao == JogoTipoAcao.venderPropriedadeParaBanco) {
				Jogo.setPROPvenderPropriedadeParaBanco(posicao,Jogo.getJOGRODid());
				frame.dispose();
			} else if (jogoTipoAcao == JogoTipoAcao.pagarAluguel) {
				try 
				{
					Jogo.setJOGRODpagarAluguel(Jogo.getJOGRODlocalizacao());
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
					return;
				}
					mensagemFrame.dispose();
					jogadorRodadaFrame.setTrueTerminaRodadaBotao();
			}
		Jogo.setGAMEAtualiza();
	}
	///// Acao Ir Para Propriedade frame
	public static void setIrParaPropriedadeFrame() {
		mensagemFrame.dispose();
		getPropriedade(Jogo.getJOGRODlocalizacao(), Jogo.getJOGRODid());
		jogadorRodadaFrame.setTrueTerminaRodadaBotao();	
	}
	///// Acao Fechar frame
	public static void setFecharMesagemFrame() {
		mensagemFrame.dispose();
		jogadorRodadaFrame.setTrueTerminaRodadaBotao();
	}
	
	/***************************************************************************
	**** Funcoes relacionadas a Apoio a Controle
	***************************************************************************/
	///// Controle sobre a Frame Propriedade
	public static void fecharPropriedadeFrameDoJogador(int numJogador) {
		if(listPropriedadeFrame.isEmpty() != true) {
			// Pecorre a lista
			int cont=0;
			while(listPropriedadeFrame.size()!=cont) {
				// guarda o elemento da posicao especifica numa vareavel local
				Frame_Propriedade tmpFrame = (Frame_Propriedade) listPropriedadeFrame.get(cont);
				// compara se a frame e do jogador desejado
				if(tmpFrame.getNumJogador() == numJogador) {
					// a frame e do jogador, logo remove da lista e fecha a janela.
					listPropriedadeFrame.remove(cont);
					tmpFrame.setCloseFrame();
					tmpFrame = null;
					cont = 0;
				} else {
					cont++;
				}
			}//WHILE
		}//IF
	}
	@SuppressWarnings("unused")
	private static boolean existePropriedadeFrame(int posicao) {
		if(listPropriedadeFrame.isEmpty() != true) {
			// Pecorre a lista
			int cont=0;
			while(listPropriedadeFrame.size()!=cont) {
				// guarda o elemento da posicao especifica numa vareavel local
				Frame_Propriedade tmpFrame = (Frame_Propriedade) listPropriedadeFrame.get(cont);
				// compara se a frame da posicao desejada ja existe
				if(tmpFrame.getPosicao() == posicao) {
					return true;	
				} 
				cont++;
			}//FOR
		}//IF
		return false;
	}
	
	///// Controle sobre qual acao deve ser tomada no jogo
	private static void apoioDecisao() {
		mensagemFrame = null;
		JogoTipoCasa tipoCasa = Jogo.getJOGRODtipoCasaTabuleiro();
		switch (tipoCasa) {
		case empresa:
			mensagemFrame = new Frame_Mensagem(TipoMensagemFrame.propriedade);
			break;
		case impostoRenda:
			mensagemFrame = new Frame_Mensagem(TipoMensagemFrame.impostoRenda);
			break;
		case lucroDividendo:
			mensagemFrame = new Frame_Mensagem(TipoMensagemFrame.lucroDividendo);
			break;
		case paradaLivre:
			jogadorRodadaFrame.setTrueTerminaRodadaBotao();
			break;
		case pontoDePartida:
			if(Jogo.getJOGRODlocalizacao()==0)
				jogadorRodadaFrame.setTrueTerminaRodadaBotao();
			break;
		case prisao:
			mensagemFrame = new Frame_Mensagem(TipoMensagemFrame.prisao);
			break;
		case sorteReves:
			mensagemFrame = new Frame_Mensagem(TipoMensagemFrame.sorteReves);
			break;
		case terreno:
			mensagemFrame = new Frame_Mensagem(TipoMensagemFrame.propriedade);
			break;
		case vaiParaPrisao:
			mensagemFrame = new Frame_Mensagem(TipoMensagemFrame.vaiParaPrisao);
			break;
		}
	}
	
	/***************************************************************************
	**** Funcoes MAIN
	****************************************************************************/

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Controle.getInstance();
			}
		});
	}
}
