package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.Creador;
import servidor.compilador.entorno.DatoReporte;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public class Arreglo extends Tipo {

    public ArrayList<Dimension> dimensiones;
    public Tipo tipoDato;

    public Arreglo(ArrayList<Dimension> dimensiones, Tipo tipoDato) {
        super(tipoDato.tipo + new String(new char[dimensiones.size()]).replace('\0', '*'));
        this.dimensiones = dimensiones;
        this.tipoDato = tipoDato;
    }

    @Override
    public boolean comparar(Tipo dos) {
        if (dos instanceof Arreglo) {
            Arreglo segundo = (Arreglo) dos;
            return dimensiones.size() == segundo.dimensiones.size() && tipoDato.comparar(segundo.tipoDato);
        }
        return dos instanceof TNil;
    }

    @Override
    public ArrayList<String> posibles() {
        ArrayList<String> posibles = new ArrayList<>();
        posibles.add(tipo);
        posibles.add(Constantes.NIL);
        return posibles;
    }

    public Valor instancia() {
        return new InstanciaArreglo().generar(dimensiones, Codigo.defecto(tipoDato.tipo));
    }

    @Override
    public String firma() {
        return tipoDato.firma() + "_" + dimensiones.size() + "_";
    }

    @Override
    public DatoReporte reporte() {
        ArrayList<DatoReporte> hijos = new ArrayList<>();
        hijos.add(new DatoReporte("Tipo: " + tipo, ""));
        hijos.add(new DatoReporte("Dimensiones: " + Integer.toString(this.dimensiones.size()), ""));
        return new DatoReporte("", "grid_on", hijos);
    }

}

class InstanciaArreglo extends Creador {

    public Valor generar(ArrayList<Dimension> dimensiones, String valor) {
        String t1 = Codigo.t();
        asignacion(t1, "h");
        arreglo(dimensiones, 0, valor);
        return Valor.arreglo(t1, codigo());
    }

    public void arreglo(ArrayList<Dimension> dimensiones, int indice, String valor) {
        if (indice == dimensiones.size() - 1) {
            arregloUltimo(dimensiones, indice, valor);
        } else {
            arregloN(dimensiones, indice, valor);
        }
    }

    public void arregloN(ArrayList<Dimension> dimensiones, int indice, String valor) {
        Dimension dimension = dimensiones.get(indice);
        String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t();
        String l1 = Codigo.l(), l2 = Codigo.l();
        suma(t1, "h", "1");
        setHeap("h", t1);
        suma("h", "h", "1");
        asignacion(t2, "h");
        suma("h", "h", dimension.cantidad);
        asignacion(t3, "0");
        etiqueta(l1);
        igual(t3, dimension.cantidad, l2);
        setHeap(t2, "h");
        suma(t2, t2, "1");
        arreglo(dimensiones, indice + 1, valor);
        suma(t3, t3, "1");
        jmp(l1);
        etiqueta(l2);
    }

    public void arregloUltimo(ArrayList<Dimension> dimensiones, int indice, String valor) {
        Dimension dimension = dimensiones.get(indice);
        String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t();
        String l1 = Codigo.l(), l2 = Codigo.l();
        suma(t1, "h", "1");
        setHeap("h", t1);
        suma("h", "h", "1");
        asignacion(t2, "h");
        suma("h", "h", dimension.cantidad);
        asignacion(t3, "0");
        etiqueta(l1);
        igual(t3, dimension.cantidad, l2);
        setHeap(t2, valor);
        suma(t2, t2, "1");
        suma(t3, t3, "1");
        jmp(l1);
        etiqueta(l2);
    }

}
