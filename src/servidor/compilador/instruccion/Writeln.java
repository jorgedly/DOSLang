package servidor.compilador.instruccion;

import java.util.ArrayList;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;

/**
 *
 * @author Jorge
 */
public class Writeln extends Write {

    public Writeln(ArrayList<Expresion> argumentos) {
        super(argumentos);
    }

    @Override
    public String generar(Entorno entorno) {
        resolver(entorno);
        printChar("10");
        return codigo();
    }

}
