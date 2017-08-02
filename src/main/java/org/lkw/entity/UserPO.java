package org.lkw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_USER")
public class UserPO extends IDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserPO() {}
	
	
	
	public UserPO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	@Column(name = "name",nullable = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserPO [name=" + name + "]";
	}
	

}
