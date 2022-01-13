package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.DatoReporte;

/**
 *
 * @author Jorge
 */
public class Rango extends Tipo {

    public String inferior;
    public String cantidad;
    public String superior;

    public Rango(String inferior, String superior, String cantidad) {
        super(Constantes.RANGO);
        this.inferior = inferior;
        this.superior = superior;
        this.cantidad = cantidad;
    }

    @Override
    public boolean comparar(Tipo dos) {
        return false;
    }

    @Override
    public ArrayList<String> posibles() {
        return new ArrayList<>();
    }

    @Override
    public String firma() {
        return tipo;
    }

    @Override
    public DatoReporte reporte() {
        ArrayList<DatoReporte> hijos = new ArrayList<>();
        hijos.add(new DatoReporte("Tipo: " + tipo, ""));
        return new DatoReporte("", "layers", hijos);
    }

}
