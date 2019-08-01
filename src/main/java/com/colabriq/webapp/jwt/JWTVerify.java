package com.colabriq.webapp.jwt;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTVerify {
	private static RSAPublicKey unencode(String encodedForm) {
		try {
			var spec = new X509EncodedKeySpec(Base64.getDecoder().decode(encodedForm.getBytes()));
			var kf = KeyFactory.getInstance(JWTParams.KEY_ALGORITHM);
	        return (RSAPublicKey)kf.generatePublic(spec);
		}
		catch (InvalidKeySpecException e) {
			throw new RuntimeException("Invalid key", e);
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Algorithm not registered", e);
		}
	}

	private final JWTVerifier verifier;
	
	public JWTVerify(String publicKey) {
		this.verifier = JWT
	    	.require(Algorithm.RSA512(unencode(publicKey), null))
	    	.acceptLeeway(5)
	        .withIssuer("colabriq")
	        .build()
	    ;
	}
	
	public boolean verify(Optional<String> token) {		
	    return verifyAndDecode(token).isPresent();
	}
	
	public Optional<DecodedJWT> verifyAndDecode(Optional<String> token) {
		try {	
		    return token.map(verifier::verify);
		}
		catch (JWTVerificationException e) {
			return Optional.empty();
		}
	}
	
	public static void main(String[] args) throws Exception {
		var pair = JWTGenerate.newKeyPair();
		var privateKey = JWTGenerate.encode(pair.getPrivate());
		var token = new JWTCreate(privateKey).create();
		var verify = new JWTVerify(JWTGenerate.encode(pair.getPublic()));
		
		System.out.print(verify.verifyAndDecode(Optional.of(token)).orElseThrow());
	}
}
