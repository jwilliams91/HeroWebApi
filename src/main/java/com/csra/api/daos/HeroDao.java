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

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.csra.api.config.HibernateConnector;
import com.csra.api.models.Hero;

import spark.Request;

public class HeroDao extends AbstractDao{

	private static List<Hero> heroes = new ArrayList<Hero>();
	
	public String getById(String id)
	{
		Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Query<Hero> query = session.createQuery("from Hero h where h.id = :id", Hero.class);
            query.setParameter("id", Integer.parseInt(id));
 
            List<Hero> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
            	Hero result = queryList.get(0);
                return typeToJson(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
	}
	
	public String getList()
	{
		
		Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Query<Hero> query = session.createQuery("from Hero h", Hero.class);
 
            List<Hero> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
            	HeroDao.heroes = queryList;
                return typeToJson(queryList.toArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
		
		
		
	}

	public String create(Request req) {
		
			Hero newHero = jsonToType(req, Hero.class);
			newHero.setId(heroes.size() + 1);
			this.addHero(newHero);
			heroes.add(newHero);
			return typeToJson(newHero);
		
	}

	public String update(Request req) {
		
			Hero updatedHero = jsonToType(req, Hero.class);
			Session session = null;
	        try {
	            session = HibernateConnector.getInstance().getSession();
	            session.saveOrUpdate(updatedHero);
	            session.flush();
	            return "200";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        } finally {
	            session.close();
	        }
		
	}

	public String delete(String id) {
		Session session = null;
		Transaction tran = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            tran = session.beginTransaction();
            Query query = session.createQuery("delete from Hero h where h.id =:id");
            query.setParameter("id", Integer.parseInt(id));
            query.executeUpdate();
            tran.commit();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            session.close();
        }
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
	
	public Hero addHero(Hero hero)
	{
		Session session = null;
		Transaction tran = null;
		try{
		session = HibernateConnector.getInstance().getSession();
		tran = session.beginTransaction();
		session.save(hero);
		tran.commit();
		return hero;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	
}
