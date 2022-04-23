package com.genesyslab.user.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genesyslab.user.api.data.PostRepository;
import com.genesyslab.user.api.data.PostsEntity;
import com.genesyslab.user.api.model.shared.PostDto;

@Service
public class PostServiceImpl implements PostService{

	PostRepository postRepository;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}
	
	@Override
	public PostDto createPost(PostDto postDto) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		PostsEntity postEntity = modelMapper.map(postDto, PostsEntity.class);		
		postRepository.save(postEntity);
		
		PostDto createdPost = modelMapper.map(postEntity, PostDto.class);
		return createdPost;
	}

	@Override
	public List<PostDto> getPosts() {

		List<PostsEntity> postsEntities = new ArrayList<>();
		postRepository.findAll().forEach(postsEntities::add);;
		ModelMapper modelMapper = new ModelMapper();
		List<PostDto> postDtos = postsEntities.stream().map(postEntity->modelMapper.map(postEntity, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getUserPosts(String userId) {
		List<PostsEntity> postsEntities = new ArrayList<>();
		postRepository.findByUserId(userId).forEach(postsEntities::add);
		ModelMapper modelMapper = new ModelMapper();
		List<PostDto> postDtos = postsEntities.stream().map(postEntity->modelMapper.map(postEntity, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
		
	}

}
