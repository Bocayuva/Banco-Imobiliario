package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Imagem {
	/***************************************************************************
	*** Vareavies Globais relacionadas Classe Imagem
	***************************************************************************/
	
	private BufferedImage image;
	private String patch;
	private int eixoX, eixoY;
	private String localLogo = "";

	/***************************************************************************
	***  Estrutura de Apoio a Classe Imagem
	***************************************************************************/

	///// Tipos identificado de imagem ao qual a classe possui.
	public enum TipoImagem {
		titulo,tabuleiro,logoJogadorTabuleiro,logoJogador, logoJogadorInserir,
		sorteReves,propriedade,minipropriedade,Hotel,Dinheiro,Casa,
		vaiParaPrisao, Prisao, LucroDividendo, ImpostoRenda, PontoPartida
	}
	
	/***************************************************************************
	***  Construtor Default da Classe Imagem
	***************************************************************************/

	/////  Para SorteReves, Propriedade
	public Imagem (TipoImagem tipoImagem, int num) {
		switch (tipoImagem) {
		case sorteReves:
			patch = "sorteReves"+num+".png";
			image = readImage(patch);
			image = resize(image, 230, 260);
			break;
		case propriedade:
			patch = "propriedade"+num+".png";
			image = readImage(patch);
			image = resize(image, 230, 260);
			break;
		case minipropriedade:
			patch = "propriedadeMini"+num+".png";
			image = readImage(patch);
			image = resize(image, 100, 30);
			break;
		default:
			break;
		}
		eixoX = 0;		eixoY = 0;
	}
	/////  Para Logo Jogador
	public Imagem (TipoImagem tipoImagem, String logo) {
		this.localLogo = logo;
		switch (tipoImagem) {
		case logoJogadorTabuleiro:
			patch = "jog"+logo+".png";
			image = readImage(patch);
			image = resize(image, 35, 35);
			break;
		case logoJogador:
			patch = "jog"+logo+".png";
			image = readImage(patch);
			image = resize(image, 70, 70);
			break;
		case logoJogadorInserir:
			patch = "jog"+logo+".png";
			image = readImage(patch);
			image = resize(image, 120, 200);
			break;
		default:
			break;
		}
		eixoX = 0;		eixoY = 0;
	}
	/////  Para Elemento
	public Imagem (TipoImagem tipoImagem) {
		switch (tipoImagem) {
		case titulo:
			patch = "elementoTitulo.gif";
			image = readImage(patch);
			break;
		case tabuleiro:
			patch = "elementoTabuleiro.png";
			image = readImage(patch);
			break;
		case Casa:
			patch = "elementoCasa.png";
			image = readImage(patch);
			image = resize(image, 30, 30);
			break;
		case Hotel:
			patch = "elementoHotel.png";
			image = readImage(patch);
			image = resize(image, 30, 30);
			break;
		case Dinheiro:
			patch = "elementoDinheiro.png";
			image = readImage(patch);
			image = resize(image, 25, 25);
			break;
		case vaiParaPrisao:
			patch = "elementoVaiParaPrisao.png";
			image = readImage(patch);
			break;
		case Prisao:
			patch = "elementoPrisao.png";
			image = readImage(patch);
			break;
		case LucroDividendo:
			patch = "elementoLucroDividendo.png";
			image = readImage(patch);
			break;
		case ImpostoRenda:
			patch = "elementoImpostoRenda.png";
			image = readImage(patch);
			break;
		case PontoPartida:
			patch = "elementoPontoPartida.png";
			image = readImage(patch);
			break;
		default:
			break;
		}
		eixoX = 0;		eixoY = 0;
	}
	
	/***************************************************************************
	***  Funcoes de SET da Classe Imagem
	***************************************************************************/
	
	///// Atualiza a posicao onde a imagem sera exibida. 
	public void setUpdateEixos(int EixoX, int EixoY) {
		eixoX = EixoX;
		eixoY = EixoY;
	}
	
	/***************************************************************************
	***  Funcoes de GET da Classe Imagem
	***************************************************************************/

	///// Retorna o nome da imagem, ao qual identifica ela.
	public String getLocalLogo() {
		return localLogo;
	}	
	///// Retorna um dimensao exata da imagem.
	public Dimension getSizeImagem () {
		return new Dimension(image.getWidth(null), image.getHeight(null));
	}
	///// Retorna a imagem em icon, para ser ultilizado como um icone dentro da label.
	public ImageIcon getIconImagem () {
		ImageIcon img = new ImageIcon(image);
		return img;
	}
	///// Retorna a imagem em Jlabel, para a imagem ser ultilizada como um componente JLabel.
	public JLabel getImagemLabel () {
		Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
		JLabel picture = new JLabel(new ImageIcon(image));
		picture.setPreferredSize(size);
		picture.setMinimumSize(size);
		picture.setMaximumSize(size);
		picture.setBackground(Color.green);
		picture.setHorizontalAlignment(JLabel.CENTER);
		picture.setVisible(true);
		return picture;
	}
	///// Retorna a imagem em Jlabel de um determinado tamanho especifico, para a imagem ser ultilizada como um componente JLabel.
	public JLabel getImagemLabel (int x, int y) {
		image = resize(image, x, y);
		JLabel picture = new JLabel(new ImageIcon(image),JLabel.CENTER);
		picture.setHorizontalAlignment(JLabel.CENTER);
		picture.setPreferredSize(new Dimension(90,90));
		picture.setVisible(true);
		return picture;
	}
	
	/***************************************************************************
	***  Funcoes de Apoio da Classe Imagem
	***************************************************************************/
	
	///// Funcao de desenho da imagem em uma Frame. 
	public void paintComponent(Graphics g) {
		g.drawImage(image, eixoX, eixoY, null);
	}
	///// Ajusta o tamanho da imagem, ampliando e reduzindo ela.	
	private static BufferedImage resize(BufferedImage img, int newW, int newH) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;
	}
	///// Realiza a leitura do arquivo da imagem, carregando ela em um BufferedImage.
	private static BufferedImage readImage(String fileLocation) {
	    BufferedImage img = null;	    
		try {
			img = ImageIO.read(ResourceLoader.load(fileLocation));
		} catch (IOException e) {
			System.out.println("\nFalha ao carregar imagem");
			e.printStackTrace();
		}
		return img;
	}
}
