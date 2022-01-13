package servidor.compilador.preanalisis;

import java.util.ArrayList;
import servidor.compilador.Control;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.instruccion.tipo.Enumeracion;
import servidor.compilador.instruccion.tipo.PreRecord;
import servidor.compilador.instruccion.tipo.PreTipo;
import servidor.compilador.instruccion.tipo.Record;
import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public class DeclaracionTipo1 extends DeclaracionTipo {

    public DeclaracionTipo1(ArrayList<Id> ids, PreTipo pretipo) {
        super(ids, pretipo);
    }

    @Override
    public void declarar(Entorno entorno, StringBuilder codigo) {
        if (pretipo instanceof PreRecord) {
            ((PreRecord) pretipo).terminar(entorno, codigo);
        }
    }

    @Override
    public void almacenar(Entorno entorno, StringBuilder codigo) {
        Tipo tipo = pretipo.resolver(entorno, codigo);
        if (!tipo.esError()) {
            for (Id id : ids) {
                if (!entorno.tieneTipo(id.nombre)) {
                    entorno.agregarTipo(id.nombre, tipo);
                    if (tipo instanceof Enumeracion) {
                        Enumeracion e = (Enumeracion) tipo;
                        entorno.enumeraciones.add(e);
                    } else if (tipo instanceof Record || tipo instanceof Enumeracion) {
                        tipo.tipo = id.nombre;
                    }
                } else {
                    //ERROR
                    Control.error(id.ubicacion, "Ya existe el tipo " + id.nombre);
                }
            }
        }
    }

}
