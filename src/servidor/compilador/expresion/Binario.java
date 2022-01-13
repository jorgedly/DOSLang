package servidor.compilador.expresion;

/**
 *
 * @author Jorge
 */
public abstract class Binario extends Expresion {

    public Expresion uno;
    public Expresion dos;

    public Binario(Expresion uno, Expresion dos) {
        super(uno.ubicacion);
        this.uno = uno;
        this.dos = dos;
    }

}
