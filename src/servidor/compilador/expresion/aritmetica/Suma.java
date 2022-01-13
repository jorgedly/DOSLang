package servidor.compilador.expresion.aritmetica;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.CreadorNativa;
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
public class Suma extends Binario {

    public Suma(Expresion uno, Expresion dos) {
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
                    case Constantes.WORD:
                        return numeroString(izq, der, entorno, "enteroString");
                    case Constantes.STRING:
                        return numeroString(izq, der, entorno, "enteroString");
                    default:
                        //ERROR
                        error("No se puede operar integer + " + der.tipo.tipo);
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
                    case Constantes.WORD:
                        return numeroString(izq, der, entorno, "decimalString");
                    case Constantes.STRING:
                        return numeroString(izq, der, entorno, "decimalString");
                    default:
                        //ERROR
                        error("No se puede operar real + " + der.tipo.tipo);
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
                    case Constantes.WORD:
                        return numeroString(izq, der, entorno, "enteroString");
                    case Constantes.STRING:
                        return numeroString(izq, der, entorno, "enteroString");
                    default:
                        //ERROR
                        error("No se puede operar char + " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.WORD:
                switch (der.tipo.tipo) {
                    case Constantes.INTEGER:
                        return stringNumero(izq, der, entorno, "enteroString");
                    case Constantes.REAL:
                        return stringNumero(izq, der, entorno, "decimalString");
                    case Constantes.CHAR:
                        return stringNumero(izq, der, entorno, "enteroString");
                    case Constantes.WORD:
                        return stringString(izq, der, entorno);
                    case Constantes.STRING:
                        return stringString(izq, der, entorno);
                    case Constantes.BOOLEAN:
                        return stringBoolean(izq, der, entorno);
                    default:
                        //ERROR
                        error("No se puede operar word + " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.STRING:
                switch (der.tipo.tipo) {
                    case Constantes.INTEGER:
                        return stringNumero(izq, der, entorno, "enteroString");
                    case Constantes.REAL:
                        return stringNumero(izq, der, entorno, "decimalString");
                    case Constantes.CHAR:
                        return stringNumero(izq, der, entorno, "enteroString");
                    case Constantes.WORD:
                        return stringString(izq, der, entorno);
                    case Constantes.STRING:
                        return stringString(izq, der, entorno);
                    case Constantes.BOOLEAN:
                        return stringBoolean(izq, der, entorno);
                    default:
                        //ERROR
                        error("No se puede operar string + " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.BOOLEAN:
                switch (der.tipo.tipo) {
                    case Constantes.STRING:
                        return booleanString(izq, der, entorno);
                    case Constantes.WORD:
                        return booleanString(izq, der, entorno);
                    default:
                        //ERROR
                        error("No se puede operar boolean + " + der.tipo.tipo);
                        return Valor.error();
                }
            default:
                //ERROR
                error("No se puede operar + con " + der.tipo.tipo);
                return Valor.error();
        }
    }

    private Valor numerico(Valor izq, Valor der, Tipo tipo, Entorno entorno) {
        String t1 = Codigo.t(entorno);
        codigo(izq.codigo);
        codigo(der.codigo);
        suma(t1, izq.cadena, der.cadena);
        entorno.quitar(izq.cadena, der.cadena);
        return Valor.valor(tipo, t1, codigo());
    }

    private Valor stringNumero(Valor izq, Valor der, Entorno entorno, String proc) {
        codigo(izq.codigo);
        codigo(der.codigo);
        String cad = numero(der, entorno, proc);
        String t1 = Codigo.t(), t2 = Codigo.t(entorno);
        suma(t1, "p", Integer.toString(entorno.tamaño() + 1));
        setStack(t1, izq.cadena);
        suma(t1, t1, "1");
        setStack(t1, cad);
        suma("p", "p", Integer.toString(entorno.tamaño()));
        call("concat");
        getStack(t2, "p");
        resta("p", "p", Integer.toString(entorno.tamaño()));
        entorno.quitar(izq.cadena, der.cadena);
        return Valor.valor(izq.tipo, t2, codigo());
    }

    private Valor numeroString(Valor izq, Valor der, Entorno entorno, String proc) {
        codigo(izq.codigo);
        codigo(der.codigo);
        String cad = numero(izq, entorno, proc);
        String t1 = Codigo.t(), t2 = Codigo.t(entorno);
        suma(t1, "p", Integer.toString(entorno.tamaño() + 1));
        setStack(t1, cad);
        suma(t1, t1, "1");
        setStack(t1, der.cadena);
        suma("p", "p", Integer.toString(entorno.tamaño()));
        call("concat");
        getStack(t2, "p");
        resta("p", "p", Integer.toString(entorno.tamaño()));
        entorno.quitar(izq.cadena, der.cadena);
        return Valor.valor(der.tipo, t2, codigo());
    }

    private String numero(Valor val, Entorno entorno, String proc) {
        String t1 = Codigo.t(), t2 = Codigo.t();
        suma(t1, "p", Integer.toString(entorno.tamaño() + 1));
        setStack(t1, val.cadena);
        suma("p", "p", Integer.toString(entorno.tamaño()));
        call(proc);
        getStack(t2, "p");
        resta("p", "p", Integer.toString(entorno.tamaño()));
        return t2;
    }

    private Valor stringString(Valor izq, Valor der, Entorno entorno) {
        codigo(izq.codigo);
        codigo(der.codigo);
        String t1 = Codigo.t(), t2 = Codigo.t(entorno);
        suma(t1, "p", Integer.toString(entorno.tamaño() + 1));
        setStack(t1, izq.cadena);
        suma(t1, t1, "1");
        setStack(t1, der.cadena);
        suma("p", "p", Integer.toString(entorno.tamaño()));
        call("concat");
        getStack(t2, "p");
        resta("p", "p", Integer.toString(entorno.tamaño()));
        entorno.quitar(izq.cadena, der.cadena);
        return Valor.valor(izq.tipo, t2, codigo());
    }

    private Valor booleanString(Valor izq, Valor der, Entorno entorno) {
        String cad = Codigo.t(entorno);
        codigo(izq.codigo);
        if (izq.listo) {
            String salida = Codigo.l();
            etiqueta(izq.verdadera);
            asignacion(cad, CreadorNativa.TTRUE);
            jmp(salida);
            etiqueta(izq.falsa);
            asignacion(cad, CreadorNativa.TFALSE);
            etiqueta(salida);
        } else {
            String l1 = Codigo.l();
            asignacion(cad, CreadorNativa.TTRUE);
            igual(izq.cadena, "1", l1);
            asignacion(cad, CreadorNativa.TFALSE);
            etiqueta(l1);
            entorno.quitar(izq.cadena);
        }
        codigo(der.codigo);
        entorno.quitar(cad);
        String t1 = Codigo.t(), t2 = Codigo.t(entorno);
        suma(t1, "p", Integer.toString(entorno.tamaño() + 1));
        setStack(t1, cad);
        suma(t1, t1, "1");
        setStack(t1, der.cadena);
        suma("p", "p", Integer.toString(entorno.tamaño()));
        call("concat");
        getStack(t2, "p");
        resta("p", "p", Integer.toString(entorno.tamaño()));
        entorno.quitar(der.cadena);
        return Valor.valor(der.tipo, t2, codigo());
    }

    private Valor stringBoolean(Valor izq, Valor der, Entorno entorno) {
        codigo(izq.codigo);
        codigo(der.codigo);
        String cad = Codigo.t();
        if (der.listo) {
            String salida = Codigo.l();
            etiqueta(der.verdadera);
            asignacion(cad, CreadorNativa.TTRUE);
            jmp(salida);
            etiqueta(der.falsa);
            asignacion(cad, CreadorNativa.TFALSE);
            etiqueta(salida);
        } else {
            String l1 = Codigo.l();
            asignacion(cad, CreadorNativa.TTRUE);
            igual(der.cadena, "1", l1);
            asignacion(cad, CreadorNativa.TFALSE);
            etiqueta(l1);
            entorno.quitar(der.cadena);
        }
        String t1 = Codigo.t(), t2 = Codigo.t(entorno);
        suma(t1, "p", Integer.toString(entorno.tamaño() + 1));
        setStack(t1, izq.cadena);
        suma(t1, t1, "1");
        setStack(t1, cad);
        suma("p", "p", Integer.toString(entorno.tamaño()));
        call("concat");
        getStack(t2, "p");
        resta("p", "p", Integer.toString(entorno.tamaño()));
        entorno.quitar(izq.cadena);
        return Valor.valor(izq.tipo, t2, codigo());
    }

}
