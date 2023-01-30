package gui;

import logica_principal.Direccion;
import logica_principal.Juego;

import ranking.Ranking;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

public class Ventana extends JFrame {
    protected Juego miJuego;
    protected Ranking miRanking;
    protected JPanel panelTablero;
    protected JPanel panelEstadisticas;
    protected JLabel lblTextoPuntaje;
    protected JLabel lblPuntaje;
    protected JLabel lblTextoTiempo;
    protected JLabel lblTiempo;
    protected JPanel panelRanking;
    protected JPanel panelMenu;
    protected JLabel lblNivel;
    protected JLabel lblNumeroNivel;

    public Ventana()
    {
        inicializarGUI();
        miRanking = new Ranking();

        inicializarMenu();
    }

    public void cambiarDireccion(Direccion dir){
        miJuego.girarSerpiente(dir);
    }

    public JPanel getPanelTablero() {
        return panelTablero;
    }

    public void terminarJuego(){
        String nombre = JOptionPane.showInputDialog(this,
                "Ingrese su nombre", "Juego terminado",
                JOptionPane.PLAIN_MESSAGE);
        if(nombre != null){
            if(nombre.isBlank())
                nombre = "Jugador desconocido";
            miRanking.agregarEntrada(nombre, miJuego.getTiempo(), miJuego.getPuntaje());
        }

        miJuego = null;
        getContentPane().remove(panelTablero);
        panelTablero = null;
        mostrarRanking();
    }

    public void actualizarTiempo(int tiempo){
        final int horas = tiempo / 3600;
        final int minutos = (tiempo % 3600)/60;
        final int segundos = (tiempo % 3600) % 60;
        String texto;
        if(horas == 0)
            texto = String.format("%02d:%02d",minutos,segundos);
        else
            texto = String.format("%02d:%02d:%02d",horas,minutos,segundos);
        lblTiempo.setText(texto);
    }

    public void actualizarPuntaje(int puntaje){
        lblPuntaje.setText(Integer.toString(puntaje));
    }

    public void actualizarNivel(int nivel){
        lblNumeroNivel.setText(Integer.toString(nivel));
    }

    protected void mostrarRanking(){
        JPanel panelEntradas = miRanking.graficar();
        if(panelMenu != null)
            getContentPane().remove(panelMenu);

        panelRanking = new JPanel(new BorderLayout());
        panelRanking.setOpaque(false);
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAMenu();
            }
        });

        panelRanking.add(panelEntradas, BorderLayout.CENTER);
        panelRanking.add(btnVolver, BorderLayout.SOUTH);

        getContentPane().add(panelRanking, BorderLayout.CENTER);

        getContentPane().revalidate();
        repaint();
    }

    private void inicializarGUI() {
        setContentPane(new JPanel(new BorderLayout()));
        try {
            setIconImage(ImageIO.read(getClass().getResource("/imagenes/logo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("Snake++");
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(miJuego != null) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP -> cambiarDireccion(Direccion.ARRIBA);
                        case KeyEvent.VK_DOWN -> cambiarDireccion(Direccion.ABAJO);
                        case KeyEvent.VK_LEFT -> cambiarDireccion(Direccion.IZQUIERDA);
                        case KeyEvent.VK_RIGHT -> cambiarDireccion(Direccion.DERECHA);
                    }
                }
            }
        });
        setBounds(100, 100, 1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.DARK_GRAY);
        setResizable(false);

    }

    private void inicializarMenu(){
        /*
            Creado con WindowBuilder, luego modificado
        */
        panelMenu = new JPanel(new GridLayout(0,3,0,0));
        panelMenu.setPreferredSize(escalarSegunResolucion(new Dimension(250,300)));

        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setOpaque(false);
        panelMenu.add(panelIzquierda);

        JPanel panelCentro = new JPanel();
        panelCentro.setOpaque(false);
        panelMenu.add(panelCentro);
        GridBagLayout gbl_panelCentro = new GridBagLayout();
        gbl_panelCentro.columnWidths = new int[] {50};
        gbl_panelCentro.rowHeights = new int[]{0,13, 0, 0};
        gbl_panelCentro.columnWeights = new double[]{1.0};
        gbl_panelCentro.rowWeights = new double[]{2,1.0, 1.0, 1};
        panelCentro.setLayout(gbl_panelCentro);

        JPanel panelDerecha = new JPanel();
        panelDerecha.setOpaque(false);

        GridBagConstraints gbc_panelDerecha = new GridBagConstraints();
        gbc_panelDerecha.insets = new Insets(0, 0, 5, 0);
        gbc_panelDerecha.weighty = 1.0;
        gbc_panelDerecha.fill = GridBagConstraints.BOTH;
        gbc_panelDerecha.gridx = 0;
        gbc_panelDerecha.gridy = 2;
        panelCentro.add(panelDerecha, gbc_panelDerecha);
        panelDerecha.setLayout(new GridLayout(3, 0, 0, 15));

        JButton btnJugar = new JButton("Jugar");
        btnJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
            }
        });
        panelDerecha.add(btnJugar);

        JButton btnRanking = new JButton("Ranking");
        panelDerecha.add(btnRanking);
        btnRanking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRanking();
            }
        });

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        panelDerecha.add(btnSalir);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);

        GridBagConstraints gbc_panelBotones = new GridBagConstraints();
        gbc_panelBotones.weighty = 1;
        gbc_panelBotones.weightx = 1.0;
        gbc_panelBotones.fill = GridBagConstraints.BOTH;
        gbc_panelBotones.gridx = 0;
        gbc_panelBotones.gridy = 1;
        panelCentro.add(panelBotones, gbc_panelBotones);
        panelBotones.setLayout(new FlowLayout());

        JLabel lblLogo = new JLabel();
        lblLogo.setIcon(new ImageIcon(LectorImagenes.getInstance().getImagen("/imagenes/logo.png", escalarSegunResolucion(new Dimension(100,100)))));

        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        panelBotones.add(lblLogo);


        JPanel panel4 = new JPanel();
        panelMenu.add(panel4);
        panelMenu.setOpaque(false);
        panel4.setOpaque(false);

        getContentPane().add(panelMenu);
        pack();
    }

    protected void iniciarJuego(){
        getContentPane().remove(panelMenu);
        panelMenu = null;
        inicializarPanelesJuego();
        miJuego = new Juego(this);
        pack();

    }

    private void inicializarPanelesJuego() {
        /*
            Creado con WindowBuilder, luego modificado
        */
        panelTablero = new JPanel();
        getContentPane().add(panelTablero, BorderLayout.NORTH);
        panelEstadisticas = new JPanel();
        getContentPane().add(panelEstadisticas, BorderLayout.SOUTH);
        panelEstadisticas.setLayout(new GridLayout(1, 2));

        JPanel panel = new JPanel();
        panelEstadisticas.add(panel);

        lblTextoPuntaje = new JLabel("Puntaje:");
        lblTextoPuntaje.setForeground(Color.WHITE);
        panel.add(lblTextoPuntaje);

        lblPuntaje = new JLabel("0");
        lblPuntaje.setForeground(Color.WHITE);
        panel.add(lblPuntaje);

        JPanel panelNivel = new JPanel();
        panelNivel.setOpaque(false);
        lblNivel = new JLabel("Nivel:");
        lblNumeroNivel = new JLabel("1");
        panelNivel.add(lblNivel);
        lblNivel.setForeground(Color.WHITE);
        lblNumeroNivel.setForeground(Color.WHITE);
        panelNivel.add(lblNumeroNivel);

        panelEstadisticas.add(panelNivel);

        JPanel panel_1 = new JPanel();
        panelEstadisticas.add(panel_1);

        lblTextoTiempo = new JLabel("Tiempo:");
        lblTextoTiempo.setForeground(Color.WHITE);
        panel_1.add(lblTextoTiempo);

        lblTiempo = new JLabel("00:00");
        lblTiempo.setForeground(Color.WHITE);
        panel_1.add(lblTiempo);
        panel.setOpaque(false);
        panel_1.setOpaque(false);
        panelTablero.setOpaque(false);

        // cambiar la fuente a los labels
        try {
            InputStream in = getClass().getResourceAsStream("/fuentes/retrogaming.ttf");
            Font retrogFuente = Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(Font.PLAIN, 20);
            in.close();

            lblTextoPuntaje.setFont(retrogFuente);
            lblPuntaje.setFont(retrogFuente);
            lblTextoTiempo.setFont(retrogFuente);
            lblTiempo.setFont(retrogFuente);
            lblNivel.setFont(retrogFuente);
            lblNumeroNivel.setFont(retrogFuente);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        panelEstadisticas.setOpaque(false);

        int preferido = (int)escalarSegunResolucion(new Dimension(300,300)).getWidth();
        panelTablero.setPreferredSize(new Dimension(preferido,preferido));
        requestFocus();
    }

    private void volverAMenu(){
        getContentPane().remove(panelRanking);
        panelRanking = null;
        if(panelEstadisticas != null)
        {
            getContentPane().remove(panelEstadisticas);
            panelEstadisticas = null;
        }
        inicializarMenu();
        revalidate();
        repaint();
    }

    private Dimension escalarSegunResolucion(Dimension d){
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int anchoPantalla = gd.getDisplayMode().getWidth();
        int altoPantalla = gd.getDisplayMode().getHeight();

        return new Dimension(d.width * anchoPantalla / 1366, d.height * altoPantalla / 768);
    }

    private void salir(){
        System.exit(0);
    }

}
