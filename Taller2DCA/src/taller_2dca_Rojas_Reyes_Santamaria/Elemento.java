package taller_2dca_Rojas_Reyes_Santamaria;

import processing.core.PImage;
import processing.core.PVector;

public abstract class Elemento {
	protected Main app;
	protected float x, y;
	protected PImage elemento;
	private boolean recogido;

	public Elemento(Main app, float x, float y) {
		this.app = app;
		this.x = x;
		this.y = y;
	}

	public abstract void pintar();

	public float getY() {
		return y;
	}

	public float getX() {
		return x;
	}

	public boolean getRecogido() {
		return recogido;
	}

	public void setRecogido(boolean recogido) {
		this.recogido = recogido;
	}

}
