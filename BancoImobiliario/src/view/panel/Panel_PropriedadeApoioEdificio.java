package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Jogo;
import view.Imagem;
import view.Imagem.TipoImagem;
import control.Controle;

@SuppressWarnings("serial")
public class Panel_PropriedadeApoioEdificio extends JPanel implements MouseListener, ActionListener{
	
	private int posicao;
	private int numJogador;
	private int qtdMaxCasas = 0;
	private int quantidade = 0;
	
	public Panel_PropriedadeApoioEdificio (TipoAcaoEdifico TipoAcaoEdifico,int posicao, int numJogador) {
		this.posicao = posicao;
		this.numJogador = numJogador;
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.green.darker());
		setInformacoes(posicao);	
		setBotao(TipoAcaoEdifico,posicao);
		this.setVisible(true);
	}
	
	public enum TipoAcaoEdifico {
		comprar, vender
	}
	
	private void setInformacoes(int posicao) {
		JLabel qtdCasaJogo = new JLabel("Quantidade de casas disponeis: "+Jogo.getTERRgrupoDoTerreno(posicao),JLabel.CENTER);//
		JLabel corGrupo = new JLabel("Propriedade do grupo: "+Jogo.getTERRgrupoDoTerreno(posicao),JLabel.CENTER);//

		int[] grupoCasa = Jogo.getTERRterrenosDoGrupo(posicao);
		JLabel[] grupoPropriedades = new JLabel[grupoCasa.length];
		
		for(int i=0; i < grupoCasa.length; i++) {
			String nomeTerreno = Jogo.getTERRnomeTerreno(grupoCasa[i]);
			int qtdEd = Jogo.getTERRqtdEdificio(grupoCasa[i]);
			// Guarda o numero max de casa existem numa das propriedades do terreno
			if(qtdMaxCasas < qtdEd)
				qtdMaxCasas = qtdEd;
			
			if(qtdEd < 5)
				grupoPropriedades[i] = new JLabel(nomeTerreno+" - Casa: "+qtdEd, new Imagem(TipoImagem.Casa).getIconImagem(), JLabel.CENTER);
			else 
				grupoPropriedades[i] = new JLabel(nomeTerreno+" - Hotel: "+qtdEd, new Imagem(TipoImagem.Hotel).getIconImagem(), JLabel.CENTER);
		}
	
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(grupoPropriedades.length+2, 1));
		panel.setBackground(Color.white);
		panel.add(qtdCasaJogo);
		panel.add(corGrupo);
		for (int i=0; i< grupoPropriedades.length ; i++)
			panel.add(grupoPropriedades[i]);
		this.add(panel, BorderLayout.PAGE_START);
	}	
	private void setBotao(final TipoAcaoEdifico tipoAcaoEdifico, int posicao) {
		JComboBox<Integer> comboBox = carregaCombo(tipoAcaoEdifico);
		comboBox.addActionListener(this);
		
		JButton botao = null;
		if(tipoAcaoEdifico == TipoAcaoEdifico.comprar){
			botao = new JButton("Pagar $"+Jogo.getTERRprecoComprarEdificio(posicao, quantidade));//
			botao.setName("PagarAoBanco");

		} else {
			botao = new JButton("Receber $"+Jogo.getTERRprecoComprarEdificio(posicao, quantidade)/2);//
			botao.setName("ReceberDoBanco");
		}
		botao.addMouseListener(this);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setBackground(Color.white);
		panel.add(comboBox);
		panel.add(botao);
		this.add(panel, BorderLayout.PAGE_END);
	}
	
	private JComboBox<Integer> carregaCombo(final TipoAcaoEdifico tipoAcaoEdifico) {
		
		int qntCasasJaConstruidas = Jogo.getTERRqtdEdificio(this.posicao);
		int[] numCasasBox = null;
		
		if(tipoAcaoEdifico == TipoAcaoEdifico.comprar)
		{
			int grupoTerreno[] = Jogo.getTERRterrenosDoGrupo(posicao);
			int numCasas;
			int numMaxCasas = 0;
			int numMinCasas = 6;
			int numMaxCompra;
			for (int i = 0; i < grupoTerreno.length; i++) {
					numCasas = Jogo.getTERRqtdEdificio( grupoTerreno[i]);
				if (numCasas < numMinCasas)
					numMinCasas = numCasas;
				else if(numCasas > numMaxCasas)
					numMaxCasas = numCasas;
			}
			
			if(numMinCasas == numMaxCasas)
				numMaxCompra = 1;
			else if(qntCasasJaConstruidas != numMaxCasas )
				numMaxCompra = numMaxCasas - qntCasasJaConstruidas;
			else
				numMaxCompra = 0;
			
			
			if(numMaxCompra == 0)
			{
				numCasasBox = new int[1];
				numCasasBox[0] = 0;
			}
			else
			{
				numCasasBox = new int[numMaxCompra];
				for (int i = 0; i < numCasasBox.length; i++)
					numCasasBox[i] = i+1;
			}
		}
			
		/*
		if(tipoAcaoEdifico == TipoAcaoEdifico.comprar)
		{
			// Inicializa listagem de casa do terreno
			numCasasBox = new int[qtdMaxCasas -  qntCasasJaConstruidas + 1];
			if(qtdMaxCasas == 5 && qntCasasJaConstruidas == 5) // N�o � mais possivel construir casas
			{
				numCasasBox[0] = 0;
			}
			else
			{
				for (int i = 0; i < numCasasBox.length; i++){
					numCasasBox[i] = i+1;
				}
			}
		}
		*/
		else //Vender
		{
			if(qntCasasJaConstruidas != 0)
			{
				numCasasBox = new int[qntCasasJaConstruidas];
				for (int i = 0; i < numCasasBox.length; i++)
					numCasasBox[i] = i+1;
			}
			else 
			{
				numCasasBox = new int[1];
				numCasasBox[0] = 0;
			}
		}
		// Cria a comboBox somente com os jogadores do jogo
		JComboBox<Integer> comboBox = new JComboBox<Integer>();
		for (int i = 0; i < numCasasBox.length; i++) {
			comboBox.addItem(numCasasBox[i]);
		}
		return comboBox;
	}
	
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("rawtypes")
		JComboBox cb = (JComboBox) e.getSource();
		quantidade  = (int) cb.getSelectedItem();
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent().getName().equals("PagarAoBanco")) {
			Controle.setComprarCasa(posicao, quantidade);
		}
		if (e.getComponent().getName().equals("ReceberDoBanco")) {
			Controle.setVenderCasa(numJogador, posicao, quantidade);
		}
	}
	
	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {	}
}
