package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.DatoReporte;

/**
 *
 * @author Jorge
 */
public class TNil extends Tipo {

    public TNil() {
        super(Constantes.NIL);
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
