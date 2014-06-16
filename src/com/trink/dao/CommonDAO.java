package com.trink.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.trink.factory.*;

public abstract class CommonDAO<T> {
	private DBConnection dbc;
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement ps;
	
	protected int doUpdate(String sql, ProcessStmt processStmt) throws SQLException{
		conn = null;
		rs = null;
		ps = null;
		try{
			dbc = new DBConnection();
			conn = dbc.getConnection();
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if(processStmt!=null)
			{
				processStmt.process(ps);
			}
			ps.executeUpdate();
			ResultSet rstmp = ps.getGeneratedKeys();
			if(rstmp.next()){
				return rstmp.getInt(1);
			}
			else{
				return 0;
			}
		}finally{
			this.close();
		}
	}
	
	protected void doQuery(String sql, ProcessStmt processStmt, RowCallback rcb)throws SQLException{
		dbc = null;
		conn = null;
		ps = null;
		rs = null;
		try{
			dbc = new DBConnection();
			conn = dbc.getConnection();
			ps = conn.prepareStatement(sql);
			if(processStmt!=null){
				processStmt.process(ps);
			}
			rs = ps.executeQuery();
			while(rs.next())
			{
				rcb.process(rs);
			}
		}finally{
			this.close();
		}
	}
	
	
	
	public void close(){
		if(this.dbc!=null)
			this.dbc.close();
		try{
			if(this.rs!=null){
				rs.close();
			}
			if(this.ps!=null){
				ps.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}