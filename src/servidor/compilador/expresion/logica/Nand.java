package servidor.compilador.expresion.logica;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Binario;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public class Nand extends Binario {

    public Nand(Expresion uno, Expresion dos) {
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
            case Constantes.BOOLEAN:
                switch (der.tipo.tipo) {
                    case Constantes.BOOLEAN:
                        codigo(izq.codigo);
                        if (!izq.listo) {
                            izq.verdadera = Codigo.l();
                            izq.falsa = Codigo.l();
                            igual(izq.cadena, "1", izq.verdadera);
                            jmp(izq.falsa);
                        }
                        etiqueta(izq.verdadera);
                        codigo(der.codigo);
                        if (!der.listo) {
                            der.verdadera = Codigo.l();
                            der.falsa = Codigo.l();
                            igual(der.cadena, "1", der.verdadera);
                            jmp(der.falsa);
                        }
                        return Valor.booleano(izq.tipo, izq.falsa + ":\n\t" + der.falsa, der.verdadera, codigo());
                    default:
                        //ERROR
                        error("No se puede operar boolean and " + der.tipo.tipo);
                        return Valor.error();
                }
            default:
                //ERROR
                error("No se puede operar and con " + der.tipo.tipo);
                return Valor.error();
        }
    }

}
