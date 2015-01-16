package com.jje.membercenter.template;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.util.EncryptionUtils;

@SuppressWarnings("deprecation")
public class EncryptionUtil {

	static String Key = "ABCDEFGHIJKLMNOPQRSTUVWXYZJJE";
	static String ALGORITHM_MODE_PADDING = "DESede/ECB/PKCS5Padding";
	static String ALGORITHM_MODE_NOPADDING = "DESede/ECB/NoPadding";
	static String ALGORITHM = "DESede";

	private final static String[] hexDigits = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String getMD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = origin;
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	public static String encryptDES(String strValue) {
		return EncryptionUtils.encrypt(Key, strValue);
	}

	public static String decryptDES(String encrypted) {
		return EncryptionUtils.decrypt(Key, encrypted);

	}

	public static String generateKey() {
		// Get a key generator for Triple DES (a.k.a DESede)
		String genKey = "";
		try {
			KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
			SecretKey key = keygen.generateKey();
			byte[] keyValue = key.getEncoded();
			genKey = new String(keyValue);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return genKey;
	}
	
	public static void main(String[] args) {
		System.out.println(EncryptionUtil.encryptDES("jack.shi"));
		System.out.println(decryptDES("X0l2fCPyLD1gOSoNkiuQdw=="));
		System.out.println(decryptDES("oWm27cJKa%252FVesh3ftU9%252FzR4DzsWalihrYDkqDZIrkHc%253D"));
	}
}
