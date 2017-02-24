package com.csra.HeroWebApi;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import com.csra.api.config.CORSConfig;
import com.csra.api.daos.HeroDao;


public class HeroApi 
{
	
    public static void main( String[] args )
    {
    	HeroDao heroDao = new HeroDao();
    	CORSConfig.enableCORS("http://localhost:4200", "*", "*");
        get("/api/heroes/:id", (req, res) -> heroDao.getById(req.params("id")));
        get("/api/heroes", (req, res) -> heroDao.getList());
        get("/api/heroes/search/", (req, res) -> heroDao.matchedHeroes(req));
        put("/api/heroes/:id", (req, res) -> heroDao.update(req));
        post("/api/heroes", (req, res) -> heroDao.create(req));
        post("/api/heroes/upload", (req, res) -> heroDao.uploadImage(req));
        delete("/api/heroes/:id", "application/json", (req,res) -> heroDao.delete(req.params("id")));
        
    }
    
    
}
