package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.Imagem;
import view.Imagem.TipoImagem;
import model.Jogo.JogoTipoAcao;
import control.Controle;

@SuppressWarnings("serial")
public class Panel_MensagemImpostoRenda extends JPanel implements MouseListener {
	
	public Panel_MensagemImpostoRenda () {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.gray.darker());	
		setImagem();
		setBotao();
		this.setVisible(true);
	}

	private void setImagem() {
		JLabel imagem = new Imagem(TipoImagem.ImpostoRenda).getImagemLabel();
		imagem.setBounds(0, 0, 230, 230);
		this.add(imagem, BorderLayout.CENTER);
	}
	private void setBotao() {
		JButton botao = new JButton("Pagar $ 200");
		botao.setName("PagarAoBanco");
		botao.addMouseListener(this);
		this.add(botao, BorderLayout.PAGE_END);
	}

	public void mouseClicked(MouseEvent e) {	
		if (e.getComponent().getName().equals("PagarAoBanco")) {
			Controle.setAcaoPagar(JogoTipoAcao.pagarImpostoRenda);
		}
	}

	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {	}
}
