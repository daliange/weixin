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
	
	//alert(111);
	var userId = $("#userId").text();
    //alert("userId="+userId);
    
 	$.ajax({
        type: "post",
        async : false,
        url: "order.do",
        data: {amt : $("#amount").val(),userId:userId,payMode:"sand_wx"},
        success: function (credential) {
        	alert(credential);
        	
        	
/*          	var charge = {
 	    	        payMode: "wx_pub",
 	    	        params: {
 	    		           "appId":"wx5d4aa509b7a68610",     //公众号名称，由商户传入     
 	    		           "timeStamp":"1492092770085",         //时间戳，自1970年以来的秒数     
 	    		           "nonceStr":"7b49a5d0226b4bb0a111a735368528be", //随机串     
 	    		           "package":"prepay_id=wx201704132212507cb46fe7f00453814588",     
 	    		           "signType":"MD5",         //微信签名方式：     
 	    		           "paySign":"8F9374CF21C40EDE186AA633637BC572" //微信签名 
 	    		           }
 	    	    };   */
        	
          //var charge = {"payMode": "ali_pub", "params": {"tradeNO":"2017050321001004490276745043"}};
        //alert(charge);
          //var a = {"tradeNO":"2017050321001004490276745043","payMode":"ali_pub"}
        
  	      paymentjs.createPayment(credential, function(result, err) {
            console.log(result);
            console.log(err.msg);
            console.log(err.extra);
        }); 
        	
        	
      
        }
    }); 

	
}
    
    
</script>
</body>
</html>