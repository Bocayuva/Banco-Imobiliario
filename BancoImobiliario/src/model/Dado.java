package model;
import java.util.Random;

class Dado {

	private static int numDado;
	private static int numFases;
	private static Random gerador;
	
	protected Dado () {
		numDado = 0;
		numFases = 6;
		gerador = new Random();
	}
	
	protected static void jogarDados () {
		numDado = gerador.nextInt(numFases) + 1;
	}
	
	protected static int getNumDado () {
		return numDado;
	}
	
	protected static void setNumFaseDado (int numFasesDado) {
		if(numFases <= 0)
			throw new IllegalArgumentException("Numero de fases do dado Invalido");
		numFases = numFasesDado;
	}
}
