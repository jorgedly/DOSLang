package servidor.compilador.instruccion;

import java.util.ArrayList;
import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.Control;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.Simbolo;
import servidor.compilador.entorno.Variable;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Primitivo;
import servidor.compilador.preanalisis.Id;

/**
 *
 * @author Jorge
 */
public class For extends Instruccion {

    public Id id;
    public Expresion expresion1;
    public Expresion expresion2;
    public boolean aumenta;
    public ArrayList<Instruccion> instrucciones;

    public For(Id id, Expresion expresion1, Expresion expresion2, boolean aumenta, ArrayList<Instruccion> intrucciones) {
        this.id = id;
        this.expresion1 = expresion1;
        this.expresion2 = expresion2;
        this.aumenta = aumenta;
        this.instrucciones = intrucciones;
    }

    @Override
    public String generar(Entorno entorno) {
        Valor val1 = expresion1.gen(entorno);
        Valor val2 = expresion2.gen(entorno);
        if (!val1.esError() && !val2.esError()) {
            if (val1.tipo.esNumerico() && val2.tipo.esNumerico()) {
                String pos = entorno.buscar(id.nombre);
                Simbolo simbolo;
                boolean quita = false;
                if (pos != null) {
                    simbolo = entorno.obtener(id.nombre);
                    if (!simbolo.tipo.comparar(val1.tipo)) {
                        //ERROR
                        Control.error(id.ubicacion, "El simbolo " + id.nombre + " no es númerico");
                        return "";
                    }
                } else {
                    quita = true;
                    simbolo = new Variable(id.nombre, new Primitivo(Constantes.INTEGER));
                    entorno.agregar(simbolo);
                    pos = "p";
                }
                codigo(val1.codigo);
                codigo(val2.codigo);
                String t1 = Codigo.t(entorno), t2 = Codigo.t(), t3 = t2;
                String l1 = Codigo.l(), l2 = Codigo.l(), l3 = Codigo.l();
                entorno.inicios.push(l3);
                entorno.salidas.push(l2);
                asignacion(t1, val1.cadena);
                suma(t2, pos, Integer.toString(simbolo.posicion));
                if (simbolo.referencia) {
                    t3 = Codigo.t(entorno);
                    getStack(t3, t2);
                }
                setStack(t3, t1);
                etiqueta(l1);
                if (aumenta) {
                    mayor(t1, val2.cadena, l2);
                } else {
                    menor(t1, val2.cadena, l2);
                }
                entorno.quitar(val1.cadena, val2.cadena);
                codigo(GeneradorInstrucciones.generar(entorno, instrucciones));
                etiqueta(l3);
                if (aumenta) {
                    suma(t1, t1, "1");
                } else {
                    resta(t1, t1, "1");
                }
                setStack(t3, t1);
                jmp(l1);
                etiqueta(l2);
                entorno.inicios.pop();
                entorno.salidas.pop();
                if (quita) {
                    entorno.quitarVariable(id.nombre);
                }
                entorno.quitar(t1, t3);
                return codigo();
            } else {
                //ERROR
                Control.error(expresion1.ubicacion, "Las expresiones deben ser numéricas");
            }
        }
        return "";
    }

}
