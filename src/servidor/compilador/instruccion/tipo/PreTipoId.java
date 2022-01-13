package servidor.compilador.instruccion.tipo;

import servidor.compilador.Control;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.preanalisis.Id;

/**
 *
 * @author Jorge
 */
public class PreTipoId extends PreTipo {

    public Id id;

    public PreTipoId(Id id) {
        super(id.ubicacion);
        this.id = id;
    }

    @Override
    public Tipo resolver(Entorno entorno, StringBuilder codigo) {
        if (entorno.buscarTipo(id.nombre)) {
            return entorno.obtenerTipo(id.nombre);
        } else {
            //ERROR
            Control.error(ubicacion, "No existe el tipo " + id.nombre);
        }
        return new TError();
    }

}
