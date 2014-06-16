<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/" %>">
<link rel="stylesheet" type="text/css" href="css/personal.css"/>
<link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-1.3.4.css">
<link rel="stylesheet" type="text/css" href="dist/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="dist/css/bootstrap-theme.min.css"/>
<link rel="stylesheet" type="text/css" href="css/jquery.fileupload-ui.css">
<script src="js/jquery-1.7.2.min.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="dist/js/bootstrap.min.js"></script>
<script src="fancybox/jquery.fancybox-1.3.4.min.js"></script>
<script src="js/jquery.iframe-transport.js"></script>
<script src="js/jquery.fileupload.js"></script>
<script src="js/personal.js"></script>



<title>个人主页</title>
</head>
<body>
	<c:set value="${sessionScope.user }" var="user" scope="page"/>
	<%-- <jsp:useBean id="user" scope="session" type="com.trink.bean.User"/> --%>
	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
        
          <a class="navbar-brand" href="#">iShare</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
          	<c:if test="${user==null }">
	            <li class="active"><a href="register">注册</a></li>
	            <li><a href="login.jsp">登录</a></li>
            </c:if>
          </ul>
          <ul class="nav navbar-nav">
			<li><a href="#bodycontent" data-toggle="tab">图片列表</a></li>
			<li><a href="#slider" data-toggle="tab" >详细信息</a></li>
			<li><a href="#upload" data-toggle="tab">上传图片</a></li>
			<li><a href="#friendlist" data-toggle="tab">好友列表</a></li>
			<li id="requestlistnum"><a href="#requestlist" data-toggle="tab">好友请求</a></li>
			<li><a href="#makefriend" data-toggle="tab">添加好友</a></li>
			<li class="dropdown">
        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">个人信息<b class="caret"></b></a>
        	<ul class="dropdown-menu">
				<li><a href="#info" data-toggle="tab">个人信息</a></li>
				<li><a id="modifyinfo" href="info" data-toggle="tab" class="iframe">修改信息</a></li>
			</ul>
			<c:if test="${user.role==\"admin\" }">
				<li><a href="#manage" data-toggle="tab">用户管理</a></li>
			</c:if>
		  </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="personal.jsp">${user.nickname}</a></li>
            <li><a href="">登出</a></li>
          </ul>
        </div>
      </div>
    </div>
	
	<div id="content" class="container">
		
		<div class="tab-content">
			<div id="bodycontent" class="tab-pane active row">
			</div>
			<div id="slider" class="tab-pane">
				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
				  <!-- Indicators -->
				  <ol id="indicators" class="carousel-indicators">
				  </ol>
				
				  <!-- Wrapper for slides -->
				  <div id="slides" class="carousel-inner">
				  </div>
				
				  <!-- Controls -->
				  <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
				    <span class="glyphicon glyphicon-chevron-left"></span>
				  </a>
				  <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
				    <span class="glyphicon glyphicon-chevron-right"></span>
				  </a>
				</div>
			</div>
			<div id="upload" class="tab-pane">
				<form id="imgform" class="form-horizontal" action="imgcontrol" method="post" onsubmit="return endofsubmit();" enctype="multipart/form-data">
					<div class="form-group">
						<label for="upimage" class="col-sm-3 control-label">选择图片</label>
						<span class="btn btn-success fileinput-button">
		                    <i class="glyphicon glyphicon-plus"></i>
		                    <span>Add Image</span>
		                    <input type="file" name="file" id="upimage">
		                </span>
<!-- 						<div class="col-sm-5"><input type="file" name="file" id="upimage" ></div> -->
					</div>
					<div class="form-group">
						<label for="imgdes" class="col-sm-3 control-label">添加描述</label>
						<div class="col-sm-5"><input type="text" name="description" id="imgdes"></div>
					</div>
					<div class="form-group">
						<label for="friendopt" class="col-sm-offset-3">
							<input type="checkbox" name="scope" value="friend" id="friendopt">
							仅好友可见
						</label>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-3">
							<input type="hidden" id="ext" name="imgName">
							<input type="submit" id="commit" class="btn btn-primary" value="上传">
						</div>
					</div>
				</form>
			</div>
			<div id="friendlist" class="tab-pane">
			</div>
			<div id="requestlist" class="tab-pane">
			</div>
			<div id="makefriend" class="tab-pane">
				<div class="col-md-6 col-md-offset-3">
				<form id="searchusers" class="form-horizontal" action="relation?action=search" method="post">
					<div class="form-group">
						<label for="pattern">查找用户</label>
						<select name="condition">
							<option value="id">ID</option>
							<option value="nickname">昵称</option>
						</select>
						<input id="pattern" name="pattern" class="form-control" type="text">
					</div>
					<input type="submit" class="btn btn-primary" value="查找">
				</form>
				</div>
			</div>
			<div id="info" class="tab-pane">
				<div class="col-md-6 col-md-offset-3">
					<div class="panel panel-info">
						<div class="panel-heading text-center">以下是你的个人信息</div>
						<table class="table table-bordered table-stripped table-hover">
							<tr><td>ID</td><td>${user.id }</td></tr>
							<tr><td>昵称</td><td>${user.nickname }</td></tr>
							<tr><td>账号</td><td>${user.account }</td></tr>
							<tr><td>好友数</td><td>${user.friends }</td></tr>
							<tr><td>上传图片数</td><td>${user.images }</td></tr>
						</table>
					</div>
				</div>
			</div>
			<c:if test="${user.role==\"admin\" }">
				<div id="manage" class="tab-pane">
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>