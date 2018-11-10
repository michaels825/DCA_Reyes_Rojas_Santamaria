package taller_2dca_Rojas_Reyes_Santamaria;

import processing.core.PConstants;

public class Pistola extends Elemento {
	public boolean perseguida;

	public Pistola(Main app, float x, float y) {
		super(app,x,y);
		this.x = x;
		this.y = y;

		elemento = app.loadImage("arma.png");
		
		perseguida = false;

	
	}

	public boolean isPerseguida() {
		return perseguida;
	}

	public void setPerseguida(boolean perseguida) {
		this.perseguida = perseguida;
	}

	@Override
	public void pintar() {
		app.imageMode(PConstants.CENTER);
		app.image(elemento, x, y);
		
	}

}
