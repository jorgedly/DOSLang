package servidor.compilador;

/**
 *
 * @author Jorge
 */
public class CError {

    public String tipo;
    public String descripcion;
    public String archivo;
    public int fila;
    public int columna;

    public CError(String tipo, String descripcion, String archivo, int fila, int columna) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.fila = fila;
        this.columna = columna;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getArchivo() {
        return archivo;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

}
