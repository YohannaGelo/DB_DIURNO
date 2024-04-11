/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.iesruizgijon.diurno._programacionbasedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author yohannagelo
 */
public class mainEj1 {

    public static void main(String[] args) throws SQLException {

        Scanner s = new Scanner(System.in);

        //establecemos los datos como constantes
        final String USER = "root";
        final String PASS = "123qweASD_";
        String nameDB = "northwind";

        //Instancia objeto Base de Datos
        dataBaseEj1 bd = new dataBaseEj1(nameDB, USER, PASS);

        //Conexión
        boolean conexion_correcta = bd.conecta();

        ////Aviso al usuario si se ha conectado correctamente o no
        if (conexion_correcta) {

            JOptionPane.showMessageDialog(null, "Conexión establecida");

        } else {
            JOptionPane.showMessageDialog(null, "Problemas en la conexión");
        }

        //Ver nombres de la base de datos
        bd.dbNames();

        //pido al usuario que elija una db
        System.out.print("\n  · Por favor seleccione una base de datos: ");
        int choose = s.nextInt();

        //actualizo la db con la que vamos a trabajar y la seteamos en nuestra db
        nameDB = bd.dbSelect(choose);
        bd.setNameDB(nameDB);

        //Consulta tablas de alguna DB
        bd.tablesName(nameDB);

        //pido al usuario que elija una db
        System.out.print("\n  · Por favor seleccione una tabla: ");
        choose = s.nextInt();

        //guardo el nombre de la base de datos en una variable
        String tableSelectName = bd.tableSelect(choose);

        //muestro un describe de la tabla
        String[] TablaPedidos = bd.describe(tableSelectName);

        for (String TablaPedido : TablaPedidos) {
            System.out.println(TablaPedido);
        }
        
        s.nextLine();

        //consultas
        System.out.print("\n  · Ingrese la consulta deseada: \n    ~ ");
        String query = s.nextLine();

        //creamos Array para guardar el resultado de las consultas
        //consulta de prueba:   select first_name, last_name, city, email_address from customers limit 10;
        ArrayList<String> tabla = null;
        tabla = bd.getQuery(query);
        
        
        //visualizamos la consulta
        bd.showQuery(tabla);
     

        //Desconexión
        bd.desconecta();

    }
}
