package com.goodforgoodbusiness.webapp.jwt;

import static com.goodforgoodbusiness.webapp.jwt.JWTParams.KEY_ALGORITHM;
import static com.goodforgoodbusiness.webapp.jwt.JWTParams.KEY_SIZE;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class JWTGenerate {
	public static KeyPair newKeyPair() throws NoSuchAlgorithmException {
		var generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		generator.initialize(KEY_SIZE, new SecureRandom());
		return generator.generateKeyPair();
	}
	
	public static String encode(Key key) {
		try {
			return new String(Base64.getEncoder().encode(key.getEncoded()), "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("JRE does not support UTF-8?!", e);
		}
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		var keyPair = newKeyPair();
		var publicKey = (RSAPublicKey) keyPair.getPublic();
		
		System.out.println("publicKey = ");
		System.out.println(encode(publicKey));
		
		var privateKey = (RSAPrivateKey) keyPair.getPrivate();
		
		System.out.println("privateKey = ");
		System.out.println(encode(privateKey));
	}
}
