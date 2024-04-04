/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iesruizgijon.diurno._programacionbasedatos;

import com.mysql.cj.conf.PropertyKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yohannagelo
 */
public class dataBaseEj1 {

    //ATRIBUTOS
    private Connection conexion;

    private final String USER;
    private final String PASS;
    private String nameDB;
    private final String URL = "jdbc:mysql://localhost:3306/";    //o en lugar de localhost podemos poner: 127.0.0.1:3306

    //CONSTRUCTOR
    public dataBaseEj1(String nameDB, String USER, String PASS) {
        this.USER = USER;
        this.PASS = PASS;
        this.nameDB = nameDB;
    }

    //SETTER
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public void setNameDB(String nameDB) {
        this.nameDB = nameDB;
    }

    //MÉTODOS
    ////Para conexiones en GUI
    public boolean conecta() {

        boolean conectado = false;

        try {
            conexion = DriverManager.getConnection(URL + nameDB, USER, PASS);
            conectado = true;

        } catch (SQLException ex) {
            Logger.getLogger(dataBaseEj1.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conectado;
    }

    ////Para desconexiones
    public void desconecta() {

        try {
            if (conexion != null) {
                conexion.close();
                System.out.println("\n\n\t# Desconexión completada\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(dataBaseEj1.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("\nError en la desconexión\n");
        }
    }

    //ver los nombres de las bases de datos
    public void dbNames() {

        System.out.println("\n-------------------------------------------------------------\n"
                + "NOMBRES DE BASE DE DATOS:\n");

        try {

            //Se intenta establecer una conexión a la base de datos utilizando el método getConnection de DriverManager.
            conexion = DriverManager.getConnection(this.URL + nameDB, USER, PASS);

            System.out.println("\t# Conexión realizada con éxito");

            Statement stmt = conexion.createStatement();

            //Recuperando los datos, usamos el mismo comando que usariamos en mysql (show databases;)
            ResultSet rs = stmt.executeQuery("Show Databases");

            System.out.println("\n   Lista de base de datos: ");
            System.out.println("   ~~~~~~~~~~~~~~~~~~~~~~\n");

            int counter = 1;

            while (rs.next()) {

                //imprime el nombre de cada base de datos
                System.out.print(counter + ". " + rs.getString(1));

                System.out.println();

                counter++;

            }

        } catch (SQLException ex) {

            Logger.getLogger(dataBaseEj1.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    //busca el nombre de la db seleccionada por el usuario
    public String dbSelect(int choose) {

        try {

            //Se intenta establecer una conexión a la base de datos utilizando el método getConnection de DriverManager.
            conexion = DriverManager.getConnection(this.URL + nameDB, USER, PASS);

            Statement stmt = conexion.createStatement();

            //Recuperando los datos, usamos el mismo comando que usariamos en mysql (show databases;)
            ResultSet rs = stmt.executeQuery("Show Databases");

            int counter = 1;

            while (rs.next()) {

                if (choose == counter) {

                    return rs.getString(1);

                }

                rs.getString(1);
                counter++;

            }

        } catch (SQLException ex) {

            Logger.getLogger(dataBaseEj1.class.getName()).log(Level.SEVERE, null, ex);

        }

        return "El número seleccionado no pertenece a ninguna base de datos.";

    }

    //dada un base de datos devuelve las tablas que contiene
    public void tablesName(String nameDBforTables) {

        System.out.println("\n-------------------------------------------------------------\n"
                + "NOMBRES DE TABLAS DE " + nameDBforTables + ":\n");

        try {

            //Se intenta establecer una conexión a la base de datos utilizando el método getConnection de DriverManager.
            conexion = DriverManager.getConnection(this.URL + nameDBforTables, USER, PASS);

            System.out.println("\t# Conexión realizada con éxito");

            Statement stmt = conexion.createStatement();

            //Recuperando los datos, usamos el mismo comando que usariamos en mysql (show tables;)
            ResultSet rs = stmt.executeQuery("Show tables");

            System.out.println("\n   Lista de tablas: ");
            System.out.println("   ~~~~~~~~~~~~~~~\n");

            int counter = 1;

            while (rs.next()) {

                //imprime el nombre de cada tabla
                System.out.print(counter + ". " + rs.getString(1));

                System.out.println();
                counter++;

            }

        } catch (SQLException ex) {

            Logger.getLogger(dataBaseEj1.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    //busca el nombre de la tabla seleccionada por el usuario
    public String tableSelect(int choose) {

        try {

            //Se intenta establecer una conexión a la base de datos utilizando el método getConnection de DriverManager.
            conexion = DriverManager.getConnection(this.URL + nameDB, USER, PASS);

            Statement stmt = conexion.createStatement();

            //Recuperando los datos, usamos el mismo comando que usariamos en mysql (show databases;)
            ResultSet rs = stmt.executeQuery("Show tables");

            int counter = 1;

            while (rs.next()) {

                if (choose == counter) {

                    return rs.getString(1);

                }

                rs.getString(1);
                counter++;

            }

        } catch (SQLException ex) {

            Logger.getLogger(dataBaseEj1.class.getName()).log(Level.SEVERE, null, ex);

        }

        return "El número seleccionado no pertenece a ninguna tabla";

    }

    //metodo que haga un describe de una tabla seleccionada
    public String[] describe(String nombre) {

        System.out.println("\n-------------------------------------------------------------\n"
                + "DESCRIBE DE TABLA: " + nombre + "\n");

        String[] columnas = null;
        int n_columnas = 0;
        int i = 0;

        try {

            //Se intenta establecer una conexión a la base de datos utilizando el método getConnection de DriverManager.
            conexion = DriverManager.getConnection(this.URL + nameDB, USER, PASS);

            Statement statement = conexion.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT * FROM " + nombre);
            ResultSetMetaData metadatos = resultset.getMetaData();  //estos nos va a ofrecer información sobre nombre de db, tablas, tipo de datos...

            //contamos el número de columnas y lo alamcenamos
            n_columnas = metadatos.getColumnCount();
            //ya defino mi array
            columnas = new String[n_columnas];

            //recorremos el array
            for (i = 1; i <= n_columnas; i++) {
                //getColumnName comienza en 1, que en realidad sería 0 en java... por eso el i-1
                columnas[i - 1] = metadatos.getColumnName(i);
            }

        } catch (SQLException ex) {

            Logger.getLogger(dataBaseEj1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnas;
    }

    //consultas de usuario desde GUI - PROFE
    public ArrayList<String> getQuery(String consulta) {

        ArrayList<String> tabla = new ArrayList<>();
        String fila = new String();

        try {   //intentamos lo siguiente

            //conecto con la base de datos y obtener el resultado de la consulta
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(consulta);
            ResultSetMetaData metadatos = resultado.getMetaData();  //estos nos va a ofrecer información sobre nombre de db, tablas, tipo de datos...

            //obtiene informacion como PK, cascade, nombre tabla...
            ResultSetMetaData rsmd = resultado.getMetaData();

            //gracias a los metadatos obtenemos el nº de columnas
            int numeroColumnas = rsmd.getColumnCount();

            rsmd.getColumnName(numeroColumnas);

            String columnas = "";

            //recorremos el array
            for (int i = 0; i < numeroColumnas; i++) {
                //getColumnName
                columnas = columnas + " | " + metadatos.getColumnName(i + 1);
            }

            //añado diseño de tabla
            tabla.add(" +···································································+");

            //el valor de todas las columnas (fila), añadimos a nuestro arrayList
            tabla.add(columnas);

            //añado diseño de tabla
            tabla.add(" +···································································+");

            //mientras sigamos obteniendo resultados (es el cursor que va de fila en fila)
            while (resultado.next()) {

                //recorremos todas las columnas de la fila
                for (int i = 0; i < numeroColumnas; i++) {

                    //el valor lo guardamos como String (i+1 porque aquí se empieza en 1)
                    //concatenamos los datos de cada columna en una cadena, que será nuestra fila
                    fila = fila + " | " + resultado.getString(i + 1);
                }

                //el valor de todas las columnas (fila), añadimos a nuestro arrayList
                tabla.add(fila);

                //vaciamos el String de fila, para guardar la información de la siguiente fila
                fila = new String();

            }

            //añado diseño de tabla
            tabla.add(" +···································································+");

            //cerramos el resultado y la conexión
            resultado.close();
            sentencia.close();

        } catch (SQLException ex) {
            Logger.getLogger(dataBaseEj1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tabla;
    }

    //muestra la consulta
    public void showQuery(ArrayList<String> tabla) {

        System.out.println("\n-------------------------------------------------------------\n"
                + "RESULTADO DE LA CONSULTA:\n");

        // Obtener el número de columnas para calcular el ancho de cada columna
        int numColumns = tabla.get(1).split("\\|").length; // Suponemos que la segunda línea contiene los nombres de las columnas
        int[] columnWidths = new int[numColumns];

        // Calcular el ancho máximo de cada columna
        for (String row : tabla) {
            String[] columns = row.split("\\|");
            for (int i = 0; i < columns.length; i++) {
                String column = columns[i].trim(); // Eliminar espacios en blanco alrededor de la columna
                if (column.length() > columnWidths[i]) {
                    columnWidths[i] = column.length();
                }
            }
        }

// Construir el formato para cada fila con columnas de tamaño uniforme
        StringBuilder formatBuilder = new StringBuilder();
        for (int width : columnWidths) {
            formatBuilder.append("%-").append(width + 2).append("s  "); // Añadir 2 espacios adicionales para separación
        }
        String format = formatBuilder.toString();

// Mostrar la tabla con columnas de tamaño uniforme
        for (String row : tabla) {
            // Dividir la fila en columnas
            String[] columns = row.split("\\|");

            // Eliminar espacios en blanco alrededor de cada columna
            for (int i = 0; i < columns.length; i++) {
                columns[i] = columns[i].trim();
            }

            // Imprimir cada columna utilizando el formato
            System.out.printf(format, (Object[]) columns);
            System.out.println(); // Nueva línea después de imprimir una fila
        }
    }

//    public void showQuery(ArrayList<String> tabla) {
//
//        System.out.println("\n-------------------------------------------------------------\n"
//                + "RESULTADO DE LA CONSULTA:\n");
//
//        //muestro el resultado de la consulta en la caja de salida
//        String contenido = "";
//
//        for (String elemento : tabla) {
//
//            contenido = contenido + elemento + "\n";
//        }
//
//        System.out.println(contenido);
//
//    }
    }
