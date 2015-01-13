package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Jogo;
import view.frame.Frame_Dado;
import control.Controle;

@SuppressWarnings("serial")
public class Panel_Dado extends JPanel {
	private JButton pararDado;
	private Dado dado1,dado2;
	private boolean jaClicado;
	
	public Panel_Dado (int numJogador) {
		this.jaClicado = false;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.green.darker().darker());
		
		//titulo do jogador
		JLabel titulo = new JLabel("Jogador "+numJogador,JLabel.CENTER);
		if(numJogador < 1 || numJogador > 6 )
			titulo.setVisible(false);
		
		//adiciona dados
		JPanel panelJuntaDado = new JPanel();
		panelJuntaDado.setLayout(new FlowLayout());
		panelJuntaDado.setBackground(Color.green.darker().darker());

		dado1 = new Dado();
		dado2 = new Dado();
		panelJuntaDado.add(dado1);
		panelJuntaDado.add(dado2);
		
		this.botaoPararDados(null, numJogador);
		this.add(titulo, BorderLayout.NORTH);
		this.add(panelJuntaDado, BorderLayout.CENTER);
	    this.add(pararDado, BorderLayout.SOUTH); 
        this.setVisible(true);
	}
	
	public Panel_Dado (final Frame_Dado dadoFrame, int numJogador) {

		//titulo do jogador
		JLabel titulo = new JLabel("Jogador "+numJogador,JLabel.CENTER);
		if(numJogador < 1 || numJogador > 6 )
			titulo.setVisible(false);
		
		//adiciona dados
		JPanel panelJuntaDado = new JPanel();
		panelJuntaDado.setLayout(new FlowLayout());
		if(Jogo.getJOGstatusJogadorNoJogo(numJogador) == "Preso") {
			panelJuntaDado.setBackground(Color.gray.darker().darker());
			this.setBackground(Color.gray.darker().darker());
		} else {
			panelJuntaDado.setBackground(Color.green.darker().darker());
			this.setBackground(Color.green.darker().darker());
		}
		dado1 = new Dado();
		dado2 = new Dado();
		panelJuntaDado.add(dado1);
		panelJuntaDado.add(dado2);
		
		this.setLayout(new BorderLayout());
		this.botaoPararDados(dadoFrame, numJogador);
		this.add(titulo, BorderLayout.NORTH);
		this.add(panelJuntaDado, BorderLayout.CENTER);
	    this.add(pararDado, BorderLayout.SOUTH); 
	    this.setVisible(true);
	}
	
	/** Botao de parar dados */
	private void botaoPararDados (final Frame_Dado dadoFrame, final int numJogador) {
		this.pararDado = new JButton("Parar Dados");
		this.pararDado.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent arg0) {
        		if(jaClicado == false) 
            	{
        			jaClicado = true;
                	dado1.PararTimerDado();
                	dado2.PararTimerDado();
                	if(Jogo.getJOGstatusJogadorNoJogo(numJogador) == "Preso")
                		Jogo.setDADOnumDadosJogadorRodadaSaiPrisao(dado1.value, dado2.value);
                	else
                		Jogo.setDADOnumDadosJogadorRodada(dado1.value, dado2.value);
                	
        			if( dadoFrame != null && Jogo.getJOGstatusJogadorNoJogo(numJogador) != "Preso") {
    					Controle.setPararDadosRodada();
        				pararDado.setText("Mover peca");
        			} 
        			else if( dadoFrame != null && Jogo.getJOGstatusJogadorNoJogo(numJogador) == "Preso") {
        				if(dado1.value == dado2.value) {
        					pararDado.setText("Saiu da prisao, Mover peca");
        					Controle.setDadoSairDaPrisao();
        				} else {		
        					pararDado.setText("Nao de Sorte, Fechar");

        				}
        			} 
        			else
        				pararDado.setVisible(false);
            	}
            	else 
            	{
            		if( dadoFrame != null && Jogo.getJOGstatusJogadorNoJogo(numJogador) == "Preso") {
    					pararDado.setText("Fechar");
    					Controle.setFecharDadoFrame();
            		} else {
	            		Controle.setMoverPeca();
            		}
            	}
            }
        });
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
			
	public boolean getJaClicado()
	{
		return jaClicado;
	}
	
	public int getSomaDados () {
		return dado1.value+dado2.value;
	}
	
	public boolean getSaoDadosIquais () {
		if(dado1.value == dado2.value)
			return true;
		return false;
	}
	
	 class Dado extends JPanel {
			
			private static final int SIDE = 15;
			protected int value;
			private final Timer t = new Timer(100, null);

			public Dado() {
				setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
				setBackground(Color.white);
				t.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						value = Jogo.setDADOjogaDado();
						repaint();
					}
				});
				t.start();
			}

			public void PararTimerDado() {
				t.stop();
			}

			public Dimension getPreferredSize() {
				return new Dimension(SIDE * 7, SIDE * 7);
			}

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.black);
				switch (value) {
				case 1:
					g.fillRect(3 * SIDE, 3 * SIDE, SIDE, SIDE);
					break;
				case 2:
					g.fillRect(5 * SIDE, SIDE, SIDE, SIDE);
					g.fillRect(SIDE, 5 * SIDE, SIDE, SIDE);
					break;
				case 3:
					g.fillRect(5 * SIDE, SIDE, SIDE, SIDE);
					g.fillRect(SIDE, 5 * SIDE, SIDE, SIDE);
					g.fillRect(3 * SIDE, 3 * SIDE, SIDE, SIDE);
					break;
				case 4:
					g.fillRect(SIDE, SIDE, SIDE, SIDE);
					g.fillRect(5 * SIDE, 5 * SIDE, SIDE, SIDE);
					g.fillRect(5 * SIDE, SIDE, SIDE, SIDE);
					g.fillRect(SIDE, 5 * SIDE, SIDE, SIDE);
					break;
				case 5:
					g.fillRect(SIDE, SIDE, SIDE, SIDE);
					g.fillRect(5 * SIDE, 5 * SIDE, SIDE, SIDE);
					g.fillRect(5 * SIDE, SIDE, SIDE, SIDE);
					g.fillRect(SIDE, 5 * SIDE, SIDE, SIDE);
					g.fillRect(3 * SIDE, 3 * SIDE, SIDE, SIDE);
					break;
				case 6:
					g.fillRect(SIDE, SIDE, SIDE, SIDE);
					g.fillRect(5 * SIDE, 5 * SIDE, SIDE, SIDE);
					g.fillRect(5 * SIDE, SIDE, SIDE, SIDE);
					g.fillRect(SIDE, 5 * SIDE, SIDE, SIDE);
					g.fillRect(SIDE, 3 * SIDE, SIDE, SIDE);
					g.fillRect(5 * SIDE, 3 * SIDE, SIDE, SIDE);
					break;
				}
			}
	 }
}
