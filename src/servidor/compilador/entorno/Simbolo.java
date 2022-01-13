package servidor.compilador.entorno;

import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public abstract class Simbolo {

    public String nombre;
    public Tipo tipo;
    public int rol;
    public int posicion;
    public boolean referencia;

    //metodos para saber si es arreglo
    public Simbolo(String nombre, Tipo tipo, int rol, boolean referencia) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.rol = rol;
        this.posicion = 1;
        this.referencia = referencia;
    }
    
    public abstract DatoReporte reporte();

}
