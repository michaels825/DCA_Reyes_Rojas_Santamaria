package taller_2dca_Rojas_Reyes_Santamaria;

import processing.core.PConstants;
import processing.core.PVector;

public class Jugador extends Personaje {
	protected int finalX, finalY;

	public Jugador(Logica log, float x, float y) {
		super(log, x, y);

		finalX = (int) x;
		finalY = (int) y;

		personaje = app.loadImage("personaje.png");

	}

	@Override
	public void pintar() {
		app.imageMode(PConstants.CENTER);
		app.image(personaje, pos.x, pos.y);
		app.noFill();
		app.stroke(255);
		app.ellipse(pos.x, pos.y, personaje.height, personaje.height);

		nivel(100, 20);
	}

	@Override
	public void mover() {
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

		if (temporizador == true) {
			contador++;
		}
		if (contador > 150) {
			temporizador = false;
			contador = 0;
		}

		// System.out.println("contador: "+contador);

	}

	public void setFinal(int xx, int yy) {
		this.finalX = xx;
		this.finalY = yy;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;

	}

	@Override
	public void atacar() {
		for (int i = 0; i < log.getEnemigos().size(); i++) {
			if (log.getEnemigos().get(i).getPos().dist(pos)<200) {
				Bala b = new Bala(app);
				b.movimientoBala(new PVector(app.mouseX, app.mouseY), pos);
				balas.add(b);
			}
		}
		
		
	}

}