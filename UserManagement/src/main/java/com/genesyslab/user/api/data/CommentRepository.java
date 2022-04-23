package com.genesyslab.user.api.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository  extends CrudRepository<CommentsEntity, Long>{

	List<CommentsEntity> findByPostId(String postId);
}
