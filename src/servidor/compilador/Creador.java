package servidor.compilador;

/**
 *
 * @author Jorge
 */
public class Creador {

    public StringBuilder codigo;

    public Creador() {
        codigo = new StringBuilder();
    }

    public void codigo(String code) {
        codigo.append(code);
    }

    public void suma(String resultado, String operando1, String operando2) {
        codigo.append("\t\t+,").append(operando1).append(",").append(operando2).append(",").append(resultado).append("\n");
    }

    public void resta(String resultado, String operando1, String operando2) {
        codigo.append("\t\t-,").append(operando1).append(",").append(operando2).append(",").append(resultado).append("\n");
    }

    public void multiplicacion(String resultado, String operando1, String operando2) {
        codigo.append("\t\t*,").append(operando1).append(",").append(operando2).append(",").append(resultado).append("\n");
    }

    public void division(String resultado, String operando1, String operando2) {
        codigo.append("\t\t/,").append(operando1).append(",").append(operando2).append(",").append(resultado).append("\n");
    }

    public void modulo(String resultado, String operando1, String operando2) {
        codigo.append("\t\t%,").append(operando1).append(",").append(operando2).append(",").append(resultado).append("\n");
    }

    public void relacional(String operador, String operando1, String operando2, String etiqueta) {
        codigo.append("\t\t").append(operador).append(",").append(operando1).append(",").append(operando2).append(",").append(etiqueta).append("\n");
    }

    public void igual(String operando1, String operando2, String etiqueta) {
        codigo.append("\t\tje,").append(operando1).append(",").append(operando2).append(",").append(etiqueta).append("\n");
    }

    public void distinto(String operando1, String operando2, String etiqueta) {
        codigo.append("\t\tjne,").append(operando1).append(",").append(operando2).append(",").append(etiqueta).append("\n");
    }

    public void mayor(String operando1, String operando2, String etiqueta) {
        codigo.append("\t\tjg,").append(operando1).append(",").append(operando2).append(",").append(etiqueta).append("\n");
    }

    public void menor(String operando1, String operando2, String etiqueta) {
        codigo.append("\t\tjl,").append(operando1).append(",").append(operando2).append(",").append(etiqueta).append("\n");
    }

    public void mayorigual(String operando1, String operando2, String etiqueta) {
        codigo.append("\t\tjge,").append(operando1).append(",").append(operando2).append(",").append(etiqueta).append("\n");
    }

    public void menorigual(String operando1, String operando2, String etiqueta) {
        codigo.append("\t\tjle,").append(operando1).append(",").append(operando2).append(",").append(etiqueta).append("\n");
    }

    public void asignacion(String resultado, String valor) {
        codigo.append("\t\t=,").append(valor).append(",,").append(resultado).append("\n");
    }

    public void setStack(String posicion, String valor) {
        codigo.append("\t\t=,").append(posicion).append(",").append(valor).append(",stack\n");
    }

    public void setHeap(String posicion, String valor) {
        codigo.append("\t\t=,").append(posicion).append(",").append(valor).append(",heap\n");
    }

    public void getStack(String resultado, String posicion) {
        codigo.append("\t\t=,stack,").append(posicion).append(",").append(resultado).append("\n");
    }

    public void getHeap(String resultado, String posicion) {
        codigo.append("\t\t=,heap,").append(posicion).append(",").append(resultado).append("\n");
    }

    public void call(String nombre) {
        codigo.append("\t\tcall,,,").append(nombre).append("\n");
    }

    public void jmp(String etiqueta) {
        codigo.append("\t\tjmp,,,").append(etiqueta).append("\n");
    }

    public void etiqueta(String etiqueta) {
        codigo.append("\t").append(etiqueta).append(":\n");
    }

    public void printChar(String valor) {
        //codigo.append("\t\tcall,print,%c,").append(valor).append("\n");
        codigo.append("\t\tprint(%c,").append(valor).append(")\n");
    }

    public void printInt(String valor) {
        //codigo.append("\t\tcall,print,%e,").append(valor).append("\n");
        codigo.append("\t\tprint(%e,").append(valor).append(")\n");
    }

    public void printDec(String valor) {
        //codigo.append("\t\tcall,print,%d,").append(valor).append("\n");
        codigo.append("\t\tprint(%d,").append(valor).append(")\n");
    }

    public String codigo() {
        return codigo.toString();
    }

}
