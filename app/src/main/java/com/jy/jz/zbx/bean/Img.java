package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 图片实体
 * @author lijin
 *
 */
public class Img extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String img_id;		//图片ID
	private String img_url;		//图片url
	private String img_pid;		//图片所属
	private String img_brief;	//图片简介
	
	
	public String getImg_id() {
		return img_id;
	}
	public void setImg_id(String img_id) {
		this.img_id = img_id;
	}
	
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	
	public String getImg_pid() {
		return img_pid;
	}
	public void setImg_pid(String img_pid) {
		this.img_pid = img_pid;
	}
	
	public String getImg_brief() {
		return img_brief;	
	}
	public void setImg_brief(String img_brief) {
		this.img_brief = img_brief;
	}	



}
