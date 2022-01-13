package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import servidor.compilador.entorno.DatoReporte;

/**
 *
 * @author Jorge
 */
public class Enumerado extends Tipo {

    public Enumerado(String tipo) {
        super(tipo);
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
        return new DatoReporte("", "");
    }

}
