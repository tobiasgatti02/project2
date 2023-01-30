package logica_principal;

import java.awt.*;
import javax.swing.JLabel;
import bloques.*;
import consumibles.*;
import gui.*;
import niveles.*;

public class Tablero {
	
    protected Bloque[][] grilla;
    protected int cantFilas;
    protected int cantColumnas;
	protected FabricaImagenesBloques fabrica;
    public Tablero(Nivel n, Ventana v, FabricaImagenesBloques fabrica, int cantFilas, int cantColumnas){
		this.fabrica = fabrica;
		this.cantFilas = cantFilas;
		this.cantColumnas = cantColumnas;

    	grilla = new Bloque[cantFilas][cantColumnas];

		FabricaImagenesBloques fabricaImagenes = fabrica;

    	generarParedes(n);
		v.getPanelTablero().setLayout(new GridLayout(cantFilas, cantColumnas, 0, 0));
    	
    	for(int fila = 0;fila<cantFilas;fila++) {
        	for(int columna = 0;columna<cantColumnas;columna++) {
        		if (grilla[fila][columna]==null) {
        			String imagen;
        		
        			if((fila+columna) % 2 == 0) {
        				imagen = fabricaImagenes.getImagenTransitablePar();
        			}
        			else {
						imagen = fabricaImagenes.getImagenTransitableImpar();
        			}
        			grilla[fila][columna] = new BloqueTransitable(imagen,new Posicion(fila,columna));
        		}
        		agregar_a_layout(grilla[fila][columna].getGrafico(),v);
        	}
    	}
    }
    
    private void generarParedes(Nivel nivel) {
    	for(Posicion p:nivel.getParedes())
    		grilla[p.getFila()][p.getColumna()] = crearPared(p);
    	
    	
		for(int columna = 0; columna < cantColumnas;columna++) {
    		if (grilla[0][columna]==null && grilla[cantColumnas-1][columna]==null) {
    			grilla[0][columna] =  crearPared(new Posicion(0,columna));
    			grilla[cantFilas-1][columna] = crearPared(new Posicion(cantColumnas-1,columna));
    		}
    	}
    	
    	for(int fila = 0; fila < grilla.length;fila++) {
    		if (grilla[fila][0]==null && grilla[fila][cantColumnas-1]==null) {
    			grilla[fila][0] = crearPared(new Posicion(fila,0));
    			grilla[fila][cantColumnas-1] = crearPared(new Posicion(fila,cantColumnas-1));
    		}
    	}

    }

	protected Pared crearPared(Posicion pos) {
		return new Pared(fabrica.getImagenPared(),pos);
	}
    


    private void agregar_a_layout(JLabel label, Ventana v) {
        v.getPanelTablero().add(label);
    }

    public Bloque getAdyacente(Bloque B, Direccion d){
		Bloque bloque = null;
		Posicion pos = B.getPosicion();
		switch(d) {
			case IZQUIERDA:
			  if (pos.getColumna()!=0) {
				  bloque = grilla[pos.getFila()][pos.getColumna()-1];
			  }
		    break;

			case DERECHA:
				if (pos.getColumna() < cantColumnas - 1) {
					bloque = grilla[pos.getFila()][pos.getColumna()+1];
				}
				break;
			case ABAJO:
			  if (pos.getFila() < cantFilas - 1) {
				  bloque = grilla[pos.getFila()+1][pos.getColumna()];
			  }
		  	break;

			case ARRIBA:
			  if (pos.getFila()!=0) {
				  bloque = grilla[pos.getFila()-1][pos.getColumna()];
			  }
			  break;
		  }
		return bloque;
    }
    
    public void agregarConsumible(Consumible c){
    	Posicion posConsumible = c.getPosicion();
    	grilla[posConsumible.getFila()][posConsumible.getColumna()].setConsumible(c);
    }

    public void destruir(){
    	Container cont = grilla[0][0].getGrafico().getParent();
		for(int fila = 0;fila<grilla.length;fila++) {
        	for(int columna = 0;columna<grilla[0].length;columna++) {
				cont.remove(grilla[fila][columna].getGrafico());
        	}
        }
		cont.revalidate();
		cont.repaint();
    }

	public Bloque getBloque(int fila, int columna){
		//Requiere fila y columna válidas
		return grilla[fila][columna];
	}
      
}
   
