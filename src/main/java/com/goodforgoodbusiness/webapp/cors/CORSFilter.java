package com.goodforgoodbusiness.webapp.cors;

import spark.Filter;
import spark.Request;
import spark.Response;

public class CorsFilter implements Filter {
	@Override
	public void handle(Request req, Response res) throws Exception {
		res.header("Access-Control-Allow-Origin", "*");
	}
}
