package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.lang.String;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Huesped implements Comparable<Huesped> {
    private String ER_TELEFONO = "[0-9]{9}";
    private String ER_CORREO = "([a-z0-9._]+)@([a-z]+(.com|.org|.es))";
    private String ER_DNI = "([0-9]{8})([a-hj-np-zA-HJ-NP-Z])";
    public String FORMATO_FECHA = "(0[1-9]|[1-3][0-9])/(0[1-9]|1[0-2])/([0-9]{4})";
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;

    /*Crea el constructor con par�metros que har� uso de los m�todos de modificaci�n.*/
    public Huesped(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento){
        try {
            setNombre(nombre);
            setTelefono(telefono);
            setCorreo(correo);
            setDni(dni);
            setFechaNacimiento(fechaNacimiento);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /*Crea el constructor copia*/
    public Huesped (Huesped huesped){
        try{
            this.nombre = huesped.getNombre();
            this.telefono = huesped.getTelefono();
            this.correo = huesped.getCorreo();
            this.dni = huesped.getDni();
            this.fechaNacimiento = huesped.getFechaNacimiento();
        }catch(NullPointerException ne){
            throw new NullPointerException("ERROR: No es posible copiar un hu�sped nulo.");
        }

    }

    /*
    Este m�todo debe normalizar un nombre eliminando caracteres en blanco de sobra y poniendo en may�sculas la primera letra de
    cada palabra y en min�sculas las dem�s.*/
    private String formateaNombre(String nombre){
        nombre = nombre.trim();//Primero limpiamos los espacios en blanco al principio y al final
        String nombreformateado = "";//Creamos una variable que nos permita almacenar el nombre correctamente formateado
        String[] arrayauxiliar = nombre.split("\s");
        for(int i = 0; i < arrayauxiliar.length; i++) {//Recorremos cada una de las palabras y establecemos su formato correcto
            if(arrayauxiliar[i] != ""){//Eliminamos los espacios adicionales
                String auxiliarminus = arrayauxiliar[i].toLowerCase();//Todas las letras pasan a ser min�sculas
                String auxiliarmayus = arrayauxiliar[i].toUpperCase();//Todas las letras may�sculas
                nombreformateado += auxiliarminus.replaceFirst(".", "" + auxiliarmayus.charAt(0)) + " ";//Sustituimos la primera letra de la cadena de min�sculas por la letra may�scula de la cadena de may�sculas y a�adimos un espacio para separar las palabras.
            }
        }
        nombreformateado = nombreformateado.trim();//Reformateamos para eliminar el �ltimo caracter espacio
        if(nombreformateado.equals(""))//Si al formatear el nombre, obtenemos una cadena vac�a
            throw new IllegalArgumentException("ERROR: El nombre de un hu�sped no puede estar vac�o.");
        return nombreformateado;
    }

    /*Este m�todo har� uso de los grupos de las expresiones regulares (para ello has debido definir la expresi�n regular con grupos)
     para quedarse con el n�mero por un lado y con la letra por otro. Para saber si la letra es v�lida puedes seguir las
     instrucciones del siguiente enlace: Comprobar letra dni.*/
    private boolean comprobarLetraDni(String dni){
        boolean valido = false;

        Pattern patron = Pattern.compile(ER_DNI);//Creamos un patron mediante una expresi�n regular por grupos
        Matcher acomplamiento = patron.matcher(dni);

        String valor = null;//Creamos una variable para guardar el primer grupo
        String letra = null;//Creamos una variable para guardar el segundo grupo (LETRA)

        if(acomplamiento.matches()){
            valor = acomplamiento.group(1);
            letra = acomplamiento.group(2);
        }

        int valorletra = 0;//Creamos una variable auxiliar para calcular la suma de los d�gitos del DNI
        valorletra = Integer.parseInt(valor);

        valorletra = valorletra % 23;//Realizamos el c�lculo del m�dulo sobre 23 para comprobar que la letra coincida

        switch (valorletra) {
            case 0:
                valido = letra.matches("[tT]");
                break;
            case 1:
                valido = letra.matches("[rR]");
                break;
            case 2:
                valido = letra.matches("[wW]");
                break;
            case 3:
                valido = letra.matches("[aA]");
                break;
            case 4:
                valido = letra.matches("[gG]");
                break;
            case 5:
                valido = letra.matches("[mM]");
                break;
            case 6:
                valido = letra.matches("[yY]");
                break;
            case 7:
                valido = letra.matches("[fF]");
                break;
            case 8:
                valido = letra.matches("[pP]");
                break;
            case 9:
                valido = letra.matches("[dD]");
                break;
            case 10:
                valido = letra.matches("[xX]");
                break;
            case 11:
                valido = letra.matches("[bB]");
                break;
            case 12:
                valido = letra.matches("[nN]");
                break;
            case 13:
                valido = letra.matches("[jJ]");
                break;
            case 14:
                valido = letra.matches("[zZ]");
                break;
            case 15:
                valido = letra.matches("[sS]");
                break;
            case 16:
                valido = letra.matches("[qQ]");
                break;
            case 17:
                valido = letra.matches("[vV]");
                break;
            case 18:
                valido = letra.matches("[hH]");
                break;
            case 19:
                valido = letra.matches("[lL]");
                break;
            case 20:
                valido = letra.matches("[cC]");
                break;
            case 21:
                valido = letra.matches("[kK]");
                break;
            case 22:
                valido = letra.matches("[eE]");
        }

        if(!valido){
            throw new IllegalArgumentException("ERROR: La letra del dni del hu�sped no es correcta.");
        }

        return valido;
    }

   /*Crea los m�todos de acceso y modificaci�n de cada atributo con la visibilidad adecuada, teniendo en cuenta que un nombre
   estar� compuesto de palabras separadas por un espacio y cada palabra comenzar� con una may�scula y continuar� con min�sculas.
   El DNI, el tel�fono y el correo deben tambi�n tener un formato v�lido. Adem�s debes comprobar que la letra del DNI sea
   correcta. Debes crear las constantes para las expresiones regulares que luego utilizar�s en los m�todos de modificaci�n.
   Los m�todos de modificaci�n lanzar�n las excepciones adecuadas en caso de que el valor que se pretenda asignar al atributo no
   sea adecuado. */

    public String getNombre(){
        return this.nombre;
    }
    public String getTelefono() {
        return this.telefono;
    }
    public String getCorreo(){
        return this.correo;
    }
    public String getDni(){
        return this.dni;
    }
    public LocalDate getFechaNacimiento(){
        return this.fechaNacimiento;
    }
    public void setNombre(String nombre){
        try{
            if(nombre.isEmpty()){
                throw new IllegalArgumentException("ERROR: El nombre de un hu�sped no puede estar vac�o.");
            }
            this.nombre = formateaNombre(nombre);//Formateamos el nombre introducido
        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: El nombre de un hu�sped no puede ser nulo.");
        }
    }
    public void setTelefono(String telefono) {
        try{
            if (telefono.matches(ER_TELEFONO)) {//Comprobamos el formato del tel�fono, si v�lido a�adimos
                this.telefono = telefono;
            }else{
                throw new IllegalArgumentException("ERROR: El tel�fono del hu�sped no tiene un formato v�lido.");
            }
        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: El tel�fono de un hu�sped no puede ser nulo.");
        }
    }
    public void setCorreo(String correo){
        try{
            if(correo.matches(ER_CORREO)){//Comprobamos el formato del correo, si v�lido a�adimos
                this.correo = correo;
            }else{
                throw new IllegalArgumentException("ERROR: El correo del hu�sped no tiene un formato v�lido.");
            }
        }catch(NullPointerException e) {
            throw new NullPointerException("ERROR: El correo de un hu�sped no puede ser nulo.");
        }
    }
    private void setDni(String dni){
        boolean valido = false;
        try{
            if(dni.matches(ER_DNI))//Comprobamos el formato del dni
                if(comprobarLetraDni(dni))//comprobamos que la letra es correcta
                    valido = true;//Si v�lido a�adimos

        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: El dni de un hu�sped no puede ser nulo.");
        }
        if(valido){
            this.dni = dni;
        }else{
            throw new IllegalArgumentException("ERROR: El dni del hu�sped no tiene un formato v�lido.");
        }
    }
    private void setFechaNacimiento(LocalDate fechaNacimiento){
        try{
            if(fechaNacimiento.isAfter(LocalDate.now())){
                throw new IllegalArgumentException("ERROR: La fecha de nacimiento no puede ser hoy o posterior.");
            }
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fecha = fechaNacimiento.format(pattern);
            boolean valido = false;//Variable que controla si la fecha es v�lida.
            if(fecha.matches(FORMATO_FECHA)) {
                Pattern patron = Pattern.compile(FORMATO_FECHA);
                Matcher acoplamiento = patron.matcher(fecha);
                String dia = "";
                String mes = "";
                String ano = "";
                if (acoplamiento.matches()) {
                    dia = acoplamiento.group(3);
                    mes = acoplamiento.group(2);
                    ano = acoplamiento.group(1);
                }

                if (!mes.matches("02")) {//Como el formato de la fecha es correcto, si el mes no es febrero no tendremos que realizar m�s comprobaciones
                    this.fechaNacimiento = fechaNacimiento;
                    valido = true;
                } else {//Si por el contrario la fecha pertenece a febrero
                    if (Integer.parseInt(ano) % 4 == 0) {//Comprobamos que el a�o sea bisiesto
                        if (Integer.parseInt(dia) < 30) {
                            this.fechaNacimiento = fechaNacimiento;
                            valido = true;
                        }
                    } else {
                        if (Integer.parseInt(dia) < 29) {//Si el a�o no es bisiesto
                            this.fechaNacimiento = fechaNacimiento;
                            valido = true;
                        }
                    }
                }
                if(!valido){
                    System.err.println("La fecha introducida no es v�lida.");
                }
            }
        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: La fecha de nacimiento de un hu�sped no puede ser nula.");
        }
    }

    /*Crea el m�todo getIniciales. Este m�todo devolver� las iniciales del nombre.*/
    private String getIniciales(){
        String iniciales = "";
        String[] array = nombre.split(" ");
        for(int i = 0; i < array.length; i++){
            iniciales += array[i].charAt(0);
        }
        return iniciales;
    }



    /*Crea los m�todos equals y hashCode, sabiendo que dos hu�spedes se considerar�n iguales si su DNI es el mismo*/
    @Override
    public boolean equals(Object obj) {
        Huesped huesped = (Huesped)obj;
        return huesped.getDni().equals(this.dni);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /*Crea el m�todo toString que devuelva la cadena que esperan los tests. */
    public String toString(){
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha = fechaNacimiento.format(pattern);
        String cadena = String.format("nombre=%s (%s), DNI=%s, correo=%s, tel�fono=%s, fecha nacimiento=%s", this.nombre, getIniciales(), this.dni, this.correo, this.telefono, fecha);
        return cadena;
    }

    @Override
    public int compareTo(Huesped o) {
        return this.nombre.compareTo(o.getNombre());
    }
}