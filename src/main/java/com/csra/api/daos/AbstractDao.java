package com.csra.api.daos;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import spark.Request;

public abstract class AbstractDao {

	private static ObjectMapper mapperObj = new ObjectMapper();
	abstract String getById(String id);
	abstract String getList();
	abstract String update(Request req);
	abstract String create(Request req);
	abstract String delete(String id);
	
	protected static <T> T jsonToType(Request req, Class<T> type)
	{
		try {
			return mapperObj.readValue(req.body(), type);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected static String typeToJson(Object o)
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
	
}
