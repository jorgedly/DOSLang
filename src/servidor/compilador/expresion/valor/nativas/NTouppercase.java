package servidor.compilador.expresion.valor.nativas;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.Control;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public class NTouppercase extends Expresion {

    public Expresion expresion;

    public NTouppercase(Ubicacion ubicacion, Expresion expresion) {
        super(ubicacion);
        this.expresion = expresion;
    }

    @Override
    public Valor gen(Entorno entorno) {
        Valor cad = expresion.gen(entorno);
        if (!cad.esError()) {
            if (cad.tipo.tipo.equals(Constantes.STRING) || cad.tipo.tipo.equals(Constantes.WORD)) {
                codigo(cad.codigo);
                String t1 = Codigo.t(), t2 = Codigo.t(entorno);
                suma(t1, "p", Integer.toString(entorno.tamaño() + 1));
                setStack(t1, cad.cadena);
                suma("p", "p", Integer.toString(entorno.tamaño()));
                call("upper");
                getStack(t2, "p");
                resta("p", "p", Integer.toString(entorno.tamaño()));
                entorno.quitar(cad.cadena);
                return Valor.valor(cad.tipo, t2, codigo());
            } else {
                //ERROR
                Control.error(ubicacion, "La expresion debe ser string o word");
            }
        }
        return Valor.error();
    }

}
