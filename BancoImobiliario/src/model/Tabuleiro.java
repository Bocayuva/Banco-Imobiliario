package model;

import model.CasaTabuleiro.TipoCasaTabuleiro;
import model.Empresa.TipoCompanhia;
import model.Terreno.GrupoPropriedade;

class Tabuleiro {


	private CasaTabuleiro tabuleiro[];
	
	protected CasaTabuleiro[] getTabuleiro () {
		return this.tabuleiro;
	}
	
	protected static int getPosicaoTabuleiro () {
		return 0;
	}

	public Tabuleiro() {
		this.tabuleiro = new CasaTabuleiro[] { 
				new Outras(TipoCasaTabuleiro.pontoDePartida), //0
					new Terreno("Leblon", GrupoPropriedade.rosa, 100, 50, 50, 50, 6, 30, 90, 270, 400, 500), //1 - ok
					new Outras(TipoCasaTabuleiro.sorteReves), //2
					new Terreno("Av. Presidente Vargas",GrupoPropriedade.rosa, 60, 50, 50, 30, 2, 10, 30, 90, 160, 250), //3 - ok
					new Terreno("Av. Nossa S, de Copacabana", GrupoPropriedade.rosa, 60, 50, 50, 30, 4, 20, 60, 180, 320, 450), //4 - ok
					new Empresa(50, 200, 100, TipoCompanhia.ferroviaria), //5 - ok
					new Terreno("Av. Brigadeiro Faria Lima", GrupoPropriedade.azul, 240, 150, 150, 120, 20, 100, 300, 750, 925, 1100), //6 - ok
					new Empresa(50, 200, 100, TipoCompanhia.onibus), //7 - ok
					new Terreno("Av. Reboucas", GrupoPropriedade.azul, 220, 150, 150, 110, 18, 90, 250, 700, 875, 1050), //8 - ok
					new Terreno("Av. 9 de Julho", GrupoPropriedade.azul, 220, 150, 150, 110, 18, 90, 250, 700, 875, 1050), //9 - ok
				new Outras(TipoCasaTabuleiro.prisao), 	//10
					new Terreno("Av.Europa", GrupoPropriedade.roxo, 200, 100, 100, 100, 16, 80, 220, 600, 800, 1000), //11 - ok
					new Outras(TipoCasaTabuleiro.sorteReves), //12
					new Terreno("Rua Agusta", GrupoPropriedade.roxo, 180, 100, 100, 90, 14, 70, 200, 550, 750, 950), //13 - ok
					new Terreno("Av. Pacaembu", GrupoPropriedade.roxo, 180, 100, 100, 90, 14, 70, 200, 550, 750, 950), //14 - ok
					new Empresa(40, 150, 75, TipoCompanhia.taxi), //15 - ok
					new Outras(TipoCasaTabuleiro.sorteReves), //16
					new Terreno("Interlagos", GrupoPropriedade.laranja, 350, 200, 200, 175, 35, 175, 500, 1100, 1300, 1500), //17 - ok
					new Outras(TipoCasaTabuleiro.lucroDividendo), //18
					new Terreno("Murumbi", GrupoPropriedade.laranja, 400, 200, 200, 200, 50, 200, 600, 1400, 1700, 2000), //19 - ok
				new Outras(TipoCasaTabuleiro.paradaLivre), //20
					new Terreno("Flamengo", GrupoPropriedade.vermelho, 120, 50, 50, 60, 8, 40, 100, 300, 450, 600), //21 - OK
					new Outras(TipoCasaTabuleiro.sorteReves), //22
					new Terreno("Botafogo", GrupoPropriedade.vermelho, 100, 50, 50, 50, 6, 30, 90, 270, 400, 500), //23 - ok
					new Outras(TipoCasaTabuleiro.impostoRenda), //24
					new Empresa(40, 150, 75, TipoCompanhia.navegacao), //25 - ok
					new Terreno("Av. Brasil", GrupoPropriedade.amarelo, 160, 100, 100, 80, 12, 60, 180, 500, 700, 900), //26 - ok 
					new Outras(TipoCasaTabuleiro.sorteReves), //27
					new Terreno("Av. Paulista", GrupoPropriedade.amarelo, 140, 100, 100, 70, 10, 50, 150, 450, 625, 750), //28 - ok
					new Terreno("Jardim Europa", GrupoPropriedade.amarelo, 140, 100, 100, 70, 10, 50, 150, 450, 625, 750), //29 - ok
				new Outras(TipoCasaTabuleiro.vaiParaPrisao),  //30
					new Terreno("Copacabana", GrupoPropriedade.verde, 340, 150, 150, 130, 22, 110, 330, 800, 975, 1150), //31
					new Empresa(50, 200, 100, TipoCompanhia.aviacao),  //32
					new Terreno("Av. Viera Souto", GrupoPropriedade.verde, 320, 200, 200, 160, 28, 150, 450, 1000, 1200, 1400), //33
					new Terreno("Av. Atlantica", GrupoPropriedade.verde, 300, 200, 200, 150, 26, 130, 390, 900, 1100, 1275), //34 - ok
					new Empresa(50, 200, 100, TipoCompanhia.taxiAereo), //35
					new Terreno("Ipanema", GrupoPropriedade.verde, 300, 200, 200, 150, 26, 130, 390, 900, 1100, 1275), //36
					new Outras(TipoCasaTabuleiro.sorteReves),  //37
					new Terreno("Brooklin", GrupoPropriedade.cinza, 280, 150, 150, 130, 22, 110, 330, 800, 875, 1150), //38
					new Terreno("Jardim Paulista", GrupoPropriedade.cinza, 260, 150, 150, 140, 24, 120, 360, 850, 1025, 1200), //39
				};
	}
}
