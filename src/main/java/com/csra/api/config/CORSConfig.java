package com.csra.api.config;

import static spark.Spark.before;
import static spark.Spark.options;

public class CORSConfig {

	
	public static void enableCORS(final String origin, final String methods, final String headers)
    {
    	options("/*", (req, res) -> {
    		
    		String accessReqHeaders = req.headers("Access-Control-Request-Headers");
    		if(accessReqHeaders != null)
    			res.header("Access-Control-Allow-Headers", accessReqHeaders);
    		
    		String accessReqMethod = req.headers("Access-Control-Request-Method");
    		if(accessReqMethod != null)
    			res.header("Access-Control-Allow-Methods", accessReqMethod);
    		
    		return "OK";
    	});
    	
    	before((req, res) -> {
    		res.header("Access-Control-Allow-Origin", origin);
    		res.header("Access-Control-Request-Method", methods);
    		res.header("Access-Control-Allow-Headers", headers);
    		
    		res.type("application/json");
    	});
    }
}
