package com.trink.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trink.bean.User;
import com.trink.dao.UserDAO;

/**
 * Servlet implementation class AdminControl
 */
@WebServlet("/admin")
public class AdminControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		User user = (User)request.getSession().getAttribute("user");
		UserDAO ud = new UserDAO();
		if(user.getRole().equals("admin"))
		{
			if(action.equals("modify"))
			{
				String userId = request.getParameter("userId");
				User tuser = new User();
				try {
					tuser = ud.getById(Integer.valueOf(userId));
				} catch (NumberFormatException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("tuser", tuser);
				request.getRequestDispatcher("modify.jsp").forward(request, response);
			}
		}
		else
		{
			response.sendRedirect("/");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		User user = (User)request.getSession().getAttribute("user");
		UserDAO ud = new UserDAO();
		if(user.getRole().equals("admin"))
		{
			if(action.equals("userlist"))
			{
				List<User> userlist = null;
				try {
					userlist = ud.getAllUsers();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(userlist!=null){
					response.setCharacterEncoding("UTF-8");
					response.getWriter().println(toJson(userlist));
				}
			}
			if(action.equals("modify"))
			{
				try {
					ud.modifyUser(getFromRequest(request));
					response.setCharacterEncoding("UTF-8");
					response.getWriter().println("修改成功！");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private String toJson(List<User> userlist){
		StringBuffer json = new StringBuffer("[");
		for(User i:userlist){
			StringBuffer item = new StringBuffer("{");
			item.append(String.format("\"userId\":\"%s\",\"nickname\":\"%s\",\"account\":\"%s\",\"imgnum\":\"%s\",\"frdnum\":\"%s\",\"role\":\"%s\"},",
					i.getId(),i.getNickname(),i.getAccount(),i.getImages(),i.getFriends(),i.getRole()));
			json.append(item);
		}
		json.append("]");
		return json.toString();
	}
	private User getFromRequest(HttpServletRequest request){
		User user = new User();
		user.setId(Integer.valueOf(request.getParameter("id")));
		user.setAccount(request.getParameter("account"));
		user.setNickname(request.getParameter("nickname"));
		return user;
	}
	
}
