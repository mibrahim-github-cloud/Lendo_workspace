package com.genesyslab.user.api.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="comments")
public class CommentsEntity implements Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1485668003715317776L;

	@Id
	@GeneratedValue
	private long Id;
	
	@Column(nullable = false)
	private String postId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
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
