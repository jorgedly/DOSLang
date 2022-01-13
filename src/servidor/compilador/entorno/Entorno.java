package servidor.compilador.entorno;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import servidor.compilador.Codigo;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Enumeracion;
import servidor.compilador.instruccion.tipo.Tipo;
import servidor.compilador.preanalisis.Subrutina;

/**
 *
 * @author Jorge
 */
public class Entorno {

    public LinkedHashMap<String, Simbolo> simbolos;
    public LinkedHashMap<String, Tipo> tipos;
    public LinkedHashMap<String, Subrutina> subrutinas;
    public Entorno superior;
    public int contador;
    public String nombre;
    //public String icono;
    public Stack<String> salidas;
    public Stack<String> inicios;
    public String salida;
    public String inicio;
    public String nulo;
    public String cero;
    public ArrayList<String> temporales;
    public ArrayList<Parametro> parametros;
    public ArrayList<Entorno> hijos;
    public ArrayList<Enumeracion> enumeraciones;
    public Stack<Valor> registrosWith;

    public Entorno(Entorno superior) {
        simbolos = new LinkedHashMap<>();
        this.superior = superior;
        contador = 0;
        nombre = "";
        tipos = new LinkedHashMap<>();
        subrutinas = new LinkedHashMap<>();
        salidas = new Stack<>();
        inicios = new Stack<>();
        salida = "";
        inicio = "";
        nulo = "";
        cero = "";
        temporales = new ArrayList<>();
        parametros = new ArrayList<>();
        hijos = new ArrayList<>();
        enumeraciones = new ArrayList<>();
        registrosWith = new Stack<>();
    }

    public void setNombre(String nombre) {
        this.nombre = superior.nombre + nombre;
    }

    // <editor-fold defaultstate="collapsed" desc="Variables/Constantes">
    public boolean tiene(String nombre) {
        return simbolos.containsKey(nombre);
    }

    public String buscar(String nombre) {
        return buscar(nombre, this);
    }

    private String buscar(String nombre, Entorno entorno) {
        if (entorno.simbolos.containsKey(nombre)) {
            return entorno.inicio;
        } else if (entorno.superior != null) {
            return buscar(nombre, entorno.superior);
        } else {
            return null;
        }
    }

    public void agregar(Simbolo simbolo) {
        simbolo.posicion = contador++;
        simbolos.put(simbolo.nombre, simbolo);
    }

    public void quitarVariable(String nombre) {
        contador--;
        simbolos.remove(nombre);
    }

    public Simbolo obtener(String nombre) {
        return obtener(nombre, this);
    }

    private Simbolo obtener(String nombre, Entorno entorno) {
        if (entorno.simbolos.containsKey(nombre)) {
            return entorno.simbolos.get(nombre);
        } else if (entorno.superior != null) {
            return obtener(nombre, entorno.superior);
        } else {
            return null;
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Tipos">
    public void agregarTipo(String nombre, Tipo tipo) {
        tipos.put(nombre, tipo);
    }

    public boolean tieneTipo(String nombre) {
        return tipos.containsKey(nombre);
    }

    public boolean buscarTipo(String nombre) {
        return buscarTipo(nombre, this);
    }

    private boolean buscarTipo(String nombre, Entorno entorno) {
        if (entorno.tipos.containsKey(nombre)) {
            return true;
        } else if (entorno.superior != null) {
            return buscarTipo(nombre, entorno.superior);
        } else {
            return false;
        }
    }

    public Tipo obtenerTipo(String nombre) {
        return obtenerTipo(nombre, this);
    }

    private Tipo obtenerTipo(String nombre, Entorno entorno) {
        if (entorno.tipos.containsKey(nombre)) {
            return entorno.tipos.get(nombre);
        } else if (entorno.superior != null) {
            return obtenerTipo(nombre, entorno.superior);
        } else {
            return null;
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Subrutina">
    public boolean tieneSubrutina(String nombre) {
        return subrutinas.containsKey(nombre);
    }

    public Subrutina buscarSubrutina(String nombre) {
        return buscarSubrutina(nombre, this);
    }

    private Subrutina buscarSubrutina(String nombre, Entorno entorno) {
        if (entorno.subrutinas.containsKey(nombre)) {
            return entorno.subrutinas.get(nombre);
        }
        for (Subrutina subrutina : entorno.subrutinas.values()) {
            if (subrutina.posibles.contains(nombre)) {
                return subrutina;
            }
        }
        if (entorno.superior != null) {
            return buscarSubrutina(nombre, entorno.superior);
        }
        return null;
    }

    public void agregarSubrutina(String nombre, Subrutina subrutina) {
        subrutinas.put(nombre, subrutina);
    }

    public Subrutina obtenerSubrutina(String nombre) {
        return obtenerSubrutina(nombre, this);
    }

    private Subrutina obtenerSubrutina(String nombre, Entorno entorno) {
        if (entorno.subrutinas.containsKey(nombre)) {
            return entorno.subrutinas.get(nombre);
        } else if (entorno.superior != null) {
            return obtenerSubrutina(nombre, entorno.superior);
        } else {
            return null;
        }
    }

    // </editor-fold>
    public int tamaño() {
        return contador + temporales.size();
        //return contador;
    }

    public String almacenar() {
        if (!temporales.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            //sb.append("\t\t//inicio guardado\n");
            String t1 = Codigo.t();
            sb.append("\t\t+,p,").append(Integer.toString(contador)).append(",").append(t1).append("\n");
            for (String temp : temporales) {
                sb.append("\t\t=,").append(t1).append(",").append(temp).append(",stack\n");
                sb.append("\t\t+,").append(t1).append(",1,").append(t1).append("\n");
            }
            //sb.append("\t\t//fin guardado\n");
            return sb.toString();
        }
        return "";
    }

    public String recuperar() {
        if (!temporales.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            String t1 = Codigo.t();
            //sb.append("\t\t//inicio recuperacion\n");
            sb.append("\t\t+,p,").append(Integer.toString(contador)).append(",").append(t1).append("\n");
            for (String temp : temporales) {
                sb.append("\t\t=,stack,").append(t1).append(",").append(temp).append("\n");
                sb.append("\t\t+,").append(t1).append(",1,").append(t1).append("\n");
            }
            //sb.append("\t\t//fin recuperacion\n");
            return sb.toString();
        }
        return "";
    }

    public void quitar(String... temp) {
        for (String t : temp) {
            temporales.remove(t);
        }
    }

    public int indiceEnum(String nombre) {
        return indiceEnum(nombre, this);
    }

    private int indiceEnum(String nombre, Entorno entorno) {
        for (Enumeracion enumeracion : entorno.enumeraciones) {
            int t = enumeracion.tiene(nombre);
            if (t >= 0) {
                return t;
            }
        }
        if (entorno.superior != null) {
            return indiceEnum(nombre, entorno.superior);
        } else {
            return -1;
        }
    }

    public DatoReporte reporte() {
        ArrayList<DatoReporte> children = new ArrayList<>();
        for (Simbolo s : simbolos.values()) {
            children.add(s.reporte());
        }
        for (Subrutina s : subrutinas.values()) {
            children.add(s.reporte());
        }
        for (Map.Entry<String, Tipo> ent : tipos.entrySet()) {
            DatoReporte dr = ent.getValue().reporte();
            dr.name = ent.getKey();
            children.add(dr);
        }
        for (Entorno ent : hijos) {
            children.add(ent.reporte());
        }
        return new DatoReporte(nombre, "assignment", children);
    }

}
