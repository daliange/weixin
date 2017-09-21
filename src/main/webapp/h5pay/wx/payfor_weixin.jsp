<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no">
<title>H5收银台</title>
<link rel="stylesheet" href="/weixin/data/css/sandbao_check_out.css"
	type="text/css">
</head>

<body class="body-bg" >
	<!--页头-->
	<script language="javascript" src="/weixin/data/js/header.js"
		id="headerScript"
		data-args="headername=H5收银台&nbsp; &headerbackurl=payInit.do"></script>
	<!--页头 结束-->
	<div class="global-navb mar_top_10 clear">
		<ul>
			<li>
				<div class="cell_per25 fl_left">
					<span class="c_333">商户名称</span>
				</div>
				<div class="cell_per650 fl_left "></div>
				<span class="c_333 fl_left pad_lr_5"><strong>杨玉川的小卖铺</strong>
			</li>
			<li>
				<div class="cell_per25 c_333 fl_left">支付金额</div>
				<div class="cell_per70 fl_left pad_lr_10">
					<input style="color: #ccc;" name="amount" id ="amount" type="text">
				</div>
			</li>
			<li>
				<div class="cell_per25 fl_left">
					<span class="c_333">付款方式</span>
				</div>
				<div class="cell_per650 fl_left "></div>
				<span class="c_333 fl_left pad_lr_5"><strong>微信支付</strong> <!--<h4 class="font_attached">余额3383.09</h4>--></span>
				<!--<div class="cell_per10 fl_right more_sel"></div>-->
			</li>
		</ul>
	</div>

<!--调用下单方法，下单完成后，将trade_no传值给支付宝JS，起调支付宝收银台-->
	<div class="clear s_btn">
		<input name="" class="submit-btn" type="button" value="去支付"
			onclick="order()">
	</div>
	
<div style="display: none" >

	<%
		String userId = (String)request.getAttribute("userId");
	System.out.println("===" + userId);
	
	%> 
	<p id="userId"><%=userId%></p>
</div>

	<script src="/weixin/data/js/zepto.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="/weixin/data/js/base.js"></script>
	<script type="text/javascript" src="/weixin/data/js/service.js"></script>
	<script type="text/javascript" src="/weixin/data/js/paymentjs.js"></script>
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

	<script>
function order(){
	
	var userId = $("#userId").text();
 	$.ajax({
        type: "post",
        async : false,
        url: "order.do",
        data: {amt : $("#amount").val(),userId:userId,payMode:"sand_wx"},
        success: function (credential) {
        	alert(credential);
  	      paymentjs.createPayment(credential, function(result, err) {
            console.log(result);
            console.log(err.msg);
            console.log(err.extra);
        }); 
        }
    }); 
 	
 	
 	/**微信官方起调
	function order(){
		
		WeixinJSBridge.invoke(
			       'getBrandWCPayRequest', {
			           "appId":"wx5d4aa509b7a68610",     //公众号名称，由商户传入     
			           "timeStamp":"1505790006",         //时间戳，自1970年以来的秒数     
			           "nonceStr":"7162b2e9c5c34c429dbb519323c493f9", //随机串     
			           "package":"prepay_id=wx20170919110006db85faeff50894684856",     
			           "signType":"MD5",         //微信签名方式：     
			           "paySign":"1339429EED3966437C8E52F4C249261C" //微信签名 
			       },
			       function(res){     
			           if(res.err_msg == "get_brand_wcpay_request:ok" ) {}     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
			       }
			   ); 
	**/
	
	/**
	function order(){
		
		WeixinJSBridge.invoke(
			       'getBrandWCPayRequest', {
			           "appId":"wxa8d0b58cefc18eb2",     //公众号名称，由商户传入     
			           "timeStamp":"1505790938",         //时间戳，自1970年以来的秒数     
			           "nonceStr":"x5g9rSl2GHJwJrnTsyTpoznEsqu9rNqY", //随机串     
			           "package":"prepay_id=wx201709191115380a2b3780140235656222",     
			           "signType":"MD5",         //微信签名方式：     
			           "paySign":"1F4DAFE135C296F29486584C79005A9F" //微信签名 
			       },
			       function(res){     
			           if(res.err_msg == "get_brand_wcpay_request:ok" ) {}     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
			       }
			   ); 
	**/
 	
 	
 	
}
	
	
    
    
</script>
</body>
</html>