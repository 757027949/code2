package com.asd.android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	private static StringUtil stringUtil;

	public static StringUtil getInstance() {
		if (null == stringUtil) {
			stringUtil = new StringUtil();
		}
		return stringUtil;
	}


	/**
	 * 验证手机号
	 *
	 * @param mobiles
	 * @return true 有效 false 无效
	 */
	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 验证 支付宝
	 *
	 * @param 支付宝
	 * @return true 有效 false 无效
	 */
	public boolean isZhiFuBao(String string) {
		Pattern p = Pattern
				.compile("^([a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+)|((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");
		Matcher m = p.matcher(string);
		return m.matches();
	}

	/**
	 * 获取网址
	 *
	 * @param string
	 *            字符串中获取网址
	 * @return 获取到：返回回去到的网址 未获取到：UrlUtils.SHARE_URL
	 */
	public String getHtmlUrl(String string) {
		Pattern pattern = Pattern
				.compile("(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*");
		Matcher m = pattern.matcher(string);
		boolean result = m.find();
		return m.group();
	}

	/**
	 * 获取字符串中的数字
	 *
	 * @param string
	 * @return -1：获取出错
	 */
	public int getNumWithString(String string) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(string);
		try {
			int i = Integer.parseInt(m.replaceAll("").trim());
			return i;
		} catch (Exception e) {
			return -1;
		}

	}

	/**
	 * 转化为Integer
	 *
	 * @param string
	 * @return -100：转化失败
	 */
	public int string2Integer(String string) {
		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -100;
		}
	}

	/**
	 * 判断是否为空字符串
	 *
	 * @param input
	 * @return true:空 false:反之
	 */
	public boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 正则表达式验证
	 *
	 * @param string
	 *            验证的字符串
	 * @param format
	 *            正则表达式格式 类似（"^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$"）
	 * @return
	 */
	public boolean isValidValue(String string, String format) {
		if (isEmpty(string)) {
			return false;
		}
		Pattern p = Pattern.compile(format);
		Matcher m = p.matcher(string);
		return m.matches();
	}

	/**
	 * 把一个文件转化为字节
	 *
	 * @param file
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] getByte(File file) throws Exception {
		byte[] bytes = null;
		if (file != null) {
			InputStream is = new FileInputStream(file);
			int length = (int) file.length();
			if (length > Integer.MAX_VALUE) // 当文件的长度超过了int的最大值
			{
				System.out.println("this file is max ");
				return null;
			}
			bytes = new byte[length];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			// 如果得到的字节长度和file实际的长度不一致就可能出错了
			if (offset < bytes.length) {
				System.out.println("file length is error");
				return null;
			}
			is.close();
		}
		return bytes;
	}
}
