package servidor.compilador.expresion;

/**
 *
 * @author Jorge
 */
public abstract class Unario extends Expresion {

    public Expresion uno;

    public Unario(Expresion uno) {
        super(uno.ubicacion);
        this.uno = uno;
    }

}
