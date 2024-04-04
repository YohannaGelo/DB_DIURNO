/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.iesruizgijon.diurno.jtableexamples;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
 
public class JTableExamples {
    
    // crea el objeto frame
    JFrame f;
    // crea el objeto Table
    JTable j;
 
    // Constructor
    JTableExamples()
    {
        // Frame initialization
        f = new JFrame();
 
        // Frame Title - podemos dar un titulo al frame
        f.setTitle("JTable Example");
 
        // Metemos los datos en filas y columnas
        String[][] data = {
            { "Kundan Kumar Jha", "4031", "CSE" },
            { "Anand Jha", "6014", "IT" }
        };
 
        // Damos nombres a las 
        String[] columnNames = { "Name", "Roll Number", "Department" };
 
        // Initializing the JTable
        j = new JTable(data, columnNames);
        // Definimos el tamaño de la tabla
        j.setBounds(30, 40, 200, 300);
 
        // adding it to JScrollPane
        //añade más espacio a la tabla para que nos permita hacer scroll
        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        // Frame Size
        f.setSize(500, 200);
        // Frame Visible = true
        f.setVisible(true);
    }
 
    // Driver  method
    public static void main(String[] args)
    {
        new JTableExamples();
    }
}