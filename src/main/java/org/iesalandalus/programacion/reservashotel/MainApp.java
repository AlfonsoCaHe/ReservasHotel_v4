package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.IModelo;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

    public class MainApp {
        private static Vista vista;
        private static IModelo modelo;
        private static Controlador controlador;

        /*Crea el m�todo main que nos mostrar� el men� de la aplicaci�n, nos pedir� una opci�n y la ejecutar� mientras no elijamos
        la opci�n salir. En caso de salir, la aplicaci�n mostrar� un mensaje de despedida.*/
        public static void main(String[] args) {
            System.out.println("Programa para la Gesti�n de Hoteles IES Al-�ndalus");
            if(args.length < 1){
                System.out.println("No se han introducido par�metros de inicio. Int�ntelo de nuevo.");
                return;
            }
            procesarArgumentosFuentesDatos(args[0]);
            vista = new Vista();
            controlador = new Controlador(modelo, vista);
            controlador.comenzar();
            controlador.terminar();
        }

        private static void procesarArgumentosFuentesDatos(String args){
            switch (args){
                case "-fdmongodb":
                    modelo = new Modelo(FactoriaFuenteDatos.MONGODB.crear());
                    break;
                case "-fdmemoria":
                    modelo = new Modelo(FactoriaFuenteDatos.MEMORIA.crear());
            }
        }
    }