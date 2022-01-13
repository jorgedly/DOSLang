package servidor.compilador.instruccion;

import servidor.compilador.Control;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public class Continue extends Instruccion {

    public Ubicacion ubicacion;

    public Continue(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String generar(Entorno entorno) {
        if (!entorno.inicios.isEmpty()) {
            jmp(entorno.inicios.peek());
            return codigo();
        } else {
            //ERROR
            Control.error(ubicacion, "continue debe estar en el ambiente de un ciclo");
        }
        return "";
    }

}
