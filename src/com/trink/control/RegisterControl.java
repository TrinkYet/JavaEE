package com.trink.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import com.trink.bean.User;
import com.trink.dao.UserDAO;

/**
 * Servlet implementation class RegisterControl
 */
@WebServlet("/register")
public class RegisterControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Enumeration<String> params = request.getParameterNames();
		if(!params.hasMoreElements())
		{
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			request.setAttribute("user", new User());
			rd.forward(request, response);
		}
		else{
			String action = params.nextElement();
			if(action.equals("logout")){
				HttpSession session = request.getSession();
				session.removeAttribute("user");
				request.getRequestDispatcher("/").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean reg = request.getParameter("op").equals("register");
		List<Integer> imgList = new ArrayList<Integer>();
		User user = getFromRequest(request, reg);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		UserDAO ud = new UserDAO();
		if(reg){
			try {
				ud.insert(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user = ud.getByAccount(user);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(user.getNickname()!=null)
			{
				try {
					imgList = ud.getImgList(user.getId().toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				session.setAttribute("imgList", imgList);
				response.sendRedirect("personal.jsp");
			}
		}
		else
		{
			try {
				user = ud.getByAccount(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(user.getNickname()!=null)
			{
				try {
					imgList = ud.getImgList(user.getId().toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				session.setAttribute("imgList", imgList);
				response.sendRedirect("personal.jsp");
			}
			else
			{
				request.setAttribute("message", "用户名或密码错误");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
	}
	public User getFromRequest(HttpServletRequest request, boolean reg) throws UnsupportedEncodingException{
		User user = new User();
		user.setAccount(request.getParameter("account"));
		user.setPassword(DigestUtils.md5Hex(request.getParameter("passwd")));
		if(reg)
		{
			user.setNickname(new String(request.getParameter("nickname").getBytes("ISO-8859-1"),"UTF-8"));
			user.setRole("user");
		}
		return user;
	}
}
