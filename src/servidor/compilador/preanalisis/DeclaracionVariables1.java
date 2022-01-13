package servidor.compilador.preanalisis;

import java.util.ArrayList;
import servidor.compilador.Control;
import servidor.compilador.Codigo;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.Variable;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Arreglo;
import servidor.compilador.instruccion.tipo.PreTipo;
import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public class DeclaracionVariables1 extends DeclaracionVariables {

    public PreTipo pretipo;

    public DeclaracionVariables1(ArrayList<Id> ids, PreTipo pretipo) {
        super(ids);
        this.pretipo = pretipo;
    }

    @Override
    public String almacenar(Entorno entorno, StringBuilder codigo) {
        Tipo tipo = pretipo.resolver(entorno, codigo);
        if (!tipo.esError()) {
            String valor = Codigo.defecto(tipo.tipo);
            for (Id id : ids) {
                if (!entorno.tiene(id.nombre)) {
                    Variable variable = new Variable(id.nombre, tipo);
                    entorno.agregar(variable);
                    String t1 = Codigo.t();
                    suma(t1, "p", Integer.toString(variable.posicion));
                    if (tipo instanceof Arreglo) {
                        Arreglo arreglo = (Arreglo) tipo;
                        Valor varreglo = arreglo.instancia();
                        codigo(varreglo.codigo);
                        valor = varreglo.referencia;
                    }
                    setStack(t1, valor);
                } else {
                    //ERROR
                    Control.error(id.ubicacion, "Ya existe el nombre " + id.nombre);
                }
            }
        }
        return codigo();
    }

}
