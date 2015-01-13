package view.frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Jogo;
import view.jmenu.main;
import view.panel.Panel_PrincipalInicio;
import view.panel.Panel_PrincipalNovosJogadores;
import view.panel.Panel_PrincipalTabuleiro;

@SuppressWarnings("serial")
public class Frame_Principal extends JFrame{
	/***************************************************************************
	*** Vareaveis Locais de Frame Principal
	***************************************************************************/

	public static int LARG_DEFAULT = 705;
	public static int ALT_DEFAULT = 750;
	
	private static Frame_Principal principalFrame = null;
	private main menu = null;
	private JPanel contentPanel = null;
	private static Panel_PrincipalInicio inicioPanel = null;
	private	static Panel_PrincipalNovosJogadores novosJogadoresPanel = null;
	private static Panel_PrincipalTabuleiro tabuleiroPanel = null;

	/***************************************************************************
	*** Constructor Default Frame Principal
	***************************************************************************/
	
	public Frame_Principal() { }
	
	/***************************************************************************
	*** Estrutura de Apoio a Frame Principal
	***************************************************************************/
	
	public enum TipoPanel {
		inicial, adicionarJogadores, tabuleiro
	}
	
	/***************************************************************************
	*** Funcoes Frame Principal
	***************************************************************************/
	
	@SuppressWarnings("static-access")
	public static Frame_Principal getInstance() {
		if (principalFrame == null) {
			principalFrame = new Frame_Principal();
			principalFrame.inicializaFrame();
			principalFrame.inicializaBarraMenu();
			principalFrame.inicializaContentPanel();
		}
		return principalFrame;
	}
	
	public static main getInstanceMenu() {
		return principalFrame.menu;
	}
	
	public static JPanel getInstanceContentPanel() {
		return principalFrame.contentPanel;
	}

	public static void inicializaFrame () {
		// Define nome ultilizado na barra de titulo da janela
		principalFrame.setTitle("Banco Imobiliario");
		// Define cor de fundo do frame (primeira camada da janela)
		principalFrame.setBackground(Color.white);
		// Define o posicionamento inicial e sua dimensao
		principalFrame.setBounds(0, 0, LARG_DEFAULT, ALT_DEFAULT);
		// Define fechamento do programa au clicar no fechamento de janela
		principalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Permite redimencionamento do tamanho da janela
		principalFrame.setResizable(false);
		// Posiciona a janela inicial no centro da tela
		principalFrame.setLocationRelativeTo(null);
		// Componentes da frame Visivel
		principalFrame.setVisible(true);
	}

	private static void inicializaBarraMenu () {
		principalFrame.menu = new main();
		principalFrame.add(principalFrame.menu);
		principalFrame.setJMenuBar(principalFrame.menu);
	}
	
	private static void inicializaContentPanel () {
		// Cria um CardLayout
		principalFrame.contentPanel = new JPanel();
		principalFrame.contentPanel.setLayout(new CardLayout());
		// Inicializacao dos Paneis da Frame e Adciona com ContentPanel
		inicioPanel = new Panel_PrincipalInicio();
		principalFrame.contentPanel.add(inicioPanel, "InicioPanel");
		// Set ContentPanel e Atualiza Frame
		principalFrame.setContentPane(principalFrame.contentPanel);
		principalFrame.repaint();
	}

	public static void setContentPanel (TipoPanel tipoPanel) {
		CardLayout cardLayout = (CardLayout) principalFrame.contentPanel.getLayout();
		principalFrame.contentPanel.removeAll();
		switch (tipoPanel) {
			case inicial:
				principalFrame.menu.setOcultarBotoesDeJogo();
				inicioPanel = new Panel_PrincipalInicio();
				principalFrame.contentPanel.add(inicioPanel, "InicioPanel");
				cardLayout.show(principalFrame.contentPanel, "InicioPanel");
				break;
			case adicionarJogadores:
				principalFrame.menu.setOcultarBotoesDeJogo();
				novosJogadoresPanel = new Panel_PrincipalNovosJogadores();
				principalFrame.contentPanel.add(novosJogadoresPanel, "NovosJogadoresPanel");
				cardLayout.show(principalFrame.contentPanel, "NovosJogadoresPanel");
				break;
			case tabuleiro:
				principalFrame.menu.setVisualizarBotoesDeJogo();
				tabuleiroPanel = new Panel_PrincipalTabuleiro();
				principalFrame.contentPanel.add(tabuleiroPanel, "TabuleiroPanel");
				cardLayout.show(principalFrame.contentPanel, "TabuleiroPanel");
				break;
		}
		// atualizando o prox panel
		principalFrame.contentPanel.revalidate();
		principalFrame.contentPanel.repaint();
		principalFrame.revalidate();
		principalFrame.repaint();
	}
	
	public static void setAtualizaTabuleiroPanel() {
		tabuleiroPanel.updateJog(Jogo.getJOGRODid());
		tabuleiroPanel.repaint();
	}
		
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
	}
}