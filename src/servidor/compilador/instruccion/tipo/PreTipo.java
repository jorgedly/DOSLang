package servidor.compilador.instruccion.tipo;

import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public abstract class PreTipo {

    public Ubicacion ubicacion;
    public String tipo;
    public boolean declaracion;

    public PreTipo(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
        tipo = "";
        declaracion = false;
    }

    public abstract Tipo resolver(Entorno entorno, StringBuilder codigo);
    
}
