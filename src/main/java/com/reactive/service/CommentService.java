package com.reactive.service;

import com.reactive.dto.CommentDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {

	Flux<CommentDto> findAll();

	Mono<CommentDto> findOne(String id);

	Flux<CommentDto> findByPost(String postId);

	Mono<CommentDto> saveOne(Mono<CommentDto> post);

	Mono<CommentDto> updateOne(Mono<CommentDto> post, String id);

	Mono<Void> deleteOne(String id);

	Flux<CommentDto> findByName(String name);
}
