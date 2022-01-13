package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import servidor.compilador.preanalisis.Id;

/**
 *
 * @author Jorge
 */
public class PreCampo {

    public ArrayList<Id> ids;
    public PreTipo pretipo;

    public PreCampo(ArrayList<Id> ids, PreTipo pretipo) {
        this.ids = ids;
        this.pretipo = pretipo;
    }

}
