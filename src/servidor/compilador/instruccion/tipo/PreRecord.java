package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import servidor.compilador.Control;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.Variable;
import servidor.compilador.preanalisis.Id;

/**
 *
 * @author Jorge
 */
public class PreRecord extends PreTipo {

    public ArrayList<PreCampo> precampos;
    public Record record;

    public PreRecord(Ubicacion ubicacion, ArrayList<PreCampo> campos) {
        super(ubicacion);
        this.precampos = campos;
        record = null;
    }

    @Override
    public Tipo resolver(Entorno entorno, StringBuilder codigo) {
        record = new Record();
        return record;
    }

    public void terminar(Entorno entorno, StringBuilder codigo) {
        for (PreCampo precampo : precampos) {
            Tipo ttipo = precampo.pretipo.resolver(entorno, codigo);
            for (Id id : precampo.ids) {
                if (!record.campos.containsKey(id.nombre)) {
                    Variable variable = new Variable(id.nombre, ttipo);
                    variable.posicion = record.tamaño++;
                    record.campos.put(id.nombre, variable);
                } else {
                    //ERROR
                    Control.error(id.ubicacion, "Ya existe el nombre " + id.nombre + " en el registro");
                }
            }
        }
    }

}
