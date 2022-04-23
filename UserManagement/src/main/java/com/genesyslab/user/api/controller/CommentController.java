package com.genesyslab.user.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genesyslab.user.api.model.CommentResponseModel;
import com.genesyslab.user.api.model.CreateCommentRequestModel;
import com.genesyslab.user.api.model.CreateCommentResponseModel;
import com.genesyslab.user.api.model.FailureResponseModel;
import com.genesyslab.user.api.model.shared.CommentDto;
import com.genesyslab.user.api.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> createComment(@RequestBody @Valid CreateCommentRequestModel commentModel) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		CommentDto commentDto = modelMapper.map(commentModel, CommentDto.class);

		CommentDto createdComment;
		try {
			createdComment = commentService.createComment(commentDto);
			CreateCommentResponseModel responseModel = modelMapper.map(createdComment, CreateCommentResponseModel.class);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new FailureResponseModel("Not able to create the Comment for "+commentDto.getPostId()));
		}

	}
	
	@GetMapping
	public ResponseEntity<List<CommentResponseModel>> getComments() {

		List<CommentDto> commentDtos = commentService.getComments();
		ModelMapper modelMapper = new ModelMapper();

		List<CommentResponseModel> returnValue = commentDtos.stream()
				.map(commentDto -> modelMapper.map(commentDto, CommentResponseModel.class)).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(returnValue);

	}
	
	@GetMapping(value = "/{postId}")
	public ResponseEntity<List<CommentResponseModel>> getUserPosts(@PathVariable("postId") String postId) {

		List<CommentDto> commentDtos = commentService.getPostComments(postId);

		ModelMapper modelMapper = new ModelMapper();

		List<CommentResponseModel> returnValue = commentDtos.stream()
				.map(commentDto -> modelMapper.map(commentDto, CommentResponseModel.class)).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(returnValue);


	}
}
