package com.selenium.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;

public class ConnectionFB {
	private final String URL = "jdbc:mysql://192.168.2.6:3306/"; // Ubicaci�n de la BD.
    private final String BD = "facebook"; // Nombre de la BD.
    private final String USER = "lmorales"; //Nomber del usuario
    private final String PASSWORD = "Carabobo?18"; //contrase�a

    public Connection connect = null;

	public Connection conectar() {
        try {
        	Class.forName("com.mysql.jdbc.Driver");
            connect = (Connection) DriverManager.getConnection(URL + BD+"?useSSL=false", USER, PASSWORD);
            
        }catch(SQLException e1) {
        	System.err.println("Error al conectarse a la base de datos "+e1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connect;
    }
}
