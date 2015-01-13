package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Jogo;
import view.Imagem;
import view.Imagem.TipoImagem;
import control.Controle;

@SuppressWarnings("serial")
public class Panel_Mercado extends JPanel implements Observer {

	/***************************************************************************
	*** Vareaveis locais relacionadas ao Panel Mercado
	***************************************************************************/
	
	private int prop[] = null;
	private MercadoBox[] mercadoBoxs = null;
	private JPanel gradePropriedade = null;
	private JScrollPane scrollpane = null;
	
	/***************************************************************************
	*** Construtor Defoult relacionadas ao Panel Mercado
	***************************************************************************/

	public Panel_Mercado() {
		super(true);
		Jogo.getInstance().addObserver(this);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		setTitulo();
		setGradeDePropriedadeListadas();
		this.setVisible(true);
	}
	
	/***************************************************************************
	*** Funcoes relacionadas ao Panel Mercado
	***************************************************************************/
	///// Funcoes de SET dos paneis internos
	private void setTitulo() {
		JLabel titulo = new JLabel("Listagem de Propriedade a Venda pelos Jogadores",JLabel.CENTER);
		this.add(titulo, BorderLayout.PAGE_START);
	}
	
	private void setGradeDePropriedadeListadas() {
		// Inicializando estruturas de apoio
		setListagemPropriedade();
		if (prop == null)
			return;
			//throw new IllegalArgumentException(	"Erro na geracao da Listagem Propriedade a venda, prop[] = NULL");
		setMercadoBox();
		// Calculo para saber quantas linhas
		int numLinha = mercadoBoxs.length/4;
		if(mercadoBoxs.length%4 != 0 )
			numLinha++;
		// Inclusao dos mercadoBox no MercadoPanel
		gradePropriedade = new JPanel();
		gradePropriedade.setLayout(new GridLayout(numLinha, 4));
		gradePropriedade.setBackground(Color.white);
		gradePropriedade.setVisible(true);
		gradePropriedade.setPreferredSize(new Dimension(600,numLinha*225));
		if (gradePropriedade.getComponentCount() != 0)
			gradePropriedade.removeAll();
		if (mercadoBoxs.length == 0)
			throw new IllegalArgumentException("Erro na geracao da Listagem Propriedade a venda, mercadoBoxs[] = NULL");
		for (int i = 0; i < mercadoBoxs.length; i++)
			gradePropriedade.add(mercadoBoxs[i]);
		scrollpane = new JScrollPane(gradePropriedade,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollpane, BorderLayout.CENTER);
	}

	private void setListagemPropriedade() {
		// Inicializacao de mercadoBox
		
		int temp[] = new int[28];
		int index = 0;
		
		if (prop != null)
			prop = null;
	
		for (int posicao = 1; posicao < Jogo.qtdTotolCasasTabuleiro; posicao++) {
			if (Jogo.getPROPvalidaCasaTabuleiroPropriedade(posicao) == true) {// checa se é uma propriedade
				if (Jogo.getPROPvalorVendaDaPropriedadeMercado(posicao) != 0) { 
					// se tem valor de venda, ha propriedade esta a venda
					temp[index] = posicao; // guarda a num da propriedade
					index++; // atualiza vetor
				}
			}
		}
		
		if(index == 0)
			return;
		
		prop = new int[index];
		for(int i = 0; i < index; i++)
			prop[i] = temp[i];
		
	}

	private void setMercadoBox() {
		// Inicializacao de mercadoBox
		if (mercadoBoxs != null) {
			mercadoBoxs = null;
		}
		
		if(prop.length == 0)
			return;
		
		mercadoBoxs = new MercadoBox[prop.length];
		if (prop.length != 0)
			for (int i = 0; i < mercadoBoxs.length; i++) {
				mercadoBoxs[i] = new MercadoBox(prop[i]);
			}
		else
			throw new IllegalArgumentException("Nao possui Propriedade a venda");
	}

	///// Funcoes de update da observer do panel
	public void update(Observable observ, Object object) {	
		
		if(gradePropriedade != null){
			gradePropriedade.removeAll();
			gradePropriedade = null;
			setGradeDePropriedadeListadas();
	//		gradePropriedade.revalidate();
	//		gradePropriedade.repaint();
		}
		this.revalidate();
		this.repaint();
	}
	
	/***************************************************************************
	*** Classe de Apoio ao Panel Mercado
	***************************************************************************/
	
	////////////////////////////////////////////////////////////////////////////
	///// Classe MercadoBox
	class MercadoBox extends JPanel implements ActionListener {

		////////////////////////////////////////////////////////////////////////////
		///// Vareaveis Locais de MercadoBox
		////////////////////////////////////////////////////////////////////////////
		
		private int numPropriedade, idJogadorCompra = 0, numPrecoSolicitado = 0;
		private JButton comprar;
		private JLabel imagem, preco;
		private JPanel botoesGroup,conteudo;
		private	JComboBox<String> comboBox = null;
		private LinkedList<String> numJogadores = null;
		
		////////////////////////////////////////////////////////////////////////////
		///// Constructor Default MercadoBox
		////////////////////////////////////////////////////////////////////////////
		
		public MercadoBox(int numPropriedade) {
			this.setLayout(null);
			this.setMaximumSize(getSize());
			this.setMinimumSize(getSize());
			this.setBackground(Color.white);
			
			getValorVareaveis(numPropriedade);
			setImagem();
			setPanel();
			
			conteudo = new JPanel();
			conteudo.setBackground(Color.white);
			conteudo.setBounds(0, 0, 150, 220);
			conteudo.setLayout(new BorderLayout());
			conteudo.add(imagem, BorderLayout.CENTER);
			conteudo.add(botoesGroup, BorderLayout.PAGE_END);
			conteudo.setVisible(true);
			this.add(conteudo);
			this.setVisible(true);
		}
		
		////////////////////////////////////////////////////////////////////////////
		///// Funcoes SET  panel interno MercadoBox
		////////////////////////////////////////////////////////////////////////////
		
		private void setImagem() {
			imagem = null;
			imagem = new Imagem(TipoImagem.propriedade,numPropriedade).getImagemLabel(150,150);
			imagem.setBounds(0, 0, 300, 200);
			imagem.setOpaque(true);
			imagem.setBackground(Color.white);
		}
		
		private void setPanel() {
			// Panel contendo o Preco da propriedade
			preco = new JLabel("Preco: $ " + numPrecoSolicitado, JLabel.CENTER);
			
			comboBox = carregaCombo();
			
			// Panel contendo o botoes de compra
			comprar = new JButton("Comprar");
			comprar.setName("Comprar");
			comprar.addActionListener(new ActionListener() {  
	            public void actionPerformed(ActionEvent e) {  
	    			if (((String)comboBox.getSelectedItem()  == "Jogador")){
	    				JOptionPane.showMessageDialog(null,
	    						"Erro! Comprador nao Identificado.\n"+
	    				"Selecione o jogador comprador, antes de solicitar a compra");
	    			}	else	{
	    				idJogadorCompra = Integer.parseInt((String) comboBox.getSelectedItem());
	    				Controle.setComprarPropriedadeDoJogador(numPropriedade, idJogadorCompra);
	    			}    				
	            }  
	        });  

			botoesGroup = new JPanel();
			botoesGroup.setBackground(Color.white);
			botoesGroup.setLayout(new BorderLayout());
			botoesGroup.add(preco, BorderLayout.PAGE_START);
			botoesGroup.add(comboBox, BorderLayout.CENTER);
			botoesGroup.add(comprar, BorderLayout.PAGE_END);
			botoesGroup.setVisible(true);
		}

		////////////////////////////////////////////////////////////////////////////
		///// Funcoes relacionadas ao ComboBox de MercadoBox
		////////////////////////////////////////////////////////////////////////////
		
		private JComboBox<String> carregaCombo() {
			
			if(numJogadores!=null){
				numJogadores = null;
				numJogadores = new LinkedList<String>();
			} else {
				numJogadores = new LinkedList<String>();
			}
			// Inicializa listagem de jogadores no jogo
			
			numJogadores.addFirst("Jogador");
			for (int i = 1; i <= Jogo.getGAMEquantidadeJogador(); i++) {
				if(i != Jogo.getPROPproprietario(numPropriedade)) {
					numJogadores.add(""+i);
				}
			}
			// Cria a comboBox somente com os jogadores do jogo
			JComboBox<String> comboBox = new JComboBox<String>();
			for (int i = 0; i < numJogadores.size(); i++) {
				comboBox.addItem(numJogadores.get(i));
			}
			return comboBox;
		}
		
		public void actionPerformed(ActionEvent e) {
			@SuppressWarnings("rawtypes")
			JComboBox cb = (JComboBox) e.getSource();
			String num = (String) cb.getSelectedItem();
			 idJogadorCompra = Integer.parseInt(num);
		}
		
		////////////////////////////////////////////////////////////////////////////
		///// Funcoes de update relacionadas a MercadoBox
		////////////////////////////////////////////////////////////////////////////
		
		public void getValorVareaveis(int numPropriedade) {
			this.numPropriedade = numPropriedade;
			this.idJogadorCompra = 0;
			this.numPrecoSolicitado = Jogo.getPROPvalorVendaDaPropriedadeMercado(numPropriedade);
		}		
	}
}
