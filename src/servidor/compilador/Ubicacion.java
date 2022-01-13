package servidor.compilador;

/**
 *
 * @author Jorge
 */
public class Ubicacion {

    public int fila;
    public int columna;
    public String archivo;

    public Ubicacion(int fila, int columna, String archivo) {
        this.fila = fila;
        this.columna = columna;
        this.archivo = archivo;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public String getArchivo() {
        return archivo;
    }

    @Override
    public String toString() {
        return fila + ":" + columna;
    }
}
