/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
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
public class App {

    public static void main(String[] args) throws SQLException {
        
        //establecemos los datos como constantes
        final String USER = "root";
        final String PASS = "123qweASD_";
        final String nameDB = "northwind";
        final String URL = "jdbc:mysql://localhost:3306/";    //o en lugar de localhost podemos poner: 127.0.0.1:3306
        

        BaseDatos bd = new BaseDatos(USER, PASS, nameDB);
        
        bd.conecta();
        
        bd.desconecta();
        
        
    }
}
