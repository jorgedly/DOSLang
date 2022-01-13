package servidor.compilador.expresion.aritmetica;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Unario;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public class Restau extends Unario {

    public Restau(Expresion uno) {
        super(uno);
    }

    @Override
    public Valor gen(Entorno entorno) {
        Valor izq = uno.gen(entorno);
        if (izq.esError()) {
            return izq;
        }
        switch (izq.tipo.tipo) {
            case Constantes.INTEGER:
                return numerico(izq, izq.tipo, entorno);
            case Constantes.REAL:
                return numerico(izq, izq.tipo, entorno);
            case Constantes.CHAR:
                return numerico(izq, izq.tipo, entorno);
            default:
                //ERROR
                error("No se puede operar - con " + izq.tipo.tipo);
                return Valor.error();
        }
    }

    private Valor numerico(Valor izq, Tipo tipo, Entorno entorno) {
        codigo(izq.codigo);
        String t1 = Codigo.t(entorno);
        multiplicacion(t1, izq.cadena, "-1");
        entorno.quitar(izq.cadena);
        return Valor.valor(tipo, t1, codigo());
    }

}
