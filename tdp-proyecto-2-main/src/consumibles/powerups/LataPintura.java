package consumibles.powerups;

import logica_principal.Posicion;
import logica_principal.Serpiente;

public class LataPintura extends PowerUp{
    public LataPintura(Posicion pos) {
        super(pos, 3, 100, "/imagenes/consumibles/powerups/lata_pintura.png");
    }
    
    @Override
    public void afectar(Serpiente s) {
        s.consumir(this);
    }
}
