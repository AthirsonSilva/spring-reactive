package com.reactive.service;

import com.reactive.dto.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
	Flux<UserDto> findAll();

	Mono<?> findOne(String id);

	Mono<UserDto> saveOne(Mono<UserDto> request);

	Mono<?> updateOne(Mono<UserDto> request, String id);

	Mono<?> deleteOne(String id);

	Flux<UserDto> findByName(String name);
}
