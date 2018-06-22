package com.example.momo.client;

public class User {
	// ORM
	// Object Relation Mapping
	
	/*CREATE TABLE `usertbl` (
			  `id` int(11) NOT NULL AUTO_INCREMENT,
			  `username` varchar(20) DEFAULT NULL,
			  `password` varchar(20) DEFAULT NULL,
			  `permission` int(11) DEFAULT NULL,
			  PRIMARY KEY (`id`)
			) ENGINE=InnoDB DEFAULT CHARSET=latin1;*/
	
	
	private int id;
	private String username;
	private String password;
	
	
	public User() {
		super();
	}
	
	public User(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
