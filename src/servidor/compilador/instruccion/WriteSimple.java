package servidor.compilador.instruccion;

import java.util.ArrayList;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;

/**
 *
 * @author Jorge
 */
public class WriteSimple extends Write {

    public WriteSimple(ArrayList<Expresion> argumentos) {
        super(argumentos);
    }

    @Override
    public String generar(Entorno entorno) {
        resolver(entorno);
        return codigo();
    }

}
