package servidor.compilador.expresion.valor.acceso;

import java.util.ArrayList;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public class Acceso extends Expresion {

    public ArrayList<Elemento> elementos;

    public Acceso(Elemento elemento) {
        super(elemento.ubicacion);
        elementos = new ArrayList<>();
        elementos.add(elemento);
    }

    @Override
    public Valor gen(Entorno entorno) {
        Elemento elemento1 = elementos.get(0);
        Valor valor = elemento1.primero(entorno);
        if (elementos.size() > 1) {
            for (int i = 1; i < elementos.size(); i++) {
                Elemento elemento = elementos.get(i);
                valor = elemento.segundo(entorno, valor);
            }
        }
        return valor;
    }

    public String asignacion(Entorno entorno, Valor valor) {
        if (elementos.size() == 1) {
            Elemento elemento1 = elementos.get(0);
            return elemento1.asignacionPrimera(entorno, valor);
        } else {
            Elemento elemento1 = elementos.get(0);
            Valor val = elemento1.primero(entorno);
            for (int i = 1; i < elementos.size() - 1; i++) {
                Elemento elemento = elementos.get(i);
                val = elemento.segundo(entorno, val);
            }
            return elementos.get(elementos.size() - 1).asignacionSegunda(entorno, valor, val);
        }
    }

    public String liberar(Entorno entorno) {
        if (elementos.size() == 1) {
            Elemento elemento1 = elementos.get(0);
            return elemento1.liberacionPrimera(entorno);
        } else {
            Elemento elemento1 = elementos.get(0);
            Valor val = elemento1.primero(entorno);
            for (int i = 1; i < elementos.size() - 1; i++) {
                Elemento elemento = elementos.get(i);
                val = elemento.segundo(entorno, val);
            }
            return elementos.get(elementos.size() - 1).liberacionSegunda(entorno, val);
        }
    }

}
