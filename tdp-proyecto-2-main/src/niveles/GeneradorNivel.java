package niveles;

// manejo de archivos
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import excepciones.ArchivoInvalidoException;
import excepciones.NivelInvalidoException;

// consumibles
import consumibles.Consumible;
import consumibles.alimentos.*;
import consumibles.powerups.*;
import logica_principal.Posicion;

// estructuras de datos
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet; 

public class GeneradorNivel {
    protected String[] rutas;
    protected static GeneradorNivel instance;
	
    protected GeneradorNivel() {
    	// ruta de la carpeta donde se guardan los niveles
    	String carpetaNiveles = "/niveles/"; 
    	
        rutas = new String[5];
        rutas[0] = carpetaNiveles + "nivel1.txt";
        rutas[1] = carpetaNiveles + "nivel2.txt";
        rutas[2] = carpetaNiveles + "nivel3.txt";
        rutas[3] = carpetaNiveles + "nivel4.txt";
        rutas[4] = carpetaNiveles + "nivel5.txt";
    }
    
    public static GeneradorNivel getInstance() {	
        if (instance == null) instance = new GeneradorNivel();
    	return instance;
    }
    
    // la posición debe estar dentro de una grilla de 20x20 comenzado desde fila y columna 1
    protected void checkPosicion(Posicion pos) {
    	if (pos.getFila() < 1 || pos.getFila() > 20 || pos.getColumna() < 1 || pos.getColumna() > 20)
    		throw new ArchivoInvalidoException("El archivo del nivel tiene posiciones fuera de la matriz");
    }
    
    // recibe el número del nivel comenzado desde 1
    public Nivel obtenerNivel(int n) {
        Nivel toReturn = null;
        
        List<Consumible> consumibles = new ArrayList<Consumible>();
        Set<Posicion> paredes = new HashSet<Posicion>();
        
        // para chequear que los consumibles no se superpongan
        Set<Posicion> consumiblesPosiciones = new HashSet<Posicion>();

        if (n > 0 && n <= rutas.length) {
        	try {
        		List<String> lineas = new ArrayList<String>();
        		
        		// leer el archivo y guadar las lineas en una lista
        		InputStream in = getClass().getResourceAsStream(rutas[n-1]);
        		BufferedReader br = new BufferedReader(new InputStreamReader(in));
        		
        		String linea = br.readLine();
        		while (linea != null) {
        			lineas.add(linea);
        			linea = br.readLine();
        		}
        		
        		br.close();
				
        		// chequear la cantidad de lineas
				if (lineas.size() != 3) throw new ArchivoInvalidoException("El archivo del nivel " + n + " debe tener exactamente tres líneas.");
				
				String[] tuplas;
				
				// cargar paredes (x, y)
				tuplas = lineas.get(0).split(";");
				
				for (String tupla : tuplas) {
					String[] componentes = tupla.split(",");				
					if (componentes.length != 2) 
						throw new ArchivoInvalidoException("El archivo del nivel " + n + " no respeta el formato.");

					int x = Integer.parseInt(componentes[0]);
					int y = Integer.parseInt(componentes[1]);
					Posicion pos = new Posicion(x+1, y+1);
					checkPosicion(pos);
					
					if (!paredes.contains(pos)) paredes.add(pos);
					else throw new ArchivoInvalidoException("El archivo del nivel " + n + " tiene dos paredes superpuestas.");
				}
				
				// cargar alimentos (tipo, x, y)
				tuplas = lineas.get(1).split(";");
				
				if (tuplas.length > 0) {
					for (String tupla : tuplas) {
						String[] componentes = tupla.split(",");
						if (componentes.length != 3) throw new ArchivoInvalidoException("El archivo del nivel " + n + " no respeta el formato.");
	
						int tipo = Integer.parseInt(componentes[0]);
						int x = Integer.parseInt(componentes[1]);
						int y = Integer.parseInt(componentes[2]);
						
						Posicion pos = new Posicion(x+1 , y+1);
						checkPosicion(pos);
						
						if (!paredes.contains(pos)) {
							Consumible alimento = null;
							
							switch (tipo) {
								case 1 -> alimento = new Frutilla(pos);
								case 2 -> alimento = new Banana(pos);
								case 3 -> alimento = new Palta(pos);
								case 4 -> alimento = new Pera(pos);
								case 5 -> alimento = new Cereza(pos);
								default -> throw new ArchivoInvalidoException("El archivo del nivel " + n + " tiene un alimento de tipo inválido.");
							}
							
							if (!consumiblesPosiciones.contains(pos)) {
								consumibles.add(alimento);
								consumiblesPosiciones.add(pos);
							} else {
								throw new ArchivoInvalidoException("El archivo del nivel " + n + " tiene dos consumibles superpuestos.");
							}
						} else throw new ArchivoInvalidoException("El archivo del nivel " + n + " tiene un alimento sobre una pared.");
					}
				}
				
				// cargar powerups (tipo, x, y)
				tuplas = lineas.get(2).split(";");
				
				if (tuplas.length > 0) {
					for (String tupla : tuplas) {
						String[] componentes = tupla.split(",");
						if (componentes.length != 3) throw new ArchivoInvalidoException("El archivo del nivel " + n + " no respeta el formato.");
	
						int tipo = Integer.parseInt(componentes[0]);
						int x = Integer.parseInt(componentes[1]);
						int y = Integer.parseInt(componentes[2]);
						
						Posicion pos = new Posicion(x+1, y+1);
						checkPosicion(pos);
						
						if (!paredes.contains(pos)) {
							Consumible powerUp = null;
							
							switch (tipo) {
								case 1 -> powerUp = new Hamburguesa(pos);
								case 2 -> powerUp = new LataPintura(pos);
								case 3 -> powerUp = new Cisne(pos);
								default -> throw new ArchivoInvalidoException("El archivo del nivel " + n + " tiene un powerup de tipo inválido.");
							}
							
							if (!consumiblesPosiciones.contains(pos)) {
								consumibles.add(powerUp);
								consumiblesPosiciones.add(pos);
							} else {
								throw new ArchivoInvalidoException("El archivo del nivel " + n + " tiene dos consumibles superpuestos.");
							}
						} else throw new ArchivoInvalidoException("El archivo del nivel " + n + " tiene un powerup sobre una pared.");
					}
				}
				
				// chequear que se hayan insertado consumibles
				if (consumibles.size() == 0) throw new NivelInvalidoException("El nivel " + n + " no contiene ningún consumible.");
				
				toReturn = new Nivel(paredes, consumibles);
			} catch (NumberFormatException e) {
				throw new ArchivoInvalidoException("El archivo del nivel " + n + " no respeta el formato.");
			} catch (IOException e) {
				e.printStackTrace();
			}
        } else {
        	throw new NivelInvalidoException("No existe el nivel " + n + ".");
        }
        
        return toReturn;
    }
}
