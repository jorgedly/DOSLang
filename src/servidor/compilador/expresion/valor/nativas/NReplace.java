package servidor.compilador.expresion.valor.nativas;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.Control;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public class NReplace extends Expresion {

    public Expresion uno;
    public Expresion dos;

    public NReplace(Ubicacion ubicacion, Expresion uno, Expresion dos) {
        super(ubicacion);
        this.uno = uno;
        this.dos = dos;
    }

    @Override
    public Valor gen(Entorno entorno) {
        Valor cad1 = uno.gen(entorno);
        Valor cad2 = dos.gen(entorno);
        if (!cad1.esError() && !cad2.esError()) {
            if ((cad1.tipo.tipo.equals(Constantes.STRING) || cad1.tipo.tipo.equals(Constantes.WORD)) && (cad2.tipo.tipo.equals(Constantes.STRING) || cad2.tipo.tipo.equals(Constantes.WORD))) {
                codigo(cad1.codigo);
                codigo(cad2.codigo);
                String t1 = Codigo.t(), t2 = Codigo.t(entorno);
                suma(t1, "p", Integer.toString(entorno.tamaño() + 1));
                setStack(t1, cad1.cadena);
                suma(t1, t1, "1");
                setStack(t1, cad2.cadena);
                suma("p", "p", Integer.toString(entorno.tamaño()));
                call("reemplazar");
                getStack(t2, "p");
                resta("p", "p", Integer.toString(entorno.tamaño()));
                entorno.quitar(cad1.cadena, cad2.cadena);
                return Valor.valor(cad1.tipo, t2, codigo());
            } else {
                //ERROR
                Control.error(ubicacion, "Las expresiones deben ser string o word");
            }
        }
        return Valor.error();
    }

}
