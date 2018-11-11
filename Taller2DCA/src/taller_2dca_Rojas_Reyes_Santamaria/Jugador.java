package taller_2dca_Rojas_Reyes_Santamaria;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Jugador extends Personaje {

	public Jugador(Logica log, float x, float y) {
		super(log, x, y);
		finalX = (int) x;
		finalY = (int) y;
		personaje = app.loadImage("jugador.png");
	}

	@Override
	public void pintar() {
		app.imageMode(PConstants.CENTER);
		app.image(personaje, pos.x, pos.y);
		app.noFill();
		nivel(100, 20);
		if (protegido) {
			app.ellipseMode(app.CENTER);
			app.noStroke();
			app.fill(255,100);
			app.ellipse(pos.x, pos.y, 150, 150);
		}
	}

	/*
	 * 
	 */
	@Override
	public void mover() {
		if (drogado == false && finalX != 600 && finalY != 350) {
			if (pos.x < finalX) {
				pos.x += vel.x;
			}
			if (pos.y < finalY) {
				pos.y += vel.y;
			}
			if (pos.x > finalX) {
				pos.x -= vel.x;
			}
			if (pos.y > finalY) {
				pos.y -= vel.y;
			}
		}
	}

	@Override
	public void atacar() {
		for (int i = 0; i < log.getEnemigos().size(); i++) {
			if (log.getEnemigos().get(i).getPos().dist(pos) < 200) {

				Bala b = new Bala(app);
				b.movimientoBala(new PVector(app.mouseX, app.mouseY), pos);
				balas.add(b);
			}
		}

	}

	@Override
	public void contacto() {
		for (int i = 0; i < balas.size(); i++) {
			PVector copi = balas.get(i).getPos().copy();

			for (int j = 0; j < log.getEnemigos().size(); j++) {
				if (PApplet.dist(copi.x, copi.y, log.getEnemigos().get(j).getPos().x, log.getEnemigos().get(j).getPos().y) < 75
						&& log.getEnemigos().get(j).getProtegido()) {
					balas.remove(i);
					return;
				}
				if (PApplet.dist(copi.x, copi.y, log.getEnemigos().get(j).getPos().x,
						log.getEnemigos().get(j).getPos().y) < 40) {
					balas.remove(i);
					log.getEnemigos().get(j).restarVida();

					return;
				}

			}
		}
	}

	public void setFinal(int xx, int yy) {
		this.finalX = xx;
		this.finalY = yy;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;

	}

}