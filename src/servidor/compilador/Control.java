package servidor.compilador;

import java.util.ArrayList;

/**
 *
 * @author Jorge
 */
public class Control {

    public static ArrayList<CError> errores = new ArrayList<>();

    public static void error(String tipo, Ubicacion ubicacion, String descripcion) {
        System.out.println(descripcion + " -> " + ubicacion.toString());
        errores.add(new CError(tipo, descripcion, ubicacion.archivo, ubicacion.fila, ubicacion.columna));
    }

    public static void error(Ubicacion ubicacion, String descripcion) {
        System.out.println(descripcion + " -> " + ubicacion.toString());
        errores.add(new CError(Constantes.SEMANTICO, descripcion, ubicacion.archivo, ubicacion.fila, ubicacion.columna));
    }

}
