package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;

public class Habitaciones implements IHabitaciones {

    private MongoCollection<Document> coleccionHabitaciones;

    //Atributo que guarda el nombre de la colecci�n de la base de datos
    private String COLECCION = "habitaciones";

    /*Crea el constructor que crear� una lista del tipo Habitacion*/
    public Habitaciones(){
        comenzar();
    }

    /*Crea el m�todo get que est� sobrecargado y devolver�
    El m�todo sin par�metros, una copia profunda de la colecci�n haciendo uso del m�todo copiaProfundaHabitaciones.
    */
    public ArrayList<Habitacion> get() {
        ArrayList<Habitacion> copiaHabitaciones = new ArrayList<Habitacion>();

        MongoCursor<Document> cursor = coleccionHabitaciones.find().iterator();

        while(cursor.hasNext()){
            copiaHabitaciones.add(MongoDB.getHabitacion(cursor.next()));
        }

        Collections.sort(copiaHabitaciones);
        return copiaHabitaciones;
    }

    /*
    El m�todo con el par�metro TipoHabitacion, un copia profunda de la colecci�n pero de solo aquellas habitaciones cuyo tipo sea
    el indicado como par�metro.
     */
    public ArrayList<Habitacion> get(TipoHabitacion tipoHabitacion) {
        ArrayList<Habitacion> habitacionesTipo = new ArrayList<>();
        ArrayList<Habitacion> copia = get();

        for(Habitacion h : copia){
            if(h instanceof Simple){
                if(tipoHabitacion.toString().toUpperCase().equals("SIMPLE"))
                    habitacionesTipo.add(new Simple((Simple)h));
            }
            if(h instanceof Doble){
                if(tipoHabitacion.toString().toUpperCase().equals("DOBLE"))
                    habitacionesTipo.add(new Doble((Doble)h));
            }
            if(h instanceof Triple){
                if(tipoHabitacion.toString().toUpperCase().equals("TRIPLE"))
                    habitacionesTipo.add(new Triple((Triple)h));
            }
            if(h instanceof Suite){
                if(tipoHabitacion.toString().toUpperCase().equals("SUITE"))
                    habitacionesTipo.add(new Suite((Suite)h));
            }
        }

        return habitacionesTipo;
    }

    public int getTamano(){
        return Integer.parseInt(""+MongoDB.getBD().getCollection(COLECCION).countDocuments());
    }

    /*Se permitir�n insertar habitaciones no nulas al final de la colecci�n sin admitir repetidos.*/
    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        try{
            if(habitacion != null) {
                if (buscar(habitacion) == null) {
                    coleccionHabitaciones.insertOne(MongoDB.getDocumento(habitacion));
                }else{
                    throw new OperationNotSupportedException("ERROR: Ya existe una habitaci�n con ese identificador.");
                }
            }else{
                throw new NullPointerException("ERROR: No se puede insertar una habitaci�n nula.");
            }
        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: No se puede insertar una habitaci�n nula.");
        }
    }

    /*El m�todo buscar devolver� una habitaci�n si �sta se encuentra en la colecci�n y null en caso contrario.*/
    public Habitacion buscar(Habitacion habitacion){
        if(habitacion == null)
            throw new NullPointerException("ERROR: No se puede buscar una habitaci�n nula.");
        Habitacion habitacionEncontrada = null;

        MongoCursor<Document> cursor = coleccionHabitaciones.find().iterator();

        while (cursor.hasNext()) {
            Habitacion h = MongoDB.getHabitacion(cursor.next());
            if (h.equals(habitacion)) {
                habitacionEncontrada = h;
            }
        }

        return habitacionEncontrada;
    }

    /*El m�todo borrar, si la habitaci�n se encuentra en la colecci�n, la borrar� y desplazar� los elementos hacia la izquierda para dejar el array compactado.*/
    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if(habitacion == null)
            throw new NullPointerException("ERROR: No se puede borrar una habitaci�n nula.");
        Habitacion h = buscar(habitacion);
        if(h != null){
            coleccionHabitaciones.deleteOne(MongoDB.getDocumento(habitacion));
        }else{
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitaci�n como la indicada.");
        }
    }

    @Override
    public void comenzar() {
        try {
            coleccionHabitaciones = MongoDB.getBD().getCollection(COLECCION);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
    }
}