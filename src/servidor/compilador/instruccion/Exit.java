package servidor.compilador.instruccion;

import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public class Exit extends Instruccion {

    public Ubicacion ubicacion;

    public Exit(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String generar(Entorno entorno) {
        jmp(entorno.salida);
        return codigo();
    }

}
