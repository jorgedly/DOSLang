package servidor.compilador.entorno;

import java.util.ArrayList;

/**
 *
 * @author Jorge
 */
public class DatoReporte {

    public String name;
    public String iconname;
    public ArrayList<DatoReporte> children;

    public DatoReporte(String name, String iconname, ArrayList<DatoReporte> children) {
        this.name = name;
        this.iconname = iconname;
        this.children = children;
    }

    public DatoReporte(String name, String iconname) {
        this.name = name;
        this.iconname = iconname;
        this.children = new ArrayList<>();
    }

}
