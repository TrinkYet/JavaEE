<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/" %>">
<link rel="stylesheet" type="text/css" href="css/personal.css"/>
<link rel="stylesheet" type="text/css" href="dist/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="dist/css/bootstrap-theme.min.css"/>
<script src="js/jquery-1.7.2.min.js"></script>
<script src="dist/js/bootstrap.min.js"></script>
<script src="js/image.js"></script>

<title>图片页</title>
</head>
<body>
<div class="container">
	<div id="imgcontainer" class="text-center">
		<img id="detailimg" src="imgcontrol?action=getimg&imgId=${param.imgId}" class="img-responsive" style="margin:auto"><br>
		<p></p>
	</div>
	<div class="container" style="padding-top:30px">
		<form id="commentform" class="form-horizontal" action="comment?action=insert" method="post">
			<input type="hidden" name="imgId" value="${param.imgId }">
			<div class="form-group">
				<label class="label-control col-md-1 col-md-offset-2" for="comment">添加评论</label>
				<div class="col-md-6">
					<input id="comment" name="comment" class="form-control" type="text">
				</div>
				<input type="submit" class="btn btn-primary btn-sm" value="提交">
				
			</div>
		</form>
	</div>
	<div class="col-md-6 col-md-offset-3">
		<ul id="commentlist" class="media-list">
		</ul>
	</div>
</div>
</body>
</html>