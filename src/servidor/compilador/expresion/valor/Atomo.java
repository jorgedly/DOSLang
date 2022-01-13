package servidor.compilador.expresion.valor;

import servidor.compilador.Constantes;
import servidor.compilador.Codigo;
import servidor.compilador.Ubicacion;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Primitivo;
import servidor.compilador.instruccion.tipo.TNil;

/**
 *
 * @author Jorge
 */
public class Atomo extends Expresion {

    public String valor;
    public String tipo;

    public Atomo(String valor, String tipo, Ubicacion ubicacion) {
        super(ubicacion);
        this.valor = valor;
        this.tipo = tipo;
    }

    @Override
    public Valor gen(Entorno entorno) {
        switch (tipo) {
            case Constantes.STRING:
                return vstring();
            case Constantes.CHAR:
                return vchar();
            case Constantes.BOOLEAN:
                return vboolean();
            case Constantes.NIL:
                return Valor.valor(new TNil(), "-1", "");
            default:
                return Valor.valor(new Primitivo(tipo), valor, "");
        }
    }

    private Valor vstring() {
        String t1 = Codigo.t();
        asignacion(t1, "h");
        for (int c : valor.toCharArray()) {
            setHeap("h", Integer.toString(c));
            suma("h", "h", "1");
        }
        setHeap("h", "-1");
        suma("h", "h", "1");
        return Valor.valor(new Primitivo(Constantes.STRING), t1, codigo());
    }

    private Valor vchar() {
        int v = valor.charAt(0);
        return Valor.valor(new Primitivo(Constantes.CHAR), Integer.toString(v), "");
    }

    private Valor vboolean() {
        String verdadera = Codigo.l();
        String falsa = Codigo.l();
        if (valor.equals(Constantes.TRUE)) {
            igual("1", "1", verdadera);
        } else {
            igual("1", "0", verdadera);
        }
        jmp(falsa);
        return Valor.booleano(new Primitivo(Constantes.BOOLEAN), verdadera, falsa, codigo());
    }

}
