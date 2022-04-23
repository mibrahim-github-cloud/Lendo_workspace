package com.genesyslab.user.api.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository  extends CrudRepository<PostsEntity, Long>{

	List<PostsEntity> findByUserId(String userId);
}
