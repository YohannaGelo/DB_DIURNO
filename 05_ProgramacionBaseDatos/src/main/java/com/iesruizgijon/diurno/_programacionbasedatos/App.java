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
        
        
        //Instancia objeto Base de Datos
        BaseDatos bd = new BaseDatos(nameDB, USER, PASS);
        
        
        //Conexión
        bd.conecta();
        
        
        //Ver nombres de la base de datos
        bd.dbNames();
        
        
        //Hacer un describe de alguna tabla
        String[] TablaPedidos = bd.describe("orders");
        
        for (String TablaPedido : TablaPedidos) {
            System.out.println(TablaPedido);
        }
        
        
        
        //Consultas
        bd.consultaPrueba();

        bd.consultaPrueba2();
        
        
        //Desconexión
        bd.desconecta();
        
        
    }
}
