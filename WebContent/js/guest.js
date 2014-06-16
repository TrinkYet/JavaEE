$(document).ready(function(){
	function getQueryString(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null) return unescape(r[2]); return null;
	}
	var userId = getQueryString("userId");
	$.get("imgcontrol",{"action":"getguestview","userId":userId},function(data){
		var imgList = eval("("+data+")");
		for(var i=0; i<imgList.length; i++){
			var img = $("<img>").attr({"src":"imgcontrol?action=getimg&imgId="+imgList[i].imgId,"data-src":"holder.js/300x200"}).addClass("img-responsive");
			var des = $("<dl></dl>").append($("<dt></dt>").text("描述"),$("<dd></dd>").text(imgList[i].description),$("<dt></dt>").text("上传时间"),$("<dd></dd>").text(imgList[i].time));
			$("#bodycontent").append($("<div></div>").addClass("col-md-4")
					.append($("<div></div>").addClass("thumbnail").append($("<a></a>").attr("href","image.jsp?imgId="+imgList[i].imgId).append(img))
							.append($("<div></div>").addClass("caption")
									.append(des))));
		}
	});
	
});