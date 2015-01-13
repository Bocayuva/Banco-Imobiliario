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
import control.Controle;
import model.Jogo;
import model.Jogo.JogoTipoAcao;

@SuppressWarnings("serial")
public class Panel_MensagemSorteReves extends JPanel implements MouseListener {
	
	/**************************************************************************
	**** Vareaveis de Panel Mensagem SorteReves
	***************************************************************************/

	private JogoTipoAcao tipoAcao = Jogo.getJOGRODacaoDoJogoAo();
	
	/**************************************************************************
	**** Constructor Panel Mensagem SorteReves
	***************************************************************************/
	
	public Panel_MensagemSorteReves () {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.orange);
		this.setVisible(true);
		
		setImagem();

		switch (tipoAcao) {
			case pagarAoBancoReves:
				setPagarAoBanco();
				break;
			case irParaPontoPartida:
				setIrParaPontoPartida();
				break;
			case vaiParaPrisao:
	
				setIrParaPrisao();
				break;
			case receberDoSorte:
	
				setIrReceberDoBanco();
				break;
			case receberDosJogadores:
	
				setReceberDosJogadores();
				break;
			case recebeCartaoLivraDaPrisao:
	
				setPegarCartaoLivraPrisao();
				break;
			default:
				break;
		}
	}
	
	/**************************************************************************
	**** Funcoes Panel Mensagem SorteReves
	***************************************************************************/

	private void setImagem() {
		JLabel imagem = null;
		imagem = new Imagem(TipoImagem.sorteReves,Jogo.getCSRimagemCartaoSorteReves()).getImagemLabel();
		imagem.setBounds(5, 5, 200, 230);
		this.add(imagem, BorderLayout.CENTER);
	}
	private void setPagarAoBanco() {
//		System.out.println("Panel_MensagemSorteReves | Construtor, Passei: pagarAoBancoReves");
		JButton botao = new JButton("Pagar $"+Jogo.getCSRvalorCartaoSorteReves());
		botao.setName("PagarAoBanco");
		botao.addMouseListener(this);
		this.add(botao, BorderLayout.PAGE_END);
	}
	private void setIrParaPontoPartida() {
//		System.out.println("Panel_MensagemSorteReves | Construtor, Passei: irParaPontoPartida");
		JButton botao = new JButton("Ir para o Ponto de Partida");
		botao.setName("IrParaPontoPartida");
		botao.addMouseListener(this);
		this.add(botao, BorderLayout.PAGE_END);
	}
	private void setIrParaPrisao() {
//		System.out.println("Panel_MensagemSorteReves | Construtor, Passei: vaiParaPrisao");
		JButton botao = new JButton("Vai para a Prisao");
		botao.setName("IrParaPrisao");
		botao.addMouseListener(this);
		this.add(botao, BorderLayout.PAGE_END);
	}
	private void setIrReceberDoBanco() {
//		System.out.println("Panel_MensagemSorteReves | Construtor, Passei: receberDoSorte");
		JButton botao = new JButton("Receber $"+Jogo.getCSRvalorCartaoSorteReves());
		botao.setName("IrReceberDoBanco");
		botao.addMouseListener(this);
		this.add(botao, BorderLayout.PAGE_END);
	}
	private void setReceberDosJogadores() {
//		System.out.println("Panel_MensagemSorteReves | Construtor, Passei: receberDosJogadores");
		JButton botao = new JButton("Receber $50 dos Jogadores");
		botao.setName("ReceberDosJogadores");
		botao.addMouseListener(this);
		this.add(botao, BorderLayout.PAGE_END);
	}
	private void setPegarCartaoLivraPrisao() {
//		System.out.println("Panel_MensagemSorteReves | Construtor, Passei: recebeCartaoLivraDaPrisao");
		JButton botao = new JButton("Guarda Cartao Free Prisao");
		botao.setName("RecebeCartaoLivraDaPrisao");
		botao.addMouseListener(this);
		this.add(botao, BorderLayout.PAGE_END);
	}

	////////////////////////////////////////////////////////////////////////////
	//// Funcoes de Tratamento de Mouse
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent().getName().equals("PagarAoBanco")) {
			Controle.setAcaoPagar(JogoTipoAcao.pagarAoBancoReves);
		}
		if (e.getComponent().getName().equals("IrParaPontoPartida")) {
			Controle.setAcaoReceber(JogoTipoAcao.irParaPontoPartida);
		}
		if (e.getComponent().getName().equals("IrReceberDoBanco")) {
			Controle.setAcaoReceber(JogoTipoAcao.receberDoSorte);
		}
		if (e.getComponent().getName().equals("ReceberDosJogadores")) {
			Controle.setAcaoReceber(JogoTipoAcao.receberDosJogadores);
		}
		if (e.getComponent().getName().equals("RecebeCartaoLivraDaPrisao")) {
			Controle.setAcaoReceber(JogoTipoAcao.recebeCartaoLivraDaPrisao);
		}
		if (e.getComponent().getName().equals("IrParaPrisao")) {
			Controle.setPrisao(JogoTipoAcao.vaiParaPrisao);
		}
	}

	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {	}
}
