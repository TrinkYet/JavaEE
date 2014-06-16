package com.trink.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class ProcessStmt {
	public void process(PreparedStatement ps) throws  SQLException{}
}
