package servidor.compilador.instruccion;

import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Valor;
import servidor.compilador.expresion.valor.acceso.Elemento;

/**
 *
 * @author Jorge
 */
public class InstruccionExpresion extends Instruccion {

    public Elemento llamada;

    public InstruccionExpresion(Elemento llamada) {
        this.llamada = llamada;
    }

    @Override
    public String generar(Entorno entorno) {
        Valor valor = llamada.primero(entorno);
        if (!valor.esError()) {
            codigo(valor.codigo);
            return codigo();
        }
        return "";
    }

}
