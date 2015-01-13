package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Jogo;
import view.Imagem;
import view.Imagem.TipoImagem;

@SuppressWarnings("serial")
public class Panel_Jogador extends JPanel implements Observer{
	
	
	/***************************************************************************
	**** Vareaveis da Classe Panel Jogador
	***************************************************************************/
	
	private Panel_JogadorGradePropriedade gradePropriedadesPanel;
	private JPanel informacoesPanel;
	private JLabel imagem, idLabel,statusLabel,dinheiroLabel,cartaoLabel,localizacaoLabel;
	private static boolean cartaoSaidaLivrePrisao;
	private static int numJogadorLocal, valorDinheiro;
	private static String valorStatus, valorLocalizacao,valorLogo;
	
	
	/***************************************************************************
	**** Constructor Default da Classe Panel Jogador
	***************************************************************************/
	
	public Panel_Jogador(int numJogador) {
		super(true);
		Jogo.getInstance().addObserver(this);
		setInicializacaoVareaveis(numJogador);
		setInformacoesPanel();
		setImagemJogadorDaRodada();
		setGradePropriedades(numJogadorLocal);
		
		JPanel conteudo = new JPanel();
		conteudo.setLayout(new BorderLayout());
		conteudo.setBackground(Color.white);
		conteudo.setSize(400, 100);

		conteudo.add(informacoesPanel);
		this.setBackground(Color.white);
		this.setLayout(null);
		this.add(conteudo);

		this.setVisible(true);
	}
	
	
	/***************************************************************************
	**** Funcoes da Classe Panel Jogador
	***************************************************************************/
	
	private void setInformacoesPanel() {
		// nome jogador
		idLabel = new JLabel();
		idLabel.setText("Jogador "+numJogadorLocal);
		idLabel.setBounds(15, 5, 100, 15);

		// status do jogador
		statusLabel = new JLabel();
		statusLabel.setText("Status: "+valorStatus);
		statusLabel.setBounds(280, 10, 100, 15);
		
		// valor dinheiro jogador
		JLabel dinheiroImgLabel = new JLabel(new Imagem(TipoImagem.Dinheiro).getIconImagem());

		dinheiroLabel = new JLabel();
		dinheiroLabel.setText("Dinheiro: "+valorDinheiro);
		JPanel dinheiroPanel = new JPanel();
		dinheiroPanel.setBounds(90, 0, 150, 30);
		dinheiroPanel.setLayout(new FlowLayout());
		dinheiroPanel.setBackground(Color.white);
		dinheiroPanel.add(dinheiroImgLabel);
		dinheiroPanel.add(dinheiroLabel);

		// status de cartao libera da prisao
		cartaoLabel = new JLabel("Cartao da prisao: NAO");
		if(cartaoSaidaLivrePrisao == true)
			cartaoLabel.setText("Cartao da prisao: SIM");
		cartaoLabel.setBounds(240, 30, 150, 15);

		// localizacao do jogador
		localizacaoLabel = new JLabel();
		localizacaoLabel.setText("Localizacao: "+valorLocalizacao);
		localizacaoLabel.setBounds(205, 50, 180, 15);
		
		// Setup Informacao Panel
		informacoesPanel = new JPanel();
		informacoesPanel.setLayout(null);
		informacoesPanel.setBounds(0, 0, 400, 100);
		informacoesPanel.setBackground(Color.white);
		informacoesPanel.add(idLabel);
		informacoesPanel.add(statusLabel);
		informacoesPanel.add(cartaoLabel);
		informacoesPanel.add(dinheiroPanel);
		informacoesPanel.add(localizacaoLabel);
		informacoesPanel.setVisible(true);
	}
	private void setImagemJogadorDaRodada() {
		if(imagem != null) {
			informacoesPanel.remove(imagem);
			imagem = null;
		}
		imagem = new Imagem(TipoImagem.logoJogador,valorLogo).getImagemLabel();
		imagem.setBounds(15, 20, 70, 70);
		informacoesPanel.add(imagem);
	}

	private void setGradePropriedades(int numJogador) {
		// Setup grade Propriedade Panel
		if(gradePropriedadesPanel != null) {
			this.remove(gradePropriedadesPanel);
			gradePropriedadesPanel = null;
		}
			
		gradePropriedadesPanel = new Panel_JogadorGradePropriedade(numJogador);
		gradePropriedadesPanel.setVisible(true);
		gradePropriedadesPanel.setLocation(0,100);
		gradePropriedadesPanel.setSize(400, 300);
		gradePropriedadesPanel.setBackground(Color.white);
		this.add(gradePropriedadesPanel);
	}
	
	private void setInicializacaoVareaveis(int numJogador) {
		if(numJogador == 0)
			numJogadorLocal = Jogo.getJOGRODid();
		else
			numJogadorLocal = numJogador;
		setVareaveis(Jogo.getJOGdinheiroJogador(numJogadorLocal), 
					Jogo.getJOGstatusJogadorNoJogo(numJogadorLocal), 
					Jogo.getJOGlocalizacaoJogadorTabuleiro(numJogadorLocal), 
					Jogo.getJOGlogoJogador(numJogadorLocal),
					Jogo.getJOGcartaoSaidaLivrePrisao(numJogadorLocal));
	}
	private void setVareaveis(int recValorDinheiro, String recValorStatus, String recValorLocalizacao, String recValorLogo, boolean recCartaoSaidaLivrePrisao) {
		valorDinheiro = recValorDinheiro;
		valorStatus = recValorStatus;
		valorLocalizacao = recValorLocalizacao;
		valorLogo = recValorLogo;
		cartaoSaidaLivrePrisao = recCartaoSaidaLivrePrisao;
	}
	
	
	/***************************************************************************
	**** Funcoes de Apoio Classe Panel Jogador
	***************************************************************************/
	
	public void update(Observable observ, Object object) {
		setInicializacaoVareaveis(Jogo.getJOGRODid());
		idLabel.setText("Jogador "+numJogadorLocal);
		statusLabel.setText("Status: "+valorStatus);
		dinheiroLabel.setText("Dinheiro: "+valorDinheiro);
		localizacaoLabel.setText("Localizacao: "+valorLocalizacao);
		if(cartaoSaidaLivrePrisao == true)
			cartaoLabel.setText("Cartao da prisao: SIM");
		else
			cartaoLabel.setText("Cartao da prisao: NAO");
		setImagemJogadorDaRodada();
		setGradePropriedades(numJogadorLocal);
		informacoesPanel.repaint();
		gradePropriedadesPanel.revalidate();
		gradePropriedadesPanel.repaint();
		informacoesPanel.revalidate();
		informacoesPanel.repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}