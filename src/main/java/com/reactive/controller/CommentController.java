package com.reactive.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.dto.CommentDto;
import com.reactive.service.CommentService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	@GetMapping()
	public Flux<CommentDto> findAll() {
		return commentService.findAll();
	}

	@GetMapping("/name")
	public Flux<CommentDto> findByName(@RequestParam String name) {
		return commentService.findByName(name);
	}

	@GetMapping("/{id}")
	public Mono<CommentDto> find(@PathVariable String id) {
		return commentService.findOne(id);
	}

	@GetMapping("/{postId}/comments")
	public Flux<CommentDto> findByPost(@PathVariable String postId) {
		return commentService.findByPost(postId);
	}

	@PostMapping()
	public Mono<CommentDto> create(@RequestBody Mono<CommentDto> request) {
		return commentService.saveOne(request);
	}

	@PutMapping("/{id}")
	public Mono<CommentDto> update(@RequestBody Mono<CommentDto> request, @PathVariable String id) {
		return commentService.updateOne(request, id);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> delete(@PathVariable String id) {
		return commentService.deleteOne(id);
	}
}
