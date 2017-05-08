/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : multichannel-business-b0005000100000017
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2014年11月25日 下午5:13:24
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2014年11月25日        Initailized
 */
package com.sand.weixin.util;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;


/**
 * BigDecimal 工具类
 * 
 * @ClassName ：BigDecimalUtils
 * @author : SJ
 * @Date : 2014年11月25日 下午5:13:24
 * @version 1.0.0
 * 
 */
public class BigDecimalUtils {
	/**
	 * 字符串转BigDecimal 元为单位
	 * 
	 * @param str
	 * @return
	 */
	public static BigDecimal strToBigDecimal(String str) {
		if (StringUtils.isNotBlank(str)) {
			BigDecimal big = new BigDecimal(str).divide(new BigDecimal("100"));
			return big.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		}
		return new BigDecimal(0);
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
		return v1.add(v2);
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */

	public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
		return v1.subtract(v2);
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */

	public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
		return v1.multiply(v2);
	}

	/**
	 * 除法运算
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return
	 */
	public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
		return div(v1, v2, 2);
	}

	public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
		return v1.divide(v2, scale, BigDecimal.ROUND_HALF_DOWN);
	}

	/**
	 * 比较是否相等
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static boolean compareTo(BigDecimal v1, BigDecimal v2) {
		return (v1.compareTo(v2) == 0);
	}

	/**
	 * 
	 * ClassName:NumberUtil.java Methods Message: 将两个数字相除
	 * 
	 * @param value1
	 * @param value2
	 * @param scale
	 * @return
	 * @throws IllegalAccessException
	 * @return [返回类型说明]
	 * @exception/throws [异常类型] [异常说明]
	 */
	public static BigDecimal divide(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.divide(b2);
	}

	/**
	 * 
	 * ClassName:NumberUtil.java Methods Message: 将两个数字相乘
	 * 
	 * @param string
	 * @param value2
	 * @return
	 * @return [返回类型说明]
	 * @exception/throws [异常类型] [异常说明]
	 */
	public static double mul(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 
	 * ClassName:NumberUtil.java Methods Message: 两个数字相加
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 * @return [返回类型说明]
	 * @exception/throws [异常类型] [异常说明]
	 */
	public static double add(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 
	 * ClassName:NumberUtil.java Methods Message: 两个数字减
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 * @return [返回类型说明]
	 * @exception/throws [异常类型] [异常说明]
	 */
	public static double subtract(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 将BigDecimal金额转成12位金额字符串
	 *
	 * @param amt
	 * @return
	 */
	public static String bigDecimalTo12AmtString(BigDecimal amt) {
		return StringUtil.charFill(Integer.toString(amt.multiply(new BigDecimal("100")).intValue()), '0', "left", 12);
	}

	public static void main(String[] args) {
		System.out.println(strToBigDecimal("000000000001"));
		System.out.println(bigDecimalTo12AmtString(new BigDecimal("0.1")));
		System.out.println();
	}
}
