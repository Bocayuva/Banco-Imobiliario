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
public class Panel_MensagemVaiParaPrisao extends JPanel implements MouseListener {

	public Panel_MensagemVaiParaPrisao () {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.gray);
		setImagem();
		setPagarAoBanco();
		this.setVisible(true);
	}

	private void setImagem() {
		JLabel 	imagem = new Imagem(TipoImagem.vaiParaPrisao).getImagemLabel();
		imagem.setSize(230, 230);
		this.add(imagem, BorderLayout.CENTER);
	}
	
	private void setPagarAoBanco() {
		JButton botao = new JButton("Vai para Prisao");
		botao.setName("VaiParaPrisao");
		botao.addMouseListener(this);
		this.add(botao, BorderLayout.PAGE_END);
	}
	
	public void mouseClicked(MouseEvent e) {	
		if (e.getComponent().getName().equals("VaiParaPrisao")) {
			Controle.setPrisao(JogoTipoAcao.vaiParaPrisao);
		}
	}
	
	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {	}
}
