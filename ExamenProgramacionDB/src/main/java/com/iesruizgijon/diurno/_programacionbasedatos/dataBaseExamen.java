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
public class dataBaseExamen {

    private Connection conexion;
    private String USER;
    private String PASS;
    private String nameDB;
    private String URL = "jdbc:mysql://127.0.0.1:3306/";

    public dataBaseExamen(String nameDB, String USER, String PASS) {
        this.USER = USER;
        this.PASS = PASS;
        this.nameDB = nameDB;
    }

    public boolean Conecta() {

        boolean conectado = false;

        try {
            conexion = DriverManager.getConnection(URL + nameDB, USER, PASS);
            conectado = true;

        } catch (SQLException ex) {
            Logger.getLogger(mainExamen.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conectado;
    }

    public void Desconecta() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(dataBaseExamen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultaPrueba() {
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

        } catch (SQLException ex) {
            Logger.getLogger(dataBaseExamen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void consultaPrueba2() {

        String nombre;
        int pedido;
        final String consulta
                = """
                    select concat_ws("-", last_name, first_name) as
                    nombre, orders.id as numeroPedido from customers
                    inner join orders on customers.id = 
                    orders.customer_id order by nombre, 
                    numeroPedido;
                    """;
        try {

            //ResultSet resultado = conexion.createStatement().executeQuery(consulta);
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(consulta);
            while (resultado.next()) {
                nombre = resultado.getString("nombre");
                pedido = resultado.getInt("numeroPedido");
                System.out.println("Nombre: " + nombre + " numero pedido: " + pedido);
            }
            resultado.close();
            sentencia.close();

        } catch (SQLException ex) {
            Logger.getLogger(dataBaseExamen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String[] describe(String nombre) {

        String[] columnas = null;
        int n_columnas = 0;
        int i = 0;

        try {

            Statement statement = conexion.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT * FROM " + nombre);
            ResultSetMetaData metadatos = resultset.getMetaData();

            n_columnas = metadatos.getColumnCount();
            columnas = new String[n_columnas];

            for (i = 1; i <= n_columnas; i++) {
                columnas[i - 1] = metadatos.getColumnName(i);
            }
        } catch (SQLException ex) {

            Logger.getLogger(dataBaseExamen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnas;
    }

    public void getDataBaseNames() {

        try {

            Statement stmt = conexion.createStatement();

            //Retrieving the data
            ResultSet rs = stmt.executeQuery("Show Databases");

            System.out.println("List of databases: ");

            while (rs.next()) {

                System.out.print(rs.getString(1));

                System.out.println();

            }

        } catch (SQLException ex) {

            Logger.getLogger(dataBaseExamen.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public ArrayList<String> getQuery(String consulta) {

        ArrayList<String> tabla = new ArrayList<>();
        String fila = new String();

        try {
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(consulta);
            ResultSetMetaData rsmd = resultado.getMetaData();
            int numeroColumnas = rsmd.getColumnCount();

            while (resultado.next()) {
                for (int i = 0; i < numeroColumnas; i++) {
                    fila = fila + " | " + resultado.getString(i + 1);
                }
                tabla.add(fila);
                fila = new String();

            }

            resultado.close();
            sentencia.close();

        } catch (SQLException ex) {
            Logger.getLogger(dataBaseExamen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tabla;
    }

    public ArrayList<String> getData(String tableName, String columns[], int numero_filas) {

        ArrayList<String> tabla = new ArrayList<>();

        //TODO
        
        //Primero creo la consulta con los datos que paso como parametro
        ////lo hago añadiendo la información a una cadena
        String query = "SELECT ";

        //despues de select añado las columnas, poniendo , entre ellas mientras no sea la última
        for (int i = 0; i < columns.length; i++) {

            if (i == 0) {
                query = query + columns[i] + ", ";

            } else {
                query = query + columns[i];

            }

        }
        
        //finalmente añado el resto de información a la consulta
        query = query + " FROM " + tableName + " LIMIT " + numero_filas + ";";
        
        //comienzo a crear el método para obtener el resultado de la consulta
        String fila = new String();

        try {   //intentamos lo siguiente

            //conecto con la base de datos y obtener el resultado de la consulta
            Statement sentencia = conexion.createStatement();
            
            //en resultado ejecutaré la consulta creada previamente
            ResultSet resultado = sentencia.executeQuery(query);
            
            ResultSetMetaData metadatos = resultado.getMetaData();  //estos nos va a ofrecer información sobre nombre de db, tablas, tipo de datos...

            //obtiene informacion como PK, cascade, nombre tabla...
            ResultSetMetaData rsmd = resultado.getMetaData();

            //gracias a los metadatos obtenemos el nº de columnas
            int numeroColumnas = rsmd.getColumnCount();

            rsmd.getColumnName(numeroColumnas);

            String columnas = "";

            //recorremos el array
            for (int i = 0; i < numeroColumnas; i++) {

                //se coge el valor de la columna y lo formateamos para que tenga un ancho fijo
                String valor = String.format("%-16s", metadatos.getColumnName(i + 1));

                //si el dato guardado es mayor que los 16 caracteres de ancho que he establecido, se corta
                if (valor.length() > 16) {
                    valor = valor.substring(0, 16);
                }

                //añado el valor de la columna a la fila de cabecera y un separador(|)
                columnas += valor + " | ";

                //pongo un separador (|) al comienzco de cada fila
                if (i == 0) {
                    columnas = " | " + columnas;
                }

            }

            //con este for alamaceno los puntos que necesito para creal las lineas separadoras de la tabla
            String separador = "";
            for (int i = 0; i < columnas.length() - 4; i++) {
                separador += "·";
            }

            //añado el separador con un + antes y otro al final
            tabla.add(" +" + separador + "+");

            //el valor de todas las columnas (fila), añadimos a nuestro arrayList
            tabla.add(columnas);

            //añado el separador con un + antes y otro al final
            tabla.add(" +" + separador + "+");

            //mientras sigamos obteniendo resultados (es el cursor que va de fila en fila)
            while (resultado.next()) {

                //recorremos todas las columnas de la fila
                for (int i = 0; i < numeroColumnas; i++) {

                    //PRUEBA
                    //se coge el valor de la columna y lo formateamos para que tenga un ancho fijo
                    String valor = String.format("%-16s", resultado.getString(i + 1));

                    //si el dato guardado es mayor que los 16 caracteres de ancho que he establecido, se corta
                    if (valor.length() > 16) {
                        valor = valor.substring(0, 16);
                    }

                    //añado el valor de la columna a la fila y un separador(|)
                    fila += valor + " | ";

                    //pongo un separador (|) al comienzco de cada fila
                    if (i == 0) {
                        fila = " | " + fila;
                    }

                }

                //el valor de todas las columnas (fila), añadimos a nuestro arrayList
                tabla.add(fila);

                //vaciamos el String de fila, para guardar la información de la siguiente fila
                fila = new String();

            }

            //añado el separador con un + antes y otro al final
            tabla.add(" +" + separador + "+");

            //cerramos el resultado y la conexión
            resultado.close();
            sentencia.close();

        } catch (SQLException ex) {
            Logger.getLogger(dataBaseExamen.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tabla;

    }

}
