package view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.Imagem;
import view.Imagem.TipoImagem;

@SuppressWarnings("serial")
public class Panel_JogadorBox extends JPanel implements ActionListener {

	String name = "";
	JLabel picture = null;
	JLabel titulo = null;
	@SuppressWarnings("rawtypes")
	JComboBox jogList = null;
	private	JPanel jogadorComboBoxPanel = null;
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Panel_JogadorBox(int num) 
	{
		super(new BorderLayout());
		jogList = new JComboBox();
		ArrayList<String> jogStrings = null;

		jogStrings = new ArrayList<String>();

		jogStrings.add("Cachorro");
		jogStrings.add("Carro");
		jogStrings.add("Chapeu");
		jogStrings.add("Ferro");
		jogStrings.add("Navio");
		jogStrings.add("Sapato");
		
		while(jogStrings.isEmpty()==false){
			String ret = jogStrings.remove(0);
			jogList.addItem(ret);
		}
		
		jogList.setSelectedIndex(-1);
		jogList.addActionListener(this);
		
		this.jogadorComboBoxPanel = new JPanel();
		jogadorComboBoxPanel.setLayout( new BorderLayout());
		jogadorComboBoxPanel.setBackground(Color.white);
		
		// Set up the picture.
		picture = new JLabel();
		picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
		picture.setHorizontalAlignment(JLabel.CENTER);
		picture.setPreferredSize(new Dimension(120,200));

		// Lay out The NovoJogadorComboBoxPanel

		jogadorComboBoxPanel.add(jogList, BorderLayout.PAGE_START);
		jogadorComboBoxPanel.add(picture, BorderLayout.CENTER);
		
		titulo = new JLabel("JOGADOR "+num,JLabel.CENTER);
		this.add(titulo, BorderLayout.PAGE_START);
		this.add(jogadorComboBoxPanel, BorderLayout.CENTER);
		this.setBackground(Color.white);
		this.setVisible(true);
	}
	
	/** Listens to the combo box. */
	@SuppressWarnings("rawtypes")
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		this.name = (String) cb.getSelectedItem();
		updateLabel(this.name);
	}

	protected void updateLabel(String name) {
		ImageIcon icon = new Imagem(TipoImagem.logoJogadorInserir,name).getIconImagem();
		picture.setIcon(icon);
		picture.setToolTipText("A drawing of a " + name.toLowerCase());
		if (icon != null) {
			picture.setText(null);
		} else {
			picture.setText("Image not found");
		}
	}

//	 /** Returns an ImageIcon, or null if the path was invalid. */
//	protected static ImageIcon createImageIcon(String path) {
//		Image img = null;
//		try {
//			img = ImageIO.read(new File(path));
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//			System.exit(1);
//		}
//		img = img.getScaledInstance(120, 200, 500);
//		if (img != null) {
//			return new ImageIcon(img);
//		} else {
//			System.err.println("Couldn't find file: " + path);
//			return null;
//		}
//	}

	public String getName(){
		return this.name; 
	}
	
	public String getIndexName(int index){
		jogList.setSelectedIndex(index);
		return jogList.getSelectedItem().toString();
	}
	
	public void disableCombobox(){
		jogList.setVisible(false);
	}
	
	public void setPicture( String name){
		updateLabel(name);
	}
}