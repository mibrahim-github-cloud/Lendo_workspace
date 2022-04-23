package com.genesyslab.user.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genesyslab.user.api.data.CommentRepository;
import com.genesyslab.user.api.data.CommentsEntity;
import com.genesyslab.user.api.model.shared.CommentDto;

@Service
public class CommentServiceImpl implements CommentService{

	CommentRepository commentRepository;
	
	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository) {
		super();
		this.commentRepository = commentRepository;
	}

	@Override
	public CommentDto createComment(CommentDto commentDto) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		CommentsEntity commentEntity = modelMapper.map(commentDto, CommentsEntity.class);		
		commentRepository.save(commentEntity);
		
		CommentDto createdComment = modelMapper.map(commentEntity, CommentDto.class);
		return createdComment;
	}

	@Override
	public List<CommentDto> getComments() {
		List<CommentsEntity> commentsEntities = new ArrayList<>();
		commentRepository.findAll().forEach(commentsEntities::add);;
		ModelMapper modelMapper = new ModelMapper();
		List<CommentDto> commentDtos = commentsEntities.stream().map(commentEntity->modelMapper.map(commentEntity, CommentDto.class))
				.collect(Collectors.toList());
		return commentDtos;
	}

	@Override
	public List<CommentDto> getPostComments(String postId) {
		List<CommentsEntity> commentsEntities = new ArrayList<>();
		commentRepository.findByPostId(postId).forEach(commentsEntities::add);
		ModelMapper modelMapper = new ModelMapper();
		List<CommentDto> commentDtos = commentsEntities.stream().map(commentEntity->modelMapper.map(commentEntity, CommentDto.class))
				.collect(Collectors.toList());
		return commentDtos;
	}

}
