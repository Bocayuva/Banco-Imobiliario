package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Jogo;
import control.Controle;

@SuppressWarnings("serial")
public class Panel_PropriedadeApoioPorVendaPropriedade extends JPanel implements MouseListener, KeyListener {
	
	private JTextField input ;
	private JLabel texto;
	private JPanel group, group2, group3;
	private JFrame frame;
	private int numJogador, posicao;
	private double valor;
	
    public Panel_PropriedadeApoioPorVendaPropriedade (JFrame frame, int posicao, int numJogador) {
    	this.frame = frame;
    	this.numJogador = numJogador;
    	this.posicao = posicao;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);	
		setInformacoes();
		setBotao();
		this.setVisible(true);
	}
	
	private void setInformacoes() {
		JLabel valorNominal = new JLabel("Valor Nominal:  $"+Jogo.getPROPvalorNominalPropriedade(posicao), JLabel.CENTER);
		JLabel valorVenda;
		if(Jogo.getPROPvalorVendaDaPropriedadeMercado(posicao) == 0){
			valorVenda = new JLabel("Propriedade nao esta a Venda", JLabel.CENTER);
		} else {
			valorVenda = new JLabel("Valor de Venda: $"+Jogo.getPROPvalorVendaDaPropriedadeMercado(posicao), JLabel.CENTER);
		}
		group3 = new JPanel();
		group3.add(valorNominal);
		group3.add(valorVenda);
		group3.setBackground(Color.white);
		group3.setLayout(new GridLayout(group3.getComponentCount(),1));

		this.add(group3, BorderLayout.PAGE_START);
	}
	
	private void setBotao() {
	
		texto = new JLabel("Valor: $");
		input = new JTextField(5); 
		input.addKeyListener( this);
		group = new JPanel();
		group.setLayout(new FlowLayout());
		group.setBackground(Color.white);
		group.add(texto);
		group.add(input);
		
	    JButton confirma = new JButton("Confirma");
	    confirma.setName("Confirma");
	    confirma.addMouseListener(this);
	    
		JButton fechar = new JButton("Fechar");
		fechar.setName("Fechar");
		fechar.addMouseListener(this);
		
		group2 = new JPanel();
		group2.setLayout(new FlowLayout());
		group2.setBackground(Color.white);
		group2.add(fechar);
		group2.add(confirma);
		
		
		JPanel uni = new JPanel();
		uni.add(group);
		uni.add(group2);
		uni.setLayout(new GridLayout(uni.getComponentCount(),1));
		uni.setVisible(true);
		
		this.add(uni, BorderLayout.PAGE_END);
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent().getName().equals("Confirma")) {
			Controle.setListarPropriedade(frame, numJogador,posicao,(int) valor);
		}
		if (e.getComponent().getName().equals("Fechar")) {
			frame.dispose();
		}
	}
	
	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {	}
	
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar(); 
		//you can add more conditions here to 
		//allow more keys to pass or ignored 
		if( key<'0' || key>'9' ){ 
		e.consume(); 
		} 
	}

	public void keyReleased(KeyEvent e) {
		try{ 
			String str = input.getText(); 
			valor = Double.parseDouble(str); 
			} catch( Exception ex){ 
			//if JTextField is empty or contains letters 
			} 
		
	} 
	
	public void keyPressed(KeyEvent e) 	{	}
}
