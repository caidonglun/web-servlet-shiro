package com.caidonglun.utils;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

public class CryptographyUtils {

	/**
	 * Base64加密
	 * @param str
	 * @return
	 */
	public static String encBase64(String str) {
		return Base64.encodeToString(str.getBytes());
	}
	
	/**
	 * Base64解密
	 * @param str
	 * @return
	 */
	public static String decBase64(String str) {
		return Base64.decodeToString(str);
		
	}
	
	/**
	 * Md5加密
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String md5(String str,String salt,int number) {
		if(number>1) {
		return new Md5Hash(str, salt,number).toString();
		}else {
		return new Md5Hash(str, salt).toString();
		}
	}
	
	
	
	public static void main(String[] args) {
		String password="123456";
		System.out.println("Base64加密："+CryptographyUtils.encBase64(password));
		System.out.println("Base64解密:"+CryptographyUtils.decBase64(CryptographyUtils.encBase64(password)));
		
		System.out.println("Md5加密："+CryptographyUtils.md5(password, "caidonglun",1));
		
	}
	
}
