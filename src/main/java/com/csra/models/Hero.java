package com.csra.models;

public class Hero {

	private int id;
	private String name;
	private String secretIdentity;
	private String bio;
	
	public Hero()
	{
		
	}
	
	public Hero(int id, String name, String secretIdentity, String bio)
	{
		this.id = id;
		this.name = name;
		this.secretIdentity = secretIdentity;
		this.bio = bio;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecretIdentity() {
		return secretIdentity;
	}
	public void setSecretIdentity(String secretIdentity) {
		this.secretIdentity = secretIdentity;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	
	
	
}
