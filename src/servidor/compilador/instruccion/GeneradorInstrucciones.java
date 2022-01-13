package servidor.compilador.instruccion;

import java.util.ArrayList;
import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public class GeneradorInstrucciones {

    public static String generar(Entorno entorno, ArrayList<Instruccion> instrucciones) {
        StringBuilder sb = new StringBuilder();
        for (Instruccion instruccion : instrucciones) {
            if (instruccion != null) {
                sb.append(instruccion.generar(entorno));
            } else {
                System.out.println("a");
            }
        }
        return sb.toString();
    }
}
