package com.mobile.jdbc.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query; 
/**
*	Hibernate Utility for building Sessions within context. 
*/
public class HibernateUtil {

	private static final Session session; 
	
	static {
		try {
			Configuration config = new Configuration(); 
			//ADD Annotated classes
			config.addAnnotatedClass(com.mobile.model.User.class); 
			config.addAnnotatedClass(com.mobile.model.UserEvent.class);
			config.addAnnotatedClass(com.mobile.model.Gps.class); 
			
			final SessionFactory sessionFactory = config.configure().buildSessionFactory();
			session = sessionFactory.openSession(); 
		} catch (Throwable ex) {
			// Log exception!
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Session getSession()
			throws HibernateException {
		return session;
	}
}
