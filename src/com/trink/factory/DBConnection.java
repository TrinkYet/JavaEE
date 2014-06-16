package com.trink.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private String DBDRIVER = "com.mysql.jdbc.Driver";
	private String URL = "jdbc:mysql://localhost:3306/je1124036?useUnicode=true&characterEncoding=UTF-8";
	private String USER = "je1124036";
	private String PASSWD = "243286";
	
	private Connection conn = null;
	
	public DBConnection(){
		try{
			Class.forName(DBDRIVER);
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		try{
			conn = DriverManager.getConnection(URL, USER, PASSWD);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return this.conn;
	}
	
	public void close(){
		if(conn!=null){
			try{
				conn.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}