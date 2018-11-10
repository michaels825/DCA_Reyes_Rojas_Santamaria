package taller_2dca_Rojas_Reyes_Santamaria;


import processing.core.PApplet;

public class Main extends PApplet{
	private Logica logica;

	public static void main(String[] args) {
		PApplet.main("taller_2dca_Rojas_Reyes_Santamaria.Main");

	}
	public void settings() {
		size(1200, 700);
		//fullScreen();
	}
	
	public void setup() {
		logica = new Logica(this);
	}
	
	public void draw() {
		logica.pintar();
	}
	
	public void keyPressed() {
		
	}
	
	public void mousePressed() {
		logica.click();
		
	}
	
	public void mouseDragged() {
		
	}
	
	public void mouseReleased() {
	}
	
}
