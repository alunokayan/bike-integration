package br.edu.ifsp.spo.bike_integration.util;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import br.edu.ifsp.spo.bike_integration.exception.CryptoException;

public class CryptoUtil {

	private static final String ALGORITHM = "AES/GCM/NoPadding";
	private static final int GCM_TAG_LENGTH = 128;

	private CryptoUtil() {
	}

	public static SecretKey generateKey() throws CryptoException {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(256); // chave de 256 bits
			return keyGen.generateKey();
		} catch (Exception e) {
			throw new CryptoException("Error generating key", e);
		}
	}
	
	public static String getSecretKeyAsString(SecretKey secretKey) {
        byte[] encodedKey = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(encodedKey);
    }
	
	public static SecretKey getSecretKeyFromString(String secretKey) {
		byte[] decodedKey = Base64.getDecoder().decode(secretKey);
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	}

	public static String encrypt(String value, SecretKey key) throws CryptoException {
		try {	
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			byte[] iv = new byte[12];
			SecureRandom random = new SecureRandom();
			random.nextBytes(iv);
			GCMParameterSpec gcmParams = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
			cipher.init(Cipher.ENCRYPT_MODE, key, gcmParams);
			byte[] encryptedValue = cipher.doFinal(value.getBytes());
			byte[] encryptedValueWithIv = new byte[iv.length + encryptedValue.length];
			System.arraycopy(iv, 0, encryptedValueWithIv, 0, iv.length);
			System.arraycopy(encryptedValue, 0, encryptedValueWithIv, iv.length, encryptedValue.length);
			return Base64.getEncoder().encodeToString(encryptedValueWithIv);
		} catch (Exception e) {
			throw new CryptoException("Error encrypting value", e);
		}
	}

	public static String decrypt(String value, SecretKey key) throws CryptoException {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			byte[] decodedValue = Base64.getDecoder().decode(value);
			byte[] iv = new byte[12];
			System.arraycopy(decodedValue, 0, iv, 0, iv.length);
			GCMParameterSpec gcmParams = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
			cipher.init(Cipher.DECRYPT_MODE, key, gcmParams);
			byte[] encryptedValue = new byte[decodedValue.length - iv.length];
			System.arraycopy(decodedValue, iv.length, encryptedValue, 0, encryptedValue.length);
			byte[] decryptedValue = cipher.doFinal(encryptedValue);
			return new String(decryptedValue);
		} catch (Exception e) {
			throw new CryptoException("Error decrypting value", e);
		}
	}
}
