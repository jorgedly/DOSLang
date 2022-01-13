package servidor.compilador.preanalisis;

import servidor.compilador.instruccion.tipo.PreTipo;
import java.util.ArrayList;

/**
 *
 * @author Jorge
 */
public class PreParametro {

    public boolean referencia;
    public ArrayList<Id> ids;
    public PreTipo pretipo;

    public PreParametro(boolean referencia, ArrayList<Id> ids, PreTipo pretipo) {
        this.referencia = referencia;
        this.ids = ids;
        this.pretipo = pretipo;
    }

}
