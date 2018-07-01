package com.yukoon.bargain.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class EncodeUtil {

	public static String encodePassword (Object credentials,String username) {
		String hashAlgorithmName = "MD5";
		Object salt = ByteSource.Util.bytes(username);;
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		return result.toString();
	}

	public static void main(String[] args) {
		System.out.println(encodePassword("admin","admin"));
	}
}
