package gui;

public class FabricaImagenesBloquesNivel1 implements FabricaImagenesBloques {
    private static final String pared = "/imagenes/bloques/nivel1/pared.png";
    private static final String transitablePar = "/imagenes/bloques/nivel1/transitable_par.png";
    private static final String transitableImpar = "/imagenes/bloques/nivel1/transitable_impar.png";

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
