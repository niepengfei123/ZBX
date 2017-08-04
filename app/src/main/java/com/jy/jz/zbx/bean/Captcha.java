package com.jy.jz.zbx.bean;

public class Captcha extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String captcha_id;//验证码ID
	
	private String captcha_code;//验证码
		
	private Long 	regist_code_time;		//注册码有效时间
	
	private String phone;//接收验证码手机号
	
	private Integer type;//验证码类型

	public String getCaptcha_id() {
		return captcha_id;
	}

	public void setCaptcha_id(String captcha_id) {
		this.captcha_id = captcha_id;
	}

	public String getCaptcha_code() {
		return captcha_code;
	}

	public void setCaptcha_code(String captcha_code) {
		this.captcha_code = captcha_code;
	}

	public Long getRegist_code_time() {
		return regist_code_time;
	}

	public void setRegist_code_time(Long regist_code_time) {
		this.regist_code_time = regist_code_time;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	
}
