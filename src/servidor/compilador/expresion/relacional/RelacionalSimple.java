package servidor.compilador.expresion.relacional;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Primitivo;
import servidor.compilador.instruccion.tipo.Rango;

/**
 *
 * @author Jorge
 */
public class RelacionalSimple extends Relacional {

    public RelacionalSimple(Expresion uno, Expresion dos, String operador) {
        super(uno, dos, operador);
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
                        return simple(izq, der);
                    case Constantes.REAL:
                        return simple(izq, der);
                    case Constantes.CHAR:
                        return simple(izq, der);
                    case Constantes.RANGO:
                        return intRango(izq, der);
                    default:
                        //ERROR
                        error("No se puede operar integer " + operador + " " + der.tipo.tipo);
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
                    case Constantes.RANGO:
                        return intRango(izq, der);
                    default:
                        //ERROR
                        error("No se puede operar real " + operador + " " + der.tipo.tipo);
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
                    case Constantes.RANGO:
                        return intRango(izq, der);
                    default:
                        //ERROR
                        error("No se puede operar char " + operador + " " + der.tipo.tipo);
                        return Valor.error();
                }
            case Constantes.RANGO:
                switch (der.tipo.tipo) {
                    case Constantes.INTEGER:
                        return rangoInt(izq, der);
                }
            default:
                //ERROR
                error("No se puede operar " + operador + " con " + izq.tipo.tipo);
                return Valor.error();
        }
    }

    private Valor rangoInt(Valor izq, Valor der) {
        Rango rango = (Rango) izq.tipo;
        codigo(izq.codigo);
        codigo(der.codigo);
        String verdadera = Codigo.l();
        String falsa = Codigo.l();
        switch (operador) {
            case "je":
                mayor(rango.inferior, der.cadena, falsa);
                menor(rango.superior, der.cadena, falsa);
                jmp(verdadera);
                break;
            case "jne":
                menor(rango.inferior, der.cadena, verdadera);
                mayor(rango.superior, der.cadena, verdadera);
                jmp(falsa);
                break;
            default:
                String op = opuesto();
                relacional(op, rango.inferior, der.cadena, falsa);
                relacional(op, rango.superior, der.cadena, falsa);
                jmp(verdadera);
                break;
        }
        return Valor.booleano(new Primitivo(Constantes.BOOLEAN), verdadera, falsa, codigo());
    }

    private Valor intRango(Valor izq, Valor der) {
        Rango rango = (Rango) der.tipo;
        codigo(izq.codigo);
        codigo(der.codigo);
        String verdadera = Codigo.l();
        String falsa = Codigo.l();
        switch (operador) {
            case "je":
                menor(izq.cadena, rango.inferior, falsa);
                mayor(izq.cadena, rango.superior, falsa);
                jmp(verdadera);
                break;
            case "jne":
                menor(izq.cadena, rango.inferior, verdadera);
                mayor(izq.cadena, rango.superior, verdadera);
                jmp(falsa);
                break;
            default:
                String op = opuesto();
                relacional(op, izq.cadena, rango.inferior, falsa);
                relacional(op, izq.cadena, rango.superior, falsa);
                jmp(verdadera);
                break;
        }
        return Valor.booleano(new Primitivo(Constantes.BOOLEAN), verdadera, falsa, codigo());
    }

    String opuesto() {
        switch (operador) {
            case "jg":
                return "jle";
            case "jl":
                return "jge";
            case "jge":
                return "jl";
            case "jle":
                return "jg";
            default:
                return "";
        }
    }

}
