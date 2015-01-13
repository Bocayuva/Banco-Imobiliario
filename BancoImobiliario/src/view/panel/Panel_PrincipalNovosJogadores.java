package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.Jogo;
import control.Controle;

@SuppressWarnings("serial")
public class Panel_PrincipalNovosJogadores extends JPanel implements Observer, MouseListener, ActionListener {

    private JPanel radioPanel = null;
    private JPanel jogadorComboBox = null;
    private JPanel decisaoBotao = null;
	private static int qtdTotalJogadores = 0;
	private static Panel_JogadorBox[] jogadorBox = null;
	private JRadioButton radiobutton2 = null,radiobutton3 = null,radiobutton4 = null,radiobutton5 = null,radiobutton6 = null;
	private JButton confirmaJogadores = null;
	
	public Panel_PrincipalNovosJogadores() {
		Jogo.getInstance().addObserver(this);
		this.setLayout(new BorderLayout());
		this.radioTotalJogadorPanel();
		this.jogadorComboBoxPanel();
		this.decisaoBotao();
		this.setBackground(Color.WHITE);
		this.setVisible(true);
	}

	private void radioTotalJogadorPanel () {
		   radiobutton2 = new JRadioButton("2");
		   radiobutton2.setSelected(false);
		   radiobutton2.setActionCommand("2");
		   radiobutton2.addActionListener(this);
		   radiobutton2.setBackground(Color.white);
	       
		   radiobutton3 = new JRadioButton("3");
		   radiobutton3.setSelected(false);
		   radiobutton3.setActionCommand("3");
		   radiobutton3.addActionListener(this);
		   radiobutton3.setBackground(Color.white);
	        
		   radiobutton4 = new JRadioButton("4");
		   radiobutton4.setSelected(false);
		   radiobutton4.setActionCommand("4");
		   radiobutton4.addActionListener(this);
		   radiobutton4.setBackground(Color.white);
	        
		   radiobutton5 = new JRadioButton("5");
		   radiobutton5.setSelected(false);
		   radiobutton5.setActionCommand("5");
		   radiobutton5.addActionListener(this);
		   radiobutton5.setBackground(Color.white);
	        
		   radiobutton6 = new JRadioButton("6");
		   radiobutton6.setSelected(false);
		   radiobutton6.setActionCommand("6");
		   radiobutton6.addActionListener(this);
		   radiobutton6.setBackground(Color.white);
	 
	        //Group the radio buttons.
	        ButtonGroup group = new ButtonGroup();
	        group.add(radiobutton2);
	        group.add(radiobutton3);
	        group.add(radiobutton4);
	        group.add(radiobutton5);
	        group.add(radiobutton6);
	        
	        JLabel tituto = new JLabel("Numero Total de Jogadores: ");
	        
	        //Put the radio buttons in a column in a panel.
	        this.radioPanel = new JPanel(new FlowLayout());
	        radioPanel.setBackground(Color.white);
	        radioPanel.add(tituto);
	        radioPanel.add(radiobutton2);
	        radioPanel.add(radiobutton3);
	        radioPanel.add(radiobutton4);
	        radioPanel.add(radiobutton5);
	        radioPanel.add(radiobutton6);
	 
	        add(radioPanel, BorderLayout.PAGE_START);
	}
	
	private void jogadorComboBoxPanel () {
		this.inicializaJogadorBox();
		this.jogadorComboBox = new JPanel();
		this.jogadorComboBox.setBackground(Color.white);
		this.jogadorComboBox.setLayout( new GridLayout(2,3));
		for (int x = 0; x < qtdTotalJogadores ; x++)
			this.jogadorComboBox.add(jogadorBox[x], BorderLayout.CENTER);
		this.add(jogadorComboBox,BorderLayout.CENTER);
		this.jogadorComboBox.setVisible(true);
	}
	
	private void decisaoBotao() {
		JButton voltarMenu = new JButton("Voltar");
		voltarMenu.setName("VoltarMenu");
		voltarMenu.addMouseListener(this);

		confirmaJogadores = new JButton("Confirma");
		confirmaJogadores.setName("ConfirmaJogadores");
		confirmaJogadores.addMouseListener(this);
		confirmaJogadores.setVisible(false);
		
		this.decisaoBotao = new JPanel(new FlowLayout());
		decisaoBotao.setBackground(Color.WHITE);
		decisaoBotao.add(voltarMenu);
		decisaoBotao.add(confirmaJogadores);
		this.add(decisaoBotao, BorderLayout.PAGE_END);
	}	
	
	private void setAtualizaPaneis () {
		this.remove(jogadorComboBox);
		if(jogadorComboBox.getComponentCount()!=qtdTotalJogadores)
			this.jogadorComboBoxPanel();
		confirmaJogadores.setVisible(true);
		this.repaint();
		this.validate();
	}
	
	private void inicializaJogadorBox () {
		if(jogadorBox!=null) {
			for(int i=0; i< jogadorBox.length ; i++)
				jogadorBox[i] = null;
			jogadorBox = null;
		}
		jogadorBox = new Panel_JogadorBox[6];
		for(int i=0; i< jogadorBox.length ; i++)
			jogadorBox[i] = new Panel_JogadorBox(i+1);		
	}
	
	private void setQtdJogadores(int qtdJogadores) {
		qtdTotalJogadores = qtdJogadores;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	public void update(Observable observ, Object object) {
		setQtdJogadores(Jogo.getGAMEquantidadeJogador()); 
	}
	
	// Listens dos Botoes
    public void actionPerformed(ActionEvent e) {
    	Controle.setConfirmaQtdTotalJogadores(Integer.parseInt(e.getActionCommand()));
    	this.setAtualizaPaneis();
    }
    
	public void mouseClicked(MouseEvent e) {	
		if (e.getComponent().getName().equals("VoltarMenu")) {
			Controle.setVoltarParaInicio();
		}
		if (e.getComponent().getName().equals("ConfirmaJogadores")) {
			this.jogadorComboBox.removeAll();
			this.remove(jogadorComboBox);
			Controle.setConfirmaAdicionarJogadores(jogadorBox);
		}
	}
	
	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {	}
}