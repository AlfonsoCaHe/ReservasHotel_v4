package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;

public class Huespedes implements IHuespedes {

    private ArrayList<Huesped> coleccionHuespedes;

    /*Crea el constructor con par�metros que crear� una lista de la capacidad indicada en el par�metro e inicializar� los atributos
    de la clase a los valores correspondientes.
    */
    public Huespedes(){
        coleccionHuespedes = new ArrayList<>();

    }

    /*El m�todo get devolver� una copia profunda de la colecci�n haciendo uso del m�todo copiaProfundaHuespedes.*/
    public ArrayList<Huesped> get(){
        return copiaProfundaHuespedes();
    }

    private ArrayList<Huesped> copiaProfundaHuespedes(){
        ArrayList<Huesped> copiaHuespedes = new ArrayList<>();

        Iterator it = coleccionHuespedes.iterator();

        while(it.hasNext()){
            copiaHuespedes.add(new Huesped((Huesped) it.next()));
        }
        return copiaHuespedes;
    }

    public int getTamano(){
        return coleccionHuespedes.size();
    }

    /*Se permitir�n insertar hu�spedes no nulos al final de la colecci�n sin admitir repetidos.*/
    public void insertar(Huesped huesped)throws OperationNotSupportedException{
        try{
            if(huesped != null) {
                if (!coleccionHuespedes.contains(huesped)) {
                    coleccionHuespedes.add(huesped);
                } else {
                    throw new OperationNotSupportedException("ERROR: Ya existe un hu�sped con ese dni.");
                }
            }else{
                throw new NullPointerException("ERROR: No se puede insertar un hu�sped nulo.");
            }
        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: No se puede insertar un hu�sped nulo.");
        }
    }

    private int buscarIndice(Huesped huesped){
        if(huesped == null)
            throw new NullPointerException("ERROR: No se puede buscar un hu�sped nulo.");
        return coleccionHuespedes.indexOf(huesped);
    }

    /*El m�todo buscar devolver� un hu�sped si �ste se encuentra en la colecci�n y null en caso contrario.*/
    public Huesped buscar(Huesped huesped){
        if(huesped == null)
            throw new NullPointerException("ERROR: No se puede buscar un hu�sped nulo.");
        Huesped huespedEncontrado = null;
        if(coleccionHuespedes.contains(huesped))
            huespedEncontrado = coleccionHuespedes.get(coleccionHuespedes.indexOf(huesped));
        return huespedEncontrado;
    }

    /*El m�todo borrar, si el hu�sped se encuentra en la colecci�n, lo borrar� y desplazar� los elementos hacia la izquierda para
    dejar el array compactado.*/
    public void borrar(Huesped huesped)throws OperationNotSupportedException{
        try{
            if(huesped == null){
                throw new NullPointerException("ERROR: No se puede borrar un hu�sped nulo.");
            }
            if(coleccionHuespedes.contains(huesped)){
                coleccionHuespedes.remove(huesped);
            }else{
                throw new OperationNotSupportedException("ERROR: No existe ning�n hu�sped como el indicado.");
            }
        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: No se puede borrar un hu�sped nulo.");
        }
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }
}