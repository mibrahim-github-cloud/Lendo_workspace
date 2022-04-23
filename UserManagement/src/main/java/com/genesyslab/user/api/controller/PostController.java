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

import com.genesyslab.user.api.model.CreatePostRequestModel;
import com.genesyslab.user.api.model.CreatePostResponseModel;
import com.genesyslab.user.api.model.FailureResponseModel;
import com.genesyslab.user.api.model.PostResponseModel;
import com.genesyslab.user.api.model.shared.PostDto;
import com.genesyslab.user.api.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	PostService postService;
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> createUser(@RequestBody @Valid CreatePostRequestModel postModel) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		PostDto postDto = modelMapper.map(postModel, PostDto.class);

		PostDto createdPost;
		try {
			createdPost = postService.createPost(postDto);
			CreatePostResponseModel responseModel = modelMapper.map(createdPost, CreatePostResponseModel.class);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new FailureResponseModel("Not able to create the Post for "+postModel.getUserId()));
		}

	}
	
	@GetMapping
	public ResponseEntity<List<PostResponseModel>> getPosts() {

		List<PostDto> postDtos = postService.getPosts();
		ModelMapper modelMapper = new ModelMapper();

		List<PostResponseModel> returnValue = postDtos.stream()
				.map(postDto -> modelMapper.map(postDto, PostResponseModel.class)).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(returnValue);

	}
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<List<PostResponseModel>> getUserPosts(@PathVariable("userId") String userId) {

		List<PostDto> postDtos = postService.getUserPosts(userId);

		ModelMapper modelMapper = new ModelMapper();

		List<PostResponseModel> returnValue = postDtos.stream()
				.map(postDto -> modelMapper.map(postDto, PostResponseModel.class)).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(returnValue);

	}

}
