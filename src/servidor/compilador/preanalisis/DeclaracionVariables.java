package servidor.compilador.preanalisis;

import java.util.ArrayList;
import servidor.compilador.Creador;
import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public abstract class DeclaracionVariables extends Creador {

    public ArrayList<Id> ids;

    public DeclaracionVariables(ArrayList<Id> ids) {
        this.ids = ids;
    }

    public abstract String almacenar(Entorno entorno, StringBuilder codigo);

}
