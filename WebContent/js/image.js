$(document).ready(function(){
	$("#commentform").submit(function(e){
		$.post($(this).attr("action"),$(this).serialize(),function(data){
			//to do
			
			$("#commentform").after($("<div></div>").text(data).addClass("alert alert-success alert-dismissable")
					.append($("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>")));
		});
		$("input[type='text']",$(this)).val("");
		e.preventDefault();
	});
	var imgId = ($("#detailimg").attr("src")).split("=")[2];
	$.get("comment", {"imgId":imgId}, function(data){
		var list = eval("("+data+")");
		for(var i=0; i<list.length; i++){
			var li = $("<li></li>").addClass("media");
			li.append($("<a></a>").addClass("pull-left").attr("href","#")
					.append($("<img>").addClass("media-object").attr({"src":"...","alt":"..."})))
					.append($("<div></div>").addClass("media-body")
							.append($("<h4 class='media-heading'></h4>").text(list[i].nickname))
							.append(list[i].content));
			$("#commentlist").append(li);
		}
	});
});