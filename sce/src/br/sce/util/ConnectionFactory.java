/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection getConnection() {
        System.out.println("Conectando ao Banco de Dados...");
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/rh",  //192.168.3.38
                    "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
