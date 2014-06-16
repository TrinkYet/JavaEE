<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/" %>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="dist/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="dist/css/bootstrap-theme.min.css"/>
<script src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/guest.js"></script>
<title>个人主页</title>
</head>
<body style="padding-top:70px">
	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
        
          <a class="navbar-brand" href="#">iShare</a>
        </div>
        <div class="navbar-collapse collapse">
          
          <ul class="nav navbar-nav navbar-right">
            <li><a href="personal.jsp">回到我的主页</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
	<div id="content" class="container">
		<div id="bodycontent" class="row">
		
		</div>
	</div>
</body>
</html>