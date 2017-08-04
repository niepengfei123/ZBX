package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 用户课程表
 * @author lijin
 *
 */
public class UserCourse extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	user_course_id;		//用户课程ID
	
	private String 	course_id;				//课程ID
	
	private String 	user_id;				//用户ID

	public String getUser_course_id() {
		return user_course_id;
	}

	public void setUser_course_id(String user_course_id) {
		this.user_course_id = user_course_id;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}



}
