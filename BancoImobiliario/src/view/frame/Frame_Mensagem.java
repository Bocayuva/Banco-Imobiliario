package view.frame;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import view.panel.Panel_MensagemImpostoRenda;
import view.panel.Panel_MensagemLucroDividendo;
import view.panel.Panel_MensagemPontoDePartida;
import view.panel.Panel_MensagemPrisao;
import view.panel.Panel_MensagemPropriedade;
import view.panel.Panel_MensagemSorteReves;
import view.panel.Panel_MensagemVaiParaPrisao;

@SuppressWarnings("serial")
public class Frame_Mensagem extends JFrame {

	private TipoMensagemFrame tipoMensagem = null;


	public Frame_Mensagem (TipoMensagemFrame tipoMensagem) {
		this.tipoMensagem = tipoMensagem; 
		this.inicializaFrame();
	}
	
	public enum TipoMensagemFrame {
		propriedade, sorteReves, lucroDividendo, impostoRenda, prisao, vaiParaPrisao, pontoDePartida;
	}
	
	public void inicializaFrame() {
		// Define o posicionamento inicial e sua dimensao
		this.setBounds(0, 0, 235, 320);
		// Define cor de fundo do frame (primeira camada da janela)
		this.setBackground(Color.white);
		// Adiciona o panel
		this.setTipoMensagemPanel();
		// Permite redimencionamento do tamanho da janela
		this.setResizable(false);
		// Posiciona a janela inicial no centro da tela
		this.setLocationRelativeTo(null);
		// Define nao deixa fechar janela
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// Componentes da frame Visivel
		this.setVisible(true);
	}
	
	public void setTipoMensagemPanel () {
		switch (tipoMensagem) {
		case propriedade:
			// Define o posicionamento inicial e sua dimensao
			this.setBounds(0, 0, 235, 330);
			// Define nome ultilizado na barra de titulo da janela
			this.setTitle("Mensagem - Propriedade");
			// Adiciona o panel do Mensagem
			Panel_MensagemPropriedade MensagemEmpresaPanel = new Panel_MensagemPropriedade();
			this.add(MensagemEmpresaPanel);
			break;
		case impostoRenda:
			// Define nome ultilizado na barra de titulo da janela
			this.setTitle("Mensagem - impostoRenda");
			// Define o posicionamento inicial e sua dimensao
			this.setBounds(0, 0, 230, 255);
			// Adiciona o panel do Mensagem
			Panel_MensagemImpostoRenda MensagemImpostoRendaPanel = new Panel_MensagemImpostoRenda();
			this.add(MensagemImpostoRendaPanel);
			break;
		case lucroDividendo:
			// Define nome ultilizado na barra de titulo da janela
			this.setTitle("Mensagem - lucroDividendo");
			// Define o posicionamento inicial e sua dimensao
			this.setBounds(0, 0, 230, 255);
			// Adiciona o panel do Mensagem
			Panel_MensagemLucroDividendo MensagemLucroDividendoPanel = new Panel_MensagemLucroDividendo();
			this.add(MensagemLucroDividendoPanel);
			break;
		case pontoDePartida:
			// Define nome ultilizado na barra de titulo da janela
			this.setTitle("Mensagem - pontoDePartida");
			// Define o posicionamento inicial e sua dimensao
			this.setBounds(0, 0, 230, 255);
			// Adiciona o panel do Mensagem
			Panel_MensagemPontoDePartida MensagemPontoDePartidaPanel = new Panel_MensagemPontoDePartida();
			this.add(MensagemPontoDePartidaPanel);
			break;
		case prisao:
			// Define nome ultilizado na barra de titulo da janela
			this.setTitle("Mensagem - prisao");
			// Adiciona o panel do Mensagem
			Panel_MensagemPrisao MensagemPrisaoPanel = new Panel_MensagemPrisao();
			this.add(MensagemPrisaoPanel);
			break;
		case sorteReves:
			// Define nome ultilizado na barra de titulo da janela
			this.setTitle("Mensagem - sorteReves");
			// Define o posicionamento inicial e sua dimensao
			this.setBounds(0, 0, 230, 320);
			// Adiciona o panel do Mensagem
			Panel_MensagemSorteReves MensagemSorteRevesPanel = new Panel_MensagemSorteReves();
			this.add(MensagemSorteRevesPanel);
			break;
		case vaiParaPrisao:
			// Define nome ultilizado na barra de titulo da janela
			this.setTitle("Mensagem - vaiParaPrisao");
			// Define o posicionamento inicial e sua dimensao
			this.setBounds(0, 0, 230, 255);
			// Adiciona o panel do Mensagem
			Panel_MensagemVaiParaPrisao MensagemVaiParaPrisaoPanel = new Panel_MensagemVaiParaPrisao();
			this.add(MensagemVaiParaPrisaoPanel);
			break;
		default:
			break;
		}
	}		
	
	public void disposeWindow() {
	      WindowEvent closingEvent = new WindowEvent(Frame_Mensagem.this, WindowEvent.WINDOW_CLOSING);
	      Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
      }
	
	public void windowClosing(WindowEvent arg0) {
		this.dispose();
	}
}
