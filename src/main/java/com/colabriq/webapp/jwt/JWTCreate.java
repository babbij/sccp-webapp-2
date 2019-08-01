package com.colabriq.webapp.jwt;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTCreate {
	private static RSAPrivateKey unencode(String encodedForm) {
		try {
			var spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(encodedForm.getBytes()));
			var kf = KeyFactory.getInstance(JWTParams.KEY_ALGORITHM);
	        return (RSAPrivateKey)kf.generatePrivate(spec);
		}
		catch (InvalidKeySpecException e) {
			throw new RuntimeException("Invalid key", e);
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Algorithm not registered", e);
		}
	}

	private final RSAPrivateKey privateKey;
	
	public JWTCreate(String privateKey) {
		this.privateKey = unencode(privateKey);
	}
	
	public String create() {
		return JWT
			.create()
			.withIssuer("colabriq")
			.withIssuedAt(new Date())
			.withExpiresAt( Date.from(Instant.now().plus(30, ChronoUnit.SECONDS)) )
			.sign(Algorithm.RSA512(null, privateKey))
		;
	}
	
	public static void main(String[] args) throws Exception {
		var pair = JWTGenerate.newKeyPair();
		var privateKey = JWTGenerate.encode(pair.getPrivate());
		var token = new JWTCreate(privateKey).create();
		
		System.out.print(token);
	}
}
