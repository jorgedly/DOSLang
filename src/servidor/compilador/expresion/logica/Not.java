package servidor.compilador.expresion.logica;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Unario;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public class Not extends Unario {

    public Not(Expresion uno) {
        super(uno);
    }

    @Override
    public Valor gen(Entorno entorno) {
        Valor izq = uno.gen(entorno);
        if (izq.esError()) {
            return izq;
        }
        if (izq.tipo.tipo.equals(Constantes.BOOLEAN)) {
            codigo(izq.codigo);
            if (!izq.listo) {
                izq.verdadera = Codigo.l();
                izq.falsa = Codigo.l();
                igual(izq.cadena, "1", izq.verdadera);
                jmp(izq.falsa);
            }
            return Valor.booleano(izq.tipo, izq.falsa, izq.verdadera, codigo());
        } else {
            //ERROR
            error("No se puede operar not con " + izq.tipo.tipo);
            return Valor.error();
        }
    }

}
