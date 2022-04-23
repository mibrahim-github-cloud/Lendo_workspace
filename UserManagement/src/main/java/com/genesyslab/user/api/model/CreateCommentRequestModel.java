package com.genesyslab.user.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateCommentRequestModel {

	@NotNull(message = "Post ID should not be empty")
	@Size(min = 1, message = "PostId should be greater than 1 character")
	private String postId;
		
	@NotNull(message = "Name should not be empty")
	private String name;
	
	@NotNull(message = "Email should not be empty")
	@Email
	private String email;
	
	@NotNull(message = "Comment's content should not be empty")
	private String body;

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
