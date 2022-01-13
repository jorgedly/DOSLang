package servidor.compilador.expresion.valor.nativas;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.Control;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Puntero;

/**
 *
 * @author Jorge
 */
public class NMalloc extends Expresion {

    public Expresion expresion;

    public NMalloc(Ubicacion ubicacion, Expresion expresion) {
        super(ubicacion);
        this.expresion = expresion;
    }

    @Override
    public Valor gen(Entorno entorno) {
        Valor valor = expresion.gen(entorno);
        if (!valor.esError()) {
            if (valor.tipo.tipo.equals(Constantes.INTEGER)) {
                codigo(valor.codigo);
                String t1 = Codigo.t(entorno);
                asignacion(t1, "h");
                suma("h", "h", valor.cadena);
                entorno.quitar(valor.cadena);
                return Valor.valor(new Puntero(), t1, codigo());
            } else {
                //ERROR
                Control.error(ubicacion, "El argumento en malloc debe ser integer");
            }
        }
        return Valor.error();
    }
}
