package com.mobile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table; 


@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	Long id; 
	
	@Column(name="USERNAME")
	String username; 
	
	@Column(name="USERIP")
	String userip; 
	
	@Column(name="USERERROR")
	String usererror; 
	
	@Column(name="USERHASH")
	String userhash; 

	public User(String user, String ip, String error, String hash){
		this.username = user; 
		this.userip = ip; 
		this.usererror = error; 
		this.userhash = hash; 
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserip() {
		return userip;
	}

	public void setUserip(String userip) {
		this.userip = userip;
	}

	public String getUsererror() {
		return usererror;
	}

	public void setUsererror(String usererror) {
		this.usererror = usererror;
	}

	public String getUserhash() {
		return userhash;
	}

	public void setUserhash(String userhash) {
		this.userhash = userhash;
	}
	
	
}
