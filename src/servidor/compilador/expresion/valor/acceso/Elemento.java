package servidor.compilador.expresion.valor.acceso;

import servidor.compilador.Creador;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public abstract class Elemento extends Creador {

    public Ubicacion ubicacion;
    public boolean asignable;

    public Elemento(Ubicacion ubicacion, boolean asignable) {
        this.ubicacion = ubicacion;
        this.asignable = asignable;
    }

    public abstract Valor primero(Entorno entorno);

    public abstract Valor segundo(Entorno entorno, Valor record);

    public abstract String asignacionPrimera(Entorno entorno, Valor valor);

    public abstract String asignacionSegunda(Entorno entorno, Valor valor, Valor record);

    public abstract String liberacionPrimera(Entorno entorno);

    public abstract String liberacionSegunda(Entorno entorno, Valor record);

}
