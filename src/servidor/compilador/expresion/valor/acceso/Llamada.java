package servidor.compilador.expresion.valor.acceso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import servidor.compilador.Codigo;
import servidor.compilador.Control;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.Parametro;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.preanalisis.Id;
import servidor.compilador.preanalisis.Subrutina;

/**
 *
 * @author Jorge
 */
public class Llamada extends Elemento {

    public Id id;
    public ArrayList<Expresion> argumentos;

    public Llamada(Id id, ArrayList<Expresion> argumentos) {
        super(id.ubicacion, false);
        this.id = id;
        this.argumentos = argumentos;
    }

    @Override
    public Valor primero(Entorno entorno) {
        String firma;
        if (argumentos.size() > 0) {
            ArrayList<Valor> valores = new ArrayList<>();
            ArrayList<String> tipos = new ArrayList<>();
            for (Expresion expresion : argumentos) {
                Valor valor = expresion.gen(entorno);
                if (!valor.esError()) {
                    valores.add(valor);
                    tipos.add(valor.tipo.firma());
                }
            }
            if (valores.size() == argumentos.size()) {
                firma = id.nombre + "_" + String.join("_", tipos);
                Subrutina subrutina = entorno.buscarSubrutina(firma);
                if (subrutina != null && valores.size() == subrutina.entorno.parametros.size()) {
                    for (Valor valor : valores) {
                        codigo(valor.codigo);
                        if (valor.esBoolean() && valor.listo) {
                            String salida = Codigo.l();
                            valor.cadena = Codigo.t(entorno);
                            etiqueta(valor.verdadera);
                            asignacion(valor.cadena, "1");
                            jmp(salida);
                            etiqueta(valor.falsa);
                            asignacion(valor.cadena, "0");
                            etiqueta(salida);
                        }
                    }
                    String t1 = Codigo.t();
                    int tam = entorno.tamaño();
                    suma(t1, "p", Integer.toString(tam));
                    setStack(t1, Codigo.defecto(subrutina.tipo.tipo));
                    suma(t1, t1, "1");
                    for (int i = 0; i < valores.size(); i++) {
                        Valor valor = valores.get(i);
                        Parametro parametro = subrutina.entorno.parametros.get(i);
                        if (parametro.referencia) {//o si es un objeto,arreglo,nil
                            if ((valor.referencia != null || !valor.referencia.isEmpty()) && valor.estructura != null) {
                                setStack(t1, valor.referencia);
                                suma(t1, t1, "1");
                                setStack(t1, valor.estructura);
                            } else {
                                //ERROR
                                Control.error(ubicacion, "El parametro " + parametro.nombre + " se recibe por referencia");
                            }
                        } else {
                            setStack(t1, valor.cadena);
                        }
                        suma(t1, t1, "1");
                    }
                    codigo(entorno.almacenar());
                    suma("p", "p", Integer.toString(tam));
                    call(subrutina.entorno.nombre);
                    String cad = Codigo.t();
                    getStack(cad, "p");
                    resta("p", "p", Integer.toString(tam));
                    codigo(entorno.recuperar());
                    entorno.temporales.add(cad);
                    return Valor.valor(subrutina.tipo, cad, codigo());
                } else {
                    //ERROR
                    Control.error(id.ubicacion, "No existe una función/procedimiento con la firma " + firma);
                }
            }
        } else {
            firma = id.nombre;
            Subrutina subrutina = entorno.buscarSubrutina(firma);
            if (subrutina != null) {
                codigo(entorno.almacenar());
                suma("p", "p", Integer.toString(entorno.tamaño()));
                call(subrutina.entorno.nombre);
                String cad = Codigo.t();
                getStack(cad, "p");
                resta("p", "p", Integer.toString(entorno.tamaño()));
                codigo(entorno.recuperar());
                entorno.temporales.add(cad);
                return Valor.valor(subrutina.tipo, cad, codigo());
            }
        }
        return Valor.error();
    }

    @Override
    public String asignacionPrimera(Entorno entorno, Valor valor) {
        return "";
    }

    @Override
    public Valor segundo(Entorno entorno, Valor record) {
        return Valor.error();
    }

    @Override
    public String asignacionSegunda(Entorno entorno, Valor valor, Valor record) {
        return "";
    }

    @Override
    public String liberacionPrimera(Entorno entorno) {
        return "";
    }

    @Override
    public String liberacionSegunda(Entorno entorno, Valor record) {
        return "";
    }

}
