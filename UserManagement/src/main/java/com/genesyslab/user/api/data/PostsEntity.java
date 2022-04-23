package com.genesyslab.user.api.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="posts")
public class PostsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1666891334207729063L;
	
	@Id
	@GeneratedValue
	private long postId;
	
	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String body;

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
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
