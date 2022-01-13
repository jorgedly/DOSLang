package servidor.compilador.instruccion.tipo;

import servidor.compilador.Codigo;
import servidor.compilador.Control;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public class PreRango extends PreTipo {
    
    public Expresion expresion1;
    public Expresion expresion2;
    
    public PreRango(Expresion expresion1, Expresion expresion2) {
        super(expresion1.ubicacion);
        this.expresion1 = expresion1;
        this.expresion2 = expresion2;
    }
    
    @Override
    public Tipo resolver(Entorno entorno, StringBuilder codigo) {
        Valor valor1 = expresion1.gen(entorno);
        Valor valor2 = expresion2.gen(entorno);
        if (!valor1.esError() && !valor2.esError()) {
            if (valor1.tipo.esNumChar() && valor2.tipo.esNumChar()) {
                codigo.append(valor1.codigo);
                codigo.append(valor2.codigo);
                String t0 = Codigo.t(entorno);
                codigo.append("\t\t-,").append(valor2.cadena).append(",").append(valor1.cadena).append(",").append(t0).append("\n");
                codigo.append("\t\t+,").append(t0).append(",1,").append(t0).append("\n");
                Rango rango = new Rango(valor1.cadena, valor2.cadena, t0);
                entorno.quitar(valor1.cadena, valor2.cadena);
                return rango;
            } else {
                //ERROR
                Control.error(expresion1.ubicacion, "Los valores de rango deben ser integer, real o char");
            }
        }
        return new TError();
    }
    
}
