package servidor.compilador.instruccion.tipo;

import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public class PreNil extends PreTipo {

    public PreNil(Ubicacion ubicacion) {
        super(ubicacion);
    }

    @Override
    public Tipo resolver(Entorno entorno, StringBuilder codigo) {
        return new TNil();
    }

}
