package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.DatoReporte;

/**
 *
 * @author Jorge
 */
public abstract class Tipo {
    //tiene metodos que reciben otro tipo para saber si el instanceof es del mismo
    //si si, y si no es primitivo realizar pruebas de tamaño y numero de dimensiones
    //numero de campos,...

    public String tipo;

    public Tipo(String tipo) {
        this.tipo = tipo;
    }

    public abstract boolean comparar(Tipo dos);

    public abstract ArrayList<String> posibles();

    public boolean esPrimitivo() {
        switch (tipo) {
            case Constantes.INTEGER:
            case Constantes.REAL:
            case Constantes.BOOLEAN:
            case Constantes.CHAR:
            case Constantes.STRING:
            case Constantes.WORD:
                return true;
            default:
                return false;
        }
    }

    public boolean esNumerico() {
        return tipo.equals(Constantes.INTEGER) || tipo.equals(Constantes.REAL);
    }

    public boolean esNumChar() {
        return tipo.equals(Constantes.INTEGER) || tipo.equals(Constantes.REAL) || tipo.equals(Constantes.CHAR);
    }

    public boolean esError() {
        return tipo.equals(Constantes.ERROR);
    }

    public boolean esBoolean() {
        return tipo.equals(Constantes.BOOLEAN);
    }

    public abstract String firma();

    public abstract DatoReporte reporte();

}
