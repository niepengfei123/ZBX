package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 类型表
 * @author lijin
 *
 */
public class Type extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	type_id;		//类型ID
	
	private String 	type_name;		//类型名称
	
	private String 	type_pid;		//父级ID
	
	private String 	type_level;		//类型级别
	
	private String 	type_logo;	//类型logo

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getType_pid() {
		return type_pid;
	}

	public void setType_pid(String type_pid) {
		this.type_pid = type_pid;
	}

	public String getType_level() {
		return type_level;
	}

	public void setType_level(String type_level) {
		this.type_level = type_level;
	}

	public String getType_logo() {
		return type_logo;
	}

	public void setType_logo(String type_logo) {
		this.type_logo = type_logo;
	}

	
	
}
