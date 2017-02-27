package com.csra.api.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Heroes")
public class Hero implements Comparable<Hero>{

	
	private static final long serialVersionUID = 1392901927933144795L;
	
	private int id;
	private String name;
	private String secretIdentity;
	private String bio;
	private Set<Sidekick> sidekicks;
	
	public Hero()
	{
		
	}
	
	public Hero(String name, String secretIdentity, String bio)
	{
		this.name = name;
		this.secretIdentity = secretIdentity;
		this.bio = bio;
	}
	
	@Id
	@Column(name ="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "hero")
	public Set<Sidekick> getSidekicks() {
		return sidekicks;
	}

	public void setSidekicks(Set<Sidekick> sidekicks) {
		this.sidekicks = sidekicks;
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
