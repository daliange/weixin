/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : multichannel-core-common-util
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2015年11月12日 下午5:12:14
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2015年11月12日        Initailized
 */
package com.sand.weixin.util;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 *
 * @ClassName ：StringUtil
 * @author : SJ
 * @Date : 2015年11月12日 下午5:12:14
 * @version 2.0.0
 *
 */
public class StringUtil {

    public  final static String ENCODE_UTF8    = "UTF-8";

    public  final static String ENCODE_DEFAULT = ENCODE_UTF8;

    public  static String genUUIDString(){
        return  UUID.randomUUID().toString();
    }

    public  static UUID genUUID(String uuid) {
        return  UUID.fromString(uuid);
    }

    public  static String toDateString(Date date,String pattern){
        String dateString = null;
        try{
            DateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }catch(Exception e){
            e.printStackTrace();
        }
        return dateString;
    }
    
    public  static String nowDateString(){
        return toDateString(new Date(), "yyyyMMddHHmmss");
    }

    public static Date toDate(String dateString,String pattern){
        Date  date = null;
        try{
            DateFormat df = new SimpleDateFormat(pattern);
            return df.parse(dateString);
        }catch(Exception e){
            e.printStackTrace();
        }
        return date;
    }

    public static String padStart(String str, int max, String pad){
        String string = str;
        int len = str.length();
        if(len < max){
            for (;  string.length() < max; ) {
                  string = pad+string;
            }
        }else{
            throw new RuntimeException("data is too long, str len:"+len+", but max length:" + max);
        }
        return string;
    }

    /**
     * 左、右、两边填充字符
     *
     * @param str
     *            待填充的字符串，可以为null
     * @param fillChar
     *            填充的字符
     * @param fillSide ['left','right','both']
     *            填充的方向，
     * @param size
     *            输出字符串的固定byte长度。
     * @return String
     */
    public static String charFill(String str, char fillChar, String  fillSide, int size) {
        str = (str == null) ? "" : str;
        StringBuffer sb = new StringBuffer(str);
        int len = str.length();
        if (len >= size)
            return (("left".equals(fillSide)) ? str.substring(len - size) : str.substring(0, size));
        int n = size - len;
        if ("left".equals(fillSide))
            for (int i = 0; i < n; ++i)
                sb.insert(0, fillChar);
        else if ("right".equals(fillSide))
            for (int i = 0; i < n; ++i)
                sb.append(fillChar);
        else if ("both".equals(fillSide)) {
            for (int i = 0; i < n; ++i) {
                if (i % 2 == 0)
                    sb.insert(0, fillChar);
                else
                    sb.append(fillChar);
            }
        }
        return sb.toString();
    }

    public static boolean isEmpty(String str){
        return null == str || "".equals(str);
    }

    public static boolean notEmpty(String str){
        if(str != null && str.length() > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * setCardNoCHeckCode:设置卡号校验码. <br/>
     * 卡号校验位置默认第8位.<br/>
     *
     * @param typeMark 卡标识
     * @param cardSerial    卡序号
     * @return String 卡号
     *
     */
    public static String setCardNoCHeckCode(String typeMark,String cardSerial){

        final int checkOffset=8;
        /**卡标识和卡序号不能为空**/
        if(typeMark==null || cardSerial==null || typeMark.length()==0 || cardSerial.length()==0){
            return null;
        }
        String cardNo=typeMark+cardSerial;
        int j=1,k=0,m=0,n=0,t=0;

        /**无校验位卡号从右朝左逐步计算**/
        for(int i=cardSerial.length()+typeMark.length();i>0;i--){
            if((j%2)==1){ /**第奇数位**/
                m=0;
                n=(cardNo.charAt(i-1)-'0')*2;
                t=n;
                if(n>9){
                    t=n/10+n%10;
                }
            }else{   /**第偶数位**/
                m=cardNo.charAt(i-1)-'0';
                t=0;
            }
            k=k+t+m;
            j++;
        }
        int check=(10-(k%10))%10;
        return cardNo.substring(0, checkOffset-1)+check+cardNo.substring(checkOffset-1);
    }



    public static boolean between(String str,String beginStr,String endStr){
        boolean between = false;
        if(beginStr != null && endStr != null && str != null && str.compareTo(beginStr) >= 0 && str.compareTo(endStr)<=0){
            between = true;
        }
        return between;
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.setCardNoCHeckCode("43203009", "0000002"));
    }

    /**
     * 字符串编码转换的实现方法
     * @param str    待转换的字符串
     * @param newCharset    目标编码
     */
    public static  String encodingFormatConversion(String str, String newCharset) throws UnsupportedEncodingException {
        if(str != null) {
            //用默认字符编码解码字符串。与系统相关，中文windows默认为GB2312
            byte[] bs = str.getBytes();
            return new String(bs, newCharset);    //用新的字符编码生成字符串
        }
        return null;
    }
}
