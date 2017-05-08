<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-cn">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>H5收银台</title>
	<link rel="stylesheet" href="/h5pay/data/css/sandbao_check_out.css" type="text/css">
</head>
<body class="body-bg">
<!--页头-->
<script language="javascript" src="/h5pay/data/js/header.js" id="headerScript" data-args="headername=杉德宝收银台&nbsp; &headerbackurl=01_index.html"></script>
<!--页头 结束-->
<div class="global-navb mar_top_10 clear">
    <div class="jy_failure">
    	<font style="font-size:1.2em">支付失败...</font>
    	<p><a href="" class="c_999 ">返回</a></p>
    </div>
</div>

<script src="/h5pay/data/js/zepto.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/h5pay/data/js/base.js"></script>
<script type="text/javascript" src="/h5pay/data/js/service.js"></script>	
<script>
$('#loginout').bind("click",function(){
    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; ");
    var exp = new Date();
        exp.setTime(exp.getTime() - 1);
    for (var i = 0; i < arrCookie.length; i++) {
        var arr = arrCookie[i].split("=");
        document.cookie = arr[0] + "=; path=/; expires=" + exp.toGMTString();
    }
    SD.goUrl("login.html");
});
</script>
</body>
</html>