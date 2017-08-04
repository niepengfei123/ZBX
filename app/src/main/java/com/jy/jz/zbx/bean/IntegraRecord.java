package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 积分记录表
 * @author lijin
 *
 */
public class IntegraRecord extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	integra_record_id;		//积分记录ID
	
	private String 	user_id;				//用户ID
	
	private Integer integra_record_num;		//积分数量
	
	private String 	integra_record_content;	//积分产生原因

	public String getIntegra_record_id() {
		return integra_record_id;
	}

	public void setIntegra_record_id(String integra_record_id) {
		this.integra_record_id = integra_record_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Integer getIntegra_record_num() {
		return integra_record_num;
	}

	public void setIntegra_record_num(Integer integra_record_num) {
		this.integra_record_num = integra_record_num;
	}

	public String getIntegra_record_content() {
		return integra_record_content;
	}

	public void setIntegra_record_content(String integra_record_content) {
		this.integra_record_content = integra_record_content;
	}


}
