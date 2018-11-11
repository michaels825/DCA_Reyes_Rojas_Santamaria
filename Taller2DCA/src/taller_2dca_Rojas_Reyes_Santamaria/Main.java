package taller_2dca_Rojas_Reyes_Santamaria;

import processing.core.PApplet;

public class Main extends PApplet {
	private Logica logica;
	private boolean cambioMov;

	public static void main(String[] args) {
		PApplet.main("taller_2dca_Rojas_Reyes_Santamaria.Main");
	}

	public void settings() {
		fullScreen();
	}

	public void setup() {
		logica = new Logica(this);
		cambioMov = true;
	}

	public void draw() {
		background(45);
		logica.pintar();
	}

	public void keyPressed() {
		if (cambioMov)
			logica.teclado();
	}

	public void mousePressed() {
		logica.click();
	}

	public void setCambioMov(boolean cambioMov) {
		this.cambioMov = cambioMov;
	}

}
