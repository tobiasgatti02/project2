package logica_principal;

import bloques.Bloque;
import bloques.BloqueTransitable;
import bloques.Pared;
import consumibles.Consumible;
import consumibles.alimentos.Alimento;
import consumibles.powerups.Hamburguesa;
import consumibles.powerups.LataPintura;
import consumibles.powerups.Cisne;
import gui.AparienciaSerpiente;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Serpiente implements Runnable {
    protected int porCrecer;
    protected Direccion miDireccion;
    protected Direccion proximaDireccion; // Evita que la serpiente se mate en 2 giros
    protected AparienciaSerpiente miApariencia;
    protected List<Bloque> misBloques;
    protected Juego miJuego;
    protected Tablero miTablero;
    protected Thread miHilo;

    AtomicBoolean parar;
    private static final int tiempoDeEspera = 200;

    public Serpiente(Juego j, Direccion dir, Tablero t, Bloque cabeza, Bloque cuerpo, Bloque cola) {
        parar = new AtomicBoolean();
        miJuego = j;
        miDireccion = dir;
        proximaDireccion = dir;
        miTablero = t;
        misBloques = new LinkedList<Bloque>();

        misBloques.add(cabeza);
        misBloques.add(cuerpo);
        misBloques.add(cola);
        cabeza.ocupar(this);
        cuerpo.ocupar(this);
        cola.ocupar(this);

        miApariencia = new AparienciaSerpiente(this);
        miHilo = new Thread(this);
        miHilo.start();
    }

    @Override
    public void run() {	
    	try {
    		// tiempo de gracia
            Thread.sleep(tiempoDeEspera*5);
       
	        while (!parar.get()) {
	            avanzar();
	            Thread.sleep(tiempoDeEspera);
	        }
    	} catch (InterruptedException e) {
	     	e.printStackTrace();
    	}
    }

    public synchronized void girar(Direccion dir) {
        boolean esPosible = true;
        switch (dir) {
            case ARRIBA -> {
                if (miDireccion == Direccion.ABAJO)
                    esPosible = false;
            }
            case ABAJO -> {
                if (miDireccion == Direccion.ARRIBA)
                    esPosible = false;
            }
            case DERECHA -> {
                if (miDireccion == Direccion.IZQUIERDA)
                    esPosible = false;
            }
            case IZQUIERDA -> {
                if (miDireccion == Direccion.DERECHA)
                    esPosible = false;
            }
        }
        if (esPosible)
            proximaDireccion = dir;
    }

    public void frenar() {
        parar.set(true);
    }

    public List<Bloque> getBloques() {
        return misBloques;
    }

    public Direccion getDireccion() {
        return miDireccion;
    }

    /*Si la pared contiene una serpiente, muere.De lo contrario, la ocupa y come su consumible en caso de existir.*/
    public void visitar(BloqueTransitable b) {
        boolean colision = false;
        for (Bloque bloque : misBloques) {
            if (bloque.equals(b)) {
                morir();
                colision = true;
                break;
            }
        }
        if (!colision) {
            Bloque cola = misBloques.get(misBloques.size() - 1);
            boolean crecio = false;
            synchronized (this) {
                if (porCrecer == 0) {
                    cola.desocupar();
                    misBloques.remove(misBloques.size() - 1);
                } else {
                    crecio = true;
                    porCrecer--;
                }
                    misBloques.add(0, b);
            }
            b.ocupar(this);

            Consumible consumible = b.getConsumible();
            if (consumible != null) {
                consumible.afectar(this);
                b.setConsumible(null);
            }

            miApariencia.redibujar(misBloques.get(0));
            miApariencia.redibujar(misBloques.get(1));
            if (!crecio)
                miApariencia.redibujar(misBloques.get(misBloques.size() - 1));
        }
    }


    @SuppressWarnings("unused")
    public void visitar(Pared p) {

        morir();
    }

    public void consumir(Hamburguesa p) {
        miApariencia.serAfectado(p);
        crecer(p.getCrecimiento());
        miJuego.aumentarPuntaje(p.getPuntaje());
        miJuego.reponerConsumible();
    }

    public void consumir(LataPintura p) {
        miApariencia.serAfectado(p);
        crecer(p.getCrecimiento());
        miJuego.aumentarPuntaje(p.getPuntaje());
        miJuego.reponerConsumible();

    }

    public void consumir(Cisne p) {
        miApariencia.serAfectado(p);
        crecer(p.getCrecimiento());
        miJuego.aumentarPuntaje(p.getPuntaje());
        miJuego.reponerConsumible();

    }

    public void consumir(Alimento a) {
        crecer(a.getCrecimiento());
        miJuego.aumentarPuntaje(a.getPuntaje());
        miJuego.reponerConsumible();
    }

    protected synchronized void avanzar() {
        miDireccion = proximaDireccion;
        miTablero.getAdyacente(misBloques.get(0), miDireccion).recibir(this);
    }

    protected void crecer(int cant) {
        porCrecer = cant;
    }

    protected void morir() {
        miJuego.terminar();
    }
}
