package com.reactive.service.impl;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reactive.dto.UserDto;
import com.reactive.entity.User;
import com.reactive.exceptions.ResourceNotFoundException;
import com.reactive.repository.UserRepository;
import com.reactive.service.UserService;
import com.reactive.utils.ApplicationUtils;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public Mono<Void> deleteOne(String id) {
		Mono<User> foundUser = userRepository.findById(id);

		if (foundUser == null)
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "There is no user with given id: " + id, null);

		return userRepository.deleteById(id);
	}

	@Override
	public Flux<UserDto> findAll() {
		return userRepository.findAll()
				.map(ApplicationUtils::convertUserEntityToDto);
	}

	@Override
	public Flux<UserDto> findByName(String name) {
		return userRepository.findByUsername(name);
	}

	@Override
	public Mono<?> findOne(String id) {
		Mono<User> foundUser = userRepository.findById(id);

		if (foundUser == null)
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "There is no user with given id: " + id, null);

		return foundUser
				.map(ApplicationUtils::convertUserEntityToDto);
	}

	@Override
	public Mono<UserDto> saveOne(Mono<UserDto> request) {
		Mono<User> userEntity = request.map(ApplicationUtils::convertUserDtoToEntity);

		userEntity.doOnNext(entity -> {
			entity.setCreatedAt(LocalDateTime.now());
			entity.setUpdatedAt(LocalDateTime.now());
		});

		Mono<User> savedUser = userEntity.flatMap(userRepository::insert);

		return savedUser.map(ApplicationUtils::convertUserEntityToDto);
	}

	@Override
	public Mono<UserDto> updateOne(Mono<UserDto> request, String id) {
		Mono<User> foundUser = userRepository.findById(id);

		if (foundUser == null)
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "There is no user with given id: " + id, null);

		Mono<User> updatedPost = foundUser
				.flatMap(p -> request.map(ApplicationUtils::convertUserDtoToEntity))
				.doOnNext(e -> {
					e.setId(id);
					e.setUpdatedAt(LocalDateTime.now());

					return;
				});

		Mono<User> savedUser = updatedPost.flatMap(userRepository::save);

		return savedUser.map(ApplicationUtils::convertUserEntityToDto);
	}
}
