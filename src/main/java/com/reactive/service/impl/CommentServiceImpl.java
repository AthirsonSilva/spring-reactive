package com.reactive.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reactive.dto.CommentDto;
import com.reactive.entity.Comment;
import com.reactive.exceptions.ResourceNotFoundException;
import com.reactive.repository.CommentRepository;
import com.reactive.service.CommentService;
import com.reactive.utils.ApplicationUtils;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentRepository commentRepository;

	@Override
	public Mono<?> deleteOne(String id) {
		return commentRepository.deleteById(id);
	}

	@Override
	public Flux<CommentDto> findAll() {
		return commentRepository.findAll()
				.map(ApplicationUtils::convertCommentEntityToDto);
	}

	@Override
	public Flux<CommentDto> findByName(String name) {
		return commentRepository.findByName(name)
				.map(ApplicationUtils::convertCommentEntityToDto);
	}

	@Override
	public Flux<CommentDto> findByPost(String postId) {
		return commentRepository.findByPost(postId)
				.map(ApplicationUtils::convertCommentEntityToDto);
	}

	@Override
	public Mono<?> findOne(String id) {
		Mono<Comment> foundComment = commentRepository.findById(id);

		if (foundComment == null)
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "There is no such comment with given ID: " + id, null);

		return foundComment
				.map(ApplicationUtils::convertCommentEntityToDto);
	}

	@Override
	public Mono<CommentDto> saveOne(Mono<CommentDto> request) {
		return request.map(ApplicationUtils::convertCommentDtoToEntity)
				.flatMap(commentRepository::insert)
				.map(ApplicationUtils::convertCommentEntityToDto);
	}

	@Override
	public Mono<?> updateOne(Mono<CommentDto> comment, String id) {
		Mono<Comment> foundComment = commentRepository.findById(id);

		if (foundComment == null)
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "There is no such comment with given ID: " + id, null);

		Mono<Comment> updatedComment = foundComment
				.flatMap(c -> comment.map(ApplicationUtils::convertCommentDtoToEntity))
				.doOnNext(e -> e.setId(id));

		Mono<Comment> savedComment = updatedComment.flatMap(commentRepository::save);

		return savedComment.map(ApplicationUtils::convertCommentEntityToDto);
	}

}
