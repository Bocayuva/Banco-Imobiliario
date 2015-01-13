package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Jogo;
import view.Imagem;
import view.Imagem.TipoImagem;
import control.Controle;

@SuppressWarnings("serial")
public class Panel_PropriedadeApoioRespostaTrocaPropriedade extends JPanel implements MouseListener{
	
	private int posicao = -1, posicaoOferecida = -1;
	private JPanel botoes = null, panelInfoSup=null;
	private JFrame frame = null;

	public Panel_PropriedadeApoioRespostaTrocaPropriedade (JFrame frame, int posicaoOferecida, int posicao) {
		this.posicao = posicao;
		this.posicaoOferecida = posicaoOferecida;
		this.frame = frame;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.black);
		
		setInformacoes();
		setBotoes();
		
		this.setVisible(true);
	}
	
	private void setInformacoes() {
		//Titulo jogador desaja troca
		JLabel titulo = new JLabel("Desejam a sua Prop.: ", JLabel.LEFT);
		JLabel imgButton = new Imagem(TipoImagem.minipropriedade,posicao).getImagemLabel();
		JPanel pan = new JPanel();
		pan.setLayout(new FlowLayout());
		pan.setBackground(Color.white);
		pan.add(titulo);
		pan.add(imgButton);
		
		JLabel titulo2 = new JLabel("Oferecem esta Prop.: ", JLabel.LEFT);
		JLabel imgButton2 = new Imagem(TipoImagem.minipropriedade,posicaoOferecida).getImagemLabel();
		JPanel pan2 = new JPanel();
		pan2.setLayout(new FlowLayout());
		pan2.setBackground(Color.white);
		pan2.add(titulo2);
		pan2.add(imgButton2);
		
		JLabel titulo1 = new JLabel("JOGADOR "+Jogo.getPROPproprietario(posicao), JLabel.CENTER);
		panelInfoSup = new JPanel();
		panelInfoSup.add(titulo1);
		panelInfoSup.add(pan);
		panelInfoSup.add(pan2);
		panelInfoSup.setLayout(new GridLayout(panelInfoSup.getComponentCount(),1));
		panelInfoSup.setBackground(Color.white);
		panelInfoSup.setVisible(true);
		panelInfoSup.setSize(300, 100);
		this.add(panelInfoSup, BorderLayout.PAGE_START);
	}	
	
	private void setBotoes() {

		JButton fechar = new JButton("Rejeitar");
		fechar.setName("Rejeitar");
		fechar.addMouseListener(this);

		JButton trocar = new JButton("Trocar");
		trocar.setName("Trocar");
		trocar.addMouseListener(this);
		
		botoes = new JPanel();
		botoes.setLayout(new FlowLayout());
		botoes.setBackground(Color.white);
		botoes.add(fechar);
		botoes.add(trocar);
		botoes.setVisible(true);
		botoes.setSize(300, 50);
		this.add(botoes, BorderLayout.PAGE_END);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getComponent().getName().equals("Trocar")) {
			Controle.setTrocaPropriedade(posicao,posicaoOferecida);
			frame.dispose();
		}
		if (e.getComponent().getName().equals("Rejeitar")) {
			frame.dispose();
		}
	}
	
	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {	}
}
