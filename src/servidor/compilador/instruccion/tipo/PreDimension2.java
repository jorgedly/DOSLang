package servidor.compilador.instruccion.tipo;

import servidor.compilador.Control;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public class PreDimension2 extends PreDimension {

    public Expresion expresion;

    public PreDimension2(Expresion expresion) {
        this.expresion = expresion;
    }

    @Override
    public Dimension colocar(Entorno entorno, StringBuilder codigo) {
        Valor valor = expresion.gen(entorno);
        if (valor.tipo instanceof Rango) {
            Rango rango = (Rango) valor.tipo;
            return new Dimension(rango.inferior, rango.cantidad);
        } else {
            //ERROR
            Control.error(expresion.ubicacion, "La expresion debe ser de tipo rango");
        }
        return new Dimension("", "");
    }

}
