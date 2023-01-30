package gui;

import ranking.EntradaRanking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
public class GraficaRanking extends JPanel{

    private GridBagLayout layout;
    protected int cantidadEntradas;
    public GraficaRanking(Iterable<EntradaRanking> entradas) {
        super();
        init();
        cantidadEntradas = 0;
        for(EntradaRanking ent: entradas){
            insertarEntrada(ent);
        }
    }

    private void insertarEntrada(EntradaRanking ent){
        JLabel labelNum = new JLabel(Integer.toString(cantidadEntradas + 1));
        labelNum.setForeground(Color.WHITE);
        JLabel labelNombre = new JLabel(ent.getNombre());
        labelNombre.setForeground(Color.WHITE);
        JLabel labelPuntaje = new JLabel(Integer.toString(ent.getPuntaje()));
        labelPuntaje.setForeground(Color.WHITE);
        JLabel labelTiempo = new JLabel(formatearTiempo(ent.getTiempo()));
        labelTiempo.setForeground(Color.WHITE);

        GridBagConstraints gbcNum = crearGBCEntrada(0);
        GridBagConstraints gbcNombre = crearGBCEntrada(1);
        GridBagConstraints gbcPuntaje = crearGBCEntrada(2);
        GridBagConstraints gbcTiempo = crearGBCEntrada(3);

        cantidadEntradas++;

        layout.rowWeights = new double[cantidadEntradas + 3];
        layout.rowWeights[cantidadEntradas + 2] = Double.MIN_VALUE;
        layout.rowHeights = new int[cantidadEntradas + 3];
        add(labelNum, gbcNum);
        add(labelNombre, gbcNombre);
        add(labelPuntaje, gbcPuntaje);
        add(labelTiempo, gbcTiempo);
    }

    private GridBagConstraints crearGBCEntrada(int columna){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = columna;
        gbc.weightx = 1.0;
        return gbc;
    }

    private String formatearTiempo(int tiempo){
        final int minutos = tiempo / 60;
        final int segundos = tiempo % 60;
        return String.format("%02d:%02d",minutos,segundos);
    }

    private void init(){
        /*
            Creado con WindowBuilder, luego modificado.
         */
        setOpaque(false);
        setBounds(100, 100, 450, 300);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 0, 0, 0, 0};
        layout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        layout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        layout.rowHeights = new int[3];
        setLayout(layout);

        JLabel lblNewLabel_4 = new JLabel("Ranking");
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.gridwidth = 4;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_4.gridx = 0;
        gbc_lblNewLabel_4.gridy = 0;
        add(lblNewLabel_4, gbc_lblNewLabel_4);

        JLabel lblNewLabel_3 = new JLabel("#");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.weightx = 0.5;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 0;
        gbc_lblNewLabel_3.gridy = 1;
        add(lblNewLabel_3, gbc_lblNewLabel_3);

        JLabel lblNewLabel = new JLabel("Nombre");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.weightx = 1.0;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 1;
        gbc_lblNewLabel.gridy = 1;
        add(lblNewLabel, gbc_lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Puntaje");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.weightx = 1.0;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 2;
        gbc_lblNewLabel_1.gridy = 1;
        add(lblNewLabel_1, gbc_lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Tiempo");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_2.weightx = 1.0;
        gbc_lblNewLabel_2.gridx = 3;
        gbc_lblNewLabel_2.gridy = 1;
        add(lblNewLabel_2, gbc_lblNewLabel_2);

        lblNewLabel_4.setForeground(Color.WHITE);
        lblNewLabel_3.setForeground(Color.WHITE);
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_2.setForeground(Color.WHITE);
    }
}
