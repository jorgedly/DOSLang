package servidor.compilador;

/**
 *
 * @author Jorge
 */
public class CreadorNativa extends Creador {

    public static String TNIL = Codigo.t();
    public static String TTRUE = Codigo.t();
    public static String TFALSE = Codigo.t();

    public static void reiniciar() {
        TNIL = Codigo.t();
        TTRUE = Codigo.t();
        TFALSE = Codigo.t();
    }

    public String generar() {
        printString();
        concat();
        if (Generador.nulocero) {
            pot();
            stringIgual();
            nullPointer();
            zero();
            toUpper();
            toLower();
            charat();
            length();
            replace();
        }
        intStr();
        decStr();
        constantes();
        return codigo();
    }

    private void pot() {
        String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t(), t4 = Codigo.t(), t5 = Codigo.t(), t6 = Codigo.t();
        String l1 = Codigo.l(), l2 = Codigo.l(), l3 = Codigo.l(), l4 = Codigo.l();
        codigo("begin,,,pot\n");
        suma(t1, "p", "1");
        getStack(t2, t1);
        suma(t3, "p", "2");
        getStack(t4, t3);
        asignacion(t5, "1");
        igual(t4, "0", l1);
        asignacion(t5, t2);
        asignacion(t6, "1");
        mayor(t4, "0", l2);
        multiplicacion(t4, t4, "-1");
        asignacion(t6, "-1");
        etiqueta(l2);
        suma(t4, t4, "-1");
        etiqueta(l3);
        igual(t4, "0", l4);
        multiplicacion(t5, t5, t2);
        suma(t4, t4, "-1");
        jmp(l3);
        etiqueta(l4);
        mayor(t6, "0", l1);
        division(t5, "1", t5);
        etiqueta(l1);
        setStack("p", t5);
        codigo("end,,,pot\n\n");
    }

    private void printString() {
        String t1 = Codigo.t(), t2 = Codigo.t();
        String l1 = Codigo.l(), l2 = Codigo.l(), l3 = Codigo.l();
        codigo("begin,,,printString\n");
        getStack(t1, "p");
        igual(t1, "-1", l3);
        etiqueta(l1);
        getHeap(t2, t1);
        igual(t2, "-1", l2);
        printChar(t2);
        suma(t1, t1, "1");
        jmp(l1);
        etiqueta(l3);
        printChar("110");
        printChar("105");
        printChar("108");
        etiqueta(l2);
        codigo("end,,,printString\n\n");
    }

    private void concat() {
        String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t(), t4 = Codigo.t(), t5 = Codigo.t(), t6 = Codigo.t();
        String l1 = Codigo.l(), l2 = Codigo.l(), l3 = Codigo.l();
        codigo("begin,,,concat\n");
        setStack("p", "h");
        suma(t1, "p", "1");
        getStack(t2, t1);
        suma(t3, "p", "2");
        getStack(t4, t3);
        menor(t2, "0", l3);
        menor(t4, "0", l3);
        etiqueta(l1);
        getHeap(t5, t2);
        menor(t5, "0", l2);
        setHeap("h", t5);
        suma("h", "h", "1");
        suma(t2, t2, "1");
        jmp(l1);
        etiqueta(l2);
        getHeap(t6, t4);
        menor(t6, "0", l3);
        setHeap("h", t6);
        suma("h", "h", "1");
        suma(t4, t4, "1");
        jmp(l2);
        etiqueta(l3);
        setHeap("h", "-1");
        suma("h", "h", "1");
        codigo("end,,,concat\n\n");
    }

    private void stringIgual() {
        String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t(), t4 = Codigo.t(), t5 = Codigo.t(), t6 = Codigo.t();
        String l1 = Codigo.l(), l2 = Codigo.l(), l3 = Codigo.l();
        codigo("begin,,,stringIgual\n");
        setStack("p", "0");
        suma(t1, "p", "1");
        getStack(t2, t1);
        menor(t2, "0", l1);
        suma(t3, "p", "2");
        getStack(t4, t3);
        menor(t4, "0", l1);
        etiqueta(l2);
        getHeap(t5, t2);
        getHeap(t6, t4);
        distinto(t5, t6, l1);
        menor(t5, "0", l3);
        suma(t2, t2, "1");
        suma(t4, t4, "1");
        jmp(l2);
        etiqueta(l3);
        setStack("p", "1");
        etiqueta(l1);
        codigo("end,,,stringIgual\n\n");
    }

    private void nullPointer() {
        String n = "nullPointerException";
        codigo("begin,,,printNullPointer\n");
        printChar("10");
        for (int c : n.toCharArray()) {
            printChar(Integer.toString(c));
        }
        printChar("10");
        codigo("end,,,printNullPointer\n\n");
    }

    private void zero() {
        String n = "DivisionConCero";
        codigo("begin,,,printZeroDivision\n");
        printChar("10");
        for (int c : n.toCharArray()) {
            printChar(Integer.toString(c));
        }
        printChar("10");
        codigo("end,,,printZeroDivision\n\n");
    }

    private void toUpper() {
        String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t();
        String l1 = Codigo.l(), l2 = Codigo.l(), l3 = Codigo.l(), l4 = Codigo.l();
        codigo("begin,,,upper\n");
        setStack("p", "h");
        suma(t1, "p", "1");
        getStack(t2, t1);
        menor(t2, "0", l1);
        etiqueta(l2);
        getHeap(t3, t2);
        menor(t3, "0", l1);
        menor(t3, "97", l3);
        mayor(t3, "122", l3);
        resta(t3, t3, "32");
        setHeap("h", t3);
        jmp(l4);
        etiqueta(l3);
        setHeap("h", t3);
        etiqueta(l4);
        suma("h", "h", "1");
        suma(t2, t2, "1");
        jmp(l2);
        etiqueta(l1);
        setHeap("h", "-1");
        suma("h", "h", "1");
        codigo("end,,,upper\n\n");
    }

    private void toLower() {
        String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t();
        String l1 = Codigo.l(), l2 = Codigo.l(), l3 = Codigo.l(), l4 = Codigo.l();
        codigo("begin,,,lower\n");
        setStack("p", "h");
        suma(t1, "p", "1");
        getStack(t2, t1);
        menor(t2, "0", l1);
        etiqueta(l2);
        getHeap(t3, t2);
        menor(t3, "0", l1);
        menor(t3, "65", l3);
        mayor(t3, "90", l3);
        suma(t3, t3, "32");
        setHeap("h", t3);
        jmp(l4);
        etiqueta(l3);
        setHeap("h", t3);
        etiqueta(l4);
        suma("h", "h", "1");
        suma(t2, t2, "1");
        jmp(l2);
        etiqueta(l1);
        setHeap("h", "-1");
        suma("h", "h", "1");
        codigo("end,,,lower\n\n");
    }

    private void charat() {
        String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t(), t4 = Codigo.t(), t5 = Codigo.t(), t6 = Codigo.t();
        String l1 = Codigo.l(), l2 = Codigo.l(), l3 = Codigo.l();
        codigo("begin,,,charat\n");
        setStack("p", "-1");
        suma(t1, "p", "1");
        getStack(t2, t1);
        menor(t2, "0", l1);
        suma(t3, "p", "2");
        getStack(t4, t3);
        menor(t4, "0", l1);
        asignacion(t5, "0");
        etiqueta(l2);
        getHeap(t6, t2);
        menor(t6, "0", l1);
        igual(t5, t4, l3);
        suma(t2, t2, "1");
        suma(t5, t5, "1");
        jmp(l2);
        etiqueta(l3);
        setStack("p", t6);
        etiqueta(l1);
        codigo("end,,,charat\n\n");
    }

    private void length() {
        String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t(), t4 = Codigo.t();
        String l1 = Codigo.l(), l2 = Codigo.l();
        codigo("begin,,,largo\n");
        suma(t1, "p", "1");
        getStack(t2, t1);
        asignacion(t3, "0");
        menor(t2, "0", l1);
        etiqueta(l2);
        getHeap(t4, t2);
        menor(t4, "0", l1);
        suma(t2, t2, "1");
        suma(t3, t3, "1");
        jmp(l2);
        etiqueta(l1);
        setStack("p", t3);
        codigo("end,,,largo\n\n");
    }

    private void intStr() {
        String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t(), t4 = Codigo.t(), t5 = Codigo.t(), t6 = Codigo.t(), t7 = Codigo.t(), t8 = Codigo.t();
        String l1 = Codigo.l(), l2 = Codigo.l(), l3 = Codigo.l(), l4 = Codigo.l(), l5 = Codigo.l(), l6 = Codigo.l();

        codigo("begin,,,enteroString\n");
        suma(t1, "p", "1");
        getStack(t2, t1);
        distinto(t2, "0", l1);
        setStack("p", "h");
        setHeap("h", "48");
        suma("h", "h", "1");
        setHeap("h", "-1");
        suma("h", "h", "1");
        jmp(l2);
        etiqueta(l1);
        asignacion(t3, "0");
        suma(t4, "p", "2");
        asignacion(t5, t4);
        mayor(t2, "0", l3);
        multiplicacion(t2, t2, "-1");
        asignacion(t3, "1");
        etiqueta(l3);
        igual(t2, "0", l4);
        modulo(t6, t2, "10");
        resta(t2, t2, t6);
        division(t2, t2, "10");
        suma(t7, t6, "48");
        setStack(t4, t7);
        suma(t4, t4, "1");
        jmp(l3);
        etiqueta(l4);
        resta(t4, t4, "1");
        setStack("p", "h");
        igual(t3, "0", l5);
        setHeap("h", "45");
        suma("h", "h", "1");
        etiqueta(l5);
        menor(t4, t5, l6);
        getStack(t8, t4);
        setHeap("h", t8);
        suma("h", "h", "1");
        resta(t4, t4, "1");
        jmp(l5);
        etiqueta(l6);
        setHeap("h", "-1");
        suma("h", "h", "1");
        etiqueta(l2);
        codigo("end,,,enteroString\n\n");
    }

    private void decStr() {
        String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t(), t4 = Codigo.t(), t5 = Codigo.t(), t6 = Codigo.t(), t7 = Codigo.t();
        codigo("begin,,,decimalString\n");
        suma(t1, "p", "1");
        getStack(t2, t1);
        modulo(t3, t2, "1");
        resta(t2, t2, t3);
        suma(t4, "p", "3");
        setStack(t4, t2);
        suma("p", "p", "2");
        call("enteroString");
        getStack(t5, "p");
        resta("p", "p", "2");
        setStack("p", t5);
        resta("h", "h", "1");
        setHeap("h", "46");
        suma("h", "h", "1");
        multiplicacion(t3, t3, "100");
        modulo(t6, t3, "1");
        resta(t3, t3, t6);
        modulo(t7, t3, "10");
        resta(t3, t3, t7);
        division(t3, t3, "10");
        suma(t3, t3, "48");
        setHeap("h", t3);
        suma("h", "h", "1");
        suma(t7, t7, "48");
        setHeap("h", t7);
        suma("h", "h", "1");
        setHeap("h", "-1");
        suma("h", "h", "1");
        codigo("end,,,decimalString\n\n");
    }

    private void replace() {
        String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t(), t4 = Codigo.t(), t5 = Codigo.t(), t6 = Codigo.t();
        String l1 = Codigo.l(), l2 = Codigo.l(), l3 = Codigo.l();
        codigo("begin,,,reemplazar\n");
        setStack("p", "h");
        suma(t1, "p", "1");
        getStack(t2, t1);
        suma(t3, "p", "2");
        getStack(t4, t3);
        etiqueta(l1);
        getHeap(t5, t2);
        getHeap(t6, t4);
        suma(t2, t2, "1");
        menor(t5, "0", l2);
        distinto(t5, t6, l3);
        menor(t6, "0", l3);
        suma(t4, t4, "1");
        jmp(l1);
        etiqueta(l3);
        setHeap("h", t5);
        suma("h", "h", "1");
        jmp(l1);
        etiqueta(l2);
        setHeap("h", "-1");
        suma("h", "h", "1");
        codigo("end,,,reemplazar\n\n");
    }

    private void constantes() {
        codigo("begin,,,constantes\n");
        String snil = "nil";
        asignacion(TNIL, "h");
        for (int c : snil.toCharArray()) {
            setHeap("h", Integer.toString(c));
            suma("h", "h", "1");
        }
        setHeap("h", "-1");
        suma("h", "h", "1");
        String strue = "true";
        asignacion(TTRUE, "h");
        for (int c : strue.toCharArray()) {
            setHeap("h", Integer.toString(c));
            suma("h", "h", "1");
        }
        setHeap("h", "-1");
        suma("h", "h", "1");
        String sfalse = "false";
        asignacion(TFALSE, "h");
        for (int c : sfalse.toCharArray()) {
            setHeap("h", Integer.toString(c));
            suma("h", "h", "1");
        }
        setHeap("h", "-1");
        suma("h", "h", "1");
        codigo("end,,,constantes\n\n");
    }
}
