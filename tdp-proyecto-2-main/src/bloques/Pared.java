package bloques;

import consumibles.Consumible;
import logica_principal.Posicion;
import logica_principal.Serpiente;


public class Pared extends Bloque{
    public Pared(String im, Posicion pos) {
        super(im, pos);
    }

    @Override
    public void setConsumible(Consumible c) {
        //Una pared no puede tener consumibles.
    }

    @Override
    public void recibir(Serpiente s) {
        s.visitar(this);
    }
}
