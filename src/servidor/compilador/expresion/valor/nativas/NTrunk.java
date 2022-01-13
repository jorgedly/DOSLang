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
public class NTrunk extends Expresion {

    public Expresion expresion;

    public NTrunk(Ubicacion ubicacion, Expresion expresion) {
        super(ubicacion);
        this.expresion = expresion;
    }

    @Override
    public Valor gen(Entorno entorno) {
        Valor val = expresion.gen(entorno);
        if (!val.esError()) {
            if (val.tipo.esNumerico()) {
                codigo(val.codigo);
                String t1 = Codigo.t(entorno), t2 = Codigo.t();
                asignacion(t1, val.cadena);
                modulo(t2, t1, "1");
                resta(t1, t1, t2);
                entorno.quitar(val.cadena);
                return Valor.valor(new Primitivo(Constantes.INTEGER), t1, codigo());
            } else {
                //ERROR
                Control.error(ubicacion, "La expresion debe ser numerica");
            }
        }
        return Valor.error();
    }

}
