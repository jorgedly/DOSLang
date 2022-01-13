package servidor.compilador.entorno;

import java.util.ArrayList;
import servidor.compilador.Constantes;
import servidor.compilador.instruccion.tipo.Arreglo;
import servidor.compilador.instruccion.tipo.Record;
import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public class Parametro extends Simbolo {

    public Parametro(String nombre, Tipo tipo, boolean referencia) {
        //super(nombre, tipo, Constantes.PARAMETRO, referencia);
        super(nombre, tipo, Constantes.PARAMETRO, tipo instanceof Arreglo || tipo instanceof Record ? false : referencia);
    }

    @Override
    public DatoReporte reporte() {
        ArrayList<DatoReporte> hijos = new ArrayList<>();
        hijos.add(new DatoReporte("Tipo: "+tipo.tipo, ""));
        hijos.add(new DatoReporte("Posicion: "+Integer.toString(this.posicion), ""));
        hijos.add(new DatoReporte(referencia ? "Por referencia" : "Por valor", ""));
        return new DatoReporte(nombre, "lens", hijos);
    }

}
