package view.jmenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import control.Controle;

@SuppressWarnings("serial")
public class Menu_Jogador extends JMenuBar implements MouseListener{
	
	private JMenu jogarDados = null;
	private JMenu terminadaRodada = null;
	
	public Menu_Jogador () {
		// Cria o botao Jogar Dado
		jogarDados = new JMenu("Declara Falencia");
		jogarDados.setName("Falencia");
		jogarDados.setVisible(true);
		jogarDados.addMouseListener(this);
		this.add(jogarDados);
		
		// Cria espacamento entre os botoes e botao Terminar Rodada
		this.add(Box.createHorizontalGlue());
		// Cria o botao Jogar Dado
		jogarDados = new JMenu("Jogar Dados");
		jogarDados.setName("JogarDados");
		jogarDados.setVisible(true);
		jogarDados.addMouseListener(this);
		this.add(jogarDados);
		
		// Cria o botao Terminar Rodada
		terminadaRodada = new JMenu("Terminar Rodada");
		terminadaRodada.setName("TerminadaRodada");
		terminadaRodada.setVisible(false);
		terminadaRodada.addMouseListener(this);
		this.add(terminadaRodada);
	}
	
	public void setTrueTerminaRodadaBotao() {
		this.terminadaRodada.setVisible(true);
	}
	
	public void setFalseJogarDado() {
		this.jogarDados.setVisible(false);
	}
	
	public void setTrueJogarDado() {
		this.jogarDados.setVisible(true);
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent().getName().equals("Falencia")) {
			this.jogarDados.setVisible(false);
			this.terminadaRodada.setVisible(false);
			Controle.setFalencia();
		}
		if (e.getComponent().getName().equals("JogarDados")) {
			this.jogarDados.setVisible(false);
			this.repaint();
			Controle.setJogarDados();
		}
		if (e.getComponent().getName().equals("TerminadaRodada")) {
			this.terminadaRodada.setVisible(false);
			this.repaint();
			Controle.setTerminarRodada();
		}
	}

	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {	}
}
