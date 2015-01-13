package view.panel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import view.Imagem;
import view.Imagem.TipoImagem;

@SuppressWarnings("serial")
public class Panel_PrincipalInicio extends JPanel {

	private Imagem images;
	
	public Panel_PrincipalInicio() {
		inicializaImagem();
	//	MiniPropriedadeButton n = new MiniPropriedadeButton(1);
	//	this.add(n);
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	private void inicializaImagem (){
		this.images = new Imagem(TipoImagem.titulo);
		this.images.setUpdateEixos((int)getX()/2+125, (int)getY()/2+180);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
			this.images.paintComponent(g);
	}
}	