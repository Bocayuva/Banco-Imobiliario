package view.frame;

import java.awt.Color;

import javax.swing.JFrame;

import model.Jogo;
import view.panel.Panel_Dado;
import view.panel.Panel_DadoOrdemJogadores;

@SuppressWarnings("serial")
public class Frame_Dado extends JFrame {

	public enum TipoDado {
		dadoDeOrdenacao, dadoDeJogo, dadoDePrisao
	}
	
	public Frame_Dado(TipoDado tipo) {
		if (TipoDado.dadoDeOrdenacao == tipo) {
			// Define nome ultilizado na barra de titulo da janela
			this.setTitle("Dado Inicial");
			// Define cor de fundo do frame (primeira camada da janela)
			this.setBackground(Color.green.darker().darker());
			// Define o posicionamento inicial e sua dimensao
			switch (Jogo.getGAMEquantidadeJogador()) {
			case 2:
				this.setBounds(0, 0, 500, 220);
				break;
			case 3:
				this.setBounds(0, 0, 500, 425);
				break;
			case 4:
				this.setBounds(0, 0, 500, 425);
				break;
			case 5:
				this.setBounds(0, 0, 500, 575);
				break;
			case 6:
				this.setBounds(0, 0, 500, 575);
				break;
			}
			// Adicionar Panel
			Panel_DadoOrdemJogadores ordemJogadoresPanel = new Panel_DadoOrdemJogadores();
			this.add(ordemJogadoresPanel);
		} else {
			if(TipoDado.dadoDePrisao == tipo){
				this.setTitle("Dados Sair da Prisao");
			}
			else
				this.setTitle("Dados de Jogo");
			// Define cor de fundo do frame (primeira camada da janela)
			this.setBackground(Color.white);
			// Define o posicionamento inicial e sua dimensao
			this.setBounds(0, 0, 250, 180);
			// Adiciona panel Dado
			Panel_Dado dadoPanel = new Panel_Dado(this, Jogo.getJOGRODid());
			this.add(dadoPanel);
		}
		// Define nao deixa fechar janela
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// Posiciona a janela inicial no centro da tela
		this.setLocationRelativeTo(null);
		// Permite redimencionamento do tamanho da janela
		this.setResizable(false);
		// Permite visibilidades dos componetes inseridos nesta janela
		this.setVisible(true);
	}
}