package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 章节表
 * @author lijin
 *
 */
public class Part extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	part_id;		//章节ID
	
	private String 	course_id;		//课程ID
	
	private String 	part_name;		//章节名称
	
	private String 	part_url;		//章节视频
	
	private Double 	part_price;	//章节价格
	
	private String 	part_brief;	//章节简介
	
	private String 	part_keyword;	//章节关键字

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

	public String getPart_name() {
		return part_name;
	}

	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}

	public String getPart_url() {
		return part_url;
	}

	public void setPart_url(String part_url) {
		this.part_url = part_url;
	}

	public Double getPart_price() {
		return part_price;
	}

	public void setPart_price(Double part_price) {
		this.part_price = part_price;
	}

	public String getPart_brief() {
		return part_brief;
	}

	public void setPart_brief(String part_brief) {
		this.part_brief = part_brief;
	}

	public String getPart_keyword() {
		return part_keyword;
	}

	public void setPart_keyword(String part_keyword) {
		this.part_keyword = part_keyword;
	}

	
}
