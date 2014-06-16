<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
			passwd2 :{
				equalTo : "#passwd"
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
<title>修改密码</title>
</head>
<body>
	 <jsp:useBean id="user" scope="session" type="com.trink.bean.User"/>
	 <form id="modify" class="form form-horizontal" role="form" action="info" method="post">
	 	<input type="hidden" name="action" value="modify">
		<div class="form-group">
		  <label class="col-sm-2 control-label" for="id">ID</label>
		  <div class="col-sm-8">
		  	<c:choose>
			  	<c:when test="${user == null }">
			  		<input id="id" name="id" type="number" class="form-control" required="true">
			  	</c:when>
			  	<c:otherwise>
			  		<input id="id" name="id" type="number" class="form-control" value="${user.id }" readonly="readonly">
			  	</c:otherwise>
		  	</c:choose>
		  </div>
		</div>
		<div class="form-group">
		  <label class="col-sm-2 control-label" for="name">昵称</label>
		  <div class="col-sm-8">
		  	<input id="nickname" name="nickname" type="text" class="form-control" value="${user.nickname }" required="required">
		  </div>
		</div>
		<div class="form-group">
		  <label class="col-sm-2 control-label" for="account">新密码</label>
		  <div class="col-sm-8">
		  	<input id="passwd" name="passwd" type="password" class="form-control">
		  </div>
		</div>
		<div class="form-group">
		  <label class="col-sm-2 control-label" for="account">再次输入</label>
		  <div class="col-sm-8">
		  	<input id="passwd2" name="passwd2" type="password" class="form-control">
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