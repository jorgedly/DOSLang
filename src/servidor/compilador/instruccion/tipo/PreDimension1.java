package servidor.compilador.instruccion.tipo;

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
public class PreDimension1 extends PreDimension {

    public Expresion expresion1;
    public Expresion expresion2;

    public PreDimension1(Expresion expresion1, Expresion expresion2) {
        this.expresion1 = expresion1;
        this.expresion2 = expresion2;
    }

    @Override
    public Dimension colocar(Entorno entorno, StringBuilder codigo) {
        Valor uno = expresion1.gen(entorno);
        Valor dos = expresion2.gen(entorno);
        if (uno.tipo.tipo.equals(Constantes.INTEGER) && dos.tipo.tipo.equals(Constantes.INTEGER)) {
            codigo.append(uno.codigo).append(dos.codigo);
            String t1 = Codigo.t(entorno);
            codigo.append("\t\t-,").append(dos.cadena).append(",").append(uno.cadena).append(",").append(t1).append("\n");
            entorno.quitar(uno.cadena, dos.cadena);
            return new Dimension(uno.cadena, t1);
        } else {
            //ERROR
            Control.error(expresion1.ubicacion, "Los limites deben ser de tipo integer");
        }
        return new Dimension("", "");
    }

}
