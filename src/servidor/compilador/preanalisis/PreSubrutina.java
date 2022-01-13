package servidor.compilador.preanalisis;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.instruccion.Instruccion;

/**
 *
 * @author Jorge
 */
public abstract class PreSubrutina {

    public Id id;
    public ArrayList<PreParametro> preparametros;
    public ArrayList<DeclaracionTipo> pretipos;
    public ArrayList<DeclaracionConstantes> constantes;
    public ArrayList<DeclaracionVariables> variables;
    public ArrayList<PreSubrutina> prefunciones;
    public ArrayList<Instruccion> instrucciones;
    public String firma;

    public PreSubrutina(
            Id id,
            ArrayList<PreParametro> preparametros,
            ArrayList<DeclaracionTipo> pretipos,
            ArrayList<DeclaracionConstantes> constantes,
            ArrayList<DeclaracionVariables> variables,
            ArrayList<PreSubrutina> presubrutinas,
            ArrayList<Instruccion> instrucciones) {
        this.id = id;
        this.preparametros = preparametros;
        this.pretipos = pretipos;
        this.constantes = constantes;
        this.variables = variables;
        this.prefunciones = presubrutinas;
        this.instrucciones = instrucciones;
        firma = "";
    }

    public abstract void primera(Entorno entorno, StringBuilder codigo);

    public abstract String segunda(Entorno entorno);

    public ArrayList<String> posibles(ArrayList<ArrayList<String>> lista_posibles) {
        ArrayList<String> posibles = new ArrayList<>();
        List<List<String>> resultados = Lists.cartesianProduct(lista_posibles);
        for (List<String> resultado : resultados) {
            posibles.add(id.nombre + "(" + String.join(",", resultado) + ")");
        }
        return posibles;
    }

}
