package view.panel;

import java.awt.Graphics;

import javax.swing.JPanel;

import model.Jogo;
import view.Imagem;
import view.Imagem.TipoImagem;

@SuppressWarnings("serial")
public class Panel_PrincipalTabuleiro extends JPanel {
	
	private static final int LARG_DEFAULT = 705;
	private static final int ALT_DEFAULT = 748;
	private int[][] alinhamentoJogadorTab;
	private int[][] posicaoTabuleiro;
	private Imagem images[];

	public Panel_PrincipalTabuleiro() {
		this.setLayout(null);
		this.inicializarJog();
		this.inicializarAlinhamentoJogadorTabuleiro();
		this.inicializarCoordenadasCasasDoTabuleiro();
		for( int i = 0; i < Jogo.getGAMEquantidadeJogador(); i++)
			updateJog(i + 1);
		repaint();
		this.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for( int i = 0; i < Jogo.getGAMEquantidadeJogador() + 1; i++) //Tabuleiro + jogadores
			if(this.images[i] != null)
				this.images[i].paintComponent(g);
	}

	/** Funcoes de posicionamento da imagem */

	private void inicializarJog (){
		images = new Imagem[ Jogo.getGAMEquantidadeJogador() + 1 ]; //Tabuleiro + Jogadores	
		this.images[0] = new Imagem(TipoImagem.tabuleiro); //Tabuleiro
		for( int i = 1; i < Jogo.getGAMEquantidadeJogador() + 1; i++) //Jogadores 
			this.images[i] = new Imagem(TipoImagem.logoJogadorTabuleiro,Jogo.getJOGlogoJogador(i));		
	}
	
	private void inicializarAlinhamentoJogadorTabuleiro() {

		alinhamentoJogadorTab = new int[Jogo.getGAMEquantidadeJogador()][2];
		switch (Jogo.getGAMEquantidadeJogador()) {
		case 2:
			this.alinhamentoJogadorTab[0][0] = 5; // x - jog1
			this.alinhamentoJogadorTab[0][1] = 5; // y - jog2
			this.alinhamentoJogadorTab[1][0] = 30;// x - jog1
			this.alinhamentoJogadorTab[1][1] = 30;// y - jog2
			break;
		case 3:
			this.alinhamentoJogadorTab[0][0] = 0; // x - jog1
			this.alinhamentoJogadorTab[0][1] = 30;// y - jog1
			this.alinhamentoJogadorTab[1][0] = 20;// x - jog2
			this.alinhamentoJogadorTab[1][1] = 0; // y - jog2
			this.alinhamentoJogadorTab[2][0] = 35;// x - jog1
			this.alinhamentoJogadorTab[2][1] = 30;// y - jog2
			break;
		case 4:
			this.alinhamentoJogadorTab[0][0] = 0;// x - jog1
			this.alinhamentoJogadorTab[0][1] = 0;// y - jog1
			this.alinhamentoJogadorTab[1][0] = 32;// x - jog2
			this.alinhamentoJogadorTab[1][1] = 0;// y - jog2
			this.alinhamentoJogadorTab[2][0] = 0;// x - jog3
			this.alinhamentoJogadorTab[2][1] = 30;// y - jog3
			this.alinhamentoJogadorTab[3][0] = 32;// x - jog4
			this.alinhamentoJogadorTab[3][1] = 30;// y - jog4
			break;
		case 5:
			this.alinhamentoJogadorTab[0][0] = -5;// x - jog1
			this.alinhamentoJogadorTab[0][1] = -8;// y - jog1
			this.alinhamentoJogadorTab[1][0] = 35;// x - jog2
			this.alinhamentoJogadorTab[1][1] = -8;// y - jog2
			this.alinhamentoJogadorTab[2][0] = 14;// x - jog3
			this.alinhamentoJogadorTab[2][1] = 16;// y - jog3
			this.alinhamentoJogadorTab[3][0] = -5;// x - jog4
			this.alinhamentoJogadorTab[3][1] = 35;// y - jog4
			this.alinhamentoJogadorTab[4][0] = 35;// x - jog5
			this.alinhamentoJogadorTab[4][1] = 35;// y - jog5
			break;
		case 6:
			this.alinhamentoJogadorTab[0][0] = -5;// x - jog1
			this.alinhamentoJogadorTab[0][1] = 10;// y - jog1
			this.alinhamentoJogadorTab[1][0] = 18;// x - jog2
			this.alinhamentoJogadorTab[1][1] = -8;// y - jog2
			this.alinhamentoJogadorTab[2][0] = 38;// x - jog3
			this.alinhamentoJogadorTab[2][1] = 10;// y - jog3
			this.alinhamentoJogadorTab[3][0] = -5;// x - jog4
			this.alinhamentoJogadorTab[3][1] = 35;// y - jog4
			this.alinhamentoJogadorTab[4][0] = 18;// x - jog5
			this.alinhamentoJogadorTab[4][1] = 25;// y - jog5
			this.alinhamentoJogadorTab[5][0] = 38;// x - jog6
			this.alinhamentoJogadorTab[5][1] = 35;// y - jog6
			break;
		}
	}

	private void inicializarCoordenadasCasasDoTabuleiro() {
		this.posicaoTabuleiro = new int[40][2];
		int xLinaBase, yLinaBase, i, w;
		int distanciaEntreCasa = 64;

		for (i = 0, w = 0; i <= 10; i++, w++) {
			xLinaBase = 0;
			yLinaBase = 0;
			this.posicaoTabuleiro[i][0] = xLinaBase + distanciaEntreCasa * w;
			this.posicaoTabuleiro[i][1] = yLinaBase;
		}

		for (i = 11, w = 0; i <= 19; i++, w++) {
			xLinaBase = LARG_DEFAULT - distanciaEntreCasa;
			yLinaBase = distanciaEntreCasa;
			this.posicaoTabuleiro[i][0] = xLinaBase;
			this.posicaoTabuleiro[i][1] = yLinaBase + distanciaEntreCasa * w;
		}

		for (i = 20, w = 0; i <= 30; i++, w++) {
			xLinaBase = LARG_DEFAULT - 64;
			yLinaBase = ALT_DEFAULT - distanciaEntreCasa*2;
			this.posicaoTabuleiro[i][0] = xLinaBase - distanciaEntreCasa * w;
			this.posicaoTabuleiro[i][1] = yLinaBase;
		}

		for (i = 31, w = 0; i <= 39; i++, w++) {
			xLinaBase = 0;
			yLinaBase = ALT_DEFAULT - distanciaEntreCasa*3;
			this.posicaoTabuleiro[i][0] = xLinaBase;
			this.posicaoTabuleiro[i][1] = yLinaBase - distanciaEntreCasa * w;
		}
	}
	
	public void removeJog(int numJogador) {
		//Apaga jogador
		for( int i = 1; i < Jogo.getGAMEquantidadeJogador() + 1; i++) {
			if(Jogo.getJOGlogoJogador(numJogador) == this.images[i].getLocalLogo())
			this.images[i] = null;
		}
		this.repaint();
	}
	
	public void updateJog(int numJogador) {
		int posicao = Jogo.getJOGposicaoJogadorTabuleiro(numJogador);
		int eixoX = posicaoTabuleiro[posicao][0]+alinhamentoJogadorTab[numJogador-1][0];
		int eixoY = posicaoTabuleiro[posicao][1]+alinhamentoJogadorTab[numJogador-1][1];
		this.images[numJogador].setUpdateEixos(eixoX, eixoY);
		posicao++;
		posicao%=40;
		this.repaint();
	}
}