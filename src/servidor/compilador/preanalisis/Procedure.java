package servidor.compilador.preanalisis;

import java.util.ArrayList;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.DatoReporte;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.Simbolo;
import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public class Procedure extends Subrutina {

    public Procedure(int noparams, Tipo tipo, String firma, Entorno entorno, ArrayList<String> posibles) {
        super(Constantes.PROCEDIMIENTO, noparams, tipo, firma, entorno, posibles);
    }

    @Override
    public DatoReporte reporte() {
        ArrayList<DatoReporte> hijos = new ArrayList<>();
        hijos.add(new DatoReporte("Tipo: " + tipo.tipo, ""));
        DatoReporte dr = new DatoReporte("Parametros", "");
        for (Simbolo s : entorno.parametros) {
            DatoReporte par = s.reporte();
            dr.children.add(par);
        }
        hijos.add(dr);
        return new DatoReporte(this.firma, "dashboard", hijos);
    }

}
