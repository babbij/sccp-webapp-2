package com.goodforgoodbusiness.webapp.jwt;

interface JWTParams {
	public final String HEADER_NAME = "Authorization";
	public final String HEADER_PREFIX = "Bearer ";
	
	public final String JWT_TOKEN_ATTR = "JWT-TOKEN";
	public final String JWT_DECODED_ATTR = "JWT-DECODED";
	
	public final String KEY_ALGORITHM = "RSA";
	public final int KEY_SIZE = 1024;
}
