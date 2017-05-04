<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>

<script type="text/javascript">
function gotox(url){
	window.location.href = url;
}
</script>
<!--http://cashie-test.sandpay.com.cn/h5pay/wx/payfor_weixin.html  -->

<body
	onload="gotox('https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx94348ceda2791351&redirect_uri=http://payment-test.sandpay.com.cn/weixin/wxReturn.do&response_type=code&scope=snsapi_base&state=mid-888002199990001')">
</body>
</html>