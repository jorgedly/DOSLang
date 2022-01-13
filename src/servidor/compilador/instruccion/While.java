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
public class While extends Instruccion {

    public Expresion expresion;
    public ArrayList<Instruccion> instrucciones;

    public While(Expresion expresion, ArrayList<Instruccion> instrucciones) {
        this.expresion = expresion;
        this.instrucciones = instrucciones;
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
                String inicio = Codigo.l();
                etiqueta(inicio);
                codigo(valor.codigo);
                etiqueta(valor.verdadera);
                entorno.salidas.add(valor.falsa);
                entorno.inicios.add(inicio);
                codigo(GeneradorInstrucciones.generar(entorno, instrucciones));
                jmp(inicio);
                etiqueta(valor.falsa);
                entorno.salidas.pop();
                entorno.inicios.pop();
            } else {
                //ERROR
                Control.error(expresion.ubicacion, "La condicion debe ser boolean");
            }
        }
        return codigo();
    }

}
