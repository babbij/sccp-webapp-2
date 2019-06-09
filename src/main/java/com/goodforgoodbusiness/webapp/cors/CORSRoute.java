package com.goodforgoodbusiness.webapp.cors;

import spark.Request;
import spark.Response;
import spark.Route;

public class CORSRoute implements Route {
	@Override
	public Object handle(Request req, Response res) throws Exception {
        var acrhHeader = req.headers("Access-Control-Request-Headers");
        if (acrhHeader != null) {
            res.header("Access-Control-Allow-Headers", acrhHeader);
        }

        var acrmHeader = req.headers("Access-Control-Request-Method");
        if (acrmHeader != null) {
            res.header("Access-Control-Allow-Methods", acrmHeader);
        }

        return "OK";
	}
}
