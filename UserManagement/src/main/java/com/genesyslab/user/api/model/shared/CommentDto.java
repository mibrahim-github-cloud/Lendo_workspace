package com.genesyslab.user.api.model.shared;

import java.io.Serializable;

public class CommentDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5984049982704237536L;
	private long Id;
	private String postId;
	private String name;
	private String email;
	private String body;
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
