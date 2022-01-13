package servidor.compilador.preanalisis;

import servidor.compilador.Ubicacion;

/**
 *
 * @author Jorge
 */
public class Id {

    public String nombre;
    public Ubicacion ubicacion;

    public Id(String nombre, Ubicacion ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

}
