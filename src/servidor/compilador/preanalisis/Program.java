package servidor.compilador.preanalisis;

import java.util.ArrayList;

/**
 *
 * @author Jorge
 */
public class Program {

    public String nombre;
    public ArrayList<String> programas;

    public Program(String nombre) {
        this.nombre = nombre;
        programas = new ArrayList<>();
    }

}
