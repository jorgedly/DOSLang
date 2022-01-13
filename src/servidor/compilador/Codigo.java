package servidor.compilador;

import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public class Codigo {

    public static int t = 0;
    public static int l = 0;

    public static String t(Entorno entorno) {
        String temp = "t" + t++;
        entorno.temporales.add(temp);
        return temp;
    }

    public static String t() {
        return "t" + t++;
    }

    public static String l() {
        return "L" + l++;
    }

    public static void reiniciar() {
        t = 0;
        l = 0;
    }

    public static String defecto(String tipo) {
        switch (tipo) {
            case Constantes.INTEGER:
                return "0";
            case Constantes.REAL:
                return "0.0";
            case Constantes.CHAR:
                return "0";
            case Constantes.BOOLEAN:
                return "0";
            default:
                return "-1";
        }
    }

    public static String temporales() {
        StringBuilder sb = new StringBuilder();
        sb.append("p,h");
        for (int i = 0; i < t; i++) {
            sb.append(",t").append(i);
        }
        sb.append("\n");
        return sb.toString();
    }

}
