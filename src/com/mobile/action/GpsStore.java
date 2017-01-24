package com.mobile.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mobile.jdbc.hibernate.HibernateUtil;
import com.mobile.model.Gps;

/**
 * Servlet implementation class GpsStore
 */
@WebServlet("/Gps")
public class GpsStore extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(GpsStore.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GpsStore() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * No use
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doGet on /Gps Servlet");
	}

	/**
	 * Add entry of gps coords to database. 
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Enumeration<String> parameterNames = request.getParameterNames();
		ArrayList<String> locationData = new ArrayList<String>(); 

		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			locationData.add(paramValues[0]);
		}
		
		Session session = null; 
		Transaction tx = null; 
		try{
			session = HibernateUtil.getSession(); 
			tx = session.beginTransaction(); 
			EntityManagerFactory emf = session.getEntityManagerFactory();
			EntityManager em = emf.createEntityManager(); 
			em.getTransaction().begin();
			Gps gps = new Gps(); 
			int numMessages = locationData.size()/4;
			for(int i=0; i<numMessages;i+=4){
				Long milTime = Long.parseLong(locationData.get(i*4),10); 
				Long unixTime = milTime/1000L;  // convert milliseconds to seconds
				gps.setId(unixTime.toString());
				Date time = new Date(unixTime*1000L); 
				logger.debug(unixTime + " -> " + time.toString()); //degub time conversion			
				gps.setDate(time.toString());
				gps.setLatitude(locationData.get((i*4)+1));
				gps.setLongitude(locationData.get((i*4)+2));
				gps.setSpeed(locationData.get((i*4)+3));

				session.save(gps);

				gps = new Gps(); 
				logger.debug("[INCOMING] OK - ** Coordinate pair data recieved  **");
			}
			tx.commit();
			session.close();
			logger.debug("Peristed " + numMessages + " entries. Closing session.");
		}catch(Exception e){ 
			e.printStackTrace();
			if(tx != null)
				tx.rollback();
		}
		
	}
	

}
