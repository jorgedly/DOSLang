package servidor.compilador.preanalisis;

import java.util.ArrayList;
import servidor.compilador.Creador;
import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public abstract class DeclaracionConstantes extends Creador {

    public ArrayList<Id> ids;

    public DeclaracionConstantes(ArrayList<Id> ids) {
        this.ids = ids;
    }

    public abstract String almacenar(Entorno entorno, StringBuilder codigo);

}
