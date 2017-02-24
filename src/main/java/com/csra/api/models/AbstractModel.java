package com.csra.api.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class AbstractModel implements Serializable{
	
	@Id
	protected int id;

}
