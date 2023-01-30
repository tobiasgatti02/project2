package niveles;

// estructuras de datos
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.ArrayDeque;

import consumibles.Consumible;

import logica_principal.Posicion;

public class Nivel {
	protected Iterable<Posicion> paredes;
	protected Queue<Consumible> consumibles;
	
    public Nivel(Iterable<Posicion> paredes, List<Consumible> consumibles){
    	this.paredes = paredes;
        this.consumibles = new ArrayDeque<Consumible>();
        
        // desordenar al azar la lista de consumibles recibida
    	Collections.shuffle(consumibles);
        
    	// insertar los consumibles en la cola de consumibles
        for (Consumible consumible : consumibles) {
        	this.consumibles.add(consumible);
        }
    }
    
    public boolean quedanConsumibles(){
        return !consumibles.isEmpty();
    }
    
    // si no hay proximo consumible retorna null
    public Consumible getProximoConsumible(){
        return consumibles.poll();
    }
    
    public Iterable<Posicion> getParedes(){
        return paredes;
    }
}