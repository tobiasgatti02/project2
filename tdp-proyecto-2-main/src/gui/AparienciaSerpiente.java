package gui;

import bloques.Bloque;
import consumibles.powerups.*;
import logica_principal.Serpiente;


public class AparienciaSerpiente {
    protected final Serpiente miSerpiente;
    protected int aparienciaActual;
    protected String[] cabezas;
    protected String[] cuerpos;
    protected String[] colas;

    public AparienciaSerpiente(Serpiente s){
    	miSerpiente = s;
    	cabezas = new String[4];
    	cuerpos = new String[4];
    	colas = new String[4];
    	cabezas[0] = "/imagenes/apariencias/normal/cabeza.png";
    	cuerpos[0] = "/imagenes/apariencias/normal/cuerpo.png";
    	colas[0] = "/imagenes/apariencias/normal/cola.png";
    	
    	cabezas[1] = "/imagenes/apariencias/hamburguesa/cabeza.png";
    	cuerpos[1] = "/imagenes/apariencias/hamburguesa/cuerpo.png";
    	colas[1] = "/imagenes/apariencias/hamburguesa/cola.png";

    	cabezas[2] = "/imagenes/apariencias/blanco_negro/cabeza.png";
		cuerpos[2] = "/imagenes/apariencias/blanco_negro/cuerpo.png";
		colas[2] = "/imagenes/apariencias/blanco_negro/cola.png";
    	
    	cabezas[3] = "/imagenes/apariencias/cisne/cabeza.png";
		cuerpos[3] = "/imagenes/apariencias/cisne/cuerpo.png";
		colas[3] = "/imagenes/apariencias/cisne/cola.png";

		redibujarTodo();

    }
    @SuppressWarnings("unused")
	public void serAfectado(Hamburguesa p){
    	aparienciaActual = 1;
    	redibujarTodo();
    }

	@SuppressWarnings("unused")
	public void serAfectado(LataPintura p){
		aparienciaActual = 2;
    	redibujarTodo();
    }

	@SuppressWarnings("unused")
	public void serAfectado(Cisne p){
		aparienciaActual = 3;
    	redibujarTodo();
    }
    protected void redibujarTodo(){
		synchronized (miSerpiente) {
			for (Bloque aDibujar : miSerpiente.getBloques()) {
				redibujar(aDibujar);
			}
		}
    }

    public void redibujar(Bloque b){
    	if (b == miSerpiente.getBloques().get(0))
    		b.setFrente(cabezas[aparienciaActual], rotacionCabeza());
    	else if (b == miSerpiente.getBloques().get(miSerpiente.getBloques().size()-1))
    		b.setFrente(colas[aparienciaActual], 0);
    	else
    		b.setFrente(cuerpos[aparienciaActual], 0);
    }
	protected int rotacionCabeza(){
		return switch (miSerpiente.getDireccion()){
			case DERECHA -> 0;
			case ABAJO -> 90;
			case IZQUIERDA -> 180;
			case ARRIBA -> 270;
		};
	}
}
