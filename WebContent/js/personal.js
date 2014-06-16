$(document).ready(function(){
	$.post("relation",{"action":"friendlist"},function(data){
		var list = eval("("+data+")");
		var table = $("<table></table>").addClass("table table-hover table-stripped")
				.append("<thead><tr><td>ID</td><td>昵称</td><td>好友主页</td></tr></thead>");
		var tbody = $("<tbody></tbody>");
		if(list.length>0){
			for(var i=0; i<list.length; i++){
				tbody.append($("<tr></tr>").append(
						$("<td></td>").text(list[i].userId),
						$("<td></td>").text(list[i].nickname), 
						$("<td></td>").append($("<a></a>").attr("href","guestview.jsp?userId="+list[i].userId).text("访问主页"))));
			}
			table.append(tbody);
			$("#friendlist").append($("<p></p>").text("总计"+list.length+"位好友"));
			$("#friendlist").append(table);
		}
	});
	
	if($("#manage").length>0){
		$.post("admin",{"action":"userlist"},function(data){
			var list = eval("("+data+")");
			var table = $("<table></table>").addClass("table table-hover table-stripped")
						.append("<thead><tr class='success'><td>ID</td><td>昵称</td><td>账号</td><td>照片数</td><td>好友数</td><td>权限</td><td>操作</td></tr></thead>");
			var tbody = $("<tbody></tbody>");
			if(list.length>0){
				for(var i=0; i<list.length; i++){
					tbody.append($("<tr></tr>").append(
							$("<td></td>").text(list[i].userId),
							$("<td></td>").text(list[i].nickname),
							$("<td></td>").text(list[i].account),
							$("<td></td>").text(list[i].imgnum),
							$("<td></td>").text(list[i].frdnum),
							$("<td></td>").text(list[i].role),
							$("<td></td>").append($("<a></a>")
									.addClass("iframe")
									.attr("href","admin?action=modify&userId="+list[i].userId)
									.text("修改信息")
									.fancybox())));
				}
			}
			table.append(tbody);
			$("#manage").addClass("text-center").append($("<p></p>").text("总计"+list.length+"位用户"));
			$("#manage").append(table);
		});
	}
	
	
	$.get("imgcontrol",{"action":"getlist"},function(data){
		var imgList = eval("("+data+")");
		for(var i=0; i<imgList.length; i++){
			var img = $("<img>").attr({"src":"imgcontrol?action=getimg&imgId="+imgList[i].imgId,"data-src":"holder.js/300x200"}).addClass("img-responsive");
			var des = $("<dl></dl>").append($("<dt></dt>").text("描述"),$("<dd></dd>").text(imgList[i].description),$("<dt></dt>").text("上传时间"),$("<dd></dd>").text(imgList[i].time));
			var indicator = $("<li data-target='#carousel-example-generic'></li>");
			$("#bodycontent").append($("<div></div>").addClass("col-md-4")
					.append($("<div></div>").addClass("thumbnail").append($("<a></a>").attr("href","image.jsp?imgId="+imgList[i].imgId).append(img))
							.append($("<div></div>").addClass("caption")
									.append(des).append($("<p></p>")
											.append($("<a href=\"image.jsp?imgId="+imgList[i].imgId+"\" class=\"btn btn-primary\" role=\"button\">Button</a>").fancybox(),
													$("<a href=\"imgcontrol?action=delete&imgId="+imgList[i].imgId+"\" class=\"btn btn-default\" role=\"button\">删除</a>"))))));
//			$("#bodycontent").append($("<li></li>").addClass("imgitem")
//					.append($("<div></div>").addClass("col-md-7").append($("<img>").attr("src","imgcontrol?imgId="+imgList[i].imgId).addClass("img-responsive")),$("<div></div>").addClass("col-md-5").append(des)));
			if(i==0){
				$("#slides").append($("<div></div>").css("text-align","center")
						.addClass("item active")
						.append($("<img>").addClass("centeralign").attr({"src":"imgcontrol?action=getimg&imgId="+imgList[i].imgId,"alt":imgList[i].description})
								,$("<div></div>").addClass("carousel-caption").text(imgList[i].description)));
				$("#indicators").append(indicator.addClass("active").attr("data-slide-to",i));
			}
			else{
				$("#slides").append($("<div></div>").addClass("item")
						.append($("<img>").attr({"src":"imgcontrol?action=getimg&imgId="+imgList[i].imgId,"alt":imgList[i].description}).addClass("centeralign"),
								$("<div></div>").addClass("carousel-caption").text(imgList[i].description)));
				$("#indicators").append(indicator.attr("data-slide-to",i));
			}
		}
	});
	$("#bodycontent").delegate("div a.btn-default","click",function(e){
		var href = $(this).attr("href");
		var parentnode = $(this).closest("div.col-md-4");
		$.get(href,{},function(data){
			parentnode.remove();
			$("#bodycontent").prepend($("<div></div>").text(data).addClass("alert alert-success alert-dismissable")
					.append($("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>")));
		});
		e.preventDefault();
	});
	$("#imgform").submit(function(e){
		var namepath = $("#upimage").val().split("\\");
		var imgName = namepath[namepath.length-1];
		$("#ext").val(imgName);
	});
	
	
	
	refreshRequest();
	function refreshRequest(){
		$.post("relation",{"action":"requestlist"},function(data){
			var list = eval("("+data+")");
			if(list.length==0)
				$("#requestlist").text("暂时没有好友请求！");
			else{
				$("#requestlistnum>a").append($("<span class=\"badge pull-right\"></span>").text(list.length));
				var lg = $("<ul></ul>").addClass("list-group");
				for(var i=0;i<list.length;i++){
					lg.append($("<li></li>").addClass("list-group-item").text(list[i].nickname+"请求加你为好友")
							.append($("<a></a>").addClass("btn btn-default").attr("href","relation?action=agree&from="+list[i].userId).text("同意"))
							.append($("<a></a>").addClass("btn btn-primary").attr("href","relation?action=refuse&from="+list[i].userId).text("拒绝")));
				}
				$("#requestlist").append(lg);
			}
		});
	}
	
	
	$("#requestlist").delegate("ul li a","click",function(e){
		$(this).parent().remove();
		var num = $("#requestlistnum span").text();
		num--;
		if(num>0)
			$("#requestlistnum span").text(num);
		else{
			$("#requestlistnum span").remove();
			$("#requestlist").text("暂时没有好友请求！");
		}
			
		$.post($(this).attr("href"),{},function(data){
			$("#requestlist").append($("<div></div>").text(data).addClass("alert alert-success alert-dismissable")
					.append($("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>")));
		});
		e.preventDefault();
	});
	
	
	$("#searchusers").delegate("table a","click",function(e){
		$.post($(this).attr("href"),{},function(data){
			$("#searchusers").append($("<div></div>").text(data).addClass("alert alert-success alert-dismissable")
					.append($("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>")));	
		});
		e.preventDefault();
	});
	
	
	$("#searchusers").submit(function(e){
		$("table",this).remove();
		
		var table = $("<table></table>").addClass("table table-hover table-stripped")
						.append("<thead><tr><td>ID</td><td>昵称</td><td>加为好友</td></tr></thead>");
		var tbody = $("<tbody></tbody>");
		$.post($(this).attr("action"),$(this).serialize(),function(data){
			var list = eval("("+data+")");
			if(list.length>0){
				for(var i=0; i<list.length; i++){
					tbody.append($("<tr></tr>").append($("<td></td>").text(list[i].userId),
							$("<td></td>").text(list[i].nickname), 
							$("<td></td>").append($("<a></a>").attr("href","relation?action=request&to="+list[i].userId).text("加为好友"))));
				}
				table.append(tbody);
				$("#searchusers").append(table);
			}
			//$("#makefriend").append($("<div></div>").text(data));
		});
		e.preventDefault();
	});
	
	$("#modifyinfo").fancybox();
	
	$("#upimage").fileupload("option",{autoUpload:false});
    function endofsubmit(){
    	$("#imgform").ajaxSubmit(function(data){
    		$("#imgform").append(data);
    	});
    	return false;
    }
});

