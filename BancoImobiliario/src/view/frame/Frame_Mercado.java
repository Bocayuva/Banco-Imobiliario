package view.frame;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import model.Jogo;
import view.panel.Panel_Mercado;

@SuppressWarnings("serial")
public class Frame_Mercado extends JFrame {
	
	/***************************************************************************
	*** Vareaveis Locais da Frame Mercado
	***************************************************************************/

	Panel_Mercado mercadoPanel;
	
	/***************************************************************************
	*** Construtor Default da Frame Mercado
	***************************************************************************/
	
	public Frame_Mercado () {	
		// Define nome ultilizado na barra de titulo da janela
		this.setTitle("Mercado de Propriedades a Venda");
		// Define cor de fundo do frame (primeira camada da janela)
		this.setBackground(Color.white);
		// Define o posicionamento inicial e sua dimensao
		this.setBounds(0, 0, 635, 610);
		// Permite redimencionamento do tamanho da janela
		this.setResizable(false);
		// Posiciona a janela inicial no centro da tela
		this.setLocationRelativeTo(null);
		// Adiciona o panel do mercado
		mercadoPanel = new Panel_Mercado();
		this.add(mercadoPanel);
		// Dispose a janela ao fechar
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// Componentes da frame Visivel
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
			Jogo.getInstance().deleteObserver(mercadoPanel);
			}
		});
	}
}
