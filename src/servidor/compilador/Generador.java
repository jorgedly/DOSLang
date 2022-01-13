package servidor.compilador;

import java.util.ArrayList;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.instruccion.GeneradorInstrucciones;
import servidor.compilador.instruccion.Instruccion;
import servidor.compilador.preanalisis.DeclaracionConstantes;
import servidor.compilador.preanalisis.DeclaracionTipo;
import servidor.compilador.preanalisis.DeclaracionVariables;
import servidor.compilador.preanalisis.PreSubrutina;

/**
 *
 * @author Jorge
 */
public class Generador {

    public StringBuilder codigo;
    public StringBuilder codigo2;
    public ArrayList<String> fragmentos;
    public static boolean nulocero = true;

    public Generador() {
        codigo = new StringBuilder();
        codigo2 = new StringBuilder();
        fragmentos = new ArrayList<>();
    }

    public String generar(
            String nombre,
            ArrayList<DeclaracionTipo> pretipos,
            ArrayList<DeclaracionConstantes> preconstantes,
            ArrayList<DeclaracionVariables> prevariables,
            ArrayList<PreSubrutina> presubrutinas,
            ArrayList<Instruccion> instrucciones,
            Entorno entorno
    ) {
        codigo.append("begin,,,").append(nombre).append("\n");
        if (nombre.equals("main")) {
            codigo.append("\t\tcall,,,constantes\n");
        }
        entorno.inicio = Codigo.t(entorno);
        codigo.append("\t\t=,p,,").append(entorno.inicio).append("\n");
        almacenarTipos(entorno, pretipos);
        almacenarConstantes(entorno, preconstantes);
        almacenarNombresSubrutinas(entorno, presubrutinas);
        almacenarVariables(entorno, prevariables);
        generarCodigo(entorno, instrucciones);
        almacenarSubrutinas(entorno, presubrutinas);
        codigo.append("end,,,").append(nombre).append("\n\n");
        return new StringBuilder()
                .append(codigo2.toString())
                .append(codigo.toString())
                .toString();
    }

    private void almacenarTipos(Entorno entorno, ArrayList<DeclaracionTipo> declaraciones) {
        for (DeclaracionTipo declaracion : declaraciones) {
            declaracion.almacenar(entorno, codigo);
        }
        for (DeclaracionTipo declaracion : declaraciones) {
            declaracion.declarar(entorno, codigo);
        }
    }

    private void almacenarNombresSubrutinas(Entorno entorno, ArrayList<PreSubrutina> presubrutinas) {
        for (PreSubrutina presubrutina : presubrutinas) {
            presubrutina.primera(entorno, codigo);
        }
    }

    private void almacenarConstantes(Entorno entorno, ArrayList<DeclaracionConstantes> decConstantes) {
        for (DeclaracionConstantes declaracion : decConstantes) {
            codigo.append(declaracion.almacenar(entorno, codigo));
        }
    }

    private void almacenarVariables(Entorno entorno, ArrayList<DeclaracionVariables> decVariables) {
        for (DeclaracionVariables declaracion : decVariables) {
            codigo.append(declaracion.almacenar(entorno, codigo));
        }
    }

    private void almacenarSubrutinas(Entorno entorno, ArrayList<PreSubrutina> presubrutinas) {
        for (PreSubrutina presubrutina : presubrutinas) {
            codigo2.append(presubrutina.segunda(entorno));
        }
    }

    private void generarCodigo(Entorno entorno, ArrayList<Instruccion> instrucciones) {
        codigo.append(GeneradorInstrucciones.generar(entorno, instrucciones));
        if (nulocero) {
            codigo.append("\t\tjmp,,,").append(entorno.salida).append("\n");
            codigo.append("\t").append(entorno.nulo).append(":\n");
            codigo.append("\t\tcall,,,printNullPointer\n");
            codigo.append("\t\tjmp,,,").append(entorno.salida).append("\n");
            codigo.append("\t").append(entorno.cero).append(":\n");
            codigo.append("\t\tcall,,,printZeroDivision\n");
        }
        codigo.append("\t").append(entorno.salida).append(":\n");
    }

}
