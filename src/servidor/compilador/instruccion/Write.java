package servidor.compilador.instruccion;

import java.util.ArrayList;
import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public abstract class Write extends Instruccion {

    public ArrayList<Expresion> argumentos;

    public Write(ArrayList<Expresion> argumentos) {
        this.argumentos = argumentos;
    }

    public void resolver(Entorno entorno) {
        ArrayList<Valor> valores = new ArrayList<>();
        for (Expresion expresion : argumentos) {
            Valor valor = expresion.gen(entorno);
            if (!valor.esError()) {
                valores.add(valor);
            }
        }
        for (Valor valor : valores) {
            codigo(valor.codigo);
            switch (valor.tipo.tipo) {
                case Constantes.INTEGER:
                    printInt(valor.cadena);
                    break;
                case Constantes.REAL:
                    printDec(valor.cadena);
                    break;
                case Constantes.CHAR:
                    String s = Codigo.l();
                    menor(valor.cadena, "0", s);
                    printChar(valor.cadena);
                    etiqueta(s);
                    break;
                case Constantes.BOOLEAN:
                    booleano(valor);
                    break;
                case Constantes.STRING:
                case Constantes.WORD:
                    suma("p", "p", Integer.toString(entorno.tamaño()));
                    setStack("p", valor.cadena);
                    call("printString");
                    resta("p", "p", Integer.toString(entorno.tamaño()));
                    break;
                default:
                    puntero(valor);
                    break;
            }
        }
    }

    public void puntero(Valor valor) {
        String nonulo = Codigo.l(), salida = Codigo.l();
        mayorigual(valor.cadena, "0", nonulo);
        printChar("110");
        printChar("105");
        printChar("108");
        jmp(salida);
        etiqueta(nonulo);
        for (int c : valor.tipo.tipo.toCharArray()) {
            printChar(Integer.toString(c));
        }
        printChar("64");
        printInt(valor.cadena);
        etiqueta(salida);
    }

    public void booleano(Valor valor) {
        if (valor.listo) {
            String l1 = Codigo.l();
            etiqueta(valor.verdadera);
            String strue = "true";
            for (int c : strue.toCharArray()) {
                printChar(Integer.toString(c));
            }
            jmp(l1);
            etiqueta(valor.falsa);
            String sfalse = "false";
            for (int c : sfalse.toCharArray()) {
                printChar(Integer.toString(c));
            }
            etiqueta(l1);
        } else {
            String l1 = Codigo.l(), l2 = Codigo.l();
            igual(valor.cadena, "1", l1);
            String sfalse = "false";
            for (int c : sfalse.toCharArray()) {
                printChar(Integer.toString(c));
            }
            jmp(l2);
            etiqueta(l1);
            String strue = "true";
            for (int c : strue.toCharArray()) {
                printChar(Integer.toString(c));
            }
            etiqueta(l2);
        }
    }

}
