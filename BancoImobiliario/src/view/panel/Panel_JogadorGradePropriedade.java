package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Jogo;
import view.Imagem;
import view.Imagem.TipoImagem;
import control.Controle;

@SuppressWarnings("serial")
public class Panel_JogadorGradePropriedade extends JPanel {

	
	/***************************************************************************
	**** Vareaveis Classe Panel Jogador Grade Propriedade
	***************************************************************************/
	
	private int prop[] = null, numJogador;
	private MiniPropriedadeButton[] miniPropriedade = null;
	private JPanel gradePropriedade = null;
	private JScrollPane scrollpane = null;
	
	/***************************************************************************
	**** Construtor Dedault Classe Panel Jogador Grade Propriedade
	***************************************************************************/
	
	public Panel_JogadorGradePropriedade(int numJogador) {
		super(true);
		this.numJogador = numJogador;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		setTitulo(numJogador);
		setGradeDePropriedadeListadas( numJogador);
		this.setVisible(true);
	}
	
	/***************************************************************************
	**** Funcoes Classe Panel Jogador Grade Propriedade
	***************************************************************************/
	
	private void setTitulo(int numJogador) {
		JLabel titulo = new JLabel("Listagem de Propriedade do Jogador",JLabel.CENTER);
		titulo.setBackground(Color.white);
		this.add(titulo, BorderLayout.PAGE_START);
	}
	
	private void setGradeDePropriedadeListadas(int numJogador) {
		// Inicializando estruturas de apoio
		setListagemPropriedade( numJogador);
		if (prop == null)
			return;
			//throw new IllegalArgumentException(	"Erro na geracao da Listagem Propriedade a venda, prop[] = NULL");
		setPropriedade();
		// Calculo para saber quantas linhas
		int numLinha = miniPropriedade.length/3;
		if(miniPropriedade.length%3 != 0 )
			numLinha++;
		// Inclusao dos mercadoBox no MercadoPanel
		if(gradePropriedade != null){
			gradePropriedade.removeAll();
			gradePropriedade = null;
			scrollpane.removeAll();
			scrollpane = null;
			this.remove(scrollpane);
		}
		gradePropriedade = new JPanel();
		gradePropriedade.setLayout(new GridLayout(numLinha, 4));
		gradePropriedade.setBackground(Color.white);
		gradePropriedade.setVisible(true);
		gradePropriedade.setPreferredSize(new Dimension(390,numLinha*50));
		if (gradePropriedade.getComponentCount() == 0) //O certo é Iqual a zero
			gradePropriedade.removeAll();
		if (miniPropriedade.length == 0)
			throw new IllegalArgumentException("Erro na geracao da Listagem Propriedade a venda, mercadoBoxs[] = NULL");
		if( prop[0] != 0){
			for (int i = 0; i < prop.length; i++){
				gradePropriedade.add(miniPropriedade[i]);
			}
		}
		scrollpane = new JScrollPane(gradePropriedade,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollpane, BorderLayout.CENTER);
	}

	private void setListagemPropriedade(int numJogador) {
		// Inicializacao de propriedade

		int temp[] = new int[28];
		int index = 0;
		
		if (prop != null)
			prop = null;
		
		for (int posicao = 1; posicao < Jogo.qtdTotolCasasTabuleiro; posicao++) {
			if (Jogo.getPROPvalidaCasaTabuleiroPropriedade(posicao) == true) {// checa se é uma propriedade
				if (Jogo.getPROPproprietario(posicao) == numJogador) { 
					// se tem valor de venda, ha propriedade esta a venda
					temp[index] = posicao; // guarda a num da propriedade
					index++; // atualiza vetor
				}
			}
		}
		
		if(index == 0)
			return;
		
		prop = new int[index];
		for(int i = 0; i < index; i++){
			prop[i] = temp[i];
		}
	}

	private void setPropriedade() {
		// Inicializacao de propriedade
		if (miniPropriedade != null) {
			miniPropriedade = null;
		}
		if(prop == null || prop.length == 0)
			return;
		
		miniPropriedade = new MiniPropriedadeButton[prop.length];

		if (prop != null){
			for (int i = 0; i < prop.length; i++){
				miniPropriedade[i] = new MiniPropriedadeButton(prop[i]);
			}
		}
	}
	
	/***************************************************************************
	**** Classe Mini Propriedade Button
	***************************************************************************/
	
	class MiniPropriedadeButton extends JPanel {	
		
	////////////////////////////////////////////////////////////////////////////
	//// Construtor Default Mini Propriedade Button
	////////////////////////////////////////////////////////////////////////////
		public int prop = 0;
		
		public MiniPropriedadeButton(final int numPropriedade) {
			this.prop = numPropriedade;
		
			// Carregua a Imagem
			Imagem images = new Imagem(TipoImagem.minipropriedade,numPropriedade);
			Dimension size = images.getSizeImagem();
			
			// Cria Jlabel e set acao de clique
			JLabel imgButton = images.getImagemLabel();
			imgButton.addMouseListener(new MouseAdapter() {
				  public void mouseClicked(MouseEvent e) {
					  Controle.getPropriedade(numPropriedade,numJogador);
				  }
				});	
			imgButton.setBackground(Color.white);
			imgButton.setSize(size);
			
			// Panel de suporte, para nao ter remencionamento do botao
			JPanel panel = new JPanel();
			panel.setBackground(Color.white);
			panel.add(imgButton);
			panel.setSize(imgButton.getSize().width, imgButton.getSize().height);
			
			this.setLayout(new BorderLayout());
			this.setSize((imgButton.getSize().width)+10, imgButton.getSize().height+10);
			this.setPreferredSize(this.getSize());
			this.setMaximumSize(this.getSize());
			this.setMinimumSize(this.getSize());

			this.add(panel, BorderLayout.CENTER);
			this.setVisible(true);
		}
	}
}