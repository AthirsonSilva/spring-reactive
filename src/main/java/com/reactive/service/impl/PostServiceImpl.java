package com.reactive.service.impl;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reactive.dto.PostDto;
import com.reactive.entity.Post;
import com.reactive.exceptions.ResourceNotFoundException;
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
				.map(ApplicationUtils::convertPostEntityToDto);
	}

	public Mono<PostDto> findOne(String id) {
		Mono<PostDto> findById = postRepository
				.findById(id)
				.map(ApplicationUtils::convertPostEntityToDto);

		if (findById == null)
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Post not found", null);

		return findById;
	}

	public Flux<PostDto> getInRange(LocalDateTime min, LocalDateTime max) {
		return postRepository.findByCreatedDateBetween(min, max);
	}

	public Mono<PostDto> saveOne(Mono<PostDto> request) {
		Mono<Post> postEntity = request.map(ApplicationUtils::convertPostDtoToEntity);

		Mono<Post> savedPost = postEntity.flatMap(postRepository::insert);

		return savedPost.map(ApplicationUtils::convertPostEntityToDto);
	}

	public Mono<PostDto> updateOne(Mono<PostDto> request, String id) {
		Mono<Post> foundPost = postRepository.findById(id);

		if (foundPost == null)
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "There is no such post with given id: " + id, null);

		Mono<Post> updatedPost = foundPost
				.flatMap(p -> request.map(ApplicationUtils::convertPostDtoToEntity)
						.doOnNext(e -> e.setId(id)));

		Mono<Post> savedPost = updatedPost.flatMap(postRepository::save);

		return savedPost.map(ApplicationUtils::convertPostEntityToDto);
	}

	public Mono<Void> deleteOne(String id) {
		return postRepository.deleteById(id);
	}

	public Flux<PostDto> findByName(String name) {
		return postRepository.findByName(name);
	}
}
