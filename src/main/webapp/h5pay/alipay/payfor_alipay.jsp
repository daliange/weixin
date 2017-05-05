<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html lang="zh-cn">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>H5收银台</title>
	<link rel="stylesheet" href="/weixin/data/css/sandbao_check_out.css" type="text/css">
</head>
<body class="body-bg">
<!--页头-->
<script language="javascript" src="/weixin/data/js/header.js" id="headerScript" data-args="headername=H5收银台&nbsp; &headerbackurl=01_index.html"></script>
<!--页头 结束-->
<div class="global-navb mar_top_10 clear">
    <ul>
    	<li>
        	<div class="cell_per25 fl_left"><span class="c_333">商户名称</span></div>
            <div class="cell_per650 fl_left "></div><span class="c_333 fl_left pad_lr_5"><strong>杨玉川的小卖铺</strong>
        </li>
    	<li>
        	<div class="cell_per25 c_333 fl_left">支付金额</div>
            <div class="cell_per70 fl_left pad_lr_10"><input style="color:#ccc;" id="amount" type="text" ></div>
        </li>
        <li>
            <div class="cell_per25 fl_left"><span class="c_333">付款方式</span></div>
            <div class="cell_per650 fl_left "></div><span class="c_333 fl_left pad_lr_5"><strong>支付宝</strong>
            <!--<h4 class="font_attached">余额3383.09</h4>--></span>
            <!--<div class="cell_per10 fl_right more_sel"></div>-->
        </li>
    </ul>
</div>

<div class="clear s_btn"><input name="" class="submit-btn" type="button" value="去支付" onclick = "order()"></div>
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


<script type="text/javascript" src="/weixin/data/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/weixin/data/js/paymentjs.js"></script>

<script>

<!--调用下单方法，下单完成后，将trade_no传值给支付宝JS，起调支付宝收银台-->
function order(){
	
	//alert(111);
	var userId = $("#userId").text();
    //alert("userId="+userId);
    
 	$.ajax({
        type: "post",
        async : false,
        url: "order.do",
        data: {amt : $("#amount").val(),userId:userId,payMode:"sand_alipay"},
        success: function (credential) {
        	alert(credential);
        	
        //  var charge = {"payMode": "ali_pub", "params": {"tradeNO":"2017050521001004490279615424"}};
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