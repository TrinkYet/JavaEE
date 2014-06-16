<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.trink.bean.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE>
<html>
<head>
<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/" %>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="common/head.html" %>
<script src="js/jquery.validate.min.js"></script>
<script>
$(document).ready(function(){
	$("#modify").validate({
		rules:{
			nickname : {
				required : true,
				maxlength : 40
			},
			account : {
				email : true,
				maxlength : 40
			}
		}
	});
	$("#modify").submit(function(e){
		$.post($(this).attr("action"),$(this).serialize(),function(data){
			$("#modify").before($("<div></div>").addClass("alert alert-success").text(data));
		});
		e.preventDefault();
	});
});
</script>
<title>修改用户信息</title>
</head>
<body>
	 <jsp:useBean id="tuser" scope="request" type="com.trink.bean.User"/>
	 <form id="modify" class="form form-horizontal" role="form" action="admin" method="post">
	 	<input type="hidden" name="action" value="modify">
		<div class="form-group">
		  <label class="col-sm-2 control-label" for="id">编号</label>
		  <div class="col-sm-8">
		  	<c:choose>
			  	<c:when test="${tuser == null }">
			  		<input id="id" name="id" type="number" class="form-control" required="true">
			  	</c:when>
			  	<c:otherwise>
			  		<input id="id" name="id" type="number" class="form-control" value="${tuser.id }" readonly="readonly">
			  	</c:otherwise>
		  	</c:choose>
		  </div>
		</div>
		<div class="form-group">
		  <label class="col-sm-2 control-label" for="name">昵称</label>
		  <div class="col-sm-8">
		  	<input id="nickname" name="nickname" type="text" class="form-control" value="${tuser.nickname }" required="required">
		  </div>
		</div>
		<div class="form-group">
		  <label class="col-sm-2 control-label" for="account">账户</label>
		  <div class="col-sm-8">
		  	<input id="account" name="account" type="email" class="form-control" value="${tuser.account }" required="required">
		  </div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-8">
				<input type="submit" value="提交" class="btn btn-default">
			</div>
		</div>
	 </form>
</body>
</html>