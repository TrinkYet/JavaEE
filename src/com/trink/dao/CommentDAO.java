package com.trink.dao;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.trink.bean.Comment;
import com.trink.bean.User;

public class CommentDAO extends CommonDAO<Comment> {
	public void insert(final HttpServletRequest request) throws UnsupportedEncodingException, SQLException{
		//final String comment = new String(request.getParameter("comment").getBytes("ISO-8859-1"),"UTF-8");
		final String comment = request.getParameter("comment");
		final int userId = ((User)request.getSession().getAttribute("user")).getId();
		String sql = "insert into comment(`imgId`,`userId`,`content`) values(?,?,?)";
		super.doUpdate(sql, new ProcessStmt(){
			public void process(PreparedStatement ps) throws NumberFormatException, SQLException{
				int i=0;
				ps.setInt(++i, Integer.valueOf(request.getParameter("imgId")));
				ps.setInt(++i, Integer.valueOf(userId));
				ps.setString(++i, comment);
			}
		});
	}
	
	public List<Comment> getCommentList(int imgId) throws SQLException{
		final List<Comment> list = new ArrayList<Comment>();
		String sql = "select userId, content, timestamp, nickname from comment join users on userId=id where imgId="+imgId+" ORDER BY timestamp DESC";
		super.doQuery(sql, null, new RowCallback(){
			public void process(ResultSet rs) throws SQLException{
				Comment comment = new Comment();
				comment.setNickname(rs.getString("nickname"));
				comment.setUserId(rs.getString("userId"));
				comment.setContent(rs.getString("content"));
				comment.setTime(rs.getString("timestamp"));
				list.add(comment);
			}
		});
		return list;
	}
}
