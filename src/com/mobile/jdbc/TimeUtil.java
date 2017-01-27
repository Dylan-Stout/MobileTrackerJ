package com.mobile.jdbc;

import org.hibernate.query.Query;

public class TimeUtil {
	
	public static String[] getTimeParam(String time){ 
		switch (time) {
		case "today":
			return getToday();
		case "yesterday":
			return getYesterday();
		case "seven_days":
			return getSevenDay();
		case "month":
			return getLastMonth();
		case "year":
			return getLastYear();
		default:
			return new String[2];
		}
	}
	
	public static Query addTimeParam(Query query, String time){
		String to = new String(); 
		String from = new String(); 
		switch (time) {
		case "today":
			to = getToday()[0]; 
			from = getToday()[1]; 
			break;
		case "yesterday":
			to = getYesterday()[0]; 
			from = getYesterday()[1]; 
			break;
		case "seven":
			to = getSevenDay()[0]; 
			from = getSevenDay()[1]; 
			break;
		case "month":
			to = getLastMonth()[0]; 
			from = getLastMonth()[1]; 
			break;
		case "year":
			to = getLastYear()[0]; 
			from = getLastYear()[1]; 
			break;

		default:
			break;
		}
		query.setParameter("to", to ); 
		query.setParameter("from", from);	
		return query; 
		
	}

	public static String getTodaySqlRange(){ 
		long from = ((System.currentTimeMillis()/1000) - (60*60*24)) ; //24 hours ago
		long to = (System.currentTimeMillis()/1000); // right now
		StringBuilder sb = new StringBuilder(); 
		sb.append(" BETWEEN ").append(from).append(" AND ").append(to);
		return sb.toString();
	}
	
	public static String getYesterdaySqlRange(){ 
		long from = ((System.currentTimeMillis()/1000) - (60*60*24*2)) ; //48 hours ago / 2 days ago
		long to = ((System.currentTimeMillis()/1000) - (60*60*24)); //24 hours ago / 1 day ago
		StringBuilder sb = new StringBuilder(); 
		sb.append(" BETWEEN ").append(from).append(" AND ").append(to);
		return sb.toString();
	}
	
	public static String getSevenDaySqlRange(){ 
		long from = ((System.currentTimeMillis()/1000) - (60*60*24*7)) ; //168 hours ago / 7 days ago
		long to = (System.currentTimeMillis()/1000); // right now
		StringBuilder sb = new StringBuilder(); 
		sb.append(" BETWEEN ").append(from).append(" AND ").append(to);
		return sb.toString();
	}
	
	public static String getLastMonthSqlRange(){ 
		long from = ((System.currentTimeMillis()/1000) - (60*60*24*31)) ; //744 hours ago / 1 month ago
		long to = (System.currentTimeMillis()/1000); // right now 
		StringBuilder sb = new StringBuilder(); 
		sb.append(" BETWEEN ").append(from).append(" AND ").append(to);
		return sb.toString();
	}
	
	public static String getLastYearSqlRange(){ 
		long from = ((System.currentTimeMillis()/1000) - (60*60*24*365)) ; // 8760 hours ago / 1 year ago
		long to = (System.currentTimeMillis()/1000); // right now 
		StringBuilder sb = new StringBuilder(); 
		sb.append(" BETWEEN ").append(from).append(" AND ").append(to);
		return sb.toString();
	}
	
	public static String[] getToday(){ 
		long from = ((System.currentTimeMillis()/1000) - (60*60*24)) ; //24 hours ago
		long to = (System.currentTimeMillis()/1000); // right now
		String[] time = new String[2]; 
		time[0]=String.valueOf(from);
		time[1]=String.valueOf(to);
		return time;
	}
	
	public static String[] getYesterday(){ 
		long from = ((System.currentTimeMillis()/1000) - (60*60*24*2)) ; //48 hours ago / 2 days ago
		long to = ((System.currentTimeMillis()/1000) - (60*60*24)); //24 hours ago / 1 day ago
		String[] time = new String[2]; 
		time[0]=String.valueOf(from);
		time[1]=String.valueOf(to);
		return time;
	}
	
	public static String[] getSevenDay(){ 
		long from = ((System.currentTimeMillis()/1000) - (60*60*24*7)) ; //168 hours ago / 7 days ago
		long to = (System.currentTimeMillis()/1000); // right now
		String[] time = new String[2]; 
		time[0]=String.valueOf(from);
		time[1]=String.valueOf(to);
		return time;
	}
	
	public static String[] getLastMonth(){ 
		long from = ((System.currentTimeMillis()/1000) - (60*60*24*31)) ; //744 hours ago / 1 month ago
		long to = (System.currentTimeMillis()/1000); // right now 
		String[] time = new String[2]; 
		time[0]=String.valueOf(from);
		time[1]=String.valueOf(to);
		return time;
	}
	
	public static String[] getLastYear(){ 
		long from = ((System.currentTimeMillis()/1000) - (60*60*24*365)) ; // 8760 hours ago / 1 year ago
		long to = (System.currentTimeMillis()/1000); // right now 
		String[] time = new String[2]; 
		time[0]=String.valueOf(from);
		time[1]=String.valueOf(to);
		return time;
	}
	

}
