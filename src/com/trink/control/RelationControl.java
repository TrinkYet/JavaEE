package com.trink.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trink.bean.User;
import com.trink.dao.UserDAO;

/**
 * Servlet implementation class RelationControl
 */
@WebServlet("/relation")
public class RelationControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelationControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		UserDAO ud = new UserDAO();
		String userId = null;
		boolean loggedin = session.getAttribute("user")!=null;
		if(loggedin){
			userId = ((User)session.getAttribute("user")).getId().toString();
			if(action.equals("requestlist")){
					List<User> requestlist = null;
					try {
						requestlist = ud.getRequestList(userId);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					response.setCharacterEncoding("UTF-8");
					response.getWriter().println(toJson(requestlist));
			}
			else
			{
				if(action.equals("agree")){
					String from = request.getParameter("from");
					try {
						ud.agree(from,userId);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					response.setCharacterEncoding("UTF-8");
					response.getWriter().println("成功添加好友！");
				}
				else
				{
					if(action.equals("refuse")){
						String from = request.getParameter("from");
						try {
							ud.refuse(from,userId);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						response.setCharacterEncoding("UTF-8");
						response.getWriter().println("成功拒绝请求！");
	
					}
					if(action.equals("search")){
						List<User> list = new ArrayList<User>();
						try {
							list = ud.searchUsers(request);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						response.setCharacterEncoding("UTF-8");
						response.getWriter().println(toJson(list));
					}
					if(action.equals("request")){
						try {
							ud.makeRequest(request);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(action.equals("friendlist")){
						List<User> friendlist = new ArrayList<User>();
						try {
							friendlist = ud.getFriendList(userId);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						response.setCharacterEncoding("UTF-8");
						response.getWriter().println(toJson(friendlist));
						
					}
				}
			}
		}
	}
	
	public String toJson(List<User> list){
		StringBuffer json = new StringBuffer("[");
		for(User i:list){
			StringBuffer item = new StringBuffer("{");
			item.append(String.format("\"userId\":\"%s\",\"nickname\":\"%s\"},",
					i.getId(),i.getNickname()));
			json.append(item);
		}
		json.append("]");
		return json.toString();
	}

}
