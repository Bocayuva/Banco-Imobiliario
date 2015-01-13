package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Jogo;
import view.Imagem;
import view.Imagem.TipoImagem;
import control.Controle;

@SuppressWarnings("serial")
public class Panel_PropriedadeApoioTrocaPropriedade extends JPanel implements MouseListener, ActionListener{
	
	private int posicao, numJogadorDesejado=-1, posicaoDesejada=-1;
	private JPanel infoInicial = null, gradePropriedades = null, botoes = null,panelInfoSup=null, apoio = null,resultado = null;
	private LinkedList<String> numJogadores = null;
	private	JComboBox<String> comboBox = null;
	private JFrame frame = null;
	private JScrollPane scrollpane = null;
	
	public Panel_PropriedadeApoioTrocaPropriedade (JFrame frame, int posicao) {
		this.posicao = posicao;
		this.frame = frame;
		this.setLayout(null);
		this.setBackground(Color.white);
		
		gradePropriedades = new JPanel();
		botoes = new JPanel();
		
		setInformacoes();
		setGradePropriedades();
		setBotoes();
		
		this.setVisible(true);
	}
	
	private void setInformacoes() {
		//Titulo jogador desaja troca
		JLabel titulo = new JLabel("Oferece a Prop.: ", JLabel.LEFT);
		// imagem do terreno de troca
		Imagem images = new Imagem(TipoImagem.minipropriedade,posicao);
		JLabel imgButton = images.getImagemLabel();
	

		panelInfoSup = new JPanel();
		panelInfoSup.add(titulo);
		panelInfoSup.add(imgButton);
		panelInfoSup.setLayout(new FlowLayout());
		panelInfoSup.setBackground(Color.white);
		
		comboBox = carregaCombo();
		
		infoInicial = null;
		infoInicial = new JPanel();
		infoInicial.add(panelInfoSup);
		infoInicial.add(comboBox);
		infoInicial.setLayout(new GridLayout(infoInicial.getComponentCount(),1));
		infoInicial.setBackground(Color.white);
		infoInicial.setVisible(true);
		infoInicial.setBounds(0, 0, 300, 70);
		this.add(infoInicial);
	}	

	private JComboBox<String> carregaCombo() {

		numJogadores = null;
		numJogadores = new LinkedList<String>();

		// Inicializa listagem de jogadores no jogo
		
		numJogadores.addFirst("Jogador");
		for (int i = 1; i <= Jogo.getGAMEquantidadeJogador(); i++) {
			if(i != Jogo.getPROPproprietario(posicao)) {
				numJogadores.add(""+i);
			}
		}
		// Cria a comboBox somente com os jogadores do jogo
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addActionListener(this);
		for (int i = 0; i < numJogadores.size(); i++) {
			comboBox.addItem(numJogadores.get(i));
		}
		return comboBox;
	}
	
	@SuppressWarnings("rawtypes")
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		String num = (String) cb.getSelectedItem();
		
		if("Jogador" == num)
			numJogadorDesejado = -1;
		else {
			if(numJogadorDesejado > 0 && resultado.getComponentCount() > 0) {
				resultado.removeAll();
				resultado.revalidate();
				resultado.repaint();
			}
			numJogadorDesejado = Integer.parseInt(num);
			gradePropriedades.removeAll();
			setGradePropriedades();
		}
	}
	
	static int proptmp = -1;
	
	private void setGradePropriedades() {	

		apoio = new JPanel();
		apoio.setBackground(Color.white);
		apoio.setVisible(true);

		if(numJogadorDesejado != -1) {
			// busca todas as propriedades do jogador
			final int prop[] = Jogo.getPROPpropriedadeDoJogador(numJogadorDesejado);
			if(numJogadorDesejado == -1 ) {
				if(apoio.getComponentCount()!=0)
					apoio.removeAll();
				JLabel mensg = new JLabel("Por Favor, Selecione um Jogador");
				apoio.add(mensg);
			
			} else if(prop == null || prop.length == 0) {
				if(apoio.getComponentCount()!=0)
					apoio.removeAll();
				JLabel mensg = new JLabel("Jogador nao possui Propriedades");
				apoio.add(mensg);
				apoio.repaint();
				apoio.revalidate();
				gradePropriedades.revalidate();
				gradePropriedades.repaint();
			} else {
				if(apoio.getComponentCount()!=0)
					apoio.removeAll();
				for(int i=0; i < prop.length ; i++) {
					proptmp = prop[i];
					MiniPropriedadeButton mini = new MiniPropriedadeButton(prop[i]);
					apoio.add(mini);
				}
				apoio.repaint();
				apoio.revalidate();
				gradePropriedades.revalidate();
				gradePropriedades.repaint();
			}
		}
		apoio.setLayout(new GridLayout(apoio.getComponentCount(),3));
		scrollpane = new JScrollPane(apoio,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		if(resultado != null && resultado.getComponentCount() > 0) {
			resultado.removeAll();	
			resultado.revalidate();
			resultado.repaint();	
			gradePropriedades.revalidate();
			gradePropriedades.repaint();
		}
		resultado = null;
		resultado =	new JPanel();
		resultado.setLayout(new FlowLayout());
		resultado.setBackground(Color.white);
		if(posicaoDesejada != -1) {
			//Titulo jogador desaja troca
			JLabel titulo = new JLabel("Deseja a Prop.: ");
			// imagem do terreno de troca
			Imagem images = new Imagem(TipoImagem.minipropriedade,posicaoDesejada);
			JLabel imgButton = images.getImagemLabel();
			resultado.add(titulo);
			resultado.add(imgButton);
			resultado.setVisible(true);
			resultado.setSize(300, 100);
		}

		gradePropriedades.setBackground(Color.red);
		gradePropriedades.setLayout(new BorderLayout());
		gradePropriedades.add(scrollpane, BorderLayout.CENTER);
		gradePropriedades.add(resultado, BorderLayout.PAGE_END);
		gradePropriedades.setVisible(true);
		gradePropriedades.setBounds(0, 70, 300, 280);
		this.add(gradePropriedades);
	}
	
	private void setBotoes() {

		botoes.setLayout(new FlowLayout());
		botoes.setBackground(Color.white);
		JButton fechar = new JButton("Fechar");
		fechar.setName("Fechar");
		fechar.addMouseListener(this);
		botoes.add(fechar);
		if(posicaoDesejada != -1){
			JButton trocar = new JButton("Enviar");
			trocar.setName("Enviar");
			trocar.addMouseListener(this);
			botoes.add(trocar);
		}
		botoes.setVisible(true);
		botoes.setBounds(0, 345, 300, 50);
		this.add(botoes);
	}
		
	
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent().getName().equals("Enviar")) {
			Controle.setEnviarProporTrocaPropriedade(posicao,posicaoDesejada);
			frame.dispose();
		}
		if (e.getComponent().getName().equals("Fechar")) {
			frame.dispose();
		}
	}
	
	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {	}
	
	public void paintComponent(Graphics g)  {
		super.paintComponent(g);
	}
	
	/***************************************************************************
	**** Classe Mini Propriedade Button
	***************************************************************************/
	
	class MiniPropriedadeButton extends JPanel {	
		
	////////////////////////////////////////////////////////////////////////////
	//// Construtor Default Mini Propriedade Button
	////////////////////////////////////////////////////////////////////////////
		int prop = 0;
		
		public MiniPropriedadeButton(final int numPropriedade) {
			this.prop = numPropriedade;
		
			// Carregua a Imagem
			Imagem images = new Imagem(TipoImagem.minipropriedade,numPropriedade);
			Dimension size = images.getSizeImagem();
			
			// Cria Jlabel e set acao de clique
			JLabel imgButton = images.getImagemLabel();
			imgButton.addMouseListener(new MouseAdapter() {
				  public void mouseClicked(MouseEvent e) {
					  posicaoDesejada = prop;
					
					  gradePropriedades.removeAll();
					  botoes.removeAll();
	
					  setGradePropriedades();
					  setBotoes();
					  
					  infoInicial.revalidate();
					  infoInicial.repaint();
 					  
					  apoio.repaint();
					  apoio.revalidate();
					  
					  gradePropriedades.revalidate();
					  gradePropriedades.repaint();
					  
					  botoes.revalidate();
					  botoes.repaint();
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
