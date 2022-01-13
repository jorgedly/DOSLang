package servidor.compilador.instruccion;

import servidor.compilador.Codigo;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.expresion.valor.acceso.Acceso;

/**
 *
 * @author Jorge
 */
public class Asignacion extends Instruccion {

    public Acceso acceso;
    public Expresion expresion;

    public Asignacion(Acceso acceso, Expresion expresion) {
        this.acceso = acceso;
        this.expresion = expresion;
    }

    @Override
    public String generar(Entorno entorno) {
        Valor valor = expresion.gen(entorno);
        if (!valor.esError()) {
            codigo(valor.codigo);
            if (valor.esBoolean() && valor.listo) {
                valor.cadena = Codigo.t(entorno);
                String salida = Codigo.l();
                etiqueta(valor.verdadera);
                asignacion(valor.cadena, "1");
                jmp(salida);
                etiqueta(valor.falsa);
                asignacion(valor.cadena, "0");
                etiqueta(salida);
            }
            codigo(acceso.asignacion(entorno, valor));
            return codigo();
        }
        return "";
    }

}
