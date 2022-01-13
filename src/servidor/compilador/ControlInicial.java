package servidor.compilador;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import servidor.Archivo;
import servidor.Message;
import servidor.Paquete;
import servidor.analizadores.Parser;
import servidor.analizadores.Scanner;
import servidor.compilador.entorno.DatoReporte;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.TiposPrimitivos;
import servidor.compilador.preanalisis.Id;
import servidor.compilador.preanalisis.PreProgram;

/**
 *
 * @author Jorge
 */
public class ControlInicial {

    public Message generar(Paquete paquete) {
        Control.errores.clear();
        Generador.nulocero = paquete.nulocero;
        ArrayList<PreProgram> preprogramas = new ArrayList<>();
        int indice = 0;
        for (Archivo archivo : paquete.archivos) {
            try {
                StringReader reader = new StringReader(archivo.contenido.concat("¼"));
                Scanner scanner = new Scanner(reader, archivo.nombre);
                Parser parser = new Parser(scanner, archivo.nombre);
                parser.parse();
                if (archivo.nombre.equals(paquete.nombre)) {
                    indice = preprogramas.size();
                }
                preprogramas.addAll(parser.preprogramas);
            } catch (Exception ex) {
                //ex.printStackTrace();
                Control.error(new Ubicacion(0, 0, archivo.nombre), "Existen errores lexicos/sintacticos");
            }
        }
        if (preprogramas.size() > 0) {
            return resolverPrograms(preprogramas, indice);
        }
        return new Message("@", Control.errores, new DatoReporte("", ""));
    }

    public Message resolverPrograms(ArrayList<PreProgram> preprogramas, int indice) {
        HashMap<String, PreProgram> preps = new HashMap<>();
        for (PreProgram preprograma : preprogramas) {
            if (!preps.containsKey(preprograma.id.nombre)) {
                preps.put(preprograma.id.nombre, preprograma);
            } else {
                //ERROR
                Control.error(preprograma.id.ubicacion, "Ya existe el programa " + preprograma.id.nombre);
            }
        }
        PreProgram primero = preprogramas.get(indice);
        if (!primero.usados.isEmpty()) {
            HashSet<String> nombres = new HashSet<>();
            nombres.add(primero.id.nombre);
            resolver(primero, primero.usados, nombres, preps);
        }
        Codigo.reiniciar();
        CreadorNativa.reiniciar();
        TiposPrimitivos.iniciarPrimitivos();
        Entorno global = new Entorno(null);
        global.salida = Codigo.l();
        global.nulo = Codigo.l();
        global.cero = Codigo.l();
        String fragmento = new Generador().generar(
                "main",
                primero.tipos,
                primero.constantes,
                primero.variables,
                primero.presubrutinas,
                primero.instrucciones,
                global);
        String nativas = new CreadorNativa().generar();
        String temporales = Codigo.temporales();
        global.nombre = "global";
        DatoReporte datos = global.reporte();
        String codigo = new StringBuilder()
                .append(temporales)
                .append(nativas)
                .append(fragmento)
                .toString();
        //escribir(codigo);
        return new Message(
                codigo,
                Control.errores,
                datos);
    }

    private void resolver(PreProgram primero, ArrayList<Id> ids, HashSet<String> nombres, HashMap<String, PreProgram> preprogramas) {
        for (Id id : ids) {
            if (!nombres.contains(id.nombre)) {
                nombres.add(id.nombre);
                if (preprogramas.containsKey(id.nombre)) {
                    PreProgram preprograma = preprogramas.get(id.nombre);
                    primero.tipos.addAll(preprograma.tipos);
                    primero.constantes.addAll(preprograma.constantes);
                    primero.variables.addAll(preprograma.variables);
                    primero.presubrutinas.addAll(preprograma.presubrutinas);
                    //primero.instrucciones.addAll(preprograma.instrucciones);
                    if (!preprograma.usados.isEmpty()) {
                        resolver(primero, preprograma.usados, nombres, preprogramas);
                    }
                } else {
                    //ERROR
                    Control.error(id.ubicacion, "No existe program " + id.nombre);
                }
            }
        }
    }

}
