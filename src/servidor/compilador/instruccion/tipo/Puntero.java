package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.DatoReporte;

/**
 *
 * @author Jorge
 */
public class Puntero extends Tipo {

    public Puntero() {
        super(Constantes.PUNTERO);
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
        return "";
    }

    @Override
    public DatoReporte reporte() {
        return new DatoReporte("", "");
    }

}
