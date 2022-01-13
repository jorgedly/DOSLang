package servidor.compilador.preanalisis;

import java.util.ArrayList;
import servidor.compilador.Control;
import servidor.compilador.Codigo;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.Variable;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.PreTipo;
import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public class DeclaracionVariables2 extends DeclaracionVariables {

    public PreTipo pretipo;
    public Expresion expresion;

    public DeclaracionVariables2(ArrayList<Id> ids, PreTipo pretipo, Expresion expresion) {
        super(ids);
        this.pretipo = pretipo;
        this.expresion = expresion;
    }

    @Override
    public String almacenar(Entorno entorno, StringBuilder codigo) {
        Valor valor = expresion.gen(entorno);
        if (!valor.esError()) {
            Tipo tipo = pretipo.resolver(entorno, codigo);
            if (!tipo.esError()) {
                if (tipo.comparar(valor.tipo)) {
                    codigo(valor.codigo);
                    if (valor.tipo.esBoolean()) {
                        valor.cadena = Codigo.t();
                        String salida = Codigo.l();
                        etiqueta(valor.verdadera);
                        asignacion(valor.cadena, "1");
                        jmp(salida);
                        etiqueta(valor.falsa);
                        asignacion(valor.cadena, "0");
                        etiqueta(salida);
                    }
                    for (Id id : ids) {
                        if (!entorno.tiene(id.nombre)) {
                            Variable variable = new Variable(id.nombre, tipo);
                            entorno.agregar(variable);
                            String t1 = Codigo.t();
                            suma(t1, "p", Integer.toString(variable.posicion));
                            setStack(t1, valor.cadena);
                        } else {
                            //ERROR
                            Control.error(id.ubicacion, "Ya existe la constante " + id.nombre);
                        }
                    }
                    entorno.quitar(valor.cadena);
                } else {
                    //ERROR
                    Control.error(expresion.ubicacion, "El tipo de la expresion no es compatible con el tipo declarado");
                }
            }
        }
        return codigo();
    }

}
