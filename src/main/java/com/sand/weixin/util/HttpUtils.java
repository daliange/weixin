/**
 * Project Name: 杉德核心清分系统(SCSP)
 * File Name: HttpClient.java
 * Package Name: cn.com.sand.scsp.http
 * Date: 2016年6月27日上午9:42:59
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 * @author fei
 */
package com.sand.weixin.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;

/**
 * ClassName: HttpClient <br/>
 * Date: 2016年6月27日 上午9:42:59 <br/>
 */
public class HttpUtils {
	
	/** 默认编码为 utf-8 */  
    private static final String HTTP_CONTENT_CHARSET = "utf-8";  
  
    public static final Integer MAX_TIME_OUT = 10000;  
    public static final Integer MAX_IDLE_TIME_OUT = 60000;  
    public static final Integer MAX_CONN = 100;
  
    public static HttpClient httpClient = null;  
  
    static {  
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();  
        connectionManager.closeIdleConnections(MAX_IDLE_TIME_OUT);  
        HttpConnectionManagerParams httpParam = connectionManager.getParams();
        httpParam.setParameter("http.connection-manager.max-total", MAX_CONN);  
        httpClient = new HttpClient(connectionManager);
        httpClient.getParams().setParameter("http.connection.timeout", MAX_TIME_OUT);  
        httpClient.getParams().setParameter("http.connection-manager.timeout", MAX_TIME_OUT.longValue());  
    }  
  
    /**
     * 发送HTTP请求 
     * @param url 
     * @param param 
     * @return HTTP响应 
     */
    public static String doGet(String url, Map<String, Object> param) {  
    	  GetMethod get = new GetMethod(url);  
    	  try {
    		  get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());  
          		if (param != null) {  
          			NameValuePair[] nvps = new NameValuePair[param.size()];  
                 
	                int i = 0;
	          		for (Entry<String, Object> entry : param.entrySet()) {  
	          			nvps[i] = new NameValuePair(entry.getKey(), entry.getValue().toString());
	          			i++;
	          		}  
	          		get.setQueryString(nvps);;
          		}  
    		  
              // 执行getMethod
              int statusCode = httpClient.executeMethod(get);
              if (statusCode != HttpStatus.SC_OK) {
                  System.err.println("Method failed: " + get.getStatusLine());
              }
              // 处理内容
              return get.getResponseBodyAsString();
          } catch (Exception e) {
              // 发生致命的异常，可能是协议不对或者返回的内容有问题
              throw new RuntimeException(e);
          } finally {
              // 释放连接
        	  if(get != null)
        		  get.releaseConnection();
          }
    }  
    
    /** 
     * 发送HTTP请求 
     * @param url 
     * @param param 
     * @return HTTP响应 
     */  
    public static String doPost(String url, Map<String, Object> param) {  
    	PostMethod post = new PostMethod(url);  
        try {  
        	post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,  
        			HTTP_CONTENT_CHARSET);  
        	if (param != null) {  
        		List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        		for (Entry<String, Object> entry : param.entrySet()) {  
        			// 排除掉空值  
        			if (entry.getValue() != null) {  
        	            nvps.add(new NameValuePair(entry.getKey(), entry.getValue().toString())); 
        			}  
        		}
        		post.setRequestBody( nvps.toArray(new NameValuePair[nvps.size()]));
        	}  
            post.addRequestHeader(new Header("Connection", "close"));  
            httpClient.executeMethod(post);  
            if (post.getStatusCode() == HttpStatus.SC_OK) {  
                return post.getResponseBodyAsString();  
            } else {  
                post.abort();//马上断开连接  
            }  
        } catch (Exception e) {  
        	throw new RuntimeException(e);
        } finally {  
            post.releaseConnection();  
        }  
        return null;  
    }  
  
    /** 
     * 发送上传请求 
     * @param url 
     * @param param 
     * @return HTTP响应 
     */  
    public static String upload(String url, Map<String, Object> param,InputStream in) {  
    	PostMethod post = new PostMethod(url);  
    	try {  
    		String fileName = param.get("fileName").toString();
    		ByteArrayOutputStream output = new ByteArrayOutputStream();
    	    byte[] buffer = new byte[4096];
    	    int n = 0;
    	    while (-1 != (n = in.read(buffer))) {
    	        output.write(buffer, 0, n);
    	    }
    	    MultipartEntity entity = new MultipartEntity();
    	    
    	    entity.addPart("file", new InputStreamBody(in, "ddd"));
    		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,  
    				HTTP_CONTENT_CHARSET);  
    		if (param != null) {  
    			List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
    			for (Entry<String, Object> entry : param.entrySet()) {  
    				// 排除掉空值  
    				if (entry.getValue() != null) { 
    					entity.addPart(entry.getKey(), new StringBody(entry.getValue().toString(), Charset.forName("UTF-8")));
    					//nvps.add(new NameValuePair(entry.getKey(), entry.getValue().toString())); 
    				}  
    			}
    			//post.setRequestBody( nvps.toArray(new NameValuePair[nvps.size()]));
    		}  
    		File file = new File("C:\\Users\\fei\\Desktop\\ParamDataExchangeController.java");
    		Part[] parts = { new FilePart(file.getName(), file) };
    		//filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
    		//Part[] parts = { new FilePart(fileName, new ByteArrayPartSource(fileName, output.toByteArray())) };
    		post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));

    		
    		output.close();
    		post.addRequestHeader(new Header("Connection", "close"));  
    		//post.addRequestHeader(new Header("enctype", "multipart/form-data"));  
    		//post.addRequestHeader(new Header("content-type", "multipart/form-data"));  
    		httpClient.executeMethod(post);  
    		if (post.getStatusCode() == HttpStatus.SC_OK) {  
    			return post.getResponseBodyAsString();  
    		} else {  
    			post.abort();//马上断开连接  
    		}  
    	} catch (Exception e) {  
    		throw new RuntimeException(e);
    	} finally {  
    		post.releaseConnection();  
    	}  
    	return null;  
    }  
    
}
