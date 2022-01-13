package servidor.compilador.expresion;

import servidor.compilador.Control;
import servidor.compilador.Creador;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public abstract class Expresion extends Creador {

    public Ubicacion ubicacion;

    public Expresion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public abstract Valor gen(Entorno entorno);

    public void error(String descripcion) {
        Control.error(ubicacion, descripcion);
    }

}
