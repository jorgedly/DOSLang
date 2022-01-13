package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;

/**
 *
 * @author Jorge
 */
public class PreArreglo extends PreTipo {

    public ArrayList<PreDimension> predimensiones;
    public PreTipo pretipo;

    public PreArreglo(Ubicacion ubicacion, ArrayList<PreDimension> dimensiones, PreTipo pretipo) {
        super(ubicacion);
        this.predimensiones = dimensiones;
        this.pretipo = pretipo;
    }

    @Override
    public Tipo resolver(Entorno entorno, StringBuilder codigo) {
        Tipo tipoDato = pretipo.resolver(entorno, codigo);
        ArrayList<Dimension> dimensiones = new ArrayList<>();
        for (PreDimension predimension : predimensiones) {
            Dimension dim = predimension.colocar(entorno, codigo);
            if (dim != null) {
                dimensiones.add(dim);
            }
        }
        return new Arreglo(dimensiones, tipoDato);
    }

}
