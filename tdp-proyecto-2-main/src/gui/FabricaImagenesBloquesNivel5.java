package gui;

public class FabricaImagenesBloquesNivel5 implements FabricaImagenesBloques {
    private static final String pared = "/imagenes/bloques/nivel5/pared.png";
    private static final String transitablePar = "/imagenes/bloques/nivel5/transitable_par.png";
    private static final String transitableImpar = "/imagenes/bloques/nivel5/transitable_impar.png";
    @Override
    public String getImagenPared() {
        return pared;
    }

    @Override
    public String getImagenTransitablePar() {
        return transitablePar;
    }

    @Override
    public String getImagenTransitableImpar() {
        return transitableImpar;
    }
}
