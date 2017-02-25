package com.csra.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Sidekicks")
public class Sidekick implements Comparable<Sidekick>{

	
	private static final long serialVersionUID = -7045665169322404206L;

	private int id;
	private String name;
	private String secretIdentity;
	private String bio;
	private int age;
	private Hero hero;
	
	public Sidekick()
	{
		
	}
	
	public Sidekick(String name, String secretIdentity, String bio, int age, Hero hero)
	{
		this.name = name;
		this.secretIdentity = secretIdentity;
		this.bio = bio;
		this.hero = hero;
		this.age = age;
	}

	@Id
	@Column(name ="sidekick_id")
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
	
	@Column(name="age")
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@ManyToOne
	@JoinColumn(name="hero_id")
	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	@Override
	public int compareTo(Sidekick o) {
		return this.toString().compareTo(o.toString());
	}

	@Override
	public String toString()
	{
		return this.name;
	}
	
}
