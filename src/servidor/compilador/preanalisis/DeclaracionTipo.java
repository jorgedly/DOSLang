package servidor.compilador.preanalisis;

import java.util.ArrayList;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.instruccion.tipo.PreTipo;

/**
 *
 * @author Jorge
 */
public abstract class DeclaracionTipo {

    public ArrayList<Id> ids;
    public PreTipo pretipo;

    public DeclaracionTipo(ArrayList<Id> ids, PreTipo pretipo) {
        this.ids = ids;
        this.pretipo = pretipo;
    }

    public abstract void declarar(Entorno entorno, StringBuilder codigo);

    public abstract void almacenar(Entorno entorno, StringBuilder codigo);

}
