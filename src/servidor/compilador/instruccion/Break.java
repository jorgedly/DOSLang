package servidor.compilador.instruccion;

import servidor.compilador.Control;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public class Break extends Instruccion {

    public Ubicacion ubicacion;

    public Break(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String generar(Entorno entorno) {
        if (!entorno.salidas.isEmpty()) {
            jmp(entorno.salidas.peek());
            return codigo();
        } else {
            //ERROR
            Control.error(ubicacion, "break debe estar en el ambiente de un ciclo");
        }
        return "";
    }

}
