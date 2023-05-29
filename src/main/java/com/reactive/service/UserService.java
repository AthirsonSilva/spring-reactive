package com.reactive.service;

import com.reactive.dto.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
	Flux<UserDto> findAll();

	Mono<UserDto> findOne(String id);

	Mono<UserDto> saveOne(Mono<UserDto> request);

	Mono<UserDto> updateOne(Mono<UserDto> request, String id);

	Mono<Void> deleteOne(String id);

	Flux<UserDto> findByName(String name);
}
