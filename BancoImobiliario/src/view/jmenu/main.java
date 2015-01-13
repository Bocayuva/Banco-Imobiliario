package view.jmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.Jogo;
import control.Controle;

@SuppressWarnings("serial")
public class main extends JMenuBar implements ActionListener, MouseListener{

	private JMenu arquvio;
	private JMenu jogador;
	private JMenu mercado;
	private JMenuItem arquivoNovo;
	private JMenuItem arquivoSair;
	private JMenuItem jogadorMI[];

	private boolean botoesJogoAtivo;
	
	public main() {
		this.botoesJogoAtivo = false;
		
		// Cria o botao Arquivo
		arquvio = new JMenu("Arquivo");
		arquvio.setName("MnuArquivo");
		arquvio.addMouseListener(this);
		arquvio.setVisible(true);
		this.add(arquvio);

		// Cria os botoes internos do Arquivo
		arquivoNovo = new JMenuItem("Novo Jogo");
		arquivoNovo.setActionCommand("MnuItNovoJgo");
		arquivoNovo.addActionListener(this);
		arquivoNovo.setVisible(true);
		arquvio.add(arquivoNovo);

		arquivoSair = new JMenuItem("Sair Jogo");
		arquivoSair.setActionCommand("MnuItSairJgo");
		arquivoSair.addActionListener(this);
		arquivoSair.setVisible(true);
		arquvio.add(arquivoSair);
		
		// Cria o botao Jogador
		jogador = new JMenu("Jogador");
		jogador.setName("MnuJogador");
		jogador.addMouseListener(this);
		jogador.setVisible(false);
		this.add(jogador);
		
		// Cria o botao Mercado
		mercado = new JMenu("Mercado");
		mercado.setName("MnuMercado");
		mercado.addMouseListener(this);
		mercado.setVisible(false);
		this.add(mercado);
	}
	
	private void inicializaJogadorBotao() {
		if(jogadorMI != null) {
			jogador.removeAll();
			jogadorMI = null;
		}
		jogadorMI = new JMenuItem[6];
		for(int i=0;i<Jogo.getGAMEquantidadeJogador();i++) {
			jogadorMI[i] = new JMenuItem("Jogador "+(i+1));
			jogadorMI[i].setActionCommand("MnuItJogador"+(i+1));
			jogadorMI[i].addActionListener(this);
			jogadorMI[i].setVisible(true);
			jogador.add(jogadorMI[i]);
		}
	}
	
	public void setVisualizarBotoesDeJogo() {
		if(botoesJogoAtivo == false) {
			botoesJogoAtivo = true;
			this.jogador.setVisible(true);
			this.mercado.setVisible(true);
			this.inicializaJogadorBotao();
			this.revalidate();
			this.repaint();
		} else {
			this.botoesJogoAtivo = false;
			this.jogador.setVisible(false);
			this.mercado.setVisible(false);
		}
		this.revalidate();
		this.repaint();
	}
	
	public void setOcultarBotoesDeJogo() {
		this.botoesJogoAtivo = false;
		this.jogador.setVisible(false);
		this.mercado.setVisible(false);
		this.revalidate();
		this.repaint();
	}
	
	public boolean getStatusBotoesDeJogoAtivo() {
		return this.botoesJogoAtivo;
	}

	public void actionPerformed(ActionEvent e) {
		if ("MnuItNovoJgo".equals(e.getActionCommand())) {
			Controle.setNovoJogo();
		}
		if ("MnuItSairJgo".equals(e.getActionCommand())) {
			Controle.setEncerrarPrograma();
		}
		if ("MnuItJogador1".equals(e.getActionCommand())) {
			Controle.getJogadorFrame(1);
		}
		if ("MnuItJogador2".equals(e.getActionCommand())) {
			Controle.getJogadorFrame(2);
		}
		if ("MnuItJogador3".equals(e.getActionCommand())) {
			Controle.getJogadorFrame(3);
		}
		if ("MnuItJogador4".equals(e.getActionCommand())) {
			Controle.getJogadorFrame(4);
		}
		if ("MnuItJogador5".equals(e.getActionCommand())) {
			Controle.getJogadorFrame(5);
		}
		if ("MnuItJogador6".equals(e.getActionCommand())) {
			Controle.getJogadorFrame(6);
		}
	}
	
	public void mouseClicked(MouseEvent e) {	
		if (e.getComponent().getName().equals("MnuMercado")) {
			Controle.getMercadoFrame();
		}
	}

	public void mousePressed(MouseEvent e) {	}

	public void mouseReleased(MouseEvent e) {	}

	public void mouseEntered(MouseEvent e) {	}

	public void mouseExited(MouseEvent e)  {	}
}