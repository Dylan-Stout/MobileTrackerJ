package com.mobile.ui;

public interface UIConstants {
	
	/*****************************
	 * Error message wrappers
	 *****************************/
	
	final String W_FONT = "</font>"; 
	
	/********* ERRORS **********/
	
	final String W_ERR_MSG = "<font class=\"errorHelpText\">"; 

	/******** SUCCESS *********/
	
	final String W_SUCCEED_MSG = "<font class=\"successHelpText\">";
	
	
	/**********************
	 * User UI Components
	 **********************/
	
	/********* ERRORS ********/
	
	final String DB_EXCEPTION = "<font class=\"errorHelpText\">Database Exception. See logs.</font>"; 
	final String DB_CONNECTION = "<font class=\"errorHelpText\">Connection Error</font>"; 
	final String SESSION_INVALID = "<center><font class=\"errorHelpText\">Session invalid, please login.</font></center>";
	final String LOGIN_INVALID = "<center><font class=\"errorHelpText\">Login invalid, please try again.</font></center>";
	final String LOGIN_IP_ERR = "<center><font class=\"errorHelpText\">Login has Expired</font></center>";
	final String LOGIN_IN_USE = "<center><font class=\"errorHelpText\">Login in use - Logout from terminal</font></center>";
	
	
}