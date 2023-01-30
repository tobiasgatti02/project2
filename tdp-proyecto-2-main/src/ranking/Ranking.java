package ranking;

import gui.GraficaRanking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.*;

public class Ranking implements Serializable{
	protected static List<EntradaRanking> lista;
	protected static final int MAX_ENTRADAS = 10;
	private static final long serialVersionUID = 1L;
	private File archivo;
	public Ranking() {
		archivo = new File("Ranking.snake");
		if(archivo.exists()) {
			try {
				lista = leerLista();
			} catch (IOException | ClassNotFoundException e) {
				lista = new ArrayList<EntradaRanking>();
			}
		}
		else {
			lista = new ArrayList<EntradaRanking>();
		}
	}
	
	private ArrayList<EntradaRanking> leerLista() throws IOException, ClassNotFoundException {
	    FileInputStream file = new FileInputStream(archivo);
	    ObjectInputStream in = new ObjectInputStream(file);
	    ArrayList<EntradaRanking> lista = (ArrayList<EntradaRanking>) in.readObject();
	    in.close();
	    file.close();
	    return lista;
	}
	private void guardarLista() throws IOException{
		FileOutputStream file = new FileOutputStream(archivo);
	    ObjectOutputStream out = new ObjectOutputStream(file);
	    out.writeObject(lista);
	    out.close();
	    file.close();
	}
    public JPanel graficar(){ 
    	return new GraficaRanking(lista);
    }
    
    
    
    public void agregarEntrada(String n, int t, int p){
        EntradaRanking entrada = new EntradaRanking(n,t,p);
        EntradaRanking entradaActual;
		boolean insertado = false;
        if(lista.size()==0) {
        	lista.add(0, entrada);
			insertado = true;
        }
        else if(entrada.compareTo(lista.get(0)) <= 0) {
			lista.add(0, entrada);
			insertado = true;
		}
		else{
			//Buscar posición de inserción
			ListIterator<EntradaRanking> it = lista.listIterator();
			boolean encontre = false;
			while(it.hasNext() && !encontre) {
				entradaActual = it.next();
				if(entrada.compareTo(entradaActual)<=0) {
					encontre = true;
				}
			}
			if(encontre) {
				it.previous();
				it.add(entrada);
				insertado = true;
				if (lista.size() > MAX_ENTRADAS) {
					lista.remove(MAX_ENTRADAS - 1);
				}
			}
			else if(lista.size()<MAX_ENTRADAS) {
				lista.add(entrada);
				insertado = true;
			}
		}
		if (insertado)
			try {
				guardarLista();
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
}
