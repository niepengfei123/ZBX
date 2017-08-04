package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 用户详情表
 * @author lijin
 *
 */
public class UserDetail extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	user_detail_id;		//用户详情ID
	
	private String 	user_id;				//用户ID
	
	private String 	user_name;				//用户真实姓名
	
	private String 	user_avatar;				//用户头像
	
	private Integer user_gender;				//1表示男，2表示女，3表示保密
	
	private String 	user_birthday;				//用户出生日期
	
	private String 	user_brief;				//用户简介
	
	private String 	user_careers;				//用户职业
	
	private Integer user_integral;				//用户总积分
	
	private Double user_money;				//用户金币

	public String getUser_detail_id() {
		return user_detail_id;
	}

	public void setUser_detail_id(String user_detail_id) {
		this.user_detail_id = user_detail_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_avatar() {
		return user_avatar;
	}

	public void setUser_avatar(String user_avatar) {
		this.user_avatar = user_avatar;
	}

	public Integer getUser_gender() {
		return user_gender;
	}

	public void setUser_gender(Integer user_gender) {
		this.user_gender = user_gender;
	}

	public String getUser_birthday() {
		return user_birthday;
	}

	public void setUser_birthday(String user_birthday) {
		this.user_birthday = user_birthday;
	}

	public String getUser_brief() {
		return user_brief;
	}

	public void setUser_brief(String user_brief) {
		this.user_brief = user_brief;
	}

	public String getUser_careers() {
		return user_careers;
	}

	public void setUser_careers(String user_careers) {
		this.user_careers = user_careers;
	}

	public Integer getUser_integral() {
		return user_integral;
	}

	public void setUser_integral(Integer user_integral) {
		this.user_integral = user_integral;
	}

	public Double getUser_money() {
		return user_money;
	}

	public void setUser_money(Double user_money) {
		this.user_money = user_money;
	}

	

}
