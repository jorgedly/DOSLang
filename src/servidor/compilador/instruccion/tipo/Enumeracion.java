package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.DatoReporte;

/**
 *
 * @author Jorge
 */
public class Enumeracion extends Tipo {

    public ArrayList<String> nombres;

    public Enumeracion(ArrayList<String> nombres) {
        super(Constantes.ENUMERACION);
        this.nombres = nombres;
    }

    @Override
    public boolean comparar(Tipo dos) {
        if (dos instanceof Enumeracion) {
            Enumeracion enu = (Enumeracion) dos;
            if (nombres.size() == enu.nombres.size()) {
                for (int i = 0; i < nombres.size(); i++) {
                    if (!nombres.get(i).equals(enu.nombres.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        } else if (dos instanceof Enumerado) {
            return nombres.indexOf(dos.tipo) >= 0;
        }
        return false;
    }

    public int tiene(String nombre) {
        return nombres.indexOf(nombre);
    }

    @Override
    public ArrayList<String> posibles() {
        ArrayList<String> posibles = new ArrayList<>();
        posibles.add(tipo);
        posibles.add(Constantes.NIL);
        return posibles;
    }

    @Override
    public String firma() {
        return tipo;
    }

    @Override
    public DatoReporte reporte() {
        ArrayList<DatoReporte> hijos = new ArrayList<>();
        for (String s : nombres) {
            hijos.add(new DatoReporte(s, ""));
        }
        return new DatoReporte("", "filter_1", hijos);
    }

}
