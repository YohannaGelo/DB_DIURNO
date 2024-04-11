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
public class mainExamen {

    public static void main(String[] args) throws SQLException {

        dataBaseExamen bd = new dataBaseExamen("northwind", "root", "123qweASD_");
        ArrayList<String> tabla = new ArrayList<>();
        String columnas[] = {"last_name", "first_Name"};

        if (bd.Conecta()) {
            System.out.println("Conectada con éxito\n\n");
        } else {
            System.out.println("Problemas con la conexión");
        }

        //llamo al método
        tabla = bd.getData("customers", columnas, 5);

        //muestro resultado de la consulta
        System.out.println("------------------------------------------------------------------------------\n");
        System.out.println("\tRESULTADO DE LA CONSULTA:");
        System.out.println("\t-------------------------\n");
        for (String fila : tabla) {

            System.out.println(fila);

        }

        System.out.println("\n------------------------------------------------------------------------------\n");
        bd.Desconecta();

    }
}
