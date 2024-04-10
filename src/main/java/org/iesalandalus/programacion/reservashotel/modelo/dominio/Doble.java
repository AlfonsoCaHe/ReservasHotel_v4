package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Doble extends Habitacion{

    private static int NUM_MAXIMO_PERSONAS = 2;
    static final int MIN_NUM_CAMAS_INDIVIDUALES = 0;
    static final int MAX_NUM_CAMAS_INDIVIDUALES = 2;
    static final int MIN_NUM_CAMAS_DOBLES = 0;
    static final int MAX_NUM_CAMAS_DOBLES = 1;
    private int numCamasIndividuales;
    private int numCamasDobles;

    public Doble(int planta, int puerta, double precio, int numCamasIndividuales, int numCamasDobles){
        super(planta, puerta, precio);
        try{
            setNumCamasIndividuales(numCamasIndividuales);
            setNumCamasDobles(numCamasDobles);
            validaNumCamas();
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Doble(Doble habitacionDoble){
        super(habitacionDoble.getPlanta(), habitacionDoble.getPuerta(), habitacionDoble.getPrecio());
        try{
            setNumCamasIndividuales(habitacionDoble.getNumCamasIndividuales());
            setNumCamasDobles(habitacionDoble.getNumCamasDobles());
            validaNumCamas();
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public int getNumCamasIndividuales() {
        return numCamasIndividuales;
    }

    public void setNumCamasIndividuales(int numCamasIndividuales) {
        if(numCamasIndividuales >= MIN_NUM_CAMAS_INDIVIDUALES && numCamasIndividuales <= MAX_NUM_CAMAS_INDIVIDUALES) {
            this.numCamasIndividuales = numCamasIndividuales;
        }else{
            throw new IllegalArgumentException("ERROR: El n�mero de camas individuales de una habitaci�n doble no puede ser inferior a 0 ni mayor que 2");
        }
    }

    public int getNumCamasDobles() {
        return numCamasDobles;
    }

    public void setNumCamasDobles(int numCamasDobles) {
        this.numCamasDobles = numCamasDobles;
    }

    private void validaNumCamas(){
        if(numCamasIndividuales < MIN_NUM_CAMAS_INDIVIDUALES || numCamasIndividuales > MAX_NUM_CAMAS_INDIVIDUALES){
            throw new IllegalArgumentException("ERROR: El n�mero de camas individuales de una habitaci�n doble no puede ser inferior a 0 ni mayor que 2");
        }else{
            if(numCamasDobles <  MIN_NUM_CAMAS_DOBLES || numCamasDobles > MAX_NUM_CAMAS_DOBLES){
                throw new IllegalArgumentException("ERROR: El n�mero de camas dobles de una habitaci�n doble no puede ser inferior a 0 ni mayor que 1");
            }
        }
        if(numCamasIndividuales + (numCamasDobles * 2) != NUM_MAXIMO_PERSONAS){
            throw new IllegalArgumentException("ERROR: La distribuci�n de camas en una habitaci�n doble tiene que ser 2 camas individuales y 0 doble o 0 camas individuales y 1 doble");
        }
    }

    @Override
    public int getNumeroMaximoPersonas() {
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        return super.toString()+"habitaci�n doble, capacidad="+NUM_MAXIMO_PERSONAS+" personas, " + "camas individuales="+numCamasIndividuales+", camas dobles="+numCamasDobles;
    }
}
