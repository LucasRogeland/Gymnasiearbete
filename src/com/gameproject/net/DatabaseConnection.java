package com.gameproject.net;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

	private java.sql.Connection connection;
	private Statement st;
	
	//Establish a database connection
	public DatabaseConnection(String url, String user, String password){
		
		try {
			connection = DriverManager.getConnection(url, user, password);
			st = connection.createStatement();
		} catch (SQLException e) {
			System.err.println("Failed to create database connection");
		}
	}
	
	//Send a query to the database and return the results
	public String query(String query){
		String results = "";
		try {
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				results += rs.getString(1) + "\n";
			}
			
		} catch (SQLException e) {
			System.err.println("Failed to send query");
		}
		return results;
	}
}
