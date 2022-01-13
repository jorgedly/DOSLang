package servidor.compilador.instruccion;

import servidor.compilador.Creador;
import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public abstract class Instruccion extends Creador {

    public abstract String generar(Entorno entorno);

}
