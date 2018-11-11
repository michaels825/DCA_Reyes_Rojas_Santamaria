package taller_2dca_Rojas_Reyes_Santamaria;

import java.util.ArrayList;
import processing.sound.*;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

public class Logica {
	private Main app;
	static Logica log; // Esta variable permite crear un objeto de tipo logica para pasarselo como
						// parametro a otras clases
	private PImage fondo, puntaje, inicio, instrucciones, ganaste, perdiste; // Todas las imagenes de pantallas y
																				// escenario
	private Jugador jugador;
	private ArrayList<Enemigo> enemigos;
	private ArrayList<Elemento> elementos;
	private int counter;
	private int pantallas;
	private PFont fuente;
	private boolean iniciarJuego;
	int time;
	boolean starTime;
	private SoundFile fondoPerder, fondoGanar, fondoJuego, fondoInicioEInstrucc; // Variables que guardan los archivos
																					// de audio.

	public Logica(Main app) {
		this.app = app;
		log = this;
		/*
		 * Se cargan todas la imagenes, sonidos y fuentes: escenario, barra puntaje y
		 * pantallas.
		 */
		fondo = app.loadImage("escenario.png");
		inicio = app.loadImage("inicio.png");
		instrucciones = app.loadImage("instrucciones.png");
		ganaste = app.loadImage("ganaste.png");
		perdiste = app.loadImage("perdiste.png");
		puntaje = app.loadImage("puntaje.png");
		fuente = app.createFont("digital-7.ttf", 50);
		fondoInicioEInstrucc = new SoundFile(app, "fondoInicioEInstrucc.mp3");
		fondoJuego = new SoundFile(app, "fondoJuego.mp3");
		fondoPerder = new SoundFile(app, "fondoPerder.mp3");
		fondoGanar = new SoundFile(app, "fondoGanar.mp3");
		fondoInicioEInstrucc.play();
	}

	private void startGame() {
		jugador = new Jugador(this, app.width / 2, app.height / 2);
		elementos = new ArrayList<Elemento>();
		enemigos = new ArrayList<Enemigo>();

		/*
		 * Se agregan los primeros enemigos que apareceran en pantalla por defecto.
		 */
		for (int i = 0; i < 3; i++) {
			enemigos.add(new Enemigo(this, app.random(150, 1200), app.random(50, 650)));
		}

		/*
		 * Se agregan los primeros elementos que apareceran en pantalla por defecto.
		 */
		for (int i = 0; i < 2; i++) {
			elementos.add(new Chocolate(app, app.random(340, 1720), app.random(160, 1020)));
			elementos.add(new Bandera(app, app.random(340, 1720), app.random(160, 1020)));
			elementos.add(new Granada(app, app.random(340, 1720), app.random(160, 1020)));
			elementos.add(new Jeringa(app, app.random(340, 1720), app.random(160, 1020)));
			elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
		}
	}

	/*
	 * En este metodo se pintan imagenes como el escenario del juego y la barra
	 * donde se presenta el nombre, nivel y el tiempo del jugador. Además del
	 * jugador, los enemigos y las balas. También se hace un switch que luego
	 * cambiará las pantallas con click en los botones.
	 */
	public void pintar() {
		switch (pantallas) {
		case 0:
			app.imageMode(PConstants.CORNER);
			app.image(inicio, 0, 0);
			break;
		case 1:
			app.imageMode(PConstants.CORNER);
			app.image(instrucciones, 0, 0);
			break;
		case 2:
			pantallaJuego();
			iniciarJuego = true;
			break;
		case 3:
			app.imageMode(PConstants.CORNER);
			app.image(ganaste, 0, 0);
			break;
		case 4:
			app.imageMode(PConstants.CORNER);
			app.image(perdiste, 0, 0);
			break;
		}
	}

	public void pantallaJuego() {
		if (iniciarJuego == true) {
			app.imageMode(app.CORNER);
			app.image(fondo, 0, 0);
			app.image(puntaje, 0, 0);
			if (jugador != null) {
				jugador.pintar();
			}
			app.textFont(fuente);
			app.fill(255);
			app.text(time, 1800, 60);
			tiempoStart();

			if (jugador != null) {
				jugador.pintarBalas();
			}

			pintarElementos();
			if ((app.millis() / 1000) > 6)
				agregarElemento();

			for (int i = 0; i < enemigos.size(); i++) {
				enemigos.get(i).pintar();
				enemigos.get(i).pintarBalas();
			}
			remover();
			calculos();
		}
	}

	/*
	 * Condiciones para ganar o perder.
	 */
	public void calculos() {
		/*
		 * Los siguientes calculos solo se realizan estando en pantalla de juego (2)
		 */
		if (pantallas == 2) {
			/*
			 * Aunque no se haya acabado el tiempo, el jugador ganará cuando haya matado a
			 * todos los enemigos.
			 */
			if (enemigos.size() < 1 && time != 0) {
				pantallas = 3;
				for (int j = 0; j < enemigos.size(); j++) {
					enemigos.get(j).interrupt();
				}
				enemigos.clear();
				jugador.interrupt();
				jugador = null;
				fondoJuego.stop();
				fondoGanar.play();
			}

			int contTemp = 0;
			for (int j = 0; j < enemigos.size(); j++) {
				contTemp += enemigos.get(j).getTamanoArmas();
			}
			/*
			 * Cuando el tiempo se acaba y el jugador tiene menos nivel que el enemigo,
			 * pierde.
			 */
			if (time <= 0) {
				if (jugador.getTamanoArmas() < contTemp) {
					pantallas = 4;
					for (int j = 0; j < enemigos.size(); j++) {
						enemigos.get(j).interrupt();
					}
					enemigos.clear();
					jugador.interrupt();
					jugador = null;
					fondoJuego.stop();
					fondoPerder.play();
					
				} else {
					/*
					 * En caso contrario, gana.
					 */
					pantallas = 3;
					for (int j = 0; j < enemigos.size(); j++) {
						enemigos.get(j).interrupt();
					}
					enemigos.clear();
					jugador.interrupt();
					jugador = null;
					fondoJuego.stop();
					fondoGanar.play();
				}
			}
		}
	}

	/*
	 * Se pintan todos los elementos del ArrayList de elementos.
	 */
	public void pintarElementos() {
		for (int i = 0; i < elementos.size(); i++) {
			elementos.get(i).pintar();
		}
	}

	/*
	 * Este metodo gestiona el movimiento del jugador con las teclas direccionales.
	 */
	public void teclado() {
		if (jugador != null) {
			if (app.keyCode == app.RIGHT) {
				jugador.setPos(new PVector(jugador.getPos().x + jugador.getVel().x, jugador.getPos().y));
			} else if (app.keyCode == app.LEFT) {
				jugador.setPos(new PVector(jugador.getPos().x - jugador.getVel().x, jugador.getPos().y));
			} else if (app.keyCode == app.DOWN) {
				jugador.setPos(new PVector(jugador.getPos().x, jugador.getPos().y + jugador.getVel().y));
			} else if (app.keyCode == app.UP) {
				jugador.setPos(new PVector(jugador.getPos().x, jugador.getPos().y - jugador.getVel().y));
			}
		}
	}

	/*
	 * Para remover del ArrayList de Elementos aquellos elementos para los que el
	 * boolean recogido sea true, para que dejen de existir y no se puedan recoger
	 * de nuevo.
	 */
	public void remover() {
		if (pantallas == 2) {
			for (int i = 0; i < elementos.size(); i++) {
				Elemento e = elementos.get(i);
				if (e.getRecogido()) {
					elementos.remove(e);
				}
			}

			for (int i = 0; i < enemigos.size(); i++) {
				Enemigo e = enemigos.get(i);
				if (e.isVivo() == false) {
					enemigos.remove(e);
				}
			}
		}
	}

	/*
	 * Con este metodo se agregan mas elementos al ArrayList elementos de manera
	 * aletoria cada 2 segundos.
	 */
	public void agregarElemento() {
		if (pantallas == 2) {
			int time = app.millis() / 1000;
			int frecuencia = 2;

			if (time % frecuencia == 0 && counter == 0) {
				int tipo = (int) app.random(0, 5);
				/*
				 * Para que no se creen siempre los mismos elementos y asegurar que se creen
				 * siempre mas pistolas, pues de estas depende el nivel y son mas importantes.
				 */
				switch (tipo) {
				case 0:
					elementos.add(new Chocolate(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					break;

				case 1:
					elementos.add(new Bandera(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					break;

				case 2:
					elementos.add(new Granada(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					break;

				case 3:
					elementos.add(new Jeringa(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					break;

				case 4:
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
					elementos.add(new Pistola(app, app.random(340, 1720), app.random(160, 1020)));
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
	}

	/*
	 * Se vuelven a inicializar las variables para reiniciar el juego.
	 */
	public void resetJuego() {
		enemigos = new ArrayList<Enemigo>();
		counter = 0;
		pantallas = 0;
		starTime = false;
		iniciarJuego = false;
		time = 45;
		fondoJuego.stop();
	}

	/*
	 * Se crean las zonas sensibles de los botones y se gestiona el paso de una
	 * pantalla a otra.
	 */
	public void click() {
		/*
		 * Click en boton JUGAR en la pantalla de INICIO, lleva a la pantalla de juego.
		 */
		if (pantallas == 0 && app.mouseX > 754 && app.mouseX < 1178 && app.mouseY > 702 && app.mouseY < 802) {
			pantallas = 2;
			startGame();
			jugador.start();
			fondoJuego.play();
			fondoInicioEInstrucc.stop();
			for (Enemigo e : enemigos) {
				e.start();
			}
		}
		/*
		 * Click en boton INSTRUCCIONES en la pantalla de INICIO, lleva a la pantalla de
		 * INSTRUCCIONES.
		 */
		if (pantallas == 0 && app.mouseX > 754 && app.mouseX < 1178 && app.mouseY > 816 && app.mouseY < 914) {
			pantallas = 1;
			
		}
		/*
		 * Click en boton JUGAR en la pantalla de INSTRUCCIONES, lleva a la pantalla de
		 * juego.
		 */
		if (pantallas == 1 && app.mouseX > 1250 && app.mouseX < 1759 && app.mouseY > 870 && app.mouseY < 986) {
			pantallas = 2;
			startGame();
			jugador.start();
			fondoJuego.play();
			fondoInicioEInstrucc.stop();
			for (Enemigo e : enemigos) {
				e.start();
			}
		}

		/*
		 * Click en boton VOLVER A JUGAR, lleva a la pantalla INICIO, desde la pantalla
		 * de PERDER o la de GANAR y se reinicia todo.
		 */
		if (pantallas == 3 && app.mouseX > 1200 && app.mouseX < 1600 && app.mouseY > 800 && app.mouseY < 980) {
			pantallas = 0;
			resetJuego();
			fondoInicioEInstrucc.play();
		}
		if (pantallas == 4 && app.mouseX > 1200 && app.mouseX < 1600 && app.mouseY > 800 && app.mouseY < 1000) {
			pantallas = 0;
			resetJuego();
			fondoInicioEInstrucc.play();
		}

		/*
		 * Aqui comienza el hilo del jugador y permite que este se mueva con la accion
		 * del click izquierdo. Haciendo un set a su posicion y conviertiendola en la
		 * posicion del mouse.
		 */
		if (app.mouseButton == PConstants.LEFT) {
			if (jugador != null && jugador.getState().name().equals("NEW") && pantallas == 2) {

			}
			if (jugador != null && jugador.movimientoTeclado == false)
				jugador.setFinal(app.mouseX, app.mouseY);
		}
		if (jugador != null) {
			jugador.atacar();
		}
	}

	public boolean colision(float x, float y) {
		if (PApplet.dist(x, y, jugador.getPos().x, jugador.getPos().y) < 20) {
			jugador.restarVida();
			return true;
		}
		return false;
	}

	/*
	 * Se establece el tiempo del juego: 45 segs.
	 */
	public void tiempoStart() {
		if (starTime == false) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					time = 45;
					while (true) {
						try {
							time--;
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					}
				}
			}).start();
			starTime = true;
		}
	}

	public Main getApp() {
		return app;
	}

	public ArrayList<Elemento> getElementos() {
		return elementos;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public int getPantallas() {
		return pantallas;
	}

	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}

	public PImage getPerdiste() {
		return perdiste;
	}

	public void setPerdiste(PImage perdiste) {
		this.perdiste = perdiste;
	}

	public void setPantallas(int pantallas) {
		this.pantallas = pantallas;
	}
}
