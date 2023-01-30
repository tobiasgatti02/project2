package consumibles.powerups;

import consumibles.Consumible;
import logica_principal.Posicion;

public abstract class PowerUp extends Consumible {
    protected PowerUp(Posicion pos, int crec, int punt, String img) {
        super(pos, crec, punt, img);
    }
}
