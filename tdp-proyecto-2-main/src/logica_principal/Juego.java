package logica_principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

import bloques.Bloque;
import gui.FabricaImagenesBloques;
import gui.FabricaImagenesBloquesNivel1;
import gui.FabricaImagenesBloquesNivel2;
import gui.FabricaImagenesBloquesNivel3;
import gui.FabricaImagenesBloquesNivel4;
import gui.FabricaImagenesBloquesNivel5;
import gui.Ventana;
import niveles.GeneradorNivel;
import niveles.Nivel;

public class Juego {

	public static final int CANT_FILAS = 22;
	public static final int CANT_COLUMNAS = 22;
	protected Ventana miVentana;
	protected Serpiente miSerpiente;
	protected Tablero miTablero;
	protected Nivel nivelActual;
	protected int puntaje;
	protected int tiempo;
	protected int numeroNivel;
	protected Timer timer;
	protected static final FabricaImagenesBloques[] fabricas = new FabricaImagenesBloques[] {new FabricaImagenesBloquesNivel1(), new FabricaImagenesBloquesNivel2(), new FabricaImagenesBloquesNivel3(), new FabricaImagenesBloquesNivel4(), new FabricaImagenesBloquesNivel5()};

	public Juego(Ventana v){
		tiempo = 0;
		puntaje = 0;
		numeroNivel = 1;
		miVentana = v;

		nivelActual = GeneradorNivel.getInstance().obtenerNivel(numeroNivel);
		miTablero = new Tablero(nivelActual, miVentana, fabricas[numeroNivel - 1], CANT_FILAS, CANT_COLUMNAS);

		timer = crearTimer();
		timer.start();

		miSerpiente = crearSerpiente();
		reponerConsumible();
	}
    public void girarSerpiente(Direccion dir){
    	miSerpiente.girar(dir);
    }
    public void terminar(){
		timer.stop();
		miSerpiente.frenar();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				miTablero.destruir();
				miTablero = null;
				miVentana.terminarJuego();
			}
		});
    }
    protected void aumentarTiempo(){
        tiempo++;
		miVentana.actualizarTiempo(tiempo);
    }
    public void aumentarPuntaje(int incrementoPuntaje){
        puntaje += incrementoPuntaje;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				miVentana.actualizarPuntaje(puntaje);
			}
		});
    }
    public void reponerConsumible(){
        if (nivelActual.quedanConsumibles())
        	miTablero.agregarConsumible(nivelActual.getProximoConsumible());
        else
        	terminarNivel();
    }
    protected void terminarNivel(){
    	numeroNivel++;
        if (numeroNivel <= 5) {
			miSerpiente.frenar();
			nivelActual = GeneradorNivel.getInstance().obtenerNivel(numeroNivel);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					miVentana.actualizarNivel(numeroNivel);
					miTablero.destruir();
					miTablero = null;

					miTablero = new Tablero(nivelActual, miVentana, fabricas[numeroNivel - 1], CANT_FILAS, CANT_COLUMNAS);
					miSerpiente = crearSerpiente();
					reponerConsumible();
				}
			});
		}
		else
        	terminar();
    }
    public int getTiempo(){
    	return tiempo;
    }
    public int getPuntaje(){
        return puntaje;
    }
    private Timer crearTimer() {
    	ActionListener tarea = new ActionListener() {
    		public void actionPerformed(ActionEvent tiempo) {
    			aumentarTiempo();
    		}
    	};
    	timer = new Timer(1000,tarea);
    	return timer;
    }
    private boolean esPared(int fila, int columna) {
    	boolean pared = false;
		if(fila == 0 || fila == CANT_FILAS - 1|| columna == 0 || columna == CANT_COLUMNAS - 1)
			pared = true;
		else{
			for (Posicion p : nivelActual.getParedes())
				if (p.getFila() == fila && p.getColumna() == columna) {
					pared = true;
					break;
				}
			}
    	return pared;
    }

	//Requiere que b no sea una pared
	private boolean cercaParedInterna(Bloque b, Direccion d, int rango){
		Bloque aux = b;
		boolean cerca = false;
		for(int i = 0; i < rango && !cerca; i++){
			aux = miTablero.getAdyacente(aux, d);
			cerca = esPared(aux.getPosicion().getFila(), aux.getPosicion().getColumna());
		}
		return cerca;
	}

	//Si es posible, rellena bloques con los bloques restantes que debe tener inicialmente la serpiente, comenzando por b, y en dirección d.
	private boolean buscarPosicionesSerpiente(Bloque b, Direccion d, int restantes, List<Bloque> bloques) {
		boolean posible;
		if(restantes == 0)
			posible = true;
		else if (bloques.contains(b) || esPared(b.getPosicion().getFila(), b.getPosicion().getColumna()))
			posible = false;
		else {
			bloques.add(b);
			Direccion[] opciones = obtenerOpcionesSerpiente(d);

			posible = false;
			for (int i = 0; i < opciones.length && !posible; i++) {
				Direccion dir = opciones[i];
				posible = buscarPosicionesSerpiente(miTablero.getAdyacente(b, dir), dir, restantes - 1, bloques);
			}
			if (!posible) //Backtracking
				bloques.remove(b);
		}
		return posible;
	}

	//Retorna las direcciones en las que se debe probar insertar las piezas de la serpiente inicial (en orden).
	private Direccion[] obtenerOpcionesSerpiente(Direccion d){
		Direccion [] opciones;
		switch (d){
			case ARRIBA -> opciones = new Direccion[]{Direccion.ABAJO, Direccion.IZQUIERDA, Direccion.DERECHA};
			case ABAJO -> opciones = new Direccion[]{Direccion.ARRIBA, Direccion.IZQUIERDA, Direccion.DERECHA};
			case IZQUIERDA -> opciones = new Direccion[]{Direccion.DERECHA, Direccion.ARRIBA, Direccion.ABAJO};
			case DERECHA -> opciones = new Direccion[]{Direccion.IZQUIERDA, Direccion.ARRIBA, Direccion.ABAJO};
			default -> opciones = null;
		}
		return opciones;
	}
    private Serpiente crearSerpiente() {
		Random rand = new Random();
		final int bloquesLibres = 5;
    	Serpiente aRetornar;

    	boolean posValida = false;
		int nDir = rand.nextInt(4);
		Direccion dir;
		switch(nDir){
			case 0-> dir = Direccion.ARRIBA;
			case 1 -> dir = Direccion.ABAJO;
			case 2 -> dir = Direccion.IZQUIERDA;
			case 3 -> dir = Direccion.DERECHA;
			default -> dir = null;
		}

		List<Bloque> bloques = new ArrayList<Bloque>();
		while (!posValida) {
			final int min = bloquesLibres + 1;
			final int maxF = CANT_FILAS - bloquesLibres;
			final int maxC = CANT_COLUMNAS - bloquesLibres;
			int f = rand.nextInt(maxF + 1 - min) + min;
			int c = rand.nextInt(maxC + 1 - min) + min;
			Bloque b = miTablero.getBloque(f, c);
			posValida = !cercaParedInterna(b, dir, bloquesLibres) && buscarPosicionesSerpiente(b, dir, 3, bloques);
    	}
    	aRetornar = new Serpiente(this, dir, miTablero, bloques.get(0), bloques.get(1), bloques.get(2));
    	return aRetornar;
    }
}
    
