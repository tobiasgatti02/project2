package bloques;

import consumibles.Consumible;
import logica_principal.Posicion;
import logica_principal.Serpiente;


public class BloqueTransitable extends Bloque{
    public BloqueTransitable(String im, Posicion pos)
    {
        super(im, pos);
    }

    @Override
    public void setConsumible(Consumible c) {
        miConsumible = c;
        if(c != null) {
            if (miSerpiente == null)
                setFrente(c.getImagen(), 0);
            else
                c.afectar(miSerpiente);
        }
        else
            setFrente(null, 0);
    }

    @Override
    public void recibir(Serpiente s) {
        s.visitar(this);
    }
}
