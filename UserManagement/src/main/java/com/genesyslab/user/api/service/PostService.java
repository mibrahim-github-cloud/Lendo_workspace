package com.genesyslab.user.api.service;

import java.util.List;

import com.genesyslab.user.api.model.shared.PostDto;


public interface PostService {

	PostDto createPost(PostDto postDto);
	
	List<PostDto> getPosts();
	
	List<PostDto> getUserPosts(String userId);
	
	
}
