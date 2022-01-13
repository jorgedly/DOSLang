package servidor;

import java.util.ArrayList;
import servidor.compilador.CError;
import servidor.compilador.entorno.DatoReporte;

/**
 *
 * @author Jorge
 */
public class Message {

    private final String codigo;
    private final ArrayList<CError> errores;
    private final DatoReporte datos;

    public Message(String codigo, ArrayList<CError> errores, DatoReporte datos) {
        this.codigo = codigo;
        this.errores = errores;
        this.datos = datos;
    }

    public Message(String codigo) {
        this.codigo = codigo;
        this.errores = new ArrayList<>();
        this.datos = new DatoReporte("", "");
    }

    public String getCodigo() {
        return codigo;
    }

    public ArrayList<CError> getErrores() {
        return errores;
    }

    public DatoReporte getDatos() {
        return datos;
    }

}
