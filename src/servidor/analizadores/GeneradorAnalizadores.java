package servidor.analizadores;

/**
 *
 * @author Jorge
 */
public class GeneradorAnalizadores {

    public static void main(String[] args) {
        gen();
    }

    private static void gen() {
        try {
            String ruta = "src/servidor/analizadores/";
            String opcFlex[] = {ruta + "scanner.jflex", "-d", ruta};
            jflex.Main.generate(opcFlex);
            String opcCUP[] = {"-destdir", ruta, "-parser", "Parser", "-symbols", "Simbolos", ruta + "parser.cup"};
            java_cup.Main.main(opcCUP);
        } catch (Exception e) {
        }
    }

}
