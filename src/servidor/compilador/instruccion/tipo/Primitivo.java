package servidor.compilador.instruccion.tipo;

import java.util.ArrayList;
import servidor.compilador.Constantes;
import servidor.compilador.entorno.DatoReporte;

/**
 *
 * @author Jorge
 */
public class Primitivo extends Tipo {

    public Primitivo(String tipo) {
        super(tipo);
    }

    @Override
    public boolean comparar(Tipo dos) {
        if (dos instanceof Primitivo) {
            Primitivo primitivo = (Primitivo) dos;
            switch (tipo) {
                case Constantes.INTEGER:
                    switch (primitivo.tipo) {
                        case Constantes.INTEGER:
                            return true;
                        case Constantes.REAL:
                            return true;
                        case Constantes.CHAR:
                            return true;
                        default:
                            return false;
                    }
                case Constantes.REAL:
                    switch (primitivo.tipo) {
                        case Constantes.INTEGER:
                            return true;
                        case Constantes.REAL:
                            return true;
                        case Constantes.CHAR:
                            return true;
                        default:
                            return false;
                    }
                case Constantes.CHAR:
                    switch (primitivo.tipo) {
                        case Constantes.INTEGER:
                            return true;
                        case Constantes.REAL:
                            return true;
                        case Constantes.CHAR:
                            return true;
                        default:
                            return false;
                    }
                case Constantes.BOOLEAN:
                    switch (primitivo.tipo) {
                        case Constantes.BOOLEAN:
                            return true;
                        default:
                            return false;
                    }
                case Constantes.WORD:
                    switch (primitivo.tipo) {
                        case Constantes.WORD:
                            return true;
                        default:
                            return false;
                    }
                case Constantes.STRING:
                    switch (primitivo.tipo) {
                        case Constantes.STRING:
                            return true;
                        case Constantes.WORD:
                            return true;
                        default:
                            return false;
                    }
                default:
                    return false;
            }
        }
        return false;
    }

    @Override
    public ArrayList<String> posibles() {
        ArrayList<String> posibles = new ArrayList<>();
        switch (tipo) {
            case Constantes.INTEGER: {
                posibles.add(Constantes.INTEGER);
                posibles.add(Constantes.REAL);
                posibles.add(Constantes.CHAR);
                break;
            }
            case Constantes.REAL: {
                posibles.add(Constantes.REAL);
                posibles.add(Constantes.INTEGER);
                posibles.add(Constantes.CHAR);
                break;
            }
            case Constantes.CHAR: {
                posibles.add(Constantes.CHAR);
                posibles.add(Constantes.INTEGER);
                posibles.add(Constantes.REAL);
                break;
            }
            case Constantes.BOOLEAN: {
                posibles.add(Constantes.BOOLEAN);
                break;
            }
            case Constantes.STRING: {
                posibles.add(Constantes.STRING);
                posibles.add(Constantes.WORD);
                break;
            }
            case Constantes.WORD: {
                posibles.add(Constantes.WORD);
                posibles.add(Constantes.STRING);
                break;
            }
        }
        return posibles;
    }

    @Override
    public String firma() {
        return tipo;
    }

    @Override
    public DatoReporte reporte() {
        ArrayList<DatoReporte> hijos = new ArrayList<>();
        hijos.add(new DatoReporte("Tipo: " + tipo, ""));
        return new DatoReporte("", "share", hijos);
    }

}
