package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 学习记录表
 * @author lijin
 *
 */
public class StudyRecord extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	study_record_id;		//学习记录ID
	
	private String 	user_id;				//用户ID
	
	private String part_id;		//章节ID
	
	private String 	course_id;	//课程ID

	public String getStudy_record_id() {
		return study_record_id;
	}

	public void setStudy_record_id(String study_record_id) {
		this.study_record_id = study_record_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPart_id() {
		return part_id;
	}

	public void setPart_id(String part_id) {
		this.part_id = part_id;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

}
