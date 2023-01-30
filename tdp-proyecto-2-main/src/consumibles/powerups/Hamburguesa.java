package consumibles.powerups;

import logica_principal.Posicion;
import logica_principal.Serpiente;

public class Hamburguesa extends PowerUp{
    public Hamburguesa(Posicion pos) {
        super(pos, 3, 50, "/imagenes/consumibles/powerups/hamburguesa.png");
    }
	
    @Override
    public void afectar(Serpiente s) {
        s.consumir(this);
    }
}
