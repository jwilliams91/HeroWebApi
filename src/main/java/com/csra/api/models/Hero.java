package com.csra.api.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="Heroes")
public class Hero extends AbstractModel implements Comparable<Hero>, Serializable{

	
	private static final long serialVersionUID = 1392901927933144795L;
	
	private String name;
	private String secretIdentity;
	private String bio;
	
	public Hero()
	{
		
	}
	
	public Hero(int id, String name, String secretIdentity, String bio)
	{
		super.setId(id);
		this.name = name;
		this.secretIdentity = secretIdentity;
		this.bio = bio;
	}
	
	
	@Column(name="name", length = 255)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="secretIdentity", length = 255)
	public String getSecretIdentity() {
		return secretIdentity;
	}
	public void setSecretIdentity(String secretIdentity) {
		this.secretIdentity = secretIdentity;
	}
	@Column(name="bio", length = 255)
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}

	
	@Override
	public String toString()
	{
		return this.name;
	}

	@Override
	public int compareTo(Hero o) {
		return this.toString().compareTo(o.toString());
	}
	
}
