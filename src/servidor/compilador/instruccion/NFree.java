package servidor.compilador.instruccion;

import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.valor.acceso.Acceso;

/**
 *
 * @author Jorge
 */
public class NFree extends Instruccion {

    public Acceso acceso;

    public NFree(Acceso acceso) {
        this.acceso = acceso;
    }

    @Override
    public String generar(Entorno entorno) {
        return acceso.liberar(entorno);
    }

}
