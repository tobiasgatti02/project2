package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class Splash extends JWindow {
	static String imagenPath = "/imagenes/splash.jpg";
	BufferedImage imagen;
	
	public Splash() {
		try {
			imagen = ImageIO.read(getClass().getResource(imagenPath));
			setSize(imagen.getWidth(), imagen.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		// centrar la ventana
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponents(g);
        g.drawImage(imagen, 0, 0, imagen.getWidth(), imagen.getHeight(), null);
	}
}