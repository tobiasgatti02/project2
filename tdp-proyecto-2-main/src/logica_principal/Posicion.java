package logica_principal;

public class Posicion {
    protected int fila;
    protected int columna;

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
    
    @Override
    public int hashCode() {
    	return (fila + " " + columna).hashCode();
    }
    
    @Override
    public boolean equals(Object pos) {
    	Posicion p = (Posicion) pos;
    	return p != null && fila == p.getFila() && columna == p.getColumna();
    }
}
