package taller_2dca_Rojas_Reyes_Santamaria;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public abstract class Personaje extends Thread {
	protected Main app;
	protected PVector pos;
	protected PVector vel;
	protected PImage personaje;
	protected boolean vivo, temporizador = false;
	protected int contador;

	private boolean recogido;
	private ArrayList<Pistola> armas;
	protected ArrayList<Bala> balas;
	protected Logica log;

	public PVector getPos() {
		return pos;
	}

	public Personaje(Logica log, float x, float y) {
		this.app = log.getApp();
		this.log = log;
		this.pos = new PVector(x, y);
		this.vel = new PVector(2, 3);

		vivo = true;
		recogido = false;
		armas = new ArrayList<Pistola>();
		balas = new ArrayList<Bala>();

		start();
	}

	public abstract void pintar();

	public abstract void mover();
	
	public abstract void atacar();
	
	public void pintarBalas(){
	for (int i = 0; i < balas.size(); i++) {
		balas.get(i).pintar();
		System.out.println("pintar");
	}	
		
		
	}

	public void run() {

		while (vivo) {

			try {
				mover();
				recoger();

				sleep(25);
			} catch (Exception e) {
				e.printStackTrace();

				vivo = false;

			}
		}
	}

	public void moverEnemigo(float x, float y) {

		if (pos.x < x) {
			pos.x += PApplet.abs(vel.x);
		}
		if (pos.y < y) {
			pos.y += PApplet.abs(vel.y);
		}
		if (pos.x > x) {
			pos.x -= PApplet.abs(vel.x);
		}
		if (pos.y > y) {
			pos.y -= PApplet.abs(vel.y);
		}
		if (pos.x - personaje.width / 2 <= 0 || pos.x + personaje.width / 2 >= app.width) {

			vel.x *= -1;
			pos.x += vel.x * 2;
		}

		if (pos.y - personaje.height / 2 <= 0 || pos.y + personaje.height / 2 >= app.height) {
			vel.y *= -1;
			pos.y += vel.y * 2;
		}

		if (temporizador == true) {
			vel.x = 0;
			vel.y = 0;
			contador++;
		}
		if (contador > 150) {
			temporizador = false;
			contador = 0;
			vel.x = 3;
			vel.y = 3;
		}

		//System.out.println("contador: " + contador);

	}

	public void recoger() {
		if (Logica.log.getElementos() != null) {
			for (int i = 0; i < log.getElementos().size(); i++) {
				Elemento e = log.getElementos().get(i);
				if (validar(e)) {
					e.setRecogido(true);

					if (e instanceof Chocolate) {
						vel.x += 3;
						vel.y += 3;
						System.out.println("Chocolate");
					}
					if (e instanceof Granada) {
						// temporizador=true;
						// vel.x = 0;
						// vel.y = 0;

						System.out.println("Granada");

					}
					if (e instanceof Jeringa) {

					}
					if (e instanceof Bandera) {

					}
					if (e instanceof Pistola) {
						armas.add((Pistola) e);

					}
				}
			}
		}
	}

	public boolean validar(Elemento elementos) {

		return PApplet.dist(pos.x, pos.y, elementos.getX(), elementos.getY()) < 30;
	}

	public void nivel(float x, float y) {
		int tamano = 20;
		app.fill(255);
		app.rectMode(PConstants.CENTER);
		if (this instanceof Jugador) {
			for (int i = 0; i < armas.size(); i++) {
				app.rect(x + (i * tamano), y,
						tamano / 2, tamano);

			}
		} else {
			for (int i = 0; i < armas.size(); i++) {
				app.rect(x + (i * tamano) - ((armas.size() - 1) * tamano / 2), y + (personaje.height / 2) + 20,
						tamano / 2, tamano);

			}
		}
	}
	
	public int getNivel() {
		return armas.size();
	}

}
