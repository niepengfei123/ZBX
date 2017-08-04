package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 用户章节表
 * @author lijin
 *
 */
public class UserPart extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	user_part_id;		//用户章节ID
	
	private String 	part_id;				//章节ID
	
	private String 	user_id;				//用户ID

	public String getUser_part_id() {
		return user_part_id;
	}

	public void setUser_part_id(String user_part_id) {
		this.user_part_id = user_part_id;
	}

	public String getPart_id() {
		return part_id;
	}

	public void setPart_id(String part_id) {
		this.part_id = part_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


}
