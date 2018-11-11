package taller_2dca_Rojas_Reyes_Santamaria;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public abstract class Personaje extends Thread {
	protected Main app;
	protected Logica log;
	protected PVector pos, vel; // PVector de la velocidad y posicion de un objeto tipo Personaje, sea enemigo o
								// jugador.
	protected PImage personaje;
	protected boolean vivo, drogado, protegido;
	private ArrayList<Pistola> armas;
	protected ArrayList<Bala> balas;
	protected boolean movimientoTeclado;
	protected int finalX, finalY;

	public Personaje(Logica log, float x, float y) {
		this.app = log.getApp();
		this.log = log;
		this.pos = new PVector(x, y);
		this.vel = new PVector(5, 3);
		vivo = true;
		drogado = false;
		movimientoTeclado = false;
		armas = new ArrayList<Pistola>();
		balas = new ArrayList<Bala>();
		protegido = false;

		for (int i = 0; i < 5; i++) {
			armas.add(new Pistola(log.getApp(), 0, 0));
		}
	}

	public abstract void pintar();

	public abstract void mover();

	public abstract void atacar();

	public abstract void contacto();

	public void pintarBalas() {
		for (int i = 0; i < balas.size(); i++) {
			balas.get(i).pintar();
			PVector copi = balas.get(i).getPos().copy();
		}
	}

	public void run() {
		while (vivo) {
			try {
				mover();
				contacto();
				recoger();
				sleep(25);
			} catch (Exception e) {
				e.printStackTrace();
				vivo = false;
			}
		}
	}

	/*
	 * El enemigo comienza rebotando por el escenario.
	 */
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
		if (pos.x - personaje.width / 2 <= 340 || pos.x + personaje.width / 2 >= app.width - 340) {
			vel.x *= -1;
			pos.x += vel.x * 2;
		}
		if (pos.y - personaje.height / 2 <= 160 || pos.y + personaje.height / 2 >= app.height - 30) {
			vel.y *= -1;
			pos.y += vel.y * 2;
		}
	}

	/*
	 * Acciones de cada elemento a recoger.
	 */
	public void recoger() {
		if (Logica.log.getElementos() != null) {
			for (int i = 0; i < log.getElementos().size(); i++) {
				Elemento e = log.getElementos().get(i);
				if (validar(e)) {
					e.setRecogido(true);
					/*
					 * CHOCOLATE: aumenta la velocidad.
					 */
					if (e instanceof Chocolate) {
						vel.x += 3;
						vel.y += 3;
					}
					/*
					 * GRANADA: paraliza por 3 segs.
					 */
					if (e instanceof Granada) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					/*
					 * JERINGA: cambia modo de juego al jugador (mouse a teclas).
					 */
					if (e instanceof Jeringa) {
						if (this instanceof Jugador)
							log.getApp().setCambioMov(true);
						drogado = !drogado;
						movimientoTeclado = !movimientoTeclado;
						finalX = (int) e.getX();
						finalY = (int) e.getY();
					}
					/*
					 * BANDERA: crea campo de fuerza que proteje de balas.
					 */
					if (e instanceof Bandera) {
						protegido = true;
						new Thread(new Runnable() {

							@Override
							public void run() {
								try {
									sleep(10000);
									protegido = false;
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

							}
						}).start();
					}
					/*
					 * PISTOLA: sube de nivel.
					 */
					if (e instanceof Pistola) {
						if (armas.size() < 10)
							armas.add((Pistola) e);
					}
				}
			}
		}
	}

	/*
	 * Este metodo se encarga de validar la distancia entre un personaje y un
	 * elemento recogible, para validar si fue recogido o no.
	 */

	public boolean validar(Elemento elementos) {
		return PApplet.dist(pos.x, pos.y, elementos.getX(), elementos.getY()) < 85;
	}

	/*
	 * Se pintan las barras de nivel para los personajes, dependiendo del numero de
	 * armas recogidas por cada uno.
	 */
	public void nivel(float x, float y) {
		int tamano = 30;
		app.fill(255);
		app.rectMode(PConstants.CORNER);

		/*
		 * NIVEL JUGADOR: Estas barras se pintan en el extremo superior izquierdo. El
		 * maximo de nivel es 10.
		 */
		if (this instanceof Jugador) {
			for (int i = 0; i < armas.size(); i++) {
				app.rect(x + 30 + (i * 22), y + 10, tamano / 3, tamano, 2);
			}
			/*
			 * NIVEL ENEMIGO: Funciona de la misma manera que el nivel del jugador, pero se
			 * pinta bajo cada enemigo.
			 */
		} else {
			app.rectMode(PConstants.CENTER);
			for (int i = 0; i < armas.size(); i++) {
				app.rect(x + (i * 22) - ((armas.size() - 1) * tamano / 3), y + (personaje.height / 2) + 20, tamano / 3,
						tamano, 2);
			}

		}
	}

	/*
	 * Este metodo se encarga de verificar que se esta restando nivel
	 */
	public void restarVida() {
		if (armas.size() > 0)
			armas.remove(0);
		if (armas.size() == 0) {
			vivo = false;
			/*
			 * Jugador pierde.
			 */
			if (this instanceof Jugador) {
				log.setPantallas(4);
			}
		}

		app.filter(app.INVERT);
	}

	public int getNivel() {
		return armas.size();
	}

	public PVector getPos() {
		return pos;
	}

	public void setPos(PVector pos) {
		if (movimientoTeclado == true)
			this.pos = pos;
	}

	public PVector getVel() {
		return vel;
	}

	public int getTamanoArmas() {
		return armas.size();
	}

	public boolean isVivo() {
		return vivo;
	}
	
	public boolean getProtegido () {
		return protegido;
	}
}
