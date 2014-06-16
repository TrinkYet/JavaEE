package com.trink.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trink.bean.Comment;
import com.trink.dao.CommentDAO;

/**
 * Servlet implementation class CommentControl
 */
@WebServlet("/comment")
public class CommentControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CommentDAO cd = new CommentDAO();
		String imgId = request.getParameter("imgId");
		List<Comment> list = null;
		if(imgId!=null){
			try {
				list = cd.getCommentList(Integer.valueOf(imgId));
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(parseJson(list));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CommentDAO cd = new CommentDAO();
		String action = request.getParameter("action");
		if(action!=null&&action.equals("insert")){
			try {
				cd.insert(request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println("评论成功！");
		}
	}
	

	private String parseJson(List<Comment> commentList) {
		// TODO Auto-generated method stub
		StringBuffer json = new StringBuffer("[");
		for(Comment i:commentList){
			StringBuffer item = new StringBuffer("{");
			item.append(String.format("\"imgId\":\"%s\",\"userId\":\"%s\",\"nickname\":\"%s\",\"content\":\"%s\",\"time\":\"%s\"},",
					i.getImgId(),i.getUserId(),i.getNickname(),i.getContent(),i.getTime()));
			json.append(item);
		}
		json.append("]");
		return json.toString();
	}
}
