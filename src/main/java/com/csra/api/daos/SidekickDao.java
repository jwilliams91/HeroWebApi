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

public class SidekickDao extends AbstractDao {

	private static List<Sidekick> sidekicks = new ArrayList<Sidekick>();
	
	@Override
	public String getList() {
		sidekicks = getList(Sidekick.class);
		return typeToJson(sidekicks.toArray());
	}

	@Override
	public String getById(String id) {
		return typeToJson(getById(id, Sidekick.class));
	}

	@Override
	public String create(Request req) {
		
		Sidekick[] newSidekick = jsonToType(req.body(), Sidekick[].class);
		for(Sidekick s : newSidekick)
		{
			HeroDao heroDao = new HeroDao();
			List<Hero> heroes = heroDao.getList(Hero.class);
			s.setHero(heroes.get(heroes.size() - 1 ));
			create(s, Sidekick.class);
		}
		return "200";
	}

	@Override
	public String update(Request req) {
		
		Sidekick updatedSidekick = jsonToType(req.body(), Sidekick.class);
		return update(updatedSidekick);
	}

	@Override
	public String delete(String id) {
		return delete(id,Sidekick.class);
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

	public String matchedSidekicks(Request req) {
		List<Sidekick> matchedSidekicks = new ArrayList<Sidekick>();
		List<Sidekick> containsSidekicks = new ArrayList<Sidekick>();
		
		
		String searchTerm = req.queryParams("name");
		
		for(Sidekick sidekick: sidekicks)
		{
			if(sidekick.getName().toLowerCase().charAt(0) == searchTerm.toLowerCase().charAt(0))
				matchedSidekicks.add(sidekick);
			if(sidekick.getName().toLowerCase().contains(searchTerm.toLowerCase()) && !matchedSidekicks.contains(sidekick))
				containsSidekicks.add(sidekick);
		}
		Collections.sort(matchedSidekicks);
		matchedSidekicks.addAll(containsSidekicks);
		return typeToJson(matchedSidekicks.toArray());
		
		
	}
}
