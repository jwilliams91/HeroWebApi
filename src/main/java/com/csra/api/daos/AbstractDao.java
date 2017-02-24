package com.csra.api.daos;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.csra.api.config.HibernateConnector;

import spark.Request;

public abstract class AbstractDao {

	private static ObjectMapper mapperObj = new ObjectMapper();
	abstract String getList();
	abstract String getById(String id);
	abstract String create(Request req);
	abstract String update(Request req);
	abstract String delete(String id);
	
	protected <T> List<T> getList(Class<T> type)
	{
		Session session = null;
		try {
            session = HibernateConnector.getInstance().getSession();
            String[] queryTxt = type.getName().split("\\.");
            String className = queryTxt[queryTxt.length - 1];
            char firstChar = className.toLowerCase().charAt(0);
            Query<T> query = session.createQuery("from " + className + " " + firstChar, type);
 
            List<T> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                return queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
	}
	
	protected <T> T getById(String id, Class<T> type)
	{
		Session session = null;
	    try {
	        session = HibernateConnector.getInstance().getSession();
	        String[] queryTxt = type.getName().split("\\.");
	        String className = queryTxt[queryTxt.length - 1];
	        char firstChar = className.toLowerCase().charAt(0);
	        Query<T> query = session.createQuery("from "+ className + " " + firstChar + " where " + firstChar +".id = :id", type);
	        query.setParameter("id", Integer.parseInt(id));
	
	        List<T> queryList = query.list();
	        if (queryList != null && queryList.isEmpty()) {
	            return null;
	        } else {
	        	T result = queryList.get(0);
	            return result;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        session.close();
	    }
	}
	protected <T> T create(Object obj, Class<T> type)
	{
		Session session = null;
		Transaction tran = null;
		try{
		session = HibernateConnector.getInstance().getSession();
		tran = session.beginTransaction();
		session.save(obj);
		tran.commit();
		T result = (T) obj;
		return result;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	protected String update(Object obj)
	{
		Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            session.saveOrUpdate(obj);
            session.flush();
            return "200";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
	}
	
	protected <T>String delete(String id, Class<T> type)
	{
		Session session = null;
		Transaction tran = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            tran = session.beginTransaction();
            String[] queryTxt = type.getName().split("\\.");
            String className = queryTxt[queryTxt.length - 1];
            char firstChar = className.toLowerCase().charAt(0);
            Query query = session.createQuery("delete from "+ className + " " + firstChar + " where " + firstChar +".id = :id");
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
