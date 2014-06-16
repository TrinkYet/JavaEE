package com.trink.dao;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.trink.bean.Image;
import com.trink.bean.User;

public class ImageDAO extends CommonDAO<Image> {
	
	private static final long MAX_IMAGE_SIZE = 1048576;
	
	private final ServletContext servletContext;
	
	public ImageDAO(ServletContext servletContext){
		this.servletContext = servletContext;
	}
	
	public void show(String imgid, final HttpServletResponse response) throws SQLException{
		String sql = "select * from images where imgId = "+imgid;
		super.doQuery(sql, null, new RowCallback(){
			public void process(ResultSet rs) throws SQLException{
				String imgName = rs.getString("imgName");
				String mimeType = servletContext.getMimeType(imgName);
				response.setContentType(mimeType);
				if(mimeType==null){
					response.setContentType("image/jpeg");
				}
				try {
					IOUtils.copy(rs.getBinaryStream("content"), response.getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public List<Integer> getlist(String userId) throws SQLException{
		final List<Integer> imgList = new ArrayList<Integer>();
		String sql = "select imgId from images where userId = "+userId;
		super.doQuery(sql, null, new RowCallback(){
			public void process(ResultSet rs) throws SQLException{
				imgList.add(rs.getInt("imgId"));
			}
		});
		return imgList;
	}
	
	public List<Image> getImageList(String userId) throws SQLException{
		final List<Image> imgList = new ArrayList<Image>();
		String sql = "select * from images where userId = "+userId+" order by timestamp desc";
		super.doQuery(sql, null, new RowCallback(){
			public void process(ResultSet rs) throws SQLException{
				Image img = new Image();
				img.setImgId(rs.getString("imgId"));
				img.setDescription(rs.getString("description"));
				img.setTimestamp(rs.getString("timestamp"));
				img.setImgName(rs.getString("imgName"));
				img.setUserId(rs.getInt("userId"));
				imgList.add(img);
			}
		});
		return imgList;
	}
	
	public String getParam(List<FileItem> images, String name) throws UnsupportedEncodingException{
		for(FileItem image:images){
			if(image.isFormField()&&image.getFieldName().equals(name)){
				return new String(image.getString().getBytes("ISO-8859-1"),"UTF-8");
			}
		}
		return null;
	}
	
	public void insert(final HttpServletRequest request)throws SQLException{
		DiskFileItemFactory factory = new DiskFileItemFactory();

        File repository  =(File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        factory.setSizeThreshold(4096);

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_IMAGE_SIZE);
        upload.setHeaderEncoding("UTF-8");
        try {
			@SuppressWarnings("unchecked")
			final List<FileItem> images = upload.parseRequest(request);
			
			for(final FileItem image:images){
				if(!image.isFormField()){
					String sql = "INSERT INTO images(userId,content,imgName,description,scope) values(?,?,?,?,?)";
					super.doUpdate(sql, new ProcessStmt(){
						public void process(PreparedStatement ps) throws SQLException{
							int i=0;
							try {
								ps.setString(++i, ((User)request.getSession().getAttribute("user")).getId().toString());
								ps.setBinaryStream(++i, image.getInputStream(), image.getSize());
								ps.setString(++i, getParam(images,"imgName"));
								ps.setString(++i, getParam(images,"description"));
								ps.setString(++i, getParam(images,"scope")!=null?getParam(images,"scope"):"public");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					break;
				}
			}
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete(String imgId) throws SQLException{
		String sql = "delete from images where imgId="+imgId;
		super.doUpdate(sql, null);
	}
}
