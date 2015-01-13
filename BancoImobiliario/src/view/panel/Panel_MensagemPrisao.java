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
public class Panel_MensagemPrisao extends JPanel implements MouseListener {
	
	private JogoTipoAcao tipoAcao = Jogo.getJOGRODacaoDoJogoAo();
	 
	public Panel_MensagemPrisao () {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.red.darker().darker());
		JPanel botaoPanel = new JPanel();		
		setImagem();
		if(tipoAcao == JogoTipoAcao.vaiParaPrisao)
			setIrParaPrisao(botaoPanel);
		else if(tipoAcao != JogoTipoAcao.pagarAoBancoPrisao)
				setDadoSorteSairDaPrisao(botaoPanel);
		else {
				setPodePagarParaSair(botaoPanel);
		}
		botaoPanel.setBackground(Color.BLACK.darker());
		botaoPanel.setLayout(new GridLayout(botaoPanel.getComponentCount(),1));
		this.add(botaoPanel, BorderLayout.PAGE_END);
		this.setVisible(true);
	}
	private void setImagem() {
		JLabel imagem = null;
		imagem = new Imagem(TipoImagem.Prisao).getImagemLabel();
		imagem.setBounds(0, 0, 230, 230);
		imagem.setBackground(Color.red.darker());
		this.add(imagem, BorderLayout.CENTER);
	}
	private void setIrParaPrisao(JPanel botaoPanel) {			
		JButton botao = new JButton("Ir para Prisao");
		botao.setName("irParaPrisao");
		botao.addMouseListener(this);
		botaoPanel.add(botao);
	}
	private void setPodePagarParaSair(JPanel botaoPanel) {
		JButton botao2 = new JButton("Pagar $50 para Sair");
		botao2.setName("PagarAoBanco");
		botao2.addMouseListener(this);
		botaoPanel.add(botao2);
		
		JButton botao3 = new JButton("Jogar Dado para Sair");
		botao3.setName("sairDados");
		botao3.addMouseListener(this);
		botaoPanel.add(botao3);
	}
	private void setDadoSorteSairDaPrisao(JPanel botaoPanel) {
		JButton botao3 = new JButton("Jogar Dado para Sair");
		botao3.setName("sairDados");
		botao3.addMouseListener(this);
		botaoPanel.add(botao3);
	}
	
	public void mouseClicked(MouseEvent e) {	
		if (e.getComponent().getName().equals("irParaPrisao")) {
			Controle.setPrisao(JogoTipoAcao.vaiParaPrisao);
		}
		if (e.getComponent().getName().equals("PagarAoBanco")) {
			Controle.setPrisao(JogoTipoAcao.pagarAoBancoPrisao);
		}
		if (e.getComponent().getName().equals("sairDados")) {
			Controle.setPrisao(JogoTipoAcao.jogarDados);
		}
		if (e.getComponent().getName().equals("usarCartaoPrisao")) {
			Controle.setPrisao(JogoTipoAcao.recebeCartaoLivraDaPrisao);
		}
	}
	
	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {	}
}
