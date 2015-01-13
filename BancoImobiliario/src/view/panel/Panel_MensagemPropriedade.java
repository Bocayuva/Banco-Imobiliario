package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Jogo;
import model.Jogo.JogoTipoAcao;
import view.Imagem;
import view.Imagem.TipoImagem;
import control.Controle;

@SuppressWarnings("serial")
public class Panel_MensagemPropriedade extends JPanel implements MouseListener{

	private JogoTipoAcao tipoAcao = Jogo.getJOGRODacaoDoJogoAo();
	
	public Panel_MensagemPropriedade () {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		setImagem();
		if(tipoAcao == JogoTipoAcao.comprarPropriedadeDoBanco)
			setComprar();
		else if(tipoAcao == JogoTipoAcao.pagarAluguel)
			setPagarAluguel();
		else if(tipoAcao == JogoTipoAcao.venderPropriedadeParaBanco)
			setPropriedade();
		else if(tipoAcao == JogoTipoAcao.pagarHipoteca)
			setPagarHipoteca();
		this.setVisible(true);
	}	
	private void setImagem() {
		JLabel imagem = null;
		imagem = new Imagem(TipoImagem.propriedade,Jogo.getJOGRODlocalizacao()).getImagemLabel();
		imagem.setBounds(5, 5, 200, 230);
		this.add(imagem, BorderLayout.CENTER);
	}
	private void setPropriedade() {
		
		JButton botao = new JButton("Fechar");
		botao.setName("FecharJanela");
		botao.addMouseListener(this);

		JButton botao2 = new JButton("Ir para Propriedade");
		botao2.setName("IrPropriedade");
		botao2.addMouseListener(this);
		
		JPanel panel = new JPanel();
		panel.add(botao);
		panel.add(botao2);
		panel.setBackground(Color.BLACK.darker());
		panel.setLayout(new GridLayout(panel.getComponentCount(),1));
		this.add(panel, BorderLayout.PAGE_END);
	}
	private void setPagarHipoteca()
	{
		JButton botao = new JButton("Fechar");
		botao.setName("FecharJanela");
		botao.addMouseListener(this);

		JButton botao7 = null;
		botao7 = new JButton("Pagar Hipoteca $ "+Jogo.getPROPvalorHipotecaPropriedade(Jogo.getJOGRODlocalizacao()));
		botao7.setName("PagarHipoteca");
		botao7.addMouseListener(this);
		
		JPanel panel = new JPanel();
		panel.add(botao);
		panel.add(botao7);
		panel.setBackground(Color.BLACK.darker());
		panel.setLayout(new GridLayout(panel.getComponentCount(),1));
		this.add(panel, BorderLayout.PAGE_END);
	}
	private void setComprar() {
		JPanel panel = new JPanel();
		
		JButton botao = new JButton("Fechar");
		botao.setName("FecharJanela");
		botao.addMouseListener(this);
		panel.add(botao);
		
		// Caso o jogador tenha dinheiro, ele podera comprar propriedade
		if(Jogo.getJOGdinheiroJogador(Jogo.getJOGRODid()) >= Jogo.getPROPvalorNominalPropriedade(Jogo.getJOGRODlocalizacao())) {
			JButton botao2 = new JButton("Comprar por $ "+Jogo.getPROPvalorNominalPropriedade(Jogo.getJOGRODlocalizacao()));
			botao2.setName("comprarPropriedade");
			botao2.addMouseListener(this);
			panel.add(botao2);
		}

		panel.setBackground(Color.BLACK.darker());
		panel.setLayout(new GridLayout(panel.getComponentCount(),1));
		this.add(panel, BorderLayout.PAGE_END);
	}
	private void setPagarAluguel() {
		JButton botao = null;
		if(Jogo.getPROPvalorDoAluguel(Jogo.getJOGRODlocalizacao()) == 0) {
			botao = new JButton("Aluguel Gratuito Atualmente");
		} else {
			botao = new JButton("Pagar Aluguel $"+Jogo.getPROPvalorDoAluguel(Jogo.getJOGRODlocalizacao()));
		}
			
		botao.setName("PagarAluguel");
		botao.addMouseListener(this);
		this.add(botao, BorderLayout.PAGE_END);
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent().getName().equals("PagarAluguel")) {
			Controle.setPropriedade(JogoTipoAcao.pagarAluguel,null,0);
		}
		if (e.getComponent().getName().equals("comprarPropriedade")) {
			Controle.setPropriedade(JogoTipoAcao.comprarPropriedadeDoBanco,null,0);
		}
		if (e.getComponent().getName().equals("PagarHipoteca")) {
			Controle.setHipoteca(JogoTipoAcao.pagarHipoteca, 0, 0);
		}
		if (e.getComponent().getName().equals("FecharJanela")) {
			Controle.setFecharMesagemFrame();
		}
		if (e.getComponent().getName().equals("IrPropriedade")) {
			Controle.setIrParaPropriedadeFrame();
		}
	}
	
	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {	}
}
