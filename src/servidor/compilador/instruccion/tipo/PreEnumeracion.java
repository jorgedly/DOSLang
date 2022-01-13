package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import servidor.compilador.Control;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.preanalisis.Id;

/**
 *
 * @author Jorge
 */
public class PreEnumeracion extends PreTipo {

    public ArrayList<Id> ids;

    public PreEnumeracion(Ubicacion ubicacion, ArrayList<Id> ids) {
        super(ubicacion);
        this.ids = ids;
    }

    @Override
    public Tipo resolver(Entorno entorno, StringBuilder codigo) {
        ArrayList<String> nombres = new ArrayList<>();
        for (Id id : ids) {
            if (!nombres.contains(id.nombre)) {
                nombres.add(id.nombre);
            } else {
                //ERROR
                Control.error(id.ubicacion, "Ya existe el id " + id.nombre + " en la enumeracion");
            }
        }
        return new Enumeracion(nombres);
    }

}
