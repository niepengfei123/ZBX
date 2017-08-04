package com.jy.jz.zbx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 课程表
 * @author lijin
 *
 */
public class Course extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	course_id;			//课程ID
	private String 	user_id;			//用户ID
	private String 	type_id;			//类型ID
	private String 	course_name;		//课程名称
	private Integer course_pay;			//1表示付费观看，2表示限时免费，3表示免费
	private Integer course_study_num;	//学习人数
	private String 	course_bdate;		//开课日期
	private String 	course_edate;		//结束日期
	private String 	course_time;		//开讲时间
	private Integer course_part_num;	//章节总数
	private Integer course_property;	//课程性质，1=众筹课，2=直播客，3=线下课，4=定价课，5=免费课
	private String 	course_brief;		//课程简介
	private String 	course_keyword;		//课程关键字
	private List<Img> imgs;				//课程的图片
	
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	
	public Integer getCourse_pay() {
		return course_pay;
	}
	public void setCourse_pay(Integer course_pay) {
		this.course_pay = course_pay;
	}
	
	public Integer getCourse_study_num() {
		return course_study_num;
	}
	public void setCourse_study_num(Integer course_study_num) {
		this.course_study_num = course_study_num;
	}
	
	public String getCourse_bdate() {
		return course_bdate;
	}
	public void setCourse_bdate(String course_bdate) {
		this.course_bdate = course_bdate;
	}
	
	public String getCourse_edate() {
		return course_edate;
	}
	public void setCourse_edate(String course_edate) {
		this.course_edate = course_edate;
	}
	
	public String getCourse_time() {
		return course_time;
	}
	public void setCourse_time(String course_time) {
		this.course_time = course_time;
	}
	
	public Integer getCourse_part_num() {
		return course_part_num;
	}
	public void setCourse_part_num(Integer course_part_num) {
		this.course_part_num = course_part_num;
	}
	
	public Integer getCourse_property() {
		return course_property;
	}
	public void setCourse_property(Integer course_property) {
		this.course_property = course_property;
	}
	
	public String getCourse_brief() {
		return course_brief;
	}
	public void setCourse_brief(String course_brief) {
		this.course_brief = course_brief;
	}
	
	public String getCourse_keyword() {
		return course_keyword;
	}
	public void setCourse_keyword(String course_keyword) {
		this.course_keyword = course_keyword;
	}

	public List<Img> getImgs() {
		return imgs;
	}
	public void setImgs(List<Img> imgs) {
		this.imgs = imgs;
	}

}
