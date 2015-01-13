package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Jogo;
import control.Controle;

@SuppressWarnings("serial")
public class Panel_DadoOrdemJogadores extends JPanel implements MouseListener{

	Panel_Dado jogNDado[];
	
	public Panel_DadoOrdemJogadores() {	
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.green.darker().darker());
		
		this.panelOrdemJogadores();
		this.botaoConfirmarOrdemJogadores();
		
		this.setVisible(true);
	}
	
	private void botaoConfirmarOrdemJogadores () {
		JButton confirmarOrdemJogadores = new JButton("Fechar");
		this.add(confirmarOrdemJogadores, BorderLayout.SOUTH);
		confirmarOrdemJogadores.addMouseListener(this);
	}
	
	private void panelOrdemJogadores () {
		JPanel panelDado = new JPanel();
		panelDado.setBackground(Color.green.darker().darker());
		panelDado.setLayout(new GridLayout( (Jogo.getGAMEquantidadeJogador() + 1)/2 , 2)); //Adiciona 1 para que a fração
																						   //resulte o numero certo de linhas necessarias
		jogNDado = new Panel_Dado[ Jogo.getGAMEquantidadeJogador() ]; //NUmero maximo de jogadores
		
		for(int i = 0; i < Jogo.getGAMEquantidadeJogador(); i++)
		{
			jogNDado[i] = new Panel_Dado(i + 1);
			panelDado.add(jogNDado[i]);
		}
		this.add(panelDado, BorderLayout.NORTH);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void mousePressed(MouseEvent e) {
		Controle.setConfirmarOrdemJogadores(jogNDado);
	}

	public void mouseClicked(MouseEvent e)  {	}

	public void mouseReleased(MouseEvent e) {	}

	public void mouseEntered(MouseEvent e)  {	}

	public void mouseExited(MouseEvent e)   {	}
}