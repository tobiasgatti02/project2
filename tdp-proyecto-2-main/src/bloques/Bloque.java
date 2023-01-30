package bloques;

import consumibles.Consumible;
import gui.LectorImagenes;
import logica_principal.Posicion;
import logica_principal.Serpiente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public abstract class Bloque {
    protected JLabel grafico;
    protected String frente;
    protected int rotacionFrente;

    protected String fondo;
    protected Posicion miPosicion;
    protected Serpiente miSerpiente;
    protected Consumible miConsumible;
    protected LectorImagenes lector;

    public Bloque(String imFondo, Posicion pos) {
        lector = LectorImagenes.getInstance();
        miConsumible = null;
        miPosicion = pos;
        miSerpiente = null;

        grafico = new JLabel();
        grafico.setSize(new Dimension(100, 100));
        fondo = imFondo;
        actualizarGrafico();
        grafico.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redimensionar();
            }
        });
    }

    public JLabel getGrafico() {
        return grafico;
    }

    public Posicion getPosicion() {
        return miPosicion;
    }

    public Consumible getConsumible(){
        return miConsumible;
    }

    // Requiere rotación múltiplo de 180º
    public void setFrente(String i, int rotacion) {
        frente =  i;
        rotacionFrente = rotacion;
        actualizarGrafico();
    }

    public void desocupar() {
        miSerpiente = null;
        frente = null;
        actualizarGrafico();
    }

    public void ocupar(Serpiente s) {
        miSerpiente = s;
    }

    public abstract void setConsumible(Consumible c);

    public abstract void recibir(Serpiente s);

    protected void actualizarGrafico(){
        Image imagenFinal;
        Image imFondo = lector.getImagen(fondo, new Dimension(grafico.getWidth(), grafico.getHeight()));
        if(frente != null) {
            int ancho = (rotacionFrente == 0 || rotacionFrente == 180) ? grafico.getWidth() : grafico.getHeight();
            int alto = (rotacionFrente == 0 || rotacionFrente == 180) ? grafico.getHeight() : grafico.getWidth();
            Image imFrente = rotarImagen(lector.getImagen(frente, new Dimension(ancho, alto)), rotacionFrente);

            imagenFinal = new BufferedImage(grafico.getWidth(), grafico.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics g = imagenFinal.getGraphics();
            g.drawImage(imFondo, 0, 0, null);
            g.drawImage(imFrente, 0, 0, null);
            g.dispose();
        }
        else
            imagenFinal = imFondo;
        ImageIcon i = new ImageIcon(imagenFinal);
        if(!EventQueue.isDispatchThread()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    grafico.setIcon(i);
                }
            });
        }
        else
            grafico.setIcon(i);

    }

    protected void redimensionar(){
        actualizarGrafico();
    }

    // Requiere rotación múltiplo de 180º
    protected Image rotarImagen(Image im, int rotacion){
        Image rotada;
        if(rotacion == 0)
            rotada = im;
        else{
            int anchoOriginal = im.getWidth(null);
            int altoOriginal = im.getHeight(null);

            int anchoNuevo;
            int altoNuevo;
            if(rotacion == 90 || rotacion == 270){
                anchoNuevo = altoOriginal;
                altoNuevo = anchoOriginal;
            }
            else{
                anchoNuevo = anchoOriginal;
                altoNuevo = altoOriginal;
            }

            BufferedImage original = new BufferedImage(anchoOriginal, altoOriginal, BufferedImage.TYPE_INT_ARGB);
            original.getGraphics().drawImage(im, 0 ,0, null);

            BufferedImage rot = new BufferedImage(anchoNuevo, altoNuevo, BufferedImage.TRANSLUCENT);
            Graphics2D gr = rot.createGraphics();

            gr.drawImage(im, anchoOriginal, altoOriginal, null);
            gr.translate(((float)anchoNuevo - anchoOriginal ) / 2, ((float)altoNuevo - altoOriginal) / 2);
            gr.rotate(Math.toRadians(rotacion), (float)anchoOriginal / 2, (float)altoOriginal /2);
            gr.drawRenderedImage(original, null);
            gr.dispose();
            rotada = rot;
        }
        return rotada;
    }
}
