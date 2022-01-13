package servidor.compilador.expresion.valor.nativas;

import servidor.compilador.Constantes;
import servidor.compilador.Control;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.Simbolo;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Primitivo;
import servidor.compilador.instruccion.tipo.Record;
import servidor.compilador.instruccion.tipo.Tipo;
import servidor.compilador.preanalisis.Id;

/**
 *
 * @author Jorge
 */
public class NSizeof extends Expresion {

    public Id id;

    public NSizeof(Ubicacion ubicacion, Id id) {
        super(ubicacion);
        this.id = id;
    }

    @Override
    public Valor gen(Entorno entorno) {
        if (entorno.buscarTipo(id.nombre)) {
            Tipo tipo = entorno.obtenerTipo(id.nombre);
            if (tipo instanceof Record) {
                Record record = (Record) tipo;
                return Valor.valor(new Primitivo(Constantes.INTEGER), Integer.toString(record.campos.size()), "");
            }
        }
        String pos = entorno.buscar(id.nombre);
        if (pos != null) {
            Simbolo simbolo = entorno.obtener(id.nombre);
            if (simbolo.tipo instanceof Record) {
                Record record = (Record) simbolo.tipo;
                return Valor.valor(new Primitivo(Constantes.INTEGER), Integer.toString(record.campos.size()), "");
            }
        }
        //ERROR
        Control.error(ubicacion, "El parametro de sizeof debe ser un nombre o variable de registro");
        return Valor.error();
    }

}
