package com.csra.api.config;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.csra.api.models.*;

public class HibernateConnector {

	private static HibernateConnector me;
	private Configuration cfg;
	private SessionFactory sessionFactory;
	
	private HibernateConnector() throws HibernateException
	{
		cfg = new Configuration();
		
        cfg.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/HeroDB");
        cfg.setProperty("hibernate.connection.username", "HeroAdmin");
        cfg.setProperty("hibernate.connection.password", "heroMadness");
        //cfg.setProperty("hibernate.show_sql", "true");
        cfg.addAnnotatedClass(Hero.class);
        cfg.addAnnotatedClass(Sidekick.class);
 
        
        sessionFactory = cfg.buildSessionFactory();
	}
	
	public static synchronized HibernateConnector getInstance()
	{
		if(me == null)
			me = new HibernateConnector();
		
		return me;
	}
	
	public Session getSession() throws HibernateException
	{
		Session session = sessionFactory.openSession();
		if(!session.isConnected())
		{
			this.reconnect();
		}
		return session;
	}

	private void reconnect() throws HibernateException{
		this.sessionFactory = cfg.buildSessionFactory();
	}
}
