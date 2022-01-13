package servidor.compilador.entorno;

import java.util.HashMap;
import servidor.compilador.Constantes;
import servidor.compilador.instruccion.tipo.Primitivo;
import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public class TiposPrimitivos {

    public static HashMap<String, Tipo> primitivos = new HashMap<>();

    public static void iniciarPrimitivos() {
        primitivos = new HashMap<>();
        primitivos.put(Constantes.INTEGER, new Primitivo(Constantes.INTEGER));
        primitivos.put(Constantes.REAL, new Primitivo(Constantes.REAL));
        primitivos.put(Constantes.CHAR, new Primitivo(Constantes.CHAR));
        primitivos.put(Constantes.BOOLEAN, new Primitivo(Constantes.BOOLEAN));
        primitivos.put(Constantes.WORD, new Primitivo(Constantes.WORD));
        primitivos.put(Constantes.STRING, new Primitivo(Constantes.STRING));
    }

    public static Tipo obtenerPrimitivo(String nombre) {
        return primitivos.get(nombre);
    }

}
