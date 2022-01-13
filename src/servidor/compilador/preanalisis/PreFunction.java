package servidor.compilador.preanalisis;

import java.util.ArrayList;
import servidor.compilador.Codigo;
import servidor.compilador.Control;
import servidor.compilador.Generador;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.Parametro;
import servidor.compilador.entorno.Variable;
import servidor.compilador.instruccion.Instruccion;
import servidor.compilador.instruccion.tipo.PreTipo;
import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public class PreFunction extends PreSubrutina {

    public PreTipo pretipo;

    public PreFunction(
            Id id,
            ArrayList<PreParametro> preparametros,
            PreTipo pretipo,
            ArrayList<DeclaracionTipo> pretipos,
            ArrayList<DeclaracionConstantes> constantes,
            ArrayList<DeclaracionVariables> variables,
            ArrayList<PreSubrutina> presubrutinas,
            ArrayList<Instruccion> instrucciones) {
        super(id, preparametros, pretipos, constantes, variables, presubrutinas, instrucciones);
        this.pretipo = pretipo;
    }

    @Override
    public void primera(Entorno entorno, StringBuilder codigo) {
        Tipo tipo = pretipo.resolver(entorno, codigo);
        if (!tipo.esError()) {
            ArrayList<String> tipos_firma = new ArrayList<>();
            ArrayList<ArrayList<String>> lista_posibles = new ArrayList<>();
            Entorno nuevo = new Entorno(entorno);
            nuevo.salida = Codigo.l();
            nuevo.nulo = Codigo.l();
            nuevo.cero = Codigo.l();
            entorno.hijos.add(nuevo);
            nuevo.agregar(new Variable(id.nombre, tipo));
            for (PreParametro preparametro : preparametros) {
                Tipo tipo_param = preparametro.pretipo.resolver(entorno, codigo);
                if (!tipo_param.esError()) {
                    for (Id idp : preparametro.ids) {
                        if (!nuevo.tiene(idp.nombre)) {
                            tipos_firma.add(tipo_param.firma());
                            Parametro parametro = new Parametro(idp.nombre, tipo_param, preparametro.referencia);
                            nuevo.agregar(parametro);
                            if (parametro.referencia) {
                                nuevo.contador++;
                            }
                            nuevo.parametros.add(parametro);
                            lista_posibles.add(tipo_param.posibles());
                        } else {
                            //ERROR
                            Control.error(idp.ubicacion, "Ya existe nombre " + idp.nombre);
                        }
                    }
                }
            }
            if (tipos_firma.isEmpty()) {
                firma = id.nombre;
            } else {
                firma = id.nombre + "_" + String.join("_", tipos_firma);
            }
            if (!entorno.tieneSubrutina(firma)) {
                nuevo.setNombre(firma);
                Function function = new Function(preparametros.size(), tipo, firma, nuevo, posibles(lista_posibles));
                entorno.agregarSubrutina(firma, function);
            } else {
                //ERROR
                Control.error(id.ubicacion, "Ya existe una subrutina con la misma firma " + this.firma);
            }
        }
    }

    @Override
    public String segunda(Entorno entorno) {
        if (entorno.tieneSubrutina(firma)) {
            Subrutina subrutina = entorno.obtenerSubrutina(firma);
            if (subrutina instanceof Function) {
                Function function = (Function) subrutina;
                return new Generador().generar(
                        function.entorno.nombre,
                        pretipos,
                        constantes,
                        variables,
                        prefunciones,
                        instrucciones,
                        function.entorno);
            }
        }
        return "";
    }

}
