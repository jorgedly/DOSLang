package servidor.compilador.instruccion;

import java.util.ArrayList;
import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public class BloqueInstrucciones extends Instruccion {

    public ArrayList<Instruccion> instrucciones;

    public BloqueInstrucciones(ArrayList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    @Override
    public String generar(Entorno entorno) {
        return "";
    }

}
