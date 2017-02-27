package com.csra.api.daos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import com.csra.api.models.Hero;
import com.csra.api.models.Sidekick;

import spark.Request;

public class HeroDao extends AbstractDao{

	private static List<Hero> heroes = new ArrayList<Hero>();
	
	public String getList()
	{
		heroes = getList(Hero.class);
		for(Hero h: heroes)
		{
			h.setSidekicks(null);
		}
		return typeToJson(heroes.toArray());
		
	}

	public String getById(String id)
	{
		return typeToJson(getById(id, Hero.class));
	}
	
	public String create(Request req) {
		
			
			Hero newHero = create(jsonToType(req, Hero.class), Hero.class);
			return typeToJson(newHero);
		
	}

	public String update(Request req) {
			
			Hero updatedHero = jsonToType(req, Hero.class);
			return update(updatedHero);
		
	}

	public String delete(String id) {
		return delete(id,Hero.class);
	}

	public Object uploadImage(Request req) {
		
		try {
			MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/tmp");
			req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
			Part file = req.raw().getPart("file[]");
			Path path = Paths.get("/Development/angular-app/src/assets/" + file.getSubmittedFileName());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	public String matchedHeroes(Request req) {
		List<Hero> matchedHeroes = new ArrayList<Hero>();
		List<Hero> containsHeroes = new ArrayList<Hero>();
		
		
		String searchTerm = req.queryParams("name");
		
		for(Hero hero: heroes)
		{
			if(hero.getName().toLowerCase().charAt(0) == searchTerm.toLowerCase().charAt(0))
				matchedHeroes.add(hero);
			if(hero.getName().toLowerCase().contains(searchTerm.toLowerCase()) && !matchedHeroes.contains(hero))
				containsHeroes.add(hero);
		}
		Collections.sort(matchedHeroes);
		matchedHeroes.addAll(containsHeroes);
		return typeToJson(matchedHeroes.toArray());
		
		
	}

	
}
