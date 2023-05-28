package com.reactive.service.impl;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reactive.dto.PostDto;
import com.reactive.exceptions.PostNotFoundException;
import com.reactive.repository.PostRepository;
import com.reactive.service.PostService;
import com.reactive.utils.ApplicationUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;

	public Flux<PostDto> findAll() {
		return postRepository
				.findAll()
				.map(ApplicationUtils::convertEntityToDto);
	}

	public Mono<PostDto> findOne(String id) {
		Mono<PostDto> findById = postRepository
				.findById(id)
				.map(ApplicationUtils::convertEntityToDto);

		if (findById == null) {
			log.info(id + " not found");
			throw new PostNotFoundException(HttpStatus.NOT_FOUND, "Post not found", null);
		}

		return findById;
	}

	public Flux<PostDto> getInRange(LocalDateTime min, LocalDateTime max) {
		return postRepository.findByCreatedDateBetween(min, max);
	}

	public Mono<PostDto> saveOne(Mono<PostDto> request) {
		// Post post = request

		return request.map(ApplicationUtils::convertDtoToEntity)
				.flatMap(postRepository::insert)
				.map(ApplicationUtils::convertEntityToDto);
	}

	public Mono<PostDto> updateOne(Mono<PostDto> post, String id) {
		return postRepository.findById(id)
				.flatMap(p -> post.map(ApplicationUtils::convertDtoToEntity)
						.doOnNext(e -> e.setId(id)))
				.flatMap(postRepository::save)
				.map(ApplicationUtils::convertEntityToDto);

	}

	public Mono<Void> deleteOne(String id) {
		return postRepository.deleteById(id);
	}

	public Flux<PostDto> findByName(String name) {
		return postRepository.findByName(name);
	}
}
