package com.sand.weixin.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.sand.weixin.SandpayConstants;
import com.sand.weixin.pojo.GatewayOrderPayRequest;
import com.sand.weixin.pojo.GatewayOrderPayRequest.GatewayOrderPayRequestBody;
import com.sand.weixin.pojo.GatewayOrderPayResponse;
import com.sand.weixin.pojo.GatewayOrderPayResponse.GatewayOrderPayResponseBody;
import com.sand.weixin.util.BigDecimalUtils;
import com.sand.weixin.util.HttpUtils;

import cn.com.sandpay.cashier.sdk.SandpayClient;
import cn.com.sandpay.cashier.sdk.SandpayRequestHead;
import cn.com.sandpay.cashier.sdk.SandpayResponseHead;
import cn.com.sandpay.cashier.sdk.util.CertUtil;
import cn.com.sandpay.cashier.sdk.util.DateUtil;
import cn.com.sandpay.cashier.sdk.util.SDKUtil;



@Controller
public class PayController {
	
	private Logger      logger = LoggerFactory.getLogger(PayController.class);
	
	@RequestMapping(value="payInit")
	public String openPay(HttpServletRequest request,HttpServletResponse response) {
		String userInfo=request.getHeader("User-Agent");
		logger.info("用户环境信息:"+userInfo);
		
		if(userInfo.indexOf("MicroMessenger")!=-1){
			logger.info("进行微信授权");
			return "/wx/wxtoken";
		}else if(userInfo.indexOf("AlipayClient")!=-1){
			logger.info("进行支付宝授权");
			return "/alipay/alitoken";
		}else{
			logger.info("error");
			return "error";
		}		
	}
	
	
	
	
	
	@RequestMapping(value="alipayReturn")
	@ResponseBody
	public ModelAndView alipay(HttpServletRequest request,HttpServletResponse response) {
		String userId ="";
		
		String app_id=request.getParameter("app_id");
		String source=request.getParameter("source");
		String scope=request.getParameter("scope");
		String auth_code=request.getParameter("auth_code");
		String state=request.getParameter("state");
		logger.info("支付宝返回app_id:"+app_id );
		logger.info(" source:"+source );
		logger.info(" scope:"+scope );
		logger.info(" auth_code:"+auth_code);
		logger.info(" state:"+state);
		
		String APP_ID = "2017040606572469";
		String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCI6qODiTbiJJAoeAw6Tb4JSmhXfIYmnINfGJKgsrA3inyFVgM9rZQ3zd+/qwhjNv+em2jzsa4QGpwUcKNDZo5pOYEEa0Pptk5A2hfSpjxCWhp7B3+1RMbsAwNaciZFQD7xEFcmFzhY4YdJ8Z5lICdSQddckLme+LKy7jFKuKbb1fmHfGi5hmHajlZ4cAgKrtLfQDu7oXSNEWsPPsevLQnMRub7aatlGK+ubkLu3OAJKsZgxmNz7BaJVLpbPIkkhcieXa0l5omp/4LurVysXh9Hq8g7YwOjiMW+OPNP5Ye3nwEpgrT+q2XfC4++tOW4VZvRQwbQK3oglHcGyZ8fRzEFAgMBAAECggEAUVBY8COrzr68RuyVBoNpW4cvVNYFriTB+NZVLs8wzKuYrG+a2SSfpEzGl8Rx5MqjIYVW3XQm1jWXgKQssjjUVzXpo8A4wzKcYvOjtl036IguteaMOBGyujRH541noTU4rF5yA5NCybaXbJjs404nTDkCOF1TgFBfgNjpuWDU6DXY3KbPK4ly5ypjLst35zAyfiMkSGEkbiB3YTl7Z1jLBqX+YCVOOBfFkix8X5XL08HwJJ8+/9cqGRfMd0d/EB9jC9kYLHMDL4Fhrk8eoAA39SdS4WuS3p6JFqPJIkkfVF67yhhs0HFDyGYErIs0H2Nf+y5GmIw6LsiuYgpgfOV7OQKBgQDc/dglgdKu1vCA9QCmRYSCauKfLWa/Ix/1w+g/wttxlgfCIjXh9+/YmQpZXWSood+sIaI4WGrxnmirncrKxn0Rz5eye6xn1afFIK9vZSd9tGKQsfRlaAJZNDMBgEw42DhS0iRqgdB2hxPGi14AoGudAJHQ/Qk9K7J5HY5mrhbWcwKBgQCemzEiq8lV5wX2f0YjTufR/31+zZ/tZB99M8JrjnkfFrwho0i2l9U0uxxITnxWcBvClM64GoDvI+vZmhJzN9STN3S/+PZSsfCg5/48htlx4ZYeNaKGHnh+yjYk6m6wo+3bEwd6dN6yFdQNHRUyZgrBFy+CGjPBT4Zc697R13qEpwKBgQCCxfyohWClcjA0P8e4nR7DBnlA9N7+cMyy5NGFGcw+NSg2K4RzSgM2y6miJHAykkgIVobmjPwMK0+JbwDYagkNBymfxyvI7lgvgR4P724lJx/EvGoBsEDjhM5CLyfr45fA+vXtyRARc/Gx5jskZ8oGyJWBdJuJBVjB70mbXZ3KHQKBgFcj/vgEmCa5Oj53KOrnRFjNXX7YrMB53Q8l6/C6I3PgQtbcZmvjucEx+FUFwiyfhbD/nx/pd+Nrwf4ZN3/UMdf8hQmgWH2QxBszuyw4DqoR/i902mmDvkIzCVJ49v3X4ywVJWuzYFNJyOq3itjY1ZnHClQZ30ubTMAq245BLFBLAoGBAMhC82yepNdIRsWdYFFgZXipbdVzoeFIdkkOsa6lpSa6nUoQwy5qD709rX+w8EjpxtWoqXI74J5T3CqvcC1eGwWj27DEAosG1PI4Sda4WEgzpGviC9wAzb843tJTbD0Ym+caYssre8YAf8IH4Q+RFLRNJdsjILhN0KWTG1vpgW51";
		String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1EYBTZV1j1YgqN3Hz07PGmJLkhXb6d8nwFW5TxXVGkTBLUDJ9L+Eu/AxX0OHZ882wRGQXt7r5gVUCuyHolRill2Z5+YIbILsS5tDAFGt8nzOyClrCIFzXYdc620YhdvJ5xDrBu785uLwalELTXFheRnOOEZSnJfzXN6Rv+4agq00FgXfUm3N0+W3XZJC5iqNTGNeGi6rbL0pf8Y8Ji2M0NqQskdVEt+lhhIBNlUtMxsD3kT2wNCtR7nAj9FarMkmURITpmsvpvnjPh4t/ytaH4DF1I5cOPLG4pMitJSSXOKYHAXBv06pas3il6Y1t2WYnEeKp7qqOJzMENoET6IDIQIDAQAB";
		AlipayClient alipayClient =
				new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
						APP_ID,APP_PRIVATE_KEY,"json","GBK",
						ALIPAY_PUBLIC_KEY,"RSA2");
		AlipaySystemOauthTokenRequest alirequest = new AlipaySystemOauthTokenRequest();
		alirequest.setCode(auth_code);
		alirequest.setGrantType("authorization_code");
		try {
		    AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(alirequest);
		    logger.info("支付宝AccessToken = "+oauthTokenResponse.getAccessToken());
		    logger.info("支付宝UserId = "+oauthTokenResponse.getUserId());
		    userId = oauthTokenResponse.getUserId();
		} catch (AlipayApiException e) {
		    //处理异常
		    e.printStackTrace();
		}
		request.setAttribute("userId", userId);
		//String url = "h5pay/alipay/payfor_alipay.jsp";
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("userId", userId);
		mv.setViewName("alipay/payfor_alipay");
		return mv;
	}
	
	
	@RequestMapping(value="order")
	@ResponseBody
	public String order(HttpServletRequest request,HttpServletResponse response) {
		BigDecimal amt=new BigDecimal(request.getParameter("amt"));
		String userId=request.getParameter("userId");
		String payMode = request.getParameter("payMode");
		logger.info("amt="+amt);
		logger.info("userId="+userId);
		/**调用支付网关公众号下单方法**/
		String payAmt = BigDecimalUtils.bigDecimalTo12AmtString(amt);
		

		// 加载证书
		try {
			CertUtil.init("classpath:sand-test.cer", "classpath:mid-test.pfx", "123456");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// 组后台报文
		SandpayRequestHead head = new SandpayRequestHead();
		GatewayOrderPayRequestBody body = new GatewayOrderPayRequestBody();
		
		GatewayOrderPayRequest gwOrderPayReq = new GatewayOrderPayRequest();
		gwOrderPayReq.setHead(head);
		gwOrderPayReq.setBody(body);
		
		head.setVersion("1.0");
		head.setMethod("sandpay.trade.pay");
		head.setAccessType("1");
		head.setMid("100211701160001");
		head.setChannelType("07");
		head.setReqTime(DateUtil.getCurrentDate14());
		
		body.setOrderCode(DateUtil.getCurrentDate14());
		
		body.setTotalAmount(payAmt);
		body.setSubject("话费充值");
		body.setBody("用户购买话费0.01");
		//body.setTxnTimeOut("");
		
		
		if(payMode!=null && payMode.equals("sand_wx")){
			//微信
			head.setProductId("00000005");
			body.setPayMode("sand_wx");
			body.setPayExtra("{\"subAppid\":\"wx94348ceda2791351\",\"userId\":\""+userId+"\"}");

//			head.setProductId("00000005");
//			body.setPayMode("sand_wx");
//			body.setPayExtra("{\"subAppid\":\"wx94348ceda2791351\",\"userId\":\"oI3GMv_boU4fi_qdQxHo8lW22bh8\"}");
//			
		}else if(payMode!=null && payMode.equals("sand_alipay")){
			//支付宝
			head.setProductId("00000006");
			body.setPayMode("sand_alipay");
			body.setPayExtra("{\"userId\":\""+userId+"\"}");
		}
		
		
		body.setClientIp("127.0.0.1");
		body.setNotifyUrl("http://127.0.0.1/WebGateway/stateChangeServlet");
		
		
		String credential ="";
		try {
			/**生产环境下单**/
			GatewayOrderPayResponse gwPayResponse = SandpayClient.execute(gwOrderPayReq, "https://cashier.sandpay.com.cn/gateway/api/order/pay");
			SandpayResponseHead respHead = gwPayResponse.getHead();
			
			if(SandpayConstants.SUCCESS_RESP_CODE.equals(respHead.getRespCode())) {
				logger.info("txn success.");
				
				GatewayOrderPayResponseBody respBody = gwPayResponse.getBody();
		        Map map = JSONObject.parseObject(respBody.getCredential());
		        Map params = JSONObject.parseObject(map.get("params").toString());
		        map.put("params",params);
		        credential = JSON.parseObject(map.toString()).toString();
		        logger.info("credential={}",credential);

				//JSON.parseObject(credential);
				
			} else {
				logger.error("txn fail respCode[{}],respMsg[{}].", respHead.getRespCode(), respHead.getRespMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return credential;
	}
	
	
	@RequestMapping(value="wxReturn")
	@ResponseBody
	public ModelAndView wxReturn(HttpServletRequest request,HttpServletResponse response) {
		
		String code=request.getParameter("code");
		String state=request.getParameter("state");
		logger.info("微信返回[code:"+code +  " state:"+state+" ]");
		
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("appid", "wx94348ceda2791351");
		//wx94348ceda2791351 生产  杉德支付
		//wx71bc1338e63671a1 测试
		param.put("secret", "1aab250f4fee9d159a98fc730799b63f");
		//1aab250f4fee9d159a98fc730799b63f  生产  支付
		//716b62a1b63a6d9a447011f3e6567ad8
		param.put("code", code);
		param.put("grant_type", "authorization_code");
		
		String ret=HttpUtils.doPost("https://api.weixin.qq.com/sns/oauth2/access_token", param);	
		logger.info("向微信申请access_token返回 " +  ret);
		JSONObject json = JSONObject.parseObject(ret);
		String openid = (String)json.get("openid");
		logger.info("openid返回 " +  openid);
		
		request.setAttribute("userId", openid);
		//String url = "h5pay/alipay/payfor_alipay.jsp";
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("userId", openid);
		mv.setViewName("wx/payfor_weixin");
		return mv;
		
	}
	
	
}
