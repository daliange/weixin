package com.sand.weixin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sand.weixin.util.HttpUtils;

@Controller
public class WeixinCallBackcontroller {

	private Logger      logger = LoggerFactory.getLogger(WeixinCallBackcontroller.class);
	
	@RequestMapping(value="weixintoken" )
	@ResponseBody
	public String openPay(HttpServletRequest request,HttpServletResponse response) {	
		String code=request.getParameter("code");
		String state=request.getParameter("state");
		logger.info("微信返回code:"+code +  " state:"+state+" ");
		
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
		
		return "{success:true}";
	}
}
