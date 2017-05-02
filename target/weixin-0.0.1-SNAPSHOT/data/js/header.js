(function(){ 
	var param = document.getElementById('headerScript').getAttribute('data-args');
	var headername = "";
	var headerbackurl = "";
	if (param != null && param != '') {
		var params = param.split("&");
		for(var i=0;i<params.length;i++){
			var m = params[i].split("=");
			if(m[0]=='headername'){
				headername=decodeURIComponent(m[1]);
			}
			if(m[0]=='headerbackurl'){
				headerbackurl=decodeURIComponent(m[1]);
			}
		}
	}
	var headerhtml = new Array();
		headerhtml.push("<header class=\"global-nav\">");
		if(headerbackurl!=""){
			headerhtml.push("<span class=\"global-back\" onclick=\"javascript:SD.goUrl('"+headerbackurl+"');\"></span>");
		}
		headerhtml.push("<h2 class=\"global-title\">"+headername+"</h2>");
		headerhtml.push("</header>");
	document.write(headerhtml.join(''));
})();


function goto(url){
	window.location.href = url;
}


function upweixincode(){
	console.log("location.search="+location.search);
	$.ajax({
	     type: 'GET',
	     url:  '../weixintoken.do'+location.search ,
	     success:function(){
	    	 
	     }

	});
}