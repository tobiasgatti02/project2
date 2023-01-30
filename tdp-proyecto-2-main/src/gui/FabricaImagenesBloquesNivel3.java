package gui;

public class FabricaImagenesBloquesNivel3 implements FabricaImagenesBloques {
    private static final String pared = "/imagenes/bloques/nivel3/pared.png";
    private static final String transitablePar = "/imagenes/bloques/nivel3/transitable_par.png";
    private static final String transitableImpar = "/imagenes/bloques/nivel3/transitable_impar.png";

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
