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
public class Resta extends Binario {
    
    public Resta(Expresion uno, Expresion dos) {
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
                        return numerico(izq, der, izq.tipo, entorno);
                    case Constantes.REAL:
                        return numerico(izq, der, der.tipo, entorno);
                    case Constantes.CHAR:
                        return numerico(izq, der, izq.tipo, entorno);
                    default:
                        //ERROR
                        error("No se puede operar integer - " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.REAL:
                switch (der.tipo.tipo) {
                    case Constantes.INTEGER:
                        return numerico(izq, der, der.tipo, entorno);
                    case Constantes.REAL:
                        return numerico(izq, der, der.tipo, entorno);
                    case Constantes.CHAR:
                        return numerico(izq, der, der.tipo, entorno);
                    default:
                        //ERROR
                        error("No se puede operar real - " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.CHAR:
                switch (der.tipo.tipo) {
                    case Constantes.INTEGER:
                        return numerico(izq, der, der.tipo, entorno);
                    case Constantes.REAL:
                        return numerico(izq, der, der.tipo, entorno);
                    case Constantes.CHAR:
                        return numerico(izq, der, new Primitivo(Constantes.INTEGER), entorno);
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
    
    private Valor numerico(Valor izq, Valor der, Tipo tipo, Entorno entorno) {
        String t1 = Codigo.t(entorno);
        codigo(izq.codigo);
        codigo(der.codigo);
        resta(t1, izq.cadena, der.cadena);
        entorno.quitar(izq.cadena, der.cadena);
        return Valor.valor(tipo, t1, codigo());
    }
    
}
