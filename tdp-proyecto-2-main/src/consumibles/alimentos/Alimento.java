package consumibles.alimentos;

import consumibles.Consumible;

import logica_principal.Posicion;
import logica_principal.Serpiente;

public abstract class Alimento extends Consumible {
    protected Alimento(Posicion pos, int crec, int punt, String img) {
        super(pos, crec, punt, img);
    }

    @Override
    public void afectar(Serpiente s) {
        s.consumir(this);
    }
}
