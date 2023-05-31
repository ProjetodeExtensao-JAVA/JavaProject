package com.example.mobileapp.Actvities.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
public class Connect {
    /**
     * Conecta a uma DB
     */
    public static void connect() {
        Connection conn = null;
        try {
            // parâmetros da DB
            // Windows
            String url = "java/com/example/mobileapp/Actvities/DataBase/sqlite-jdbc-3.42.0.0.jar";
            // Linux
            //criando conexão
            conn = DriverManager.getConnection(url);

            System.out.println("Conexão estabelecida.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        connect();
    }
}
