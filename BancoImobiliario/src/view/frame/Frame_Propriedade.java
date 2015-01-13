package view.frame;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import model.Jogo;
import view.panel.Panel_Propriedade;
import view.panel.Panel_PropriedadeApoioEdificio;
import view.panel.Panel_PropriedadeApoioEdificio.TipoAcaoEdifico;
import view.panel.Panel_PropriedadeApoioPorVendaPropriedade;
import view.panel.Panel_PropriedadeApoioRespostaTrocaPropriedade;
import view.panel.Panel_PropriedadeApoioTrocaPropriedade;

@SuppressWarnings("serial")
public class Frame_Propriedade extends JFrame {

	/***************************************************************************
	*** Vareaveis Locais de Frame Propriedade
	***************************************************************************/

	private int posicao, numJogador;
	private Panel_Propriedade propriedadePanel;
	
	/***************************************************************************
	*** Estrutura de Apoio a Frame Propriedade
	***************************************************************************/
	
	public enum TipoPropriedadeApoio {
		edificio, porVendaPropriedade, trocaPropriedade, respostaTrocaPropriedade
	}
	
	/***************************************************************************
	*** Construtor Default de Frame Propriedade
	***************************************************************************/
	////////////////////////////////////////////////////////////////////////////
	///// Construtor Default de Frame Propriedade
	public Frame_Propriedade (int posicao, int numJogador) { 
		// Aguarda referencia do dados de identificacao da frame
		this.posicao = posicao;
		this.numJogador = numJogador;
		
		// Define nome ultilizado na barra de titulo da janela
		this.setTitle("Propriedades "+posicao);
		// Define cor de fundo do frame (primeira camada da janela)
		this.setBackground(Color.white);
		// Define o posicionamento inicial e sua dimensao
		this.setBounds(0, 0, 510, 315);
		// Permite redimencionamento do tamanho da janela
		this.setResizable(false);
		// Posiciona a janela inicial no centro da tela
		this.setLocationRelativeTo(null);
		// Adiciona o panel do mercado
		this.propriedadePanel = new Panel_Propriedade(this, posicao,numJogador);
		this.add(propriedadePanel);
		// Dispose a janela ao fechar
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Componentes da frame Visivel
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent e){
			Jogo.getInstance().deleteObserver(propriedadePanel);
		}});
	}
	
	////////////////////////////////////////////////////////////////////////////
	///// Construtor Default de Frame Propriedade Apoio
	public Frame_Propriedade(TipoPropriedadeApoio TipoPropriedadeApoio,int posicao, int numJogador) {
		// Adiciona o panel de PropriedadeApoio, de acordo com o tipo
		setTipoPropriedadeApoio(TipoPropriedadeApoio, null,  posicao, numJogador);
		// Define cor de fundo do frame (primeira camada da janela)
		this.setBackground(Color.white);
		// Permite redimencionamento do tamanho da janela
		this.setResizable(false);
		// Posiciona a janela inicial no centro da tela
		this.setLocationRelativeTo(null);
		// Componentes da frame Visivel
		this.setVisible(true);
	}
	
	public Frame_Propriedade(TipoPropriedadeApoio TipoPropriedadeApoio,TipoAcaoEdifico TipoAcaoEdifico, int posicao, int numJogador) {
		// Adiciona o panel de PropriedadeApoio, de acordo com o tipo
		setTipoPropriedadeApoio(TipoPropriedadeApoio, TipoAcaoEdifico, posicao, numJogador);
		// Define cor de fundo do frame (primeira camada da janela)
		this.setBackground(Color.white);
		// Permite redimencionamento do tamanho da janela
		this.setResizable(false);
		// Posiciona a janela inicial no centro da tela
		this.setLocationRelativeTo(null);
		// Componentes da frame Visivel
		this.setVisible(true);
	}
		
	/***************************************************************************
	*** Funcoes de Frame Propriedade
	***************************************************************************/
	
	private void setTipoPropriedadeApoio (final TipoPropriedadeApoio TipoPropriedadeApoio, TipoAcaoEdifico TipoAcaoEdifico, int posicao, int numJogador) {
		this.posicao = posicao;
		this.numJogador = numJogador;
		switch (TipoPropriedadeApoio) {
		case edificio:
			Panel_PropriedadeApoioEdificio propriedadeApoioEdificio = new Panel_PropriedadeApoioEdificio(TipoAcaoEdifico, posicao, numJogador);
			this.add(propriedadeApoioEdificio);
			this.setTitle("Gerenciar Edificos da Propriedades "+posicao);
			this.setBounds(0, 0, 300, 250);
			break;
		case porVendaPropriedade:
			Panel_PropriedadeApoioPorVendaPropriedade propriedadeApoioPorVendaPropriedade = new Panel_PropriedadeApoioPorVendaPropriedade(this, posicao, numJogador);
			this.add(propriedadeApoioPorVendaPropriedade);
			this.setTitle("Por a Venda Propriedades "+posicao);
			this.setBounds(0, 0, 250, 250);
			break;
		case trocaPropriedade:
			Panel_PropriedadeApoioTrocaPropriedade propriedadeApoioTrocaPropriedade = new Panel_PropriedadeApoioTrocaPropriedade(this, posicao);
			this.add(propriedadeApoioTrocaPropriedade);
			this.setTitle("Prop Troca Jog "+numJogador);
			this.setBounds(0, 0, 300, 400);
			break;
		case respostaTrocaPropriedade:
			Panel_PropriedadeApoioRespostaTrocaPropriedade propriedadeApoioRespostaTrocaPropriedade = new Panel_PropriedadeApoioRespostaTrocaPropriedade(this, posicao, numJogador);
			this.add(propriedadeApoioRespostaTrocaPropriedade);
			this.setTitle("?? Sera Que Trocar ??"+posicao);
			this.setBounds(0, 0, 300, 180);
			break;
		}
	}
	
	public void setCloseFrame() {
		Jogo.getInstance().deleteObserver(propriedadePanel);
		this.removeAll();
		this.dispose();
	}
	
	public int getPosicao () {
		return this.posicao;
	}
	
	public int getNumJogador () {
		return this.numJogador;
	}
}