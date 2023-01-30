package gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LectorImagenes {
    private Map<String, Image> imagenes;
    private Map<Image, Image> imagenesRedimensionadas;
    private static LectorImagenes instance;

    public static LectorImagenes getInstance() {
        if(instance == null)
            instance = new LectorImagenes();
        return instance;
    }

    private LectorImagenes(){
        imagenes = new HashMap<String, Image>();
        imagenesRedimensionadas = new HashMap<Image, Image>();
    }

    public Image getImagen(String ruta, Dimension tamano){
        Image imagen = imagenes.get(ruta);
        if(imagen == null){
            try {
                imagen = ImageIO.read(getClass().getResource(ruta));
                imagenes.put(ruta, imagen);
            } catch (IOException e) {
                throw new RuntimeException("Error al leer imágen");
            }
        }
        Image imagenRedimensionada =  imagenesRedimensionadas.get(imagen);
        if(imagenRedimensionada == null ||
                imagenRedimensionada.getWidth(null) != tamano.width || imagenRedimensionada.getHeight(null) != tamano.height){
            imagenRedimensionada = imagen.getScaledInstance(tamano.width, tamano.height, Image.SCALE_FAST);
            imagenesRedimensionadas.put(imagen, imagenRedimensionada);
        }
        return imagenRedimensionada;
    }
}
