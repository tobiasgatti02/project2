package main;

import gui.Splash;
import gui.Ventana;
import javax.swing.*;
import java.awt.*;

public class Launcher {
    public static void main(String[] args) {
    	// mostrar un splash
    	try {
    		Splash splash = new Splash();
        	splash.setVisible(true);
			Thread.sleep(4000);
			splash.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	Ventana vent = new Ventana();
                    vent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    vent.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
