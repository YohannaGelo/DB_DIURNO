/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iesruizgijon.diurno._programacionbasedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yohannagelo
 */
public class BaseDatos {

    //ATRIBUTOS
    private Connection conexion;

    private String USER;
    private String PASS;
    private String nameDB;
    private String URL = "jdbc:mysql://localhost:3306/";    //o en lugar de localhost podemos poner: 127.0.0.1:3306

    //CONSTRUCTOR
    public BaseDatos(String USER, String PASS, String nameDB) {
        this.USER = USER;
        this.PASS = PASS;
        this.nameDB = nameDB;
    }

    //MÉTODOS
    ////Para conexiones
    public void conecta() {

        //Esta variable se utilizará para representar la conexión a la base de datos.
        Connection conexion;

        //Comienza un bloque try para manejar posibles excepciones.
        try {

            //Se intenta establecer una conexión a la base de datos utilizando el método getConnection de DriverManager.
            conexion = DriverManager.getConnection(this.URL + nameDB, USER, PASS);
            System.out.println("Conexión establecida");

        } catch (SQLException ex) {     //Si se produce una excepción SQLException, el flujo del programa se desvía aquí.
            //Excepción para errores
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error de conexión");

        }
        
    }

        
    ////Para desconexiones
    public void desconecta() {

        try {
            if (conexion != null) {
            conexion.close();
                System.out.println("Desconexión completada");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en la desconexión");
        }
    }
    

}
