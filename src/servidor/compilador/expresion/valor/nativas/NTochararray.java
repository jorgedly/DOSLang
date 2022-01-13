package servidor.compilador.expresion.valor.nativas;

import java.util.ArrayList;
import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.Control;
import servidor.compilador.Generador;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Arreglo;
import servidor.compilador.instruccion.tipo.Dimension;
import servidor.compilador.instruccion.tipo.Primitivo;

/**
 *
 * @author Jorge
 */
public class NTochararray extends Expresion {

    public Expresion expresion;

    public NTochararray(Ubicacion ubicacion, Expresion expresion) {
        super(ubicacion);
        this.expresion = expresion;
    }

    @Override
    public Valor gen(Entorno entorno) {
        Valor cad = expresion.gen(entorno);
        if (!cad.esError()) {
            if (cad.tipo.tipo.equals(Constantes.STRING) || cad.tipo.tipo.equals(Constantes.WORD)) {
                codigo(cad.codigo);
                String t1 = Codigo.t(entorno), t2 = Codigo.t(), t3 = Codigo.t(), t4 = Codigo.t();
                String l1 = Codigo.l(), l2 = Codigo.l();
                if(Generador.nulocero) {
                    menor(cad.cadena, "0", entorno.nulo);
                }
                asignacion(t1, "h");
                suma("h", "h", "1");
                setHeap(t1, "h");
                asignacion(t2, cad.cadena);
                asignacion(t4, "0");
                etiqueta(l2);
                getHeap(t3, t2);
                menor(t3, "0", l1);
                setHeap("h", t3);
                suma("h", "h", "1");
                suma(t2, t2, "1");
                suma(t4, t4, "1");
                jmp(l2);
                etiqueta(l1);
                ArrayList<Dimension> dims = new ArrayList<>();
                dims.add(new Dimension("0", t4));
                Arreglo arreglo = new Arreglo(dims, new Primitivo(Constantes.CHAR));
                entorno.quitar(cad.cadena);
                return Valor.valor(arreglo, t1, codigo());
            } else {
                //ERROR
                Control.error(ubicacion, "La expresion debe ser string o word");
            }
        }
        return Valor.error();
    }

}
