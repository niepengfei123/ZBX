package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 订单章节表
 * @author lijin
 *
 */
public class OrderPart extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	order_part_id;		//订单章节ID
	
	private String 	order_id;				//订单ID
	
	private String 	part_id;				//章节ID

	public String getOrder_part_id() {
		return order_part_id;
	}

	public void setOrder_part_id(String order_part_id) {
		this.order_part_id = order_part_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getPart_id() {
		return part_id;
	}

	public void setPart_id(String part_id) {
		this.part_id = part_id;
	}



}
