package com.mobile.jdbc.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration; 

public class HibernateUtil {

	private static final SessionFactory sessionFactory;
	
	//TODO - Avoid sqli
//	 Query safeHQLQuery = session.createQuery("from Inventory where productID=:productid");
//	 safeHQLQuery.setParameter("productid", userSuppliedParameter);
	
	static {
		try {
			Configuration config = new Configuration(); 
			//ADD Annotated classes
			config.addAnnotatedClass(com.mobile.model.User.class); 
			
			sessionFactory = config.configure().buildSessionFactory();
			
		} catch (Throwable ex) {
			// Log exception!
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Session getSession()
			throws HibernateException {
		return sessionFactory.openSession();
	}
}