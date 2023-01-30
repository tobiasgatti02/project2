package consumibles.powerups;

import logica_principal.Posicion;
import logica_principal.Serpiente;

public class Cisne extends PowerUp{
    public Cisne(Posicion pos) {
        super(pos, 3, 75, "/imagenes/consumibles/powerups/cisne.png");
    }
    
    @Override
    public void afectar(Serpiente s) {
        s.consumir(this);
    }
}
