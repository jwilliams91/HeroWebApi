package com.csra.HeroWebApi;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.options;

import com.csra.daos.HeroDao;


public class HeroApi 
{
	
    public static void main( String[] args )
    {
    	enableCORS("http://localhost:4200", "*", "*");
        get("/api/heroes/:id", (req, res) -> HeroDao.getHeroById(req.params("id")));
        get("/api/heroes", (req, res) -> HeroDao.getHeroes());
        get("/api/heroes/search/", (req, res) -> HeroDao.matchedHeroes(req));
        put("/api/heroes/:id", (req, res) -> HeroDao.updateHero(req));
        post("/api/heroes", (req, res) -> HeroDao.createHero(req));
        post("/api/heroes/upload", (req, res) -> HeroDao.uploadImage(req));
        delete("/api/heroes/:id", "application/json", (req,res) -> HeroDao.deleteHero(req.params("id")));
        
    }
    
    private static void enableCORS(final String origin, final String methods, final String headers)
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
