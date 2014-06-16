package com.trink.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class RowCallback {
	public void process(ResultSet rs)throws SQLException{}
}
