package com.reactive.service;

import java.time.LocalDateTime;

import com.reactive.dto.PostDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

	Flux<PostDto> findAll();

	Mono<PostDto> findOne(String id);

	Flux<PostDto> getInRange(LocalDateTime min, LocalDateTime max);

	Mono<PostDto> saveOne(Mono<PostDto> post);

	Mono<PostDto> updateOne(Mono<PostDto> post, String id);

	Mono<Void> deleteOne(String id);

	Flux<PostDto> findByName(String name);
}