package com.jy.jz.zbx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 众筹表
 * @author lijin
 *
 */
public class Crowdfunding extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	crowdfunding_id;			//众筹ID
	private String 	course_id;					//课程ID
	private Integer crowdfunding_target_num;	//众筹目标人数
	private Integer crowdfunding_have_num;		//众筹已有人数
	private String 	crowdfunding_target_money;	//众筹目标金额
	private String 	crowdfunding_have_money;	//众筹已有金额
	private String 	crowdfunding_bdate;			//众筹开始日期
	private List<Img> imgs;						//课程的图片
	
	public String getCrowdfunding_id() {
		return crowdfunding_id;
	}
	public void setCrowdfunding_id(String crowdfunding_id) {
		this.crowdfunding_id = crowdfunding_id;
	}
	
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	
	public Integer getCrowdfunding_target_num() {
		return crowdfunding_target_num;
	}
	public void setCrowdfunding_target_num(Integer crowdfunding_target_num) {
		this.crowdfunding_target_num = crowdfunding_target_num;
	}
	
	public Integer getCrowdfunding_have_num() {
		return crowdfunding_have_num;
	}
	public void setCrowdfunding_have_num(Integer crowdfunding_have_num) {
		this.crowdfunding_have_num = crowdfunding_have_num;
	}
	
	public String getCrowdfunding_target_money() {
		return crowdfunding_target_money;
	}
	public void setCrowdfunding_target_money(String crowdfunding_target_money) {
		this.crowdfunding_target_money = crowdfunding_target_money;
	}
	
	public String getCrowdfunding_have_money() {
		return crowdfunding_have_money;
	}
	public void setCrowdfunding_have_money(String crowdfunding_have_money) {
		this.crowdfunding_have_money = crowdfunding_have_money;
	}
	
	public String getCrowdfunding_bdate() {
		return crowdfunding_bdate;
	}
	public void setCrowdfunding_bdate(String crowdfunding_bdate) {
		this.crowdfunding_bdate = crowdfunding_bdate;
	}
	
	public List<Img> getImgs() {
		return imgs;
	}
	public void setImgs(List<Img> imgs) {
		this.imgs = imgs;
	}

}
