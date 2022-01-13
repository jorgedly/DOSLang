package servidor.compilador.expresion.valor.acceso;

import servidor.compilador.Codigo;
import servidor.compilador.Control;
import servidor.compilador.Generador;
import servidor.compilador.entorno.Constante;
import servidor.compilador.entorno.Entorno;
import servidor.compilador.entorno.Simbolo;
import servidor.compilador.expresion.Valor;
import servidor.compilador.instruccion.tipo.Enumerado;
import servidor.compilador.instruccion.tipo.Puntero;
import servidor.compilador.instruccion.tipo.Record;
import servidor.compilador.preanalisis.Id;

/**
 *
 * @author Jorge
 */
public class Identificador extends Elemento {

    public Id id;

    public Identificador(Id id) {
        super(id.ubicacion, true);
        this.id = id;
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
            String t1 = Codigo.t(entorno), t2 = Codigo.t(entorno);
            suma(t1, pos, Integer.toString(simbolo.posicion));
            getStack(t2, t1);
            if (simbolo.referencia) {
                String t3 = Codigo.t(), t4 = Codigo.t(), t5 = Codigo.t(entorno), t6 = Codigo.t(entorno);
                String l1 = Codigo.l(), l2 = Codigo.l();
                suma(t3, pos, Integer.toString(simbolo.posicion + 1));
                getStack(t4, t3);
                igual(t4, "0", l1);
                asignacion(t6, t4);
                getStack(t5, t2);
                jmp(l2);
                etiqueta(l1);
                getHeap(t5, t2);
                etiqueta(l2);
                entorno.quitar(t1);
                return Valor.valor(simbolo.tipo, t5, t2, t6, codigo());
            } else {
                return Valor.valor(simbolo.tipo, t2, t1, "1", codigo());
            }
        } else {
            int i = entorno.indiceEnum(id.nombre);
            if (i >= 0) {
                Enumerado enumerado = new Enumerado(id.nombre);
                return Valor.valor(enumerado, Integer.toString(i));
            } else {
                //ERROR
                Control.error(id.ubicacion, "No existe el simbolo " + id.nombre);
            }
        }
        return Valor.error();
    }

    @Override
    public Valor segundo(Entorno entorno, Valor record) {
        if (!record.esError()) {
            if (record.tipo instanceof Record) {
                Record registro = (Record) record.tipo;
                if (registro.campos.containsKey(id.nombre)) {
                    codigo(record.codigo);
                    Simbolo simbolo = registro.campos.get(id.nombre);
                    String t1 = Codigo.t(entorno), t2 = Codigo.t(entorno);
                    if (Generador.nulocero) {
                        menor(record.cadena, "0", entorno.nulo);
                    }
                    suma(t1, record.cadena, Integer.toString(simbolo.posicion));
                    getHeap(t2, t1);
                    return Valor.valor(simbolo.tipo, t2, t1, "0", codigo());
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
            if (!(simbolo instanceof Constante)) {
                if (simbolo.tipo.comparar(valor.tipo)) {
                    if (simbolo.tipo instanceof Record && valor.tipo instanceof Puntero) {
                        ((Record) simbolo.tipo).defectos(codigo, valor.cadena);
                    }
                    String t1 = Codigo.t();
                    suma(t1, pos, Integer.toString(simbolo.posicion));
                    entorno.quitar(valor.cadena);
                    if (simbolo.referencia) {
                        String t2 = Codigo.t(), t3 = Codigo.t(), t4 = Codigo.t();
                        String l1 = Codigo.l(), l2 = Codigo.l();
                        getStack(t2, t1);
                        suma(t3, pos, Integer.toString(simbolo.posicion + 1));
                        getStack(t4, t3);
                        igual(t4, "0", l1);
                        setStack(t2, valor.cadena);
                        jmp(l2);
                        etiqueta(l1);
                        setHeap(t2, valor.cadena);
                        etiqueta(l2);
                        return codigo();
                    } else {
                        setStack(t1, valor.cadena);
                        return codigo();
                    }
                } else {
                    //ERROR
                    Control.error(ubicacion, "No se puede asignar " + valor.tipo.tipo + " a " + simbolo.tipo.tipo);
                }
            } else {
                //ERROR
                Control.error(ubicacion, "No se puede asignar una constante");
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
                    if (simbolo.tipo.comparar(valor.tipo)) {
                        codigo(record.codigo);
                        String t3 = Codigo.t();
                        if (Generador.nulocero) {
                            menor(record.cadena, "0", entorno.nulo);
                        }
                        suma(t3, record.cadena, Integer.toString(simbolo.posicion));
                        setHeap(t3, valor.cadena);
                        entorno.quitar(valor.cadena);
                        return codigo();
                    } else {
                        //ERROR
                        Control.error(ubicacion, "No se puede asignar " + valor.tipo.tipo + " a " + simbolo.tipo.tipo);
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
            if (simbolo.tipo instanceof Record) {
                String t1 = Codigo.t();
                suma(t1, pos, Integer.toString(simbolo.posicion));
                setStack(t1, "-1");
                return codigo();
            } else {
                //ERROR
                Control.error(ubicacion, "Solo se puede liberar una variable record");
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
                    if (simbolo.tipo instanceof Record) {
                        String t1 = Codigo.t();
                        if (Generador.nulocero) {
                            menor(record.cadena, "0", entorno.nulo);
                        }
                        suma(t1, record.cadena, Integer.toString(simbolo.posicion));
                        setHeap(t1, "-1");
                        return codigo();
                    } else {
                        //ERROR
                        Control.error(ubicacion, "Solo se puede liberar una variable record");
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
