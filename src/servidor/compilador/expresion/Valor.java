package servidor.compilador.expresion;

import servidor.compilador.instruccion.tipo.TError;
import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public class Valor {

    public Tipo tipo;
    public String verdadera;
    public String falsa;
    public String cadena;
    public String referencia;
    public String codigo;
    public boolean listo;
    public String estructura;

    private Valor(Tipo tipo, String cadena, String referencia, String verdadera, String falsa, boolean listo, String estructura, String codigo) {
        this.tipo = tipo;
        this.cadena = cadena;
        this.referencia = referencia;
        this.verdadera = verdadera;
        this.falsa = falsa;
        this.codigo = codigo;
        this.listo = listo;
        this.estructura = estructura;
    }

    public static Valor booleano(Tipo tipo, String verdadera, String falsa, String codigo) {
        return new Valor(tipo, null, null, verdadera, falsa, true, null, codigo);
    }

    /**
     * Este sirve para valor mapeado de arreglo
     *
     * @param tipo El tipo del valor mapeado
     * @param cadena Un temporal con la posicion mapeada
     * @return Un valor de posicion mapeada de arreglo
     */
    public static Valor valor(Tipo tipo, String cadena) {
        return new Valor(tipo, cadena, "", null, null, false, null, "");
    }

    //este puede tener conflictos
    /**
     * Este sirve para obtener el valor de una operacion aritmetica, que no
     * posee referencia
     *
     * @param tipo El tipo del valor mapeado
     * @param cadena Un temporal con la posicion mapeada
     * @param codigo El codigo generado de toda la expresion
     * @return Un valor de una operacion
     */
    public static Valor valor(Tipo tipo, String cadena, String codigo) {
        return new Valor(tipo, cadena, "", null, null, false, null, codigo);
    }

    public static Valor valor(Tipo tipo, String cadena, String referencia, String estructura, String codigo) {
        return new Valor(tipo, cadena, referencia, null, null, false, estructura, codigo);
    }

    public static Valor arreglo(String referencia, String codigo) {
        return new Valor(null, null, referencia, null, null, false, null, codigo);
    }

    public static Valor error() {
        return new Valor(new TError(), null, null, null, null, false, null, null);
    }

    public boolean esError() {
        return tipo.esError();
    }

    public boolean esBoolean() {
        return tipo.esBoolean();
    }

}
