package com.reactive.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.reactive.dto.PostDto;
import com.reactive.entity.Post;

import reactor.core.publisher.Flux;

public interface PostRepository extends ReactiveMongoRepository<Post, String> {
	@Query("{'created_at':{$gt: ?0, $lt: ?1}}")
	Flux<PostDto> findByCreatedDateBetween(LocalDateTime min, LocalDateTime max);

	@Query("{'name': {$regex: ?0, $options: 'i'}}")
	Flux<PostDto> findByName(String name);
}
