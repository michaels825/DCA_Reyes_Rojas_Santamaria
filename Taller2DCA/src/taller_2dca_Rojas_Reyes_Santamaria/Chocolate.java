package taller_2dca_Rojas_Reyes_Santamaria;

import processing.core.PConstants;

public class Chocolate extends Elemento {

	public Chocolate(Main app, float x, float y) {
		super(app, x, y);
		
		elemento = app.loadImage("chocolate.png");
		
	}

	@Override
	public void pintar() {
		app.imageMode(PConstants.CENTER);
		app.image(elemento, x, y);				
	}

}
