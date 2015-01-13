package view.frame;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import view.jmenu.Menu_Jogador;
import view.panel.Panel_Jogador;

@SuppressWarnings("serial")
public class Frame_Jogador extends JFrame {
	
	private Menu_Jogador menu = null;
	private JPanel jogadorPanel = null;

	public Frame_Jogador (int numJogador) 
	{
		if(numJogador == 0) {
			// PARA Jogador da RODADA
			// Define barra de menu
			menu = new Menu_Jogador();
			this.add(menu);
			this.setJMenuBar(menu);
			// Define nome ultilizado na barra de titulo da janela
			this.setTitle("Jogador da Rodada");
			// Define nao deixa fechar janela
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			// Posiciona a janela inicial no centro da tela
			this.setLocation(Frame_Principal.getInstance().getX(), Frame_Principal.getInstance().getY());
		} 
		else 
		{
			// Define nome ultilizado na barra de titulo da janela
			this.setTitle("Jogador "+numJogador);
			// Dispose a janela ao fechar
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		// Define cor de fundo do frame (primeira camada da janela)
		this.setBackground(Color.white);
		// Define o posicionamento inicial e sua dimensao
		this.setBounds(0, 0, 400, 440);
		// Permite redimencionamento do tamanho da janela
		this.setResizable(false);
		// Posiciona a janela inicial no centro da tela
		this.setLocationRelativeTo(null);
		// Adiciona o panel do jogador
		jogadorPanel = new Panel_Jogador(numJogador);
		this.add(jogadorPanel);

		// Permite visibilidades dos componetes inseridos nesta janela
		this.setVisible(true);
	}
	public void setFalseMenuJogador() {
		menu.setVisible(false);
		this.revalidate();
		this.repaint();
	}
	
	public void setTrueTerminaRodadaBotao() {
		menu.setTrueTerminaRodadaBotao();
		this.revalidate();
		this.repaint();
	}
	
	public void setFalseJogarDado() {
		menu.setFalseJogarDado();
		this.revalidate();
		this.repaint();
	}
	
	public void setTrueJogarDado() {
		menu.setTrueJogarDado();
		this.revalidate();
		this.repaint();
	}
	
	public void setCloseFrame() {
		this.jogadorPanel.removeAll();
		this.removeAll();
		this.dispose();
	}
}
