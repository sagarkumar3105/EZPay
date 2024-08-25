package com.ezpay.repo;

import java.sql.*;
//import java.util.DataSource;

public class DatabaseConnection {
    private static Connection connection;
    
    private static String url= "jdbc:oracle:thin:@//localhost:1521/XE";
    private static String username= "ezpay";
	private static String password=  "admin";
    static {
    	try
    	{
    		connection = DriverManager.getConnection(url, username, password);
    		 if (connection != null) {
                 System.out.println("Connected to the Oracle database!");
             } else {
                 System.out.println("Failed to connect to the Oracle database.");
             }
    	 } catch (SQLException e) {
             e.printStackTrace();
         }
    	}
    public static Connection getConnection() {
        return connection;
    }
    
}
	

