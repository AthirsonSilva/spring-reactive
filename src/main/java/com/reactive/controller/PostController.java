package com.reactive.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.dto.PostDto;
import com.reactive.service.PostService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	@GetMapping
	public Flux<PostDto> findAll() {
		return postService.findAll();
	}

	@GetMapping("/{id}")
	public Mono<?> findOne(@PathVariable String id) {
		return postService.findOne(id);
	}

	@GetMapping("/range")
	public Flux<PostDto> findInRange(@RequestParam("min") LocalDateTime min,
			@RequestParam("max") LocalDateTime max) {
		return postService.getInRange(min, max);
	}

	@GetMapping("/name")
	public Flux<PostDto> findByName(@RequestParam("name") String name) {
		return postService.findByName(name);
	}

	@PostMapping
	public Mono<PostDto> findAll(@RequestBody Mono<PostDto> post) {
		return postService.saveOne(post);
	}

	@PutMapping("/{id}")
	public Mono<?> updateOne(@RequestBody Mono<PostDto> post, @PathVariable String id) {
		return postService.updateOne(post, id);
	}

	@DeleteMapping("/{id}")
	public Mono<?> deleteOne(@PathVariable String id) {
		return postService.deleteOne(id);
	}
}
