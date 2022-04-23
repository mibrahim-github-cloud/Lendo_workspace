package com.genesyslab.user.api.model.shared;

import java.io.Serializable;

public class PostDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4563770798255458728L;
	
	private String userId;
	private String title;
	private String body;
	private String postId;
	
	
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	

}
