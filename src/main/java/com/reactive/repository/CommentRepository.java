package com.reactive.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.reactive.entity.Comment;

import reactor.core.publisher.Flux;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
	@Query("{ postId: ?0 }")
	public Flux<Comment> findByPost(String postId);

	@Query("{ name: {$regex: ?0, $options: 'i'} }")
	Flux<Comment> findByName(String name);
}
