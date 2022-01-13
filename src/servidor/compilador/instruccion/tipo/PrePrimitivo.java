package servidor.compilador.instruccion.tipo;

import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.TiposPrimitivos;

/**
 *
 * @author Jorge
 */
public class PrePrimitivo extends PreTipo {

    public PrePrimitivo(Ubicacion ubicacion, String tipo) {
        super(ubicacion);
        this.tipo = tipo;
    }

    @Override
    public Tipo resolver(Entorno entorno, StringBuilder codigo) {
        return TiposPrimitivos.obtenerPrimitivo(tipo);
    }

}
