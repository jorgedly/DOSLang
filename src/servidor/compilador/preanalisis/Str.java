package servidor.compilador.preanalisis;

import servidor.compilador.Ubicacion;

/**
 *
 * @author Jorge
 */
public class Str {

    public String valor;
    public Ubicacion ubicacion;

    public Str(String valor, Ubicacion ubicacion) {
        this.valor = valor;
        this.ubicacion = ubicacion;
    }

}
