package taller_2dca_Rojas_Reyes_Santamaria;

import processing.core.PConstants;

public class Bandera extends Elemento {

	public Bandera(Main app, float x, float y) {
		super(app, x, y);
		this.x = x;
		this.y = y;
		elemento = app.loadImage("bandera.png");
	}

	@Override
	public void pintar() {
		app.imageMode(PConstants.CENTER);
		app.image(elemento, x, y);
	}

}
