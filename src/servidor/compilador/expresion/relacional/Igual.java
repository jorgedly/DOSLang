package servidor.compilador.expresion.relacional;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Primitivo;

/**
 *
 * @author Jorge
 */
public class Igual extends Relacional {

    public Igual(Expresion uno, Expresion dos) {
        super(uno, dos, "je");
    }

    @Override
    public Valor gen(Entorno entorno) {
        Valor izq = uno.gen(entorno);
        Valor der = dos.gen(entorno);
        if (izq.esError()) {
            return izq;
        }
        if (der.esError()) {
            return der;
        }
        switch (izq.tipo.tipo) {
            case Constantes.INTEGER:
                switch (der.tipo.tipo) {
                    case Constantes.INTEGER:
                        return simple(izq, der);
                    case Constantes.REAL:
                        return simple(izq, der);
                    case Constantes.CHAR:
                        return simple(izq, der);
                    default:
                        //ERROR
                        error("No se puede operar integer = " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.REAL:
                switch (der.tipo.tipo) {
                    case Constantes.INTEGER:
                        return simple(izq, der);
                    case Constantes.REAL:
                        return simple(izq, der);
                    case Constantes.CHAR:
                        return simple(izq, der);
                    default:
                        //ERROR
                        error("No se puede operar real = " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.CHAR:
                switch (der.tipo.tipo) {
                    case Constantes.INTEGER:
                        return simple(izq, der);
                    case Constantes.REAL:
                        return simple(izq, der);
                    case Constantes.CHAR:
                        return simple(izq, der);
                    default:
                        //ERROR
                        error("No se puede operar char = " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.BOOLEAN:
                switch (der.tipo.tipo) {
                    case Constantes.BOOLEAN:
                        return booleanos(izq, der, entorno);
                    default:
                        //ERROR
                        error("No se puede operar boolean = " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.STRING:
                switch (der.tipo.tipo) {
                    case Constantes.STRING:
                        return strings(izq, der, entorno);
                    case Constantes.WORD:
                        return strings(izq, der, entorno);
                    default:
                        //ERROR
                        error("No se puede operar string = " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.WORD:
                switch (der.tipo.tipo) {
                    case Constantes.STRING:
                        return strings(izq, der, entorno);
                    case Constantes.WORD:
                        return strings(izq, der, entorno);
                    default:
                        //ERROR
                        error("No se puede operar word = " + der.tipo.tipo);
                        return Valor.error();
                }
        }
        if (izq.tipo.comparar(der.tipo)) {
            codigo(izq.codigo);
            codigo(der.codigo);
            String verdadera = Codigo.l(), falsa = Codigo.l();
            igual(izq.cadena, der.cadena, verdadera);
            jmp(falsa);
            return Valor.booleano(new Primitivo(Constantes.BOOLEAN), verdadera, falsa, codigo());
        }
        return Valor.error();
    }

    private Valor booleanos(Valor izq, Valor der, Entorno entorno) {
        codigo(izq.codigo);
        if (izq.listo) {
            String l1 = Codigo.l();
            izq.cadena = Codigo.t(entorno);
            etiqueta(izq.verdadera);
            asignacion(izq.cadena, "1");
            jmp(l1);
            etiqueta(izq.falsa);
            asignacion(izq.cadena, "0");
            etiqueta(l1);
        }
        codigo(der.codigo);
        if (der.listo) {
            String l2 = Codigo.l();
            der.cadena = Codigo.t();
            etiqueta(der.verdadera);
            asignacion(der.cadena, "1");
            jmp(l2);
            etiqueta(der.falsa);
            asignacion(der.cadena, "0");
            etiqueta(l2);
        }
        String verdadera = Codigo.l(), falsa = Codigo.l();
        igual(izq.cadena, der.cadena, verdadera);
        jmp(falsa);
        entorno.quitar(izq.cadena, der.cadena);
        return Valor.booleano(izq.tipo, verdadera, falsa, codigo());
    }

    private Valor strings(Valor izq, Valor der, Entorno entorno) {
        codigo(izq.codigo);
        codigo(der.codigo);
        String t1 = Codigo.t(), t2 = Codigo.t();
        suma(t1, "p", Integer.toString(entorno.tamaño() + 1));
        setStack(t1, izq.cadena);
        suma(t1, t1, "1");
        setStack(t1, der.cadena);
        suma("p", "p", Integer.toString(entorno.tamaño()));
        call("stringIgual");
        getStack(t2, "p");
        resta("p", "p", Integer.toString(entorno.tamaño()));
        String verdadera = Codigo.l(), falsa = Codigo.l();
        igual(t2, "1", verdadera);
        jmp(falsa);
        entorno.quitar(izq.cadena, der.cadena);
        return Valor.booleano(new Primitivo(Constantes.BOOLEAN), verdadera, falsa, codigo());
    }

}
