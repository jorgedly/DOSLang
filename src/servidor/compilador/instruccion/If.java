package servidor.compilador.instruccion;

import java.util.ArrayList;
import servidor.compilador.Control;
import servidor.compilador.Codigo;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.expresion.Valor;

/**
 *
 * @author Jorge
 */
public class If extends Instruccion {

    public Sif sif;
    public ArrayList<Sif> elseIfs;
    public ArrayList<Instruccion> instruccionesElse;

    public If(Sif sif, ArrayList<Sif> elseIfs, ArrayList<Instruccion> instruccionesElse) {
        this.sif = sif;
        this.elseIfs = elseIfs;
        this.instruccionesElse = instruccionesElse;
    }

    @Override
    public String generar(Entorno entorno) {
        String salida = Codigo.l();
        generarIf(sif, entorno, salida);
        if (elseIfs.size() > 0) {
            for (Sif iif : elseIfs) {
                generarIf(iif, entorno, salida);
            }
        }
        if (instruccionesElse.size() > 0) {
            codigo(GeneradorInstrucciones.generar(entorno, instruccionesElse));
        }
        etiqueta(salida);
        return codigo();
    }

    private void generarIf(Sif iif, Entorno entorno, String salida) {
        Valor valor1 = iif.expresion.gen(entorno);
        if (!valor1.esError()) {
            if (valor1.esBoolean()) {
                codigo(valor1.codigo);
                if (!valor1.listo) {
                    valor1.verdadera = Codigo.l();
                    valor1.falsa = Codigo.l();
                    igual(valor1.cadena, "1", valor1.verdadera);
                    jmp(valor1.falsa);
                }
                etiqueta(valor1.verdadera);
                codigo(GeneradorInstrucciones.generar(entorno, iif.instrucciones));
                jmp(salida);
                etiqueta(valor1.falsa);
            } else {
                //ERROR
                Control.error(iif.expresion.ubicacion, "La condicion debe ser boolean");
            }
        }
    }

}
