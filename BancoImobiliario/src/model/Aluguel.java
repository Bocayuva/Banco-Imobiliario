package model;

class Aluguel {
	
	private int terreno;
	private int casa1;
	private int casa2;
	private int casa3;
	private int casa4;
	private int hotel;

	protected Aluguel(int terreno, int casa1, int casa2, int casa3, int casa4,	int hotel) {
		
		if(terreno < 0 || casa1 < 0 || casa2 < 0 || casa3 < 0 || casa4 < 0 || hotel < 0)
			throw new IllegalArgumentException("Valor de Aluguel Invalido");
		
		this.terreno = terreno;
		this.casa1 = casa1;
		this.casa2 = casa2;
		this.casa3 = casa3;
		this.casa4 = casa4;
		this.hotel = hotel;
	}
	
	protected enum TipoAluguel{
		terreno, casa, hotel 
	}
	
	protected int getAluguel (TipoAluguel tipoAluguel, int qtd){
		
		if(tipoAluguel==TipoAluguel.terreno)
			return this.terreno;
		else if(tipoAluguel==TipoAluguel.casa && qtd == 1)
			return this.casa1;
		else if(tipoAluguel==TipoAluguel.casa && qtd == 2)
			return this.casa2;
		else if(tipoAluguel==TipoAluguel.casa & qtd == 3)
			return this.casa3;
		else if(tipoAluguel==TipoAluguel.casa && qtd == 4)
			return this.casa4;
		else 
			return this.hotel;
	}
}
