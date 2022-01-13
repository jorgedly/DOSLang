package servidor.compilador.instruccion;

import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.Control;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.Simbolo;
import servidor.compilador.preanalisis.Id;

/**
 *
 * @author Jorge
 */
public class Read extends Instruccion {

    public Id id;

    public Read(Id id) {
        this.id = id;
    }

    @Override
    public String generar(Entorno entorno) {
        String pos = entorno.buscar(id.nombre);
        if (pos != null) {
            Simbolo simbolo = entorno.obtener(id.nombre);
            if (simbolo.tipo.esPrimitivo()) {
                String t1 = Codigo.t(), cad = t1, t2 = Codigo.t();
                suma(t1, pos, Integer.toString(simbolo.posicion));
                if (simbolo.referencia) {
                    getStack(t2, t1);
                    cad = t2;
                }
                String t3 = Codigo.t();
                if (simbolo.tipo.tipo.equals(Constantes.STRING) || simbolo.tipo.tipo.equals(Constantes.WORD)) {
                    suma(t3, "p", Integer.toString(entorno.tamaño() + 2));
                } else {
                    suma(t3, "p", Integer.toString(entorno.tamaño() + 1));
                }
                setStack(t3, cad);
                suma("p", "p", Integer.toString(entorno.tamaño()));
                call("$_in_value");
                resta("p", "p", Integer.toString(entorno.tamaño()));
                return codigo();
            } else {
                //ERROR
                Control.error(id.ubicacion, "El simbolo " + id.nombre + " no es primitivo");
            }
        } else {
            //ERROR
            Control.error(id.ubicacion, "No existe la variable " + id.nombre);
        }
        return "";
    }

}
