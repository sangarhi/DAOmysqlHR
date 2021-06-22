package com.proyecto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerMySQL {
	
	private static DriverManagerMySQL instance;
	
	private DriverManagerMySQL() {
		super();
	}
	
	public static DriverManagerMySQL getDriverManagerMySQL() {
		if (instance == null)
			instance = new DriverManagerMySQL();
			return instance;
	}
	
	public Connection getConexion() {
		//String connectionString = "jdbc:mysql://localhost:3306/hr";
		Connection conexion = null;
		String connectionString = "jdbc:mysql://localhost:3307/hr?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		// Cargamos el driver en memoria
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conexion = DriverManager.getConnection(connectionString, "HR", "hr");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conexion;
		
	}

}
