package servidor.compilador.instruccion;

import java.util.ArrayList;
import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.Control;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public class Switch extends Instruccion {

    public Expresion expresion;
    public ArrayList<Case> casos;
    public ArrayList<Instruccion> defecto;

    public Switch(Expresion expresion, ArrayList<Case> casos, ArrayList<Instruccion> defecto) {
        this.expresion = expresion;
        this.casos = casos;
        this.defecto = defecto;
    }

    @Override
    public String generar(Entorno entorno) {
        Valor valor = expresion.gen(entorno);
        if (valor.tipo.esPrimitivo()) {
            codigo(valor.codigo);
            if (valor.listo) {
                String l1 = Codigo.l();
                valor.cadena = Codigo.t(entorno);
                etiqueta(valor.verdadera);
                asignacion(valor.cadena, "1");
                jmp(l1);
                etiqueta(valor.falsa);
                asignacion(valor.cadena, "0");
                etiqueta(l1);
            }
            ArrayList<ArrayList<Valor>> lista_valores = new ArrayList<>();
            for (Case caso : casos) {
                ArrayList<Valor> valores = new ArrayList<>();
                for (Expresion expCase : caso.expresiones) {
                    Valor valExp = expCase.gen(entorno);
                    if (valor.tipo.comparar(valExp.tipo)) {
                        valores.add(valExp);
                    } else {
                        //ERROR
                        Control.error(expCase.ubicacion, "La expresion no es compatible con la expresion inicial");
                    }
                }
                lista_valores.add(valores);
            }
            ArrayList<String> etiquetas = new ArrayList<>();
            for (ArrayList<Valor> valores : lista_valores) {
                String etiqueta = Codigo.l();
                etiquetas.add(etiqueta);
                for (Valor val : valores) {
                    codigo(val.codigo);
                    switch (val.tipo.tipo) {
                        case Constantes.STRING:
                        case Constantes.WORD:
                            strings(valor, val, entorno, etiqueta);
                            break;
                        case Constantes.BOOLEAN:
                            booleanos(valor, val, etiqueta, entorno);
                            break;
                        default:
                            igual(valor.cadena, val.cadena, etiqueta);
                            entorno.quitar(val.cadena);
                            break;
                    }
                }
            }
            entorno.quitar(valor.cadena);
            String def = Codigo.l();
            jmp(def);
            String salida = Codigo.l();
            entorno.salidas.push(salida);
            if (casos.size() == etiquetas.size()) {
                for (int i = 0; i < casos.size(); i++) {
                    Case caso = casos.get(i);
                    etiqueta(etiquetas.get(i));
                    codigo(GeneradorInstrucciones.generar(entorno, caso.instrucciones));
                    jmp(salida);
                }
            }
            etiqueta(def);
            if (!defecto.isEmpty()) {
                codigo(GeneradorInstrucciones.generar(entorno, defecto));
            }
            etiqueta(salida);
            entorno.salidas.pop();
            return codigo();
        } else {
            //ERROR
            Control.error(expresion.ubicacion, "La expresion debe ser primitivo");
        }
        return "";
    }

    private void strings(Valor exp, Valor val, Entorno entorno, String etiqueta) {
        String t1 = Codigo.t(), t2 = Codigo.t();
        suma(t1, "p", Integer.toString(entorno.tamaño() + 1));
        setStack(t1, exp.cadena);
        suma(t1, t1, "1");
        setStack(t1, val.cadena);
        suma("p", "p", Integer.toString(entorno.tamaño()));
        call("stringIgual");
        getStack(t2, "p");
        resta("p", "p", Integer.toString(entorno.tamaño()));
        igual(t2, "1", etiqueta);
        entorno.quitar(val.cadena);
    }

    private void booleanos(Valor exp, Valor val, String etiqueta, Entorno entorno) {
        if (val.listo) {
            String l2 = Codigo.l();
            val.cadena = Codigo.t();
            etiqueta(val.verdadera);
            asignacion(val.cadena, "1");
            jmp(l2);
            etiqueta(val.falsa);
            asignacion(val.cadena, "0");
            etiqueta(l2);
        }
        igual(exp.cadena, val.cadena, etiqueta);
        entorno.quitar(val.cadena);
    }

}
