package com.jy.jz.zbx.bean;

import java.io.Serializable;
import java.util.UUID;

/**
 * 收藏实体
 * @author lijin
 *
 */
public class Collection extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5502848310262623482L;
	private String collection_id;	//收藏ID
	private String course_id;		//课程ID
	
	public String getCollection_id() {
		if ("".equals(collection_id) || null == collection_id)
			collection_id = UUID.randomUUID().toString();
		System.out.println("生成的userId=" + collection_id);
		return collection_id;
	}
	public void setCollection_id(String collection_id) {
		this.collection_id = collection_id;
	}
	
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

}
