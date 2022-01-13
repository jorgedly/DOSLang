package servidor.compilador.instruccion;

import java.util.ArrayList;
import servidor.compilador.expresion.Expresion;

/**
 *
 * @author Jorge
 */
public class Case {

    public ArrayList<Expresion> expresiones;
    public ArrayList<Instruccion> instrucciones;

    public Case(ArrayList<Expresion> expresiones, ArrayList<Instruccion> instrucciones) {
        this.expresiones = expresiones;
        this.instrucciones = instrucciones;
    }

}
