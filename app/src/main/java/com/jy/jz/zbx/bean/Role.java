package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 角色表
 * @author lijin
 *
 */
public class Role extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	role_id;		//角色ID
	
	private String 	role_name;				//角色名

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	
	
	
}
