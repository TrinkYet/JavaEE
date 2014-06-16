package com.trink.dao;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.trink.bean.User;
import com.trink.dao.ProcessStmt;


public class UserDAO extends CommonDAO<User> {
	
	public void insert(final User user)throws SQLException{
		String sql = "insert into users(`account`, `password`, `nickname`, `role`) values(?,?,?,?)";
		super.doUpdate(sql, new ProcessStmt(){
			public void process(PreparedStatement ps) throws SQLException{
				int i = 0;
				ps.setString(++i, user.getAccount());
				ps.setString(++i, user.getPassword());
				ps.setString(++i, user.getNickname());
				ps.setString(++i, user.getRole());
			}
		});
	}
	
	public User getById(final int id) throws SQLException{
		final User user = new User();
		String sql = "select id, account, nickname, role,(select count(*) from images where userId="
						+"id)as imgnum, (select count(*) from friend where user1=id) as frdnum from users where id = "+id;
		super.doQuery(sql, null, new RowCallback(){
			public void process(ResultSet rs) throws SQLException{
				user.setAccount(rs.getString("account"));
				user.setNickname(rs.getString("nickname"));
				user.setRole(rs.getString("role"));
				user.setImages(rs.getInt("imgnum"));
				user.setFriends(rs.getInt("frdnum"));
				user.setId(id);
			}
		});
		return user;
	}
	
	public List<User> getAllUsers() throws SQLException{
		final List<User> userlist = new ArrayList<User>();
		String sql = "select users.*,count(distinct(images.imgId)) as imgnum,count(distinct(friend.user2)) as frdnum "+
					"from users left join images on users.id=images.userId left join friend on friend.user1=users.id "+
					"group by users.id";
		super.doQuery(sql, null, new RowCallback(){
			public void process(ResultSet rs) throws SQLException{
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setAccount(rs.getString("account"));
				user.setNickname(rs.getString("nickname"));
				user.setRole(rs.getString("role"));
				user.setFriends(rs.getInt("frdnum"));
				user.setImages(rs.getInt("imgnum"));
				userlist.add(user);
			}
		});
		return userlist;
	}
	
	
	public User getByAccount(final User user) throws SQLException{
		String sql = "select id,nickname,role,(select count(*) from images where userId=id) as imgnum,(select count(*) from friend where user1=id) as frdnum from users where account = ? and password = ?";
		super.doQuery(sql, new ProcessStmt(){
			public void process(PreparedStatement ps)throws SQLException{
				int i=0;
				ps.setString(++i, user.getAccount());
				ps.setString(++i, user.getPassword());
			}
		}, new RowCallback(){
			public void process(ResultSet rs) throws SQLException{
				user.setNickname(rs.getString("nickname"));
				user.setId(rs.getInt("id"));
				user.setRole(rs.getString("role"));
				user.setImages(rs.getInt("imgnum"));
				user.setFriends(rs.getInt("frdnum"));
			}
		});
		return user;
	}
	
	public List<Integer> getImgList(String userId) throws SQLException{
		final List<Integer> imgList = new ArrayList<Integer>();
		String sql = "select imgId from images where userId = "+userId;
		super.doQuery(sql, null, new RowCallback(){
			public void process(ResultSet rs) throws SQLException{
				imgList.add(rs.getInt("imgId"));
			}
		});
		return imgList;
	}
	
	public List<User> getRequestList(String userId) throws SQLException{
		final List<User> requestlist = new ArrayList<User>();
		String sql = "select `from`, `nickname` from request join users on `from`=`id` where `to`= "+userId;
		super.doQuery(sql, null, new RowCallback(){
			public void process(ResultSet rs) throws SQLException{
				User user = new User();
				user.setId(rs.getInt("from"));
				user.setNickname(rs.getString("nickname"));
				requestlist.add(user);
			}
		});
		return requestlist;
		
	}
	
	public List<User> getFriendList(String userId) throws SQLException{
		final List<User> friendlist = new ArrayList<User>();
		String sql = "select `id`,`nickname` from users where `id` in( select user2 from friend where user1="+userId+")";
		super.doQuery(sql, null, new RowCallback(){
			public void process(ResultSet rs) throws SQLException{
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setNickname(rs.getString("nickname"));
				friendlist.add(user);
			}
		});
		return friendlist;
	}
	
	
	
	public void agree(final String from, final String to)throws SQLException{
		String sql = "insert into friend values(?,?),(?,?);";
		super.doUpdate(sql, new ProcessStmt(){
			public void process(PreparedStatement ps) throws SQLException{
				int i=0;
				ps.setString(++i, from);
				ps.setString(++i, to);
				ps.setString(++i, to);
				ps.setString(++i, from);
			}
		});
		sql = "delete from `request` where `from`=? and `to`=?";
		super.doUpdate(sql, new ProcessStmt(){
			public void process(PreparedStatement ps) throws NumberFormatException, SQLException{
				int i=0;
				ps.setInt(++i,Integer.valueOf(from));
				ps.setInt(++i, Integer.valueOf(to));
			}
		});
		
	}
	
	public void refuse(final String from, final String to) throws SQLException{
		String sql = "delete from `request` where `from`=? and `to`=?";
		super.doUpdate(sql, new ProcessStmt(){
			public void process(PreparedStatement ps) throws NumberFormatException, SQLException{
				int i=0;
				ps.setInt(++i,Integer.valueOf(from));
				ps.setInt(++i, Integer.valueOf(to));
			}
		});
	}
	
	public List<User> searchUsers(final HttpServletRequest request) throws SQLException, UnsupportedEncodingException{
		final List<User> list = new ArrayList<User>();
		final String condition = request.getParameter("condition");
		final String pattern = request.getParameter("pattern");
		String sql = null;
		if(condition.equals("id")){
			sql = "select id,nickname from users where id = "+pattern;
		}
		else{
			sql = "select id,nickname from users where `nickname` like '%"+pattern+"%'";
		}
		super.doQuery(sql,null, new RowCallback(){
			public void process(ResultSet rs) throws SQLException{
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setNickname(rs.getString("nickname"));
				list.add(user);
			}
		});
		return list;
	}
	
	public void makeRequest(final HttpServletRequest request) throws SQLException{
		final String to = request.getParameter("to");
		final String from = ((User)request.getSession().getAttribute("user")).getId().toString();
		String sql = "insert into request(`from`,`to`) values(?,?)";
		super.doUpdate(sql, new ProcessStmt(){
			public void process(PreparedStatement ps) throws NumberFormatException, SQLException{
				int i=0;
				ps.setInt(++i, Integer.valueOf(from));
				ps.setInt(++i, Integer.valueOf(to));
			}
		});
	}
	public void selfmodify(final User user) throws SQLException{
		String passwd = user.getPassword();
		String sql;
		if(passwd==null){
			sql = "update users set nickname=? where id=?";
		}
		else{
			sql = "update users set nickname=?, password=? where id=?";
		}
		super.doUpdate(sql, new ProcessStmt(){
			public void process(PreparedStatement ps)throws SQLException{
				int i=0;
				ps.setString(++i, user.getNickname());
				if(!(user.getPassword()==null))
				ps.setString(++i, user.getPassword());
				ps.setInt(++i, user.getId());
			}
		});
	}
	public void modifyUser(final User user) throws SQLException{
		String sql = "update users set account=?, nickname=? where id=?";
		super.doUpdate(sql, new ProcessStmt(){
			public void process(PreparedStatement ps) throws SQLException{
				int i=0;
				ps.setString(++i, user.getAccount());
				ps.setString(++i, user.getNickname());
				ps.setString(++i, user.getId().toString());
			}
		});
	}
	
	public void deleteUser(int id) throws SQLException{
		String sql = "delete from users where id="+id;
		super.doUpdate(sql, null);
	}
	
}
