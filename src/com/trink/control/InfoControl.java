package com.trink.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.trink.bean.User;
import com.trink.dao.UserDAO;

/**
 * Servlet implementation class InfoControl
 */
@WebServlet("/info")
public class InfoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = (User) request.getSession().getAttribute("user");
		if(user!=null)
		{
			request.getRequestDispatcher("password.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession().getAttribute("user");
		User nuser = getFromRequest(request);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if(nuser.getId()!=user.getId())
		{
			out.println("没有修改权限!");
		}
		else
		{
			UserDAO ud = new UserDAO();
			try {
				ud.selfmodify(nuser);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				request.getSession().setAttribute("user", ud.getById(user.getId()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println("修改成功！");
		}
	}
	private User getFromRequest(HttpServletRequest request){
		User user = new User();
		user.setId(Integer.valueOf(request.getParameter("id")));
		user.setNickname(request.getParameter("nickname"));
		String passwd = request.getParameter("passwd");
		if(!passwd.equals(""))
		{
			user.setPassword(DigestUtils.md5Hex(request.getParameter("passwd")));
		}
		return user;
	}
}
