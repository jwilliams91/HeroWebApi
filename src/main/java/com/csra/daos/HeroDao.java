package com.csra.daos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.csra.models.Hero;

import spark.Request;

public class HeroDao {

	private static ObjectMapper mapperObj = new ObjectMapper();
	
	private static ArrayList<Hero> heroes = init();
	
	
	public static ArrayList<Hero> init()
	{
		ArrayList<Hero> list = new ArrayList<Hero>(10);
		list.add(new Hero(1, "Iron Man","Tony Stark","An American billionaire playboy, business magnate, and ingenious engineer."));
		list.add(new Hero(2, "Thor","Thor, Son of Odin", "Thor is a hammer-wielding god associated with thunder, lightning, storms, oak trees, strength, and the protection of mankind."));
		list.add(new Hero(3, "Captain America", "Steve Rodgers", "Captain America wears a costume that bears an American flag motif, and is armed with a nearly indestructible shield that he throws at foes."));
		list.add(new Hero(4, "Hulk", "Bruce Banner", "Following his accidental exposure to gamma radiation during the detonation of an experimental bomb, Banner is physically transformed into the Hulk when subjected to emotional stress, at or against his will."));
		return list;
	}
	
	public static String getHeroById(String passedId)
	{
		for(Hero hero: heroes)
			if(hero.getId() == Integer.parseInt(passedId))
				return toJson(hero);
		
		return null;
	}
	
	public static String getHeroes()
	{
		return toJson(heroes);
	}

	public static String createHero(Request req) {
		
			Hero newHero = jsonToHero(req);
			newHero.setId(heroes.size() + 1);
			heroes.add(newHero);
			return toJson(newHero);
		
	}

	public static Hero updateHero(Request req) {
		
			Hero updatedHero = jsonToHero(req);
			for(int i =0; i < heroes.size(); i++)
				if(heroes.get(i).getId() == updatedHero.getId())
				{
					heroes.set(i, updatedHero);
					return updatedHero;
				}	
			return null;
		
	}

	public static Object deleteHero(String passedId) {
		int indexOfRemoved = 0;
		for(int i=0; i < heroes.size(); i++) //Remove Hero from List
			if(heroes.get(i).getId() == Integer.parseInt(passedId))
			{
				indexOfRemoved = i;
				heroes.remove(i);
			}
		
		for(int j=heroes.size() - 1; j >= indexOfRemoved; j--) //Updates the Hero Object ID's to remain sequential
		{
			Hero current = heroes.get(j);
			current.setId(current.getId() - 1);
		}
			
		return "200";
	}

	private static Hero jsonToHero(Request req)
	{
		try {
			Hero hero = mapperObj.readValue(req.body(), Hero.class);
			return hero;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	public static String toJson(Object o)
	{
		try {
			return mapperObj.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String printHeroes()
	{
		String result = "";
		for(Hero hero: heroes)
		{
			result += "(" + hero.getId() + ", " + hero.getName() +")\n";
		}
		return result;
	}

	public static Object uploadImage(Request req) {
		
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

	public static String matchedHeroes(Request req) {
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

		matchedHeroes.addAll(containsHeroes);
		return toJson(matchedHeroes.toArray());
		
		
	}
	
}
