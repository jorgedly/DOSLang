package servidor.compilador.instruccion;

import java.util.ArrayList;
import servidor.compilador.Control;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Valor;
import servidor.compilador.expresion.valor.acceso.Acceso;
import servidor.compilador.instruccion.tipo.Record;

/**
 *
 * @author Jorge
 */
public class With extends Instruccion {

    public Acceso acceso;
    public ArrayList<Instruccion> instrucciones;

    public With(Acceso acceso, ArrayList<Instruccion> instrucciones) {
        this.acceso = acceso;
        this.instrucciones = instrucciones;
    }

    @Override
    public String generar(Entorno entorno) {
        Valor valor = acceso.gen(entorno);
        if (!valor.esError()) {
            if (valor.tipo instanceof Record) {
                entorno.registrosWith.push(valor);
                codigo(valor.codigo);
                codigo(GeneradorInstrucciones.generar(entorno, instrucciones));
                entorno.registrosWith.pop();
                return codigo();
            } else {
                //ERROR
                Control.error(acceso.ubicacion, "La variable debe ser un registro");
            }
        }
        return "";
    }

}
