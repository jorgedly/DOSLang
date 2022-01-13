package servidor.compilador.instruccion;

import java.util.ArrayList;
import servidor.compilador.Control;
import servidor.compilador.Codigo;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public class Repeat extends Instruccion {

    public ArrayList<Instruccion> instrucciones;
    public Expresion expresion;

    public Repeat(ArrayList<Instruccion> instrucciones, Expresion expresion) {
        this.instrucciones = instrucciones;
        this.expresion = expresion;
    }

    @Override
    public String generar(Entorno entorno) {
        Valor valor = expresion.gen(entorno);
        if (!valor.esError()) {
            if (valor.esBoolean()) {
                if (!valor.listo) {
                    valor.verdadera = Codigo.l();
                    valor.falsa = Codigo.l();
                    igual(valor.cadena, "1", valor.verdadera);
                    jmp(valor.falsa);
                }
                String lcond = Codigo.l();
                etiqueta(valor.verdadera);
                entorno.salidas.add(valor.verdadera);
                entorno.inicios.add(lcond);
                codigo(GeneradorInstrucciones.generar(entorno, instrucciones));
                etiqueta(lcond);
                codigo(valor.codigo);
                etiqueta(valor.falsa);
                entorno.salidas.pop();
                entorno.inicios.pop();
            } else {
                //ERROR
                Control.error(expresion.ubicacion, "La expresion debe ser boolean");
            }
        }
        return codigo();
    }

}
