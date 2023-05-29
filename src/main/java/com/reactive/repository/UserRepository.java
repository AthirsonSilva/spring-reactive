package com.reactive.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.reactive.dto.UserDto;
import com.reactive.entity.User;

import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
	@Query("{ name: ?0 }")
	Flux<UserDto> findByUsername(String name);
}
