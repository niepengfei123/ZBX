package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 用户表
 * @author lijin
 *
 */
public class User extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	user_id;		//用户ID
	
	private String 	user_nickname;				//用户昵称
	
	private String 	user_email;				//用户邮箱
	
	private String 	user_phone;				//用户电话
	
	private String 	user_password;				//用户密码
	
	private String 	invitation_code;				//邀请码
	
	private String 	user_role;				//用户角色
	
	private String 	user_token;				//用户token
	
	private Integer user_type;				//1表示本系统账户，2表示第三方账户
	
	private Integer client_type;				//客户端类型1为android，2为ios
	
	private String client_imei;				//客户端类型1为android，2为ios
	
	private String captcha_code; 	//验证码

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getInvitation_code() {
		return invitation_code;
	}

	public void setInvitation_code(String invitation_code) {
		this.invitation_code = invitation_code;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public String getUser_token() {
		return user_token;
	}

	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	public String getCaptcha_code() {
		return captcha_code;
	}

	public void setCaptcha_code(String captcha_code) {
		this.captcha_code = captcha_code;
	}

	public Integer getClient_type() {
		return client_type;
	}

	public void setClient_type(Integer client_type) {
		this.client_type = client_type;
	}

	public String getClient_imei() {
		return client_imei;
	}

	public void setClient_imei(String client_imei) {
		this.client_imei = client_imei;
	}

	
}
