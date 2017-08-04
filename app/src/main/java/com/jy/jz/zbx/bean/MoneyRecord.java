package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 金币记录表
 * @author lijin
 *
 */
public class MoneyRecord extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	money_record_id;		//金币记录ID
	
	private String 	user_id;				//用户ID
	
	private Double money_record_num;		//金币数量
	
	private String 	money_record_content;	//金币产生原因

	public String getMoney_record_id() {
		return money_record_id;
	}

	public void setMoney_record_id(String money_record_id) {
		this.money_record_id = money_record_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Double getMoney_record_num() {
		return money_record_num;
	}

	public void setMoney_record_num(Double money_record_num) {
		this.money_record_num = money_record_num;
	}

	public String getMoney_record_content() {
		return money_record_content;
	}

	public void setMoney_record_content(String money_record_content) {
		this.money_record_content = money_record_content;
	}

	

}
