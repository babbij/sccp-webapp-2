package com.goodforgoodbusiness.webapp.jwt;

import static com.goodforgoodbusiness.webapp.jwt.JWTParams.HEADER_NAME;
import static com.goodforgoodbusiness.webapp.jwt.JWTParams.HEADER_PREFIX;
import static spark.Spark.halt;

import java.util.Optional;

import spark.Filter;
import spark.Request;
import spark.Response;

public class JWTFilter implements Filter {
	private static Optional<String> getToken(String authHeader) {
		if (authHeader != null) {
			var authHeaderTrimmed = authHeader.trim();
			if (authHeaderTrimmed.startsWith(HEADER_PREFIX)) {
				return Optional.of(authHeaderTrimmed.substring(HEADER_PREFIX.length()).trim());
			}
		}
		
		return Optional.empty();
	}

	private JWTVerify verify;
	
	public JWTFilter(JWTVerify verify) {
		this.verify = verify;
	}
	
	@Override
	public void handle(Request req, Response res) throws Exception {
		var token = getToken(req.headers(HEADER_NAME));
		req.attribute(JWTParams.JWT_TOKEN_ATTR, token);
		
		var decoded = verify.verifyAndDecode(token);
		req.attribute(JWTParams.JWT_DECODED_ATTR, decoded);
		
		if (!decoded.isPresent()) {
			halt(403, "Not authenticated");
		}
	}
}
