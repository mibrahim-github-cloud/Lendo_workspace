package com.genesyslab.user.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreatePostRequestModel {

	@NotNull(message = "User ID should not be empty")
	@Size(min = 10, message = "User Id should be greater than 10 character")
	private String userId;
		
	@NotNull(message = "Title should not be empty")
	private String title;
	
	@NotNull(message = "Post's content should not be empty")
	private String body;

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
