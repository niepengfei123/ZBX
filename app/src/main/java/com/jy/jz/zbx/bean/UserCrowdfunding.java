package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 用户众筹表
 * @author lijin
 *
 */
public class UserCrowdfunding extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	user_crowdfunding_id;		//用户众筹ID
	
	private String 	crowdfunding_id;				//众筹ID
	
	private String 	user_id;				//用户ID
	
	private Double 	crowdfunding_money;				//众筹金额

	public String getUser_crowdfunding_id() {
		return user_crowdfunding_id;
	}

	public void setUser_crowdfunding_id(String user_crowdfunding_id) {
		this.user_crowdfunding_id = user_crowdfunding_id;
	}

	public String getCrowdfunding_id() {
		return crowdfunding_id;
	}

	public void setCrowdfunding_id(String crowdfunding_id) {
		this.crowdfunding_id = crowdfunding_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Double getCrowdfunding_money() {
		return crowdfunding_money;
	}

	public void setCrowdfunding_money(Double crowdfunding_money) {
		this.crowdfunding_money = crowdfunding_money;
	}


}
