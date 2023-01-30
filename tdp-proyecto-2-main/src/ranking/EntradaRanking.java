package ranking;

import java.io.Serializable;

public class EntradaRanking implements Comparable<EntradaRanking>, Serializable {
    protected String nombre;
    protected int tiempo;
    protected int puntaje;
    private static final long serialVersionUID = 1L;

    public EntradaRanking(String n, int t, int p){
        nombre = n;
        tiempo = t;
        puntaje = p;
    }
    public String getNombre(){
        return nombre;
    }
    public int getTiempo(){
        return tiempo;
    }
    public int getPuntaje(){
        return puntaje;
    }

    public int compareTo(EntradaRanking e) {
    	int retorno;
    	if((getPuntaje() < e.getPuntaje()) || (getPuntaje() == e.getPuntaje() && getTiempo() > e.getTiempo()))
    		retorno = 1;
    	else if(getPuntaje() == e.getPuntaje() && getTiempo() == e.getTiempo())
    		retorno = 0;
    	else
    		retorno = -1;    	
        return retorno;
    }
}
