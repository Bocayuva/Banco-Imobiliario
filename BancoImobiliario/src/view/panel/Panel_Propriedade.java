package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Jogo;
import model.Jogo.JogoTipoAcao;
import view.Imagem;
import view.Imagem.TipoImagem;
import view.panel.Panel_PropriedadeApoioEdificio.TipoAcaoEdifico;
import control.Controle;

@SuppressWarnings("serial")
public class Panel_Propriedade extends JPanel implements MouseListener, Observer {
	
	private int posicao, numJogador, valorVendaDaPropriedadeMercado, valorDoAluguel,qtdEdificio;
	private String tipoPropriedade,tipoEdificio;
	private boolean propriedadeEstaHipotecada,proprietarioDeTodoGrupo;
	
	private JPanel direita, esquerda, informacoes, botoes;
	private JLabel imagem, infotipoPropriedade,infoVendaNoMercado,infoTipoEdificios,infoQtdAtualEdificios,
	infoValorAtualdoAluguel,infoHipoteca,espacamento1,espacamento2,espacamento3,espacamento4;
	private JButton botao1,botao2,botao3,botao4,botao5,botao6,botao7,botao8;
	private JFrame frame;
		
	public Panel_Propriedade(JFrame frame, int posicao, int numJogador) {
		super(true);
		Jogo.getInstance().addObserver(this);
		
		this.frame = frame;
		this.posicao = posicao;
		this.numJogador = numJogador;
		this.setBackground(Color.white);
		this.setLayout(new FlowLayout());
		
		setUpdate();
		setImagem();
		setInformacoes();
		setBotoes();
		setDireita();
		setEsquerda();
		
		this.setVisible(true);
	}
	
	private void setDireita() {
		direita = null;
		direita = new JPanel();
		direita.setLayout(new BorderLayout());
		direita.setBackground(Color.white);
		direita.add(imagem, BorderLayout.CENTER);
		direita.setPreferredSize(new Dimension(245, 285));
		direita.setVisible(true);
        this.add(direita);
	}
	
	private void setEsquerda() {
		
		esquerda = null;
		esquerda = new JPanel();
		esquerda.setLayout(new FlowLayout());
		esquerda.setBackground(Color.white);
		esquerda.add(informacoes);
		esquerda.add(botoes);
		esquerda.setPreferredSize(new Dimension(245, 285));
		esquerda.setVisible(true);
		this.add(esquerda);
	}
	
	private void setImagem() {
		imagem = null;
		imagem = new Imagem(TipoImagem.propriedade,posicao).getImagemLabel();
		imagem.setBounds(5, 5, 210, 230);
		imagem.setVisible(true);
	}
	
	private void setInformacoes() {
		informacoes = null;
		
		infotipoPropriedade = new JLabel("Tipo de Propriedade      : "+tipoPropriedade);
		infoVendaNoMercado = new JLabel("Propriedade a venda por: "+valorVendaDaPropriedadeMercado);
		infoValorAtualdoAluguel = new JLabel("Valor Atual do Aluguel  : "+valorDoAluguel);

		if(propriedadeEstaHipotecada ==true) 
			infoHipoteca = new JLabel("Propriedade hipotecada : SIM");
		else
			infoHipoteca = new JLabel("Propriedade hipotecada : NAO");
		
		informacoes = new JPanel();
		informacoes.add(infotipoPropriedade);
		informacoes.add(infoHipoteca);
		informacoes.add(infoVendaNoMercado);
		informacoes.add(infoValorAtualdoAluguel);

		if(tipoPropriedade == "Terreno") {
			infoTipoEdificios		= new JLabel("Tipo de Edificios            : "+tipoEdificio);
			infoQtdAtualEdificios	= new JLabel("Quantidade de Edificios : "+qtdEdificio);
			informacoes.add(infoTipoEdificios);
			informacoes.add(infoQtdAtualEdificios);
		} else {
			espacamento1	= new JLabel(" ");
			espacamento2	= new JLabel(" ");
			espacamento3	= new JLabel(" ");
			espacamento4 = new JLabel(" ");
			informacoes.add(espacamento1);
			informacoes.add(espacamento2);
			informacoes.add(espacamento3);
			informacoes.add(espacamento4);
		}
		
		informacoes.setLayout(new GridLayout(informacoes.getComponentCount(),1));
		informacoes.setBackground(Color.white);
		informacoes.setVisible(true);
	}
	
	private void setBotoes() {
		botoes = null;
		botoes = new JPanel();
		
		if(Jogo.getPROPproprietario(posicao) == numJogador && Jogo.getPROPpropriedadeEstaHipotecada(posicao) == false) {		
				botao1 = new JButton("Vender Para Banco");
				botao1.setName("VenderPropriedade");
				botao1.addMouseListener(this);
				botoes.add(botao1);
				if(Jogo.getPROPstatusAVendaPeloJogador(posicao)==true) {
					botao4 = new JButton("Alterar Valor Propriedade Mercado");
					botao4.setName("ColocarVendaPropriedade");
					botao4.addMouseListener(this);
					botoes.add(botao4);
					botao7 = new JButton("Retirar Propriedade do Mercado");
					botao7.setName("RetirarPropriedadeMercado");
					botao7.addMouseListener(this);
					botoes.add(botao7);
				}else{
					botao2 = new JButton("Colocar a Venda Mercado");
					botao2.setName("ColocarVendaPropriedade");
					botao2.addMouseListener(this);
					botoes.add(botao2);
					botao8 = new JButton("Propor Troca");
					botao8.setName("ProporTroca");
					botao8.addMouseListener(this);
					botoes.add(botao8);
				}
				botao3 = new JButton("Hipotecar");
				botao3.setName("Hipotecar");
				botao3.addMouseListener(this);
				botoes.add(botao3);
				if(tipoPropriedade == "Terreno") {
					if(proprietarioDeTodoGrupo == true) {
						botao5 = new JButton("Comprar Edificio");
						botao5.setName("compraEdificil");
						botao5.addMouseListener(this);
						botoes.add(botao5);
					}
					if(qtdEdificio > 0 && qtdEdificio< 5) {
						botao6 = new JButton("Vender Edificio");
						botao6.setName("venderEdificil");
						botao6.addMouseListener(this);
						botoes.add(botao6);
					}
				}
		} else if(Jogo.getPROPstatusAVendaPeloJogador(posicao) == true) {
			botao1 = new JButton("Comprar");
			botao1.setName("Comprar do Jogador");
			botao1.addMouseListener(this);
		}
		botoes.setLayout(new GridLayout(botoes.getComponentCount(),1));
		botoes.setBackground(Color.white);
		botoes.setVisible(true);
	}
	
	public void setUpdate() {
		tipoPropriedade = Jogo.getPROPtipoPropriedade(posicao);
		valorVendaDaPropriedadeMercado = Jogo.getPROPvalorVendaDaPropriedadeMercado(posicao);
		valorDoAluguel = Jogo.getPROPvalorDoAluguel(posicao);
		propriedadeEstaHipotecada = Jogo.getPROPpropriedadeEstaHipotecada(posicao);
		if(tipoPropriedade == "Terreno") {
			tipoEdificio = Jogo.getTERRtipoEdificio(posicao);
			qtdEdificio = Jogo.getTERRqtdEdificio(posicao);
			proprietarioDeTodoGrupo = Jogo.getJOGRODproprietarioDeTodoGrupo(posicao);
		}
	}
	
	public void update(Observable observ, Object object) {
		this.removeAll();
		
		setUpdate();
		setImagem();
		setInformacoes();
		setBotoes();
		setDireita();
		setEsquerda();
				
		this.revalidate();
		this.repaint();
	}
	
	public void mouseClicked(MouseEvent e)  {
		if (e.getComponent().getName().equals("comprarPropriedade")) {
			Controle.setPropriedade(JogoTipoAcao.comprarPropriedadeDoBanco,null,0);
		}
		if (e.getComponent().getName().equals("VenderPropriedade")) {
			Controle.setPropriedade(JogoTipoAcao.venderPropriedadeParaBanco, frame, posicao);
		}
		if (e.getComponent().getName().equals("ColocarVendaPropriedade")) {
			Controle.getListagemPropriedadeFrame(posicao, numJogador);
		}
		if (e.getComponent().getName().equals("RetirarPropriedadeMercado")) {
			Controle.setRetirarPropriedadeMercado(posicao, numJogador);
		}
		if (e.getComponent().getName().equals("Hipotecar")) {
			Controle.setHipoteca(JogoTipoAcao.hipotecarPropriedade, posicao, numJogador);
		}
		if (e.getComponent().getName().equals("compraEdificil")) {
			Controle.setEdificio(TipoAcaoEdifico.comprar, posicao, numJogador);
		}
		if (e.getComponent().getName().equals("venderEdificil")) {
			Controle.setEdificio(TipoAcaoEdifico.vender, posicao, numJogador);
		}
		if (e.getComponent().getName().equals("ProporTroca")) {
			Controle.setProporTrocaPropriedade(posicao, numJogador);
		}
	}
	
	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
