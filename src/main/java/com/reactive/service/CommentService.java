package com.reactive.service;

import com.reactive.dto.CommentDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {

	Flux<CommentDto> findAll();

	Mono<?> findOne(String id);

	Flux<CommentDto> findByPost(String postId);

	Mono<CommentDto> saveOne(Mono<CommentDto> post);

	Mono<?> updateOne(Mono<CommentDto> post, String id);

	Mono<?> deleteOne(String id);

	Flux<CommentDto> findByName(String name);
}
