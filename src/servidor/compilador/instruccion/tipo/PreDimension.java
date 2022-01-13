package servidor.compilador.instruccion.tipo;

import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public abstract class PreDimension {

    public abstract Dimension colocar(Entorno entorno, StringBuilder codigo);
}
