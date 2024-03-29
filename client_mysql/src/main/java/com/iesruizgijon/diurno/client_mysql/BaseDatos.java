/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iesruizgijon.diurno.client_mysql;

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
public class BaseDatos {

    //ATRIBUTOS
    private Connection conexion;

    private final String USER;
    private final String PASS;
    private final String nameDB;
    private final String URL = "jdbc:mysql://localhost:3306/";    //o en lugar de localhost podemos poner: 127.0.0.1:3306

    //CONSTRUCTOR
    public BaseDatos(String nameDB, String USER, String PASS) {
        this.USER = USER;
        this.PASS = PASS;
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
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conectado;
    }

    ////Para desconexiones
    public void desconecta() {

        try {
            if (conexion != null) {
                conexion.close();
                System.out.println("\nDesconexión completada\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("\nError en la desconexión\n");
        }
    }

    ////Para realizar consultas
    public void consultaPrueba() {

        System.out.println("\n---------------------------------------------\n"
                + "PRUEBA 1 - Consulta simple:\n");

        String company;
        String apellidos;
        String nombre;

        try {

            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("select company, last_name, first_name from customers limit 10");

            while (resultado.next()) {
                company = resultado.getString("company");
                apellidos = resultado.getString("last_name");
                nombre = resultado.getString("first_name");

                System.out.println("Company " + company + " Apellidos " + apellidos + " Nombre " + nombre);

            }
            resultado.close();
            sentencia.close();

            System.out.println("\nConsulta realizada correctamente\n"
                    + "---------------------------------------------\n");
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Crea el método consultaPrueba2 en la que aparezcan los nombres de los clientes 
    //concatenados apellidos-nombre con los pedidos realizados (2 campos)
    public void consultaPrueba2() {

        System.out.println("\n---------------------------------------------\n"
                + "PRUEBA 2 - Consulta concat - inner:\n");

        String NombreCliente;
        int NumeroPedido;
        final String consulta
                = """
                   select concat(c.last_name, ' ', c.first_name) AS NombreCliente, o.id AS pedido
                   from customers AS c
                   INNER JOIN orders AS o
                   ON c.id = o.customer_id
                   limit 10;
                   """;

        try {

            Statement sentencia = conexion.createStatement();

            //la consulta puede pasarse directamente o como una variable ya definida:
            ResultSet resultado = sentencia.executeQuery(consulta);
            //ResultSet resultado = sentencia.executeQuery("select concat(c.last_name, ' ', c.first_name) AS NombreCliente, o.id AS pedido from customers AS c INNER JOIN orders AS o ON c.id = o.customer_id limit 10");

            while (resultado.next()) {
                NombreCliente = resultado.getString("NombreCliente");
                NumeroPedido = resultado.getInt("pedido");

                System.out.println("Cliente:\t" + NombreCliente + "\t\t---->\t\tPedido:\t" + NumeroPedido);

            }
            resultado.close();
            sentencia.close();

            System.out.println("\nConsulta realizada correctamente\n"
                    + "---------------------------------------------\n");
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    

        ///   /*  /// --->   G.U.I.   <--- \\\   *\   \\\  
    
        //consultas de usuario desde GUI - PROFE
        public ArrayList<String> getQuery(String consulta){
        
        ArrayList<String> tabla = new ArrayList<>();
        String fila = new String();
        
        
        try {   //intentamos lo siguiente
            
            //conecto con la base de datos y obtener el resultado de la consulta
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(consulta);
            
            //obtiene informacion como PK, cascade, nombre tabla...
            ResultSetMetaData rsmd = resultado.getMetaData();
            
            //gracias a los metadatos obtenemos el nº de columnas
            int numeroColumnas = rsmd.getColumnCount();
            
            //mientras sigamos obteniendo resultados (es el cursor que va de fila en fila)
            while (resultado.next()) {
                
                //recorremos todas las columnas de la fila
                for (int i = 0; i <numeroColumnas;i++){
                    
                    //el valor lo guardamos como String (i+1 porque aquí se empieza en 1)
                    //concatenamos los datos de cada columna en una cadena, que será nuestra fila
                    fila = fila + " | " + resultado.getString(i+1);
                }
                
               //el valor de todas las columnas (fila), añadimos a nuestro arrayList
               tabla.add(fila);
               
               //vaciamos el String de fila, para guardar la información de la siguiente fila
               fila = new String();

            }
            
            //cerramos el resultado y la conexión
            resultado.close();
            sentencia.close();

        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tabla;
    }
    
    

    //Hacer un Describe de la tabla que se le pasa
    public String[] describe(String nombre) {

        System.out.println("\n---------------------------------------------\n"
                + "DESCRIBE DE TABLA: " + nombre + "\n");

        String[] columnas = null;
        int n_columnas = 0;
        int i = 0;

        try {

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

            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnas;
    }
    

    
    //ver los nombres de las bases de datos
    public void dbNames() {
        
        System.out.println("\n---------------------------------------------\n"
                + "NOMBRES DE BASE DE DATOS:\n");

        try {

            //Se intenta establecer una conexión a la base de datos utilizando el método getConnection de DriverManager.
            conexion = DriverManager.getConnection(this.URL + nameDB, USER, PASS);

            System.out.println("Conexión realizada con éxito");

            Statement stmt = conexion.createStatement();

            //Recuperando los datos, usamos el mismo comando que usariamos en mysql (show databases;)
            ResultSet rs = stmt.executeQuery("Show Databases");

            System.out.println("List of databases: ");

            while (rs.next()) {
                
                //imprime el nombre de cada base de datos
                System.out.print(rs.getString(1));

                System.out.println();

            }

        } catch (SQLException ex) {

            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
        
    
    ///   /*  /// --->   G.U.I.   <--- \\\   *\   \\\
        
    //nombres de bases de datos para gui
    public String dataBaseNames() {
        
        System.out.println("\n---------------------------------------------\n"
                + "NOMBRES DE BASE DE DATOS:\n");
        
        String names = "";

        try {

            //Se intenta establecer una conexión a la base de datos utilizando el método getConnection de DriverManager.
            conexion = DriverManager.getConnection(this.URL + nameDB, USER, PASS);

            System.out.println("Conexión realizada con éxito");

            Statement stmt = conexion.createStatement();

            //Recuperando los datos, usamos el mismo comando que usariamos en mysql (show databases;)
            ResultSet rs = stmt.executeQuery("Show Databases");

            System.out.println("List of databases: ");

            while (rs.next()) {
                
                //imprime el nombre de cada base de datos
                names = names + rs.getString(1) + "\n";
//                System.out.print(rs.getString(1));
//
//                System.out.println();

            }

        } catch (SQLException ex) {

            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        return names;

    }

}
