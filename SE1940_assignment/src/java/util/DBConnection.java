/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:sqlserver://rax\\SQLEXPRESS:1433;databaseName=LeaveManagementDB;trustServerCertificate=true;"; // Update as needed
    private static final String USER = "sa"; // Optional if using Windows Authentication
    private static final String PASSWORD = "sa"; // Optional if using Windows Authentication

    public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL, USER, PASSWORD); // Remove USER and PASSWORD if using integrated security
    }
    
    public static void main(String[] args) {
        Connection conn = null;
        try {
            System.out.println("Testing database connection...");
            conn = getConnection();
            if (conn != null) {
                System.out.println("Connection successful! Connected to: " + conn.getCatalog());
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MSSQL JDBC Driver not found. Ensure the driver JAR is in the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failed! SQLException: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
}
