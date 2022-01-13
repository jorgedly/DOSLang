package servidor.compilador.expresion.valor.acceso;

import java.util.ArrayList;
import servidor.compilador.Codigo;
import servidor.compilador.Constantes;
import servidor.compilador.Control;
import servidor.compilador.Generador;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.Simbolo;
import servidor.compilador.expresion.Expresion;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Arreglo;
import servidor.compilador.instruccion.tipo.Dimension;
import servidor.compilador.instruccion.tipo.Record;
import servidor.compilador.preanalisis.Id;

/**
 *
 * @author Jorge
 */
public class AccesoArreglo extends Elemento {

    public Id id;
    public ArrayList<Expresion> expresiones;

    public AccesoArreglo(Id id, ArrayList<Expresion> dimensiones) {
        super(id.ubicacion, true);
        this.id = id;
        this.expresiones = dimensiones;
    }

    @Override
    public Valor primero(Entorno entorno) {
        if (!entorno.registrosWith.isEmpty()) {
            Valor reg = entorno.registrosWith.peek();
            Record rec = (Record) reg.tipo;
            if (rec.campos.containsKey(id.nombre)) {
                return segundo(entorno, reg);
            }
        }
        String pos = entorno.buscar(id.nombre);
        if (pos != null) {
            Simbolo simbolo = entorno.obtener(id.nombre);
            if (simbolo.tipo instanceof Arreglo) {
                Arreglo arreglo = (Arreglo) simbolo.tipo;
                String t1 = Codigo.t(), t2 = Codigo.t();
                suma(t1, pos, Integer.toString(simbolo.posicion));
                getStack(t2, t1);
                Valor mapeo = mapeo(arreglo, entorno, t2);
                String t3 = Codigo.t(entorno);
                getHeap(t3, mapeo.cadena);
                return Valor.valor(mapeo.tipo, t3, mapeo.cadena, "0", codigo());
            } else {
                //ERROR
                Control.error(ubicacion, "La variable " + id.nombre + " no es arreglo");
            }
        } else {
            //ERROR
            Control.error(id.ubicacion, "No existe el simbolo " + id.nombre);
        }
        return Valor.error();
    }

    @Override
    public Valor segundo(Entorno entorno, Valor record) {
        if (!record.esError()) {
            if (record.tipo instanceof Record) {
                Record registro = (Record) record.tipo;
                if (registro.campos.containsKey(id.nombre)) {
                    Simbolo simbolo = entorno.obtener(id.nombre);
                    if (simbolo.tipo instanceof Arreglo) {
                        Arreglo arreglo = (Arreglo) simbolo.tipo;
                        codigo(record.codigo);
                        String t1 = Codigo.t(), t2 = Codigo.t();
                        if (Generador.nulocero) {
                            menor(record.cadena, "0", entorno.nulo);
                        }
                        suma(t1, record.cadena, Integer.toString(simbolo.posicion));
                        getHeap(t2, t1);
                        Valor mapeo = mapeo(arreglo, entorno, t2);
                        String t3 = Codigo.t(entorno);
                        getHeap(t3, mapeo.cadena);
                        return Valor.valor(mapeo.tipo, t3, mapeo.cadena, "0", codigo());
                    } else {
                        //ERROR
                        Control.error(ubicacion, "La variable " + id.nombre + " no es arreglo");
                    }
                } else {
                    //ERROR
                    Control.error(ubicacion, "El tipo " + record.tipo.tipo + " no tiene el atributo " + id.nombre);
                }
            } else {
                //ERROR
                Control.error(ubicacion, "El tipo " + record.tipo.tipo + " no es record");
            }
        }
        return Valor.error();
    }

    private Valor mapeo(Arreglo arreglo, Entorno entorno, String ini) {
        ArrayList<Valor> valores = new ArrayList<>();
        for (Expresion expresion : expresiones) {
            Valor valor = expresion.gen(entorno);
            if (!valor.esError()) {
                if (valor.tipo.tipo.equals(Constantes.INTEGER)) {
                    valores.add(valor);
                } else {
                    //ERROR
                    Control.error(expresion.ubicacion, "La expresion debe ser de tipo integer");
                }
            }
        }
        if (valores.size() <= arreglo.dimensiones.size()) {
            for (Valor valor : valores) {
                codigo(valor.codigo);
            }
            String inicio = ini;
            ArrayList<Dimension> dims = new ArrayList<>(arreglo.dimensiones);
            dims.remove(0);
            for (int i = 0; i < valores.size() - 1; i++) {
                Valor valor = valores.get(i);
                Dimension dimension = arreglo.dimensiones.get(i);
                String t3 = Codigo.t(), t4 = Codigo.t(), t5 = Codigo.t(), t6 = Codigo.t();
                getHeap(t3, inicio);
                resta(t4, valor.cadena, dimension.inferior);
                suma(t5, t3, t4);
                getHeap(t6, t5);
                inicio = t6;
                dims.remove(0);
            }
            Valor valorUltimo = valores.get(valores.size() - 1);
            Dimension dimensionUltima = arreglo.dimensiones.get(arreglo.dimensiones.size() - 1);
            String t3 = Codigo.t(), t4 = Codigo.t(), t5 = Codigo.t();
            getHeap(t3, inicio);
            resta(t4, valorUltimo.cadena, dimensionUltima.inferior);
            suma(t5, t3, t4);
            if (arreglo.dimensiones.size() == valores.size()) {
                return Valor.valor(arreglo.tipoDato, t5);
            } else {
                return Valor.valor(new Arreglo(dims, arreglo.tipoDato), t5);
            }
        } else {
            //ERROR
            Control.error(ubicacion, "El numero de valores no concuerda con el numero de dimensiones");
        }
        return Valor.error();
    }

    @Override
    public String asignacionPrimera(Entorno entorno, Valor valor) {
        if (!entorno.registrosWith.isEmpty()) {
            Valor reg = entorno.registrosWith.peek();
            Record rec = (Record) reg.tipo;
            if (rec.campos.containsKey(id.nombre)) {
                return asignacionSegunda(entorno, valor, reg);
            }
        }
        String pos = entorno.buscar(id.nombre);
        if (pos != null) {
            Simbolo simbolo = entorno.obtener(id.nombre);
            if (simbolo.tipo instanceof Arreglo) {
                Arreglo arreglo = (Arreglo) simbolo.tipo;
                String t5 = Codigo.t(), t6 = Codigo.t(), t7 = Codigo.t(), t8 = Codigo.t();
                suma(t5, pos, Integer.toString(simbolo.posicion));
                getStack(t6, t5);
                Valor mapeo = mapeo(arreglo, entorno, t6);
                if (!mapeo.esError()) {
                    if (mapeo.tipo.comparar(valor.tipo)) {
                        setHeap(mapeo.cadena, valor.cadena);
                        entorno.quitar(valor.cadena);
                        return codigo();
                    } else {
                        //ERROR
                        Control.error(ubicacion, "No se puede asignar " + valor.tipo.tipo + " a " + simbolo.tipo.tipo);
                    }
                }
            } else {
                //ERROR
                Control.error(ubicacion, "La variable " + id.nombre + " no es arreglo");
            }
        } else {
            //ERROR
            Control.error(id.ubicacion, "No existe el simbolo " + id.nombre);
        }
        return "";
    }

    @Override
    public String asignacionSegunda(Entorno entorno, Valor valor, Valor record) {
        if (!record.esError()) {
            if (record.tipo instanceof Record) {
                Record registro = (Record) record.tipo;
                if (registro.campos.containsKey(id.nombre)) {
                    Simbolo simbolo = registro.campos.get(id.nombre);
                    if (simbolo.tipo instanceof Arreglo) {
                        Arreglo arreglo = (Arreglo) simbolo.tipo;
                        String t1 = Codigo.t(), t2 = Codigo.t();
                        if (Generador.nulocero) {
                            menor(record.cadena, "0", entorno.nulo);
                        }
                        suma(t1, record.cadena, Integer.toString(simbolo.posicion));
                        getHeap(t2, t1);
                        Valor mapeo = mapeo(arreglo, entorno, t2);
                        if (!mapeo.esError()) {
                            if (mapeo.tipo.comparar(valor.tipo)) {
                                setHeap(mapeo.cadena, valor.cadena);
                                entorno.quitar(valor.cadena);
                                return codigo();
                            } else {
                                //ERROR
                                Control.error(ubicacion, "No se puede asignar " + valor.tipo.tipo + " a " + simbolo.tipo.tipo);
                            }
                        }
                    } else {
                        //ERROR
                        Control.error(ubicacion, "La variable " + id.nombre + " no es arreglo");
                    }
                } else {
                    //ERROR
                    Control.error(ubicacion, "El tipo " + record.tipo.tipo + " no tiene el atributo " + id.nombre);
                }
            } else {
                //ERROR
                Control.error(ubicacion, "El tipo " + record.tipo.tipo + " no es record");
            }
        }
        return "";
    }

    @Override
    public String liberacionPrimera(Entorno entorno) {
        if (!entorno.registrosWith.isEmpty()) {
            Valor reg = entorno.registrosWith.peek();
            Record rec = (Record) reg.tipo;
            if (rec.campos.containsKey(id.nombre)) {
                return liberacionSegunda(entorno, entorno.registrosWith.peek());
            }
        }
        String pos = entorno.buscar(id.nombre);
        if (pos != null) {
            Simbolo simbolo = entorno.obtener(id.nombre);
            if (simbolo.tipo instanceof Arreglo) {
                Arreglo arreglo = (Arreglo) simbolo.tipo;
                String t1 = Codigo.t(), t2 = Codigo.t(), t3 = Codigo.t();
                suma(t1, pos, Integer.toString(simbolo.posicion));
                getStack(t2, t1);
                if(Generador.nulocero) {
                    menor(t2, "0", entorno.nulo);
                }
                getHeap(t3, t2);
                Valor mapeo = mapeo(arreglo, entorno, t3);
                if (!mapeo.esError()) {
                    if (mapeo.tipo instanceof Record) {
                        setHeap(mapeo.cadena, "-1");
                        return codigo();
                    } else {
                        //ERROR
                        Control.error(ubicacion, "Solo se puede liberar una variable record");
                    }
                }
            } else {
                //ERROR
                Control.error(ubicacion, "La variable " + id.nombre + " no es arreglo");
            }
        } else {
            //ERROR
            Control.error(id.ubicacion, "No existe el simbolo " + id.nombre);
        }
        return "";
    }

    @Override
    public String liberacionSegunda(Entorno entorno, Valor record) {
        if (!record.esError()) {
            if (record.tipo instanceof Record) {
                Record registro = (Record) record.tipo;
                if (registro.campos.containsKey(id.nombre)) {
                    Simbolo simbolo = registro.campos.get(id.nombre);
                    if (simbolo.tipo instanceof Arreglo) {
                        Arreglo arreglo = (Arreglo) simbolo.tipo;
                        String t1 = Codigo.t(), t2 = Codigo.t();
                        if(Generador.nulocero) {
                            menor(record.cadena, "0", entorno.nulo);
                        }
                        suma(t1, record.cadena, Integer.toString(simbolo.posicion));
                        getHeap(t2, t1);
                        Valor mapeo = mapeo(arreglo, entorno, t2);
                        if (!mapeo.esError()) {
                            if (mapeo.tipo instanceof Record) {
                                setHeap(mapeo.cadena, "-1");
                                return codigo();
                            } else {
                                //ERROR
                                Control.error(ubicacion, "Solo se puede liberar una variable record");
                            }
                        }
                    } else {
                        //ERROR
                        Control.error(ubicacion, "La variable " + id.nombre + " no es arreglo");
                    }
                } else {
                    //ERROR
                    Control.error(ubicacion, "El tipo " + record.tipo.tipo + " no tiene el atributo " + id.nombre);
                }
            } else {
                //ERROR
                Control.error(ubicacion, "El tipo " + record.tipo.tipo + " no es record");
            }
        }
        return "";
    }

}
