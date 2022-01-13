package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.Creador;
import servidor.compilador.entorno.DatoReporte;
import servidor.compilador.entorno.Simbolo;

/**
 *
 * @author Jorge
 */
public class Record extends Tipo {

    public LinkedHashMap<String, Simbolo> campos;
    public int tamaño;

    public Record() {
        super(Constantes.RECORD);
        this.campos = new LinkedHashMap<>();
        tamaño = 0;
    }

    @Override
    public boolean comparar(Tipo dos) {
        if (dos instanceof Record) {
            Record rec = (Record) dos;
            return campos.equals(rec.campos);
        } else if (dos instanceof Puntero) {
            return true;
        }
        return dos instanceof TNil;
    }

    @Override
    public ArrayList<String> posibles() {
        ArrayList<String> posibles = new ArrayList<>();
        posibles.add(tipo);
        posibles.add(Constantes.NIL);
        return posibles;
    }

    public void defectos(StringBuilder codigo, String inicio) {
        codigo.append(new Defecto().defectos(inicio, campos));
    }

    @Override
    public String firma() {
        return tipo;
    }

    @Override
    public DatoReporte reporte() {
        ArrayList<DatoReporte> hijos = new ArrayList<>();
        for (Simbolo s : campos.values()) {
            DatoReporte dr = new DatoReporte(s.nombre, "");
            dr.children.add(new DatoReporte("Tipo: " + s.tipo.tipo, ""));
            hijos.add(dr);
        }
        return new DatoReporte("", "widgets", hijos);
    }

}

class Defecto extends Creador {

    public String defectos(String inicio, LinkedHashMap<String, Simbolo> simbolos) {
        String t1 = Codigo.t();
        asignacion(t1, inicio);
        for (Simbolo sim : simbolos.values()) {
            setHeap(t1, Codigo.defecto(sim.tipo.tipo));
            suma(t1, t1, "1");
        }
        return codigo();
    }
}
