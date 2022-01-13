package servidor.compilador.preanalisis;

import java.util.ArrayList;
import servidor.compilador.entorno.DatoReporte;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.instruccion.tipo.Tipo;

/**
 *
 * @author Jorge
 */
public abstract class Subrutina {

    public String firma;
    public int noparams;
    public Entorno entorno;
    public ArrayList<String> posibles;
    public Tipo tipo;
    public String rol;

    public Subrutina(String rol, int noparams, Tipo tipo, String firma, Entorno entorno, ArrayList<String> posibles) {
        this.rol = rol;
        this.noparams = noparams;
        this.tipo = tipo;
        this.firma = firma;
        this.entorno = entorno;
        this.posibles = posibles;
    }
    
    public abstract DatoReporte reporte();

}
