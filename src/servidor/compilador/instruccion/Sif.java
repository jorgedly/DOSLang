package servidor.compilador.instruccion;

import java.util.ArrayList;
import servidor.compilador.expresion.Expresion;

/**
 *
 * @author Jorge
 */
public class Sif {

    public Expresion expresion;
    public ArrayList<Instruccion> instrucciones;

    public Sif(Expresion expresion, ArrayList<Instruccion> instrucciones) {
        this.expresion = expresion;
        this.instrucciones = instrucciones;
    }
}
