package servidor.compilador.preanalisis;

import java.util.ArrayList;
import servidor.compilador.instruccion.Instruccion;

/**
 *
 * @author Jorge
 */
public class PreProgram {

    public Id id;
    public ArrayList<Id> usados;
    public ArrayList<DeclaracionTipo> tipos;
    public ArrayList<DeclaracionConstantes> constantes;
    public ArrayList<DeclaracionVariables> variables;
    public ArrayList<PreSubrutina> presubrutinas;
    public ArrayList<Instruccion> instrucciones;

    public PreProgram(
            Id id,
            ArrayList<Id> usados,
            ArrayList<DeclaracionTipo> tipos,
            ArrayList<DeclaracionConstantes> constantes,
            ArrayList<DeclaracionVariables> variables,
            ArrayList<PreSubrutina> presubrutinas,
            ArrayList<Instruccion> instrucciones) {
        this.id = id;
        this.usados = usados;
        this.tipos = tipos;
        this.constantes = constantes;
        this.variables = variables;
        this.presubrutinas = presubrutinas;
        this.instrucciones = instrucciones;
    }

}
