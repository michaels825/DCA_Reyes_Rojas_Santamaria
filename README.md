# Taller 2 DCA - Michael Rojas - Nicolas Santamaria - Andrea Reyes 

## Clase Logica
Es la clase donde me hace el procesamiento general de programa.
### Metodos
* Logica(PApplet) : este método es el encargado de ejecutar el programa.
* PintarElementos():void : este metodo es el encargado de pintar los elementos creados por la clase Figura.
* Click():void : este metodo es el encargado de ejecutar las interacciones cuando el usuario interactua con la interfaz.
* Teclado():void : este método es el encargado del movimiento del personaje. 
***
## Clase Personaje 
Esta es la clase padre del personaje controlado por el jugador y los enemigos que van a ser autómatas. Esta clase va a extender de Thread.
Esta clase también va a ser la responsable de tener un ArrayList de enemigos. 
### Metodos
Personaje(app,x,y): recibe el PApplet y la posición en x y en y de cada personaje.
Pintar():void:  este método se ejecutara cuando se pinten los personajes.
Mover():void: este metodo se encarga del movimiento de los personajes.
Atacar():void: este metodo validara si el personaje puede o no atacar a su enemigo y si el enemigo puede o no atacar al personaje 
ValidarArma():void: el personaje valida cuando esta a una distancia x de un arma y la pueda tomar. 
ValidarChocolate():void: el personaje valida cuando esta a una distancia x de un elemento y lo pueda tomar.
ValidarJeringa():void: el personaje valida cuando esta a una distancia x de un elemento y lo pueda tomar. 
ValidarGranada():void: el personaje valida cuando esta a una distancia x de un elemento y lo pueda tomar. 
ValidarBandera():void: el personaje valida cuando esta a una distancia x de un elemento y lo pueda tomar. 
run():void: este metodo permite que comience a ejecutarse el hilo, aqui se implmentara el movimiento.
***
## Jugador
Esta es la clase de el personaje el cual va a ser controlado por el usuario.
### Metodos
Jugador(app,x,y):recibe el PApplet y la posición en x y en y de cada personaje.
Pintar():void:  este método es el encargado de proporcionar informacion para pintar el jugador. 
***
## Enemigo
Esta es la clase de los enemigos los cuales van a ser automatas.
### Metodos
Enemigo(app,x,y):recibe el PApplet y la posición en x y en y de cada personaje.
Pintar():void:  este método es el encargado de proporcionar informacion para pintar el enemigo. 
***
### Elemento 
Esta es la clase padre de todos los elementos que apareceran en la interfaz grafica. 
### Metodos
Elemento(app,x,y): recibe el PApplet y la posición en x y en y de cada elemento.
Pintar():void:  este método se ejecutara cuando se pinten los Elemento.
***
## Pistola
esta clase es la responsable de crear objetos que le anaden nivel al personaje. 
### Metodos
pistola(): este es el constructor de la clase, es el encargado de crear los elementos de tipo Pistola.
pintar():void: Este metodo es el encargado de pintar los elementos. 
***
## Bandera
esta clase es la responsable de crear modificadores para el personaje.
### Metodos
Bandera(): este es el constructor de la clase, es el encargado de crear los elementos de tipo Bandera.
pintar():void: Este metodo es el encargado de pintar los elementos. 
***
## Chocolate
esta clase es la responsable de crear modificadores para el personaje.
### Metodos
Chocolate(): este es el constructor de la clase, es el encargado de crear los elementos de tipo Chocolate.
pintar():void: Este metodo es el encargado de pintar los elementos. 
***
## Granada
esta clase es la responsable de crear modificadores para el personaje.
### Metodos
Granada(): este es el constructor de la clase, es el encargado de crear los elementos de tipo Granada.
pintar():void: Este metodo es el encargado de pintar los elementos. 
***
## Jeringa
esta clase es la responsable de crear modificadores para el personaje.
### Metodos
Jeringa(): este es el constructor de la clase, es el encargado de crear los elementos de tipo Jeringa.
pintar():void: Este metodo es el encargado de pintar los elementos. 

