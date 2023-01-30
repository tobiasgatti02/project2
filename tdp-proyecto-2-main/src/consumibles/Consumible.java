package consumibles;

import logica_principal.Posicion;
import logica_principal.Serpiente;

public abstract class Consumible {
	protected Posicion posicion;
	protected int crecimiento;
	protected int puntaje;
	protected String imagen;
	
    protected Consumible(Posicion pos, int crec, int punt, String img) {
        posicion = pos;
        crecimiento = crec;
        puntaje = punt;
        imagen = img;
	}
    
    public abstract void afectar(Serpiente s);
    
    public int getCrecimiento() {
        return crecimiento;
    }
    
    public int getPuntaje() {
    	 return puntaje;
    }
    
    public Posicion getPosicion() {
    	return posicion;
    }

    public String getImagen() {
        return imagen;
    }
}
