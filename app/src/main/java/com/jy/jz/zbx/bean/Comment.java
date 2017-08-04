package com.jy.jz.zbx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 评论实体
 * @author lijin
 *
 */
public class Comment extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String 	comment_id;		//评论ID
	private String 	user_id;		//评论人ID
	private String 	use_user_id;	//被评论人ID
	private String 	course_id;		//被评论课程ID
	private String 	comment_content;//评论内容
	private List<Img> imgs;			//评论的图片
	
	public String getComment_id() {
		return comment_id;
	}
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getUse_user_id() {
		return use_user_id;
	}
	public void setUse_user_id(String use_user_id) {
		this.use_user_id = use_user_id;
	}
	
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	
	public List<Img> getImgs() {
		return imgs;
	}
	public void setImgs(List<Img> imgs) {
		this.imgs = imgs;
	}

}
