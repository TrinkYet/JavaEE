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

import com.trink.bean.Image;
import com.trink.bean.User;
import com.trink.dao.ImageDAO;

/**
 * Servlet implementation class ImageControl
 */
@WebServlet("/imgcontrol")
public class ImageControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String imgId = request.getParameter("imgId");
		String action = request.getParameter("action"); 
		HttpSession session = request.getSession();
		ImageDAO imagedao = new ImageDAO(getServletContext());
		User user = (User)session.getAttribute("user");
		if(user!=null&&action!=null){
			if(action.equals("getimg")&&imgId!=null){
				if(!imgId.trim().equals(""))
				try {
					imagedao.show(imgId,response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				if(action.equals("getlist")){
					try {
						List<Image> imgList = imagedao.getImageList(user.getId().toString());
						String jsonResponse = parseJson(imgList);
						response.setCharacterEncoding("UTF-8");
						response.getWriter().print(jsonResponse);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					if(action.equals("delete")){
						try {
							imagedao.delete(imgId);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						response.setCharacterEncoding("UTF-8");
						response.getWriter().println("图片已删除！");
					}
					if(action.equals("getguestview")){
						String hostId = request.getParameter("userId");
						List<Image> imgList = new ArrayList<Image>();
						try {
							imgList = imagedao.getImageList(hostId);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String jsonResponse = parseJson(imgList);
						response.setCharacterEncoding("UTF-8");
						response.getWriter().print(jsonResponse);
					}
				}
			}
		}
		
	}

	private String parseJson(List<Image> imgList) {
		// TODO Auto-generated method stub
		StringBuffer json = new StringBuffer("[");
		for(Image i:imgList){
			StringBuffer item = new StringBuffer("{");
			item.append(String.format("\"imgId\":\"%s\",\"imgName\":\"%s\",\"description\":\"%s\",\"time\":\"%s\"},",
					i.getImgId(),i.getImgName(),i.getDescription(),i.getTimestamp()));
			json.append(item);
		}
		json.append("]");
		return json.toString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ImageDAO imagedao = new ImageDAO(getServletContext());
//		System.out.println(imgName);
		try {
			imagedao.insert(request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().println("image uploaded successfully!");
		response.sendRedirect("personal.jsp");
		
	}

}
