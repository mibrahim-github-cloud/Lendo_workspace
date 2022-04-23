package com.genesyslab.user.api.service;

import java.util.List;

import com.genesyslab.user.api.model.shared.CommentDto;


public interface CommentService {

	CommentDto createComment(CommentDto commentDto);
	
	List<CommentDto> getComments();
	
	List<CommentDto> getPostComments(String postId);
	
	
}
