package com.mobile.jdbc.hibernate;

import org.hibernate.*;
import org.hibernate.cfg.*; 

public class HibernateUtil {

	private static final SessionFactory sessionFactory;
	
	static {
		try {
			sessionFactory = new Configuration()
					.configure().buildSessionFactory();
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