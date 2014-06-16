<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/" %>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="common/head.html" %>
<script src="js/jquery.validate.min.js"></script>
<script>
$(document).ready(function(){
	$("#form").validate({
			rules:{
				account : {
					required : true,
					email : true,
					maxlength : 40
				},
				nickname : {
					required : true,
					maxlength : 40
				},
				passwd2 :{
					equalTo : "#passwd"
				}
			}
	});		
});
</script>
<title>Personal Info</title>
</head>
<body>
	<div class="container col-md-6 col-md-offset-3">
		<jsp:useBean id="user" scope="request" type="com.trink.bean.User" />
		<h2 class="form-signin-heading text-center">欢迎注册</h2>
		<form id="form" action="register" class="form-horizontal form-signin" method="post">
			<input type="hidden" name="op" value="register">
			<div class="form-group">
			  <label class="col-sm-2 control-label" for="account">账号</label>
			  <div class="col-sm-8">
			  	<c:choose>
				  	<c:when test="${user.account == null }">
				  		<input id="account" name="account" type="email" class="form-control" required="true">
				  	</c:when>
				  	<c:otherwise>
				  		<input id="account" name="account" type="email" class="form-control" value="${customer.account }" readonly="readonly">
				  	</c:otherwise>
			  	</c:choose>
			  </div>
			</div>
			<div class="form-group">
			  <label class="col-sm-2 control-label" for="passwd">密码</label>
			  <div class="col-sm-8">
			  	<input id="passwd" name="passwd" type="password" class="form-control">
			  </div>
			</div>
			<div class="form-group">
			  <label class="col-sm-2 control-label" for="passwd2">密码</label>
			  <div class="col-sm-8">
			  	<input id="passwd2" name="passwd2" type="password" class="form-control">
			  </div>
			</div>
			<div class="form-group">
			  <label class="col-sm-2 control-label" for="phone">昵称</label>
			  <div class="col-sm-8">
			  	<input id="nickname" name="nickname" type="text" class="form-control">
			  </div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-8">
					<input type="submit" value="提交" class="btn btn-default">
					<a href="index.html" class="btn btn-info">返回</a>
				</div>
			</div>
		</form>
	</div>
</body>
</html>