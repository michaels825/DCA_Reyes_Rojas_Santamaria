package taller_2dca_Rojas_Reyes_Santamaria;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Logica {
	private Main app;
	static Logica log;
	private PImage fondo, puntaje;
	private Jugador jugador;
	private ArrayList<Enemigo> enemigos;
	private ArrayList<Elemento> elementos;

	public Logica(Main app) {
		this.app = app;
		log = this;

		fondo = app.loadImage("escenario.jpg");
		puntaje = app.loadImage("Puntaje.png");
		jugador = new Jugador(this, app.width / 2, app.height / 2);
		enemigos = new ArrayList<Enemigo>();
		for (int i = 0; i < 2; i++) {
			enemigos.add(new Enemigo(this, app.random(150, 1200), app.random(50, 650)));
		}

		elementos = new ArrayList<Elemento>();
		for (int i = 0; i < 10; i++) {
			elementos.add(new Chocolate(app, app.random(150, 1050), app.random(50, 670)));
			elementos.add(new Bandera(app, app.random(150, 1050), app.random(50, 670)));
			elementos.add(new Granada(app, app.random(150, 1050), app.random(50, 670)));
			elementos.add(new Jeringa(app, app.random(150, 1050), app.random(50, 670)));
			elementos.add(new Pistola(app, app.random(150, 1050), app.random(50, 670)));
		}

	}

	public ArrayList<Elemento> getElementos() {
		return elementos;
	}

	public void pintar() {
		app.imageMode(app.CORNER);
		app.image(fondo, 0, 0);
		app.imageMode(app.CORNER);
		app.image(puntaje, 0, 0);
		jugador.pintar();
		jugador.pintarBalas();
		pintarElementos();
		if ((app.millis() / 1000) > 6)
			agregarElemento();

		for (int i = 0; i < enemigos.size(); i++) {
			enemigos.get(i).pintar();
			enemigos.get(i).pintarBalas();
		}

		remover();
	}

	public void pintarElementos() {
		for (int i = 0; i < elementos.size(); i++) {
			elementos.get(i).pintar();
		}
	}

	public void remover() {
		for (int i = 0; i < elementos.size(); i++) {
			Elemento e = elementos.get(i);
			if (e.getRecogido()) {
				elementos.remove(e);
			}
		}
	}

	int counter = 0;

	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}

	public void agregarElemento() {
		int time = app.millis() / 1000;
		int frecuencia = 5;

		// System.out.println(time);

		if (time % 5 == 0 && counter == 0) {
			int tipo = (int) app.random(0, 5);

			switch (tipo) {
			case 0:
				elementos.add(new Chocolate(app, app.random(150, 1050), app.random(50, 670)));
				elementos.add(new Pistola(app, app.random(150, 1050), app.random(50, 670)));
				elementos.add(new Pistola(app, app.random(150, 1050), app.random(50, 670)));
				break;

			case 1:
				elementos.add(new Bandera(app, app.random(150, 1050), app.random(50, 670)));
				elementos.add(new Pistola(app, app.random(150, 1050), app.random(50, 670)));
				elementos.add(new Pistola(app, app.random(150, 1050), app.random(50, 670)));
				break;

			case 2:
				elementos.add(new Granada(app, app.random(150, 1050), app.random(50, 670)));
				elementos.add(new Pistola(app, app.random(150, 1050), app.random(50, 670)));
				elementos.add(new Pistola(app, app.random(150, 1050), app.random(50, 670)));
				break;

			case 3:
				elementos.add(new Jeringa(app, app.random(150, 1050), app.random(50, 670)));
				elementos.add(new Pistola(app, app.random(150, 1050), app.random(50, 670)));
				elementos.add(new Pistola(app, app.random(150, 1050), app.random(50, 670)));
				break;

			case 4:
				elementos.add(new Pistola(app, app.random(150, 1050), app.random(50, 670)));
				elementos.add(new Pistola(app, app.random(150, 1050), app.random(50, 670)));
				break;

			default:
				break;
			}

			counter = 1;

		} else if (counter >= 1 && counter < 80) {
			counter++;
		} else {
			counter = 0;
		}
	}

	public void click() {

		if (app.mouseButton == PConstants.LEFT) {

			if (jugador.getState().name().equals("NEW")) {
				jugador.start();
			}

			jugador.setFinal(app.mouseX, app.mouseY);
		} else {
			jugador.interrupt();
		}

		jugador.atacar();

	}

	public Main getApp() {
		return app;
	}

	public Jugador getJugador() {
		return jugador;
	}

}
