package com.reactive.service.impl;

import org.springframework.stereotype.Service;

import com.reactive.dto.CommentDto;
import com.reactive.entity.Comment;
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
	public Mono<Void> deleteOne(String id) {
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
	public Mono<CommentDto> findOne(String id) {
		return commentRepository.findById(id)
				.map(ApplicationUtils::convertCommentEntityToDto);
	}

	@Override
	public Mono<CommentDto> saveOne(Mono<CommentDto> request) {
		return request.map(ApplicationUtils::convertCommentDtoToEntity)
				.flatMap(commentRepository::insert)
				.map(ApplicationUtils::convertCommentEntityToDto);
	}

	@Override
	public Mono<CommentDto> updateOne(Mono<CommentDto> comment, String id) {
		Mono<Comment> foundComment = commentRepository.findById(id);

		Mono<Comment> updatedComment = foundComment
				.flatMap(c -> comment.map(ApplicationUtils::convertCommentDtoToEntity))
				.doOnNext(e -> e.setId(id));

		Mono<Comment> savedComment = updatedComment.flatMap(commentRepository::save);

		return savedComment.map(ApplicationUtils::convertCommentEntityToDto);
	}

}
