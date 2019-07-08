//package com.goodforgoodbusiness.webapp.jwt;
//
//import static com.goodforgoodbusiness.webapp.jwt.JWTParams.HEADER_NAME;
//import static com.goodforgoodbusiness.webapp.jwt.JWTParams.HEADER_PREFIX;
//import static com.goodforgoodbusiness.webapp.jwt.JWTParams.JWT_DECODED_ATTR;
//import static com.goodforgoodbusiness.webapp.jwt.JWTParams.JWT_TOKEN_ATTR;
//
//import java.net.http.HttpRequest;
//import java.util.Optional;
//
//import spark.Request;
//
//public class JWTUtil {
//	@SuppressWarnings("unchecked")
//	public Optional<String> getToken(Request req) {
//		return (Optional<String>) req.attribute(JWT_TOKEN_ATTR);
//	}
//	
//	@SuppressWarnings("unchecked")
//	public Optional<String> getDecoded(Request req) {
//		return (Optional<String>) req.attribute(JWT_DECODED_ATTR);
//	}
//	
//	/**
//	 * Return the Authorisation header in a convenient format suitable for {@link HttpRequest}
//	 */
//	public String [] headers(Request req) {
//		return getToken(req)
//			.map(token -> new String [] { HEADER_NAME, HEADER_PREFIX + token })
//			.orElse(new String [] { })
//		;
//	}
//}
