package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * 订单表
 * @author lijin
 *
 */
public class Order extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	order_id;		//订单ID
	
	private String 	user_id;		//下单用户ID
	
	private String 	order_code;		//订单编码
	
	private Double 	order_price;	//订单价格
	
	private Double 	pay_money;	//支付金额

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOrder_code() {
		return order_code;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}

	public Double getOrder_price() {
		return order_price;
	}

	public void setOrder_price(Double order_price) {
		this.order_price = order_price;
	}

	public Double getPay_money() {
		return pay_money;
	}

	public void setPay_money(Double pay_money) {
		this.pay_money = pay_money;
	}

	

}
