package servidor.compilador.expresion.aritmetica;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Binario;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Primitivo;
import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public class Potencia extends Binario {

    public Potencia(Expresion uno, Expresion dos) {
        super(uno, dos);
    }

    @Override
    public Valor gen(Entorno entorno) {
        Valor izq = uno.gen(entorno);
        if (izq.esError()) {
            return izq;
        }
        Valor der = dos.gen(entorno);
        if (der.esError()) {
            return der;
        }
        switch (izq.tipo.tipo) {
            case Constantes.INTEGER:
                switch (der.tipo.tipo) {
                    case Constantes.INTEGER:
                        return numerico(entorno, izq, der, izq.tipo);
                    case Constantes.CHAR:
                        return numerico(entorno, izq, der, izq.tipo);
                    default:
                        //ERROR
                        error("No se puede operar integer - " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.REAL:
                switch (der.tipo.tipo) {
                    case Constantes.INTEGER:
                        return numerico(entorno, izq, der, der.tipo);
                    case Constantes.CHAR:
                        return numerico(entorno, izq, der, der.tipo);
                    default:
                        //ERROR
                        error("No se puede operar real - " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.CHAR:
                switch (der.tipo.tipo) {
                    case Constantes.INTEGER:
                        return numerico(entorno, izq, der, der.tipo);
                    case Constantes.CHAR:
                        return numerico(entorno, izq, der, new Primitivo(Constantes.INTEGER));
                    default:
                        //ERROR
                        error("No se puede operar char - " + der.tipo.tipo);
                        return Valor.error();
                }
            default:
                //ERROR
                error("No se puede operar - con " + der.tipo.tipo);
                return Valor.error();
        }
    }

    private Valor numerico(Entorno entorno, Valor izq, Valor der, Tipo tipo) {
        codigo(izq.codigo);
        codigo(der.codigo);
        String t1 = Codigo.t(), t2 = Codigo.t(entorno);
        suma(t1, "p", Integer.toString(entorno.tamaño() + 1));
        setStack(t1, izq.cadena);
        suma(t1, t1, "1");
        setStack(t1, der.cadena);
        suma("p", "p", Integer.toString(entorno.tamaño()));
        call("pot");
        getStack(t2, "p");
        resta("p", "p", Integer.toString(entorno.tamaño()));
        entorno.quitar(izq.cadena, der.cadena);
        return Valor.valor(tipo, t2, codigo());
    }

}
