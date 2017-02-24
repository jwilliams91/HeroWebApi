package com.csra.api.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Heroes")
public class Hero implements Comparable<Hero>, Serializable{

	
	private static final long serialVersionUID = 1392901927933144795L;

	@Id
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
	
	
	@Column(name="id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
