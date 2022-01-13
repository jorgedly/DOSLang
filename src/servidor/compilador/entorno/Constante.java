package servidor.compilador.entorno;

import java.util.ArrayList;
import servidor.compilador.Constantes;
import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public class Constante extends Simbolo {

    public Constante(String nombre, Tipo tipo) {
        super(nombre, tipo, Constantes.CONSTANTE, false);
    }

    @Override
    public DatoReporte reporte() {
        ArrayList<DatoReporte> hijos = new ArrayList<>();
        hijos.add(new DatoReporte("Tipo: " + tipo.tipo, ""));
        hijos.add(new DatoReporte("Posicion: " + Integer.toString(this.posicion), ""));
        return new DatoReporte(nombre, "crop_square", hijos);
    }

}
