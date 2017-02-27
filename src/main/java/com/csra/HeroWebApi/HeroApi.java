package com.csra.HeroWebApi;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import com.csra.api.config.CORSConfig;
import com.csra.api.daos.HeroDao;
import com.csra.api.daos.SidekickDao;


public class HeroApi 
{
	
    public static void main( String[] args )
    {
    	CORSConfig.enableCORS("http://localhost:4200", "*", "*");
    	
    	//Hero Listeners
    	HeroDao heroDao = new HeroDao();
    	//heroDao.createCRUD();
        get("/api/heroes/:id", (req, res) -> heroDao.getById(req.params("id")));
        get("/api/heroes", (req, res) -> heroDao.getList());
        get("/api/heroes/search/", (req, res) -> heroDao.matchedHeroes(req));
        put("/api/heroes/:id", (req, res) -> heroDao.update(req));
        post("/api/heroes", (req, res) -> heroDao.create(req));
        post("/api/heroes/upload", (req, res) -> heroDao.uploadImage(req));
        delete("/api/heroes/:id", "application/json", (req,res) -> heroDao.delete(req.params("id")));
    	
        //Sidekick Listeners
        SidekickDao sidekickDao = new SidekickDao();
        
        get("/api/sidekick/:id", (req, res) -> sidekickDao.getById(req.params("id")));
        get("/api/sidekick", (req, res) -> sidekickDao.getList());
        get("/api/sidekick/search/", (req, res) -> sidekickDao.matchedSidekicks(req));
        put("/api/sidekick/:id", (req, res) -> sidekickDao.update(req));
        post("/api/sidekick", (req, res) -> sidekickDao.create(req));
        post("/api/sidekick/upload", (req, res) -> sidekickDao.uploadImage(req));
        delete("/api/sidekick/:id", "application/json", (req,res) -> sidekickDao.delete(req.params("id")));
    }
    
    
}
