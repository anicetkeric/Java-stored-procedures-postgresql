package com.source.stored.procedures.postgresql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;





public class Manager  {

	//static reference to itself
    private static Manager instance = new Manager();
    public static final String URL = "jdbc:postgresql://localhost:5432/book_db"; 
    public static final String USER = "<your user database>";
    public static final String PASSWORD = "<your user password>"; 
    public static final String DRIVER_CLASS = "org.postgresql.Driver"; 
     
    //private constructor
    private Manager() {
        try {
            //Step 2: Load MySQL Java driver
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     
    private Connection createConnection() {
 
        Connection connection = null;
        try {
            //Step 3: Establish Java MySQL connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("ERREUR: Unable to connect to database.");
        }
        return connection;
    }   
     
    public static Connection getConnection() {
        return instance.createConnection();
    }
 	
}



