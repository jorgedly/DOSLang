package servidor.compilador;

/**
 *
 * @author Jorge
 */
public class Constantes {

    public static final String ENUMERACION = "enum";
    public static final String ARRAY = "array";
    public static final String RECORD = "record";
    public static final String VOID = "void";
    public static final String RANGO = "rango";
    public static final String PUNTERO = "puntero";

    public static final String INTEGER = "integer";
    public static final String REAL = "real";
    public static final String CHAR = "char";
    public static final String BOOLEAN = "boolean";
    public static final String STRING = "string";
    public static final String WORD = "word";
    public static final String ERROR = "error";
    public static final String NIL = "nil";

    public static final int SIZEOF = 1;
    public static final int MALLOC = 2;
    public static final int FREE = 3;
    public static final int CHARAT = 4;
    public static final int LENGTH = 5;
    public static final int REPLACE = 6;
    public static final int TOCHARARRAY = 7;
    public static final int TOLOWERCASE = 8;
    public static final int TOUPPERCASE = 9;
    public static final int EQUALS = 10;
    public static final int TRUNK = 11;
    public static final int ROUND = 12;

    public static final String TRUE = "true";
    public static final String FALSE = "false";

    public static final int CONSTANTE = 1;
    public static final int VARIABLE = 2;
    public static final int TIPO = 3;
    public static final int PARAMETRO = 4;

    public static final String FUNCION = "Funcion";
    public static final String PROCEDIMIENTO = "Procedimiento";

    public static final String rolString(int rol) {
        switch (rol) {
            case 1:
                return "Constante";
            case 2:
                return "Variable";
            case 4:
                return "Parametro";
            default:
                return "";
        }
    }

    public static final int EERROR = 0;
    public static final int OK = 1;

    public static final String LEXICO = "Léxico";
    public static final String SINTACTICO = "Sintáctico";
    public static final String SEMANTICO = "Semántico";

}
