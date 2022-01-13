package servidor.compilador.expresion.valor.nativas;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.Control;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Primitivo;

/**
 *
 * @author Jorge
 */
public class NCharat extends Expresion {
    
    public Expresion uno;
    public Expresion dos;
    
    public NCharat(Ubicacion ubicacion, Expresion uno, Expresion dos) {
        super(ubicacion);
        this.uno = uno;
        this.dos = dos;
    }
    
    @Override
    public Valor gen(Entorno entorno) {
        Valor cad = uno.gen(entorno);
        if (!cad.esError()) {
            if (cad.tipo.tipo.equals(Constantes.STRING) || cad.tipo.tipo.equals(Constantes.WORD)) {
                Valor ind = dos.gen(entorno);
                if (!ind.esError()) {
                    if (ind.tipo.esNumerico()) {
                        codigo(cad.codigo);
                        codigo(ind.codigo);
                        String t1 = Codigo.t(), t2 = Codigo.t(entorno);
                        suma(t1, "p", Integer.toString(entorno.tamaño() + 1));
                        setStack(t1, cad.cadena);
                        suma(t1, t1, "1");
                        setStack(t1, ind.cadena);
                        suma("p", "p", Integer.toString(entorno.tamaño()));
                        call("charat");
                        getStack(t2, "p");
                        resta("p", "p", Integer.toString(entorno.tamaño()));
                        entorno.quitar(cad.cadena, ind.cadena);
                        return Valor.valor(new Primitivo(Constantes.CHAR), t2, codigo());
                    } else {
                        //ERROR
                        Control.error(ubicacion, "La expresion debe ser integer");
                    }
                }
            } else {
                //ERROR
                Control.error(ubicacion, "La expresion debe ser string o word");
            }
        }
        return Valor.error();
    }
    
}
