package taller_2dca_Rojas_Reyes_Santamaria;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Enemigo extends Personaje {

	private Pistola pistola;

	public Enemigo(Logica log, float x, float y) {
		super(log, x, y);

		personaje = app.loadImage("Enemigo.png");

	}

	@Override
	public void pintar() {
		app.imageMode(PConstants.CENTER);
		app.image(personaje, pos.x, pos.y);
		app.noFill();
		app.stroke(255);
		app.ellipse(pos.x, pos.y, personaje.width, personaje.height);

		if (pistola != null) {
			app.stroke(255);
			app.strokeWeight(5);
			app.line(pos.x, pos.y, pistola.getX(), pistola.getY());
			app.noStroke();
		}
		nivel(pos.x, pos.y);

	}

	@Override
	public void mover() {
		if (pistola == null && pos != null && vel != null && personaje != null) {

			if (pos.x - personaje.width / 2 <= 0 || pos.x + personaje.width / 2 >= app.width) {

				vel.x *= -1;
				pos.x += vel.x * 2;
			}

			if (pos.y - personaje.height / 2 <= 0 || pos.y + personaje.height / 2 >= app.height) {
				vel.y *= -1;
				pos.y += vel.y * 2;
			}

			pos.x += vel.x;
			pos.y += vel.y;

			if (Logica.log.getElementos() != null) {

				for (int i = 0; i < Logica.log.getElementos().size(); i++) {
					Elemento e = Logica.log.getElementos().get(i);

					if (e instanceof Pistola) {
						Pistola p = (Pistola) e;
						if (p.isPerseguida() == false) {
							pistola = p;
							p.setPerseguida(true);
							return;
						}

					}
				}
			}

		} else if (pistola != null) {
			moverEnemigo(pistola.getX(), pistola.getY());
			if (PApplet.dist(pos.x, pos.y, pistola.getX(), pistola.getY()) < 30
					|| Logica.log.getElementos().indexOf(pistola) == -1) {
				pistola = null;
			}
		}

	}

	int counter = 0;

	@Override
	public void atacar() {

		int time = app.millis() / 1000;
		int frecuencia = 1;

		// System.out.println(time);

		if (time % 5 == 0 && counter == 0) {
			int tipo = (int) app.random(0, 5);
			if (pos.dist(log.getJugador().getPos()) < 400 && log.getJugador().getNivel() <= getNivel()) {
				Bala b = new Bala(app);
				PVector destino = new PVector(log.getJugador().getPos().x, log.getJugador().getPos().y);
				b.movimientoBala(destino, pos);

				balas.add(b);
			}

			counter = 1;

		} else if (counter >= 1 && counter < 80) {
			counter++;
		} else {
			counter = 0;
		}
	}

	public void run() {

		while (vivo) {

			try {
				mover();
				recoger();

				atacar();

				sleep(25);
			} catch (Exception e) {
				e.printStackTrace();

				vivo = false;

			}
		}
	}
}
