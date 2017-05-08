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

<body
	onload="gotox('https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2017040606572469&scope=auth_base&redirect_uri=http%3a%2f%2fcashier.sandpay.com.cn%2fh5pay%2falipayReturn.do&state=1')">
</body>
</html>