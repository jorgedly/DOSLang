package servidor.compilador.expresion.relacional;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.expresion.Binario;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Primitivo;

/**
 *
 * @author Jorge
 */
public abstract class Relacional extends Binario {

    public String operador;

    public Relacional(Expresion uno, Expresion dos, String operador) {
        super(uno, dos);
        this.operador = operador;
    }

    public Valor simple(Valor izq, Valor der) {
        String verdadera = Codigo.l();
        String falsa = Codigo.l();
        codigo(izq.codigo);
        codigo(der.codigo);
        relacional(operador, izq.cadena, der.cadena, verdadera);
        jmp(falsa);
        return Valor.booleano(new Primitivo(Constantes.BOOLEAN), verdadera, falsa, codigo());
    }

}
