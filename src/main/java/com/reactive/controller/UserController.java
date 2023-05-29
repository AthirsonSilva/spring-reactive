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

import com.reactive.dto.UserDto;
import com.reactive.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping
	public Flux<UserDto> findAll() {
		return userService.findAll();
	}

	@GetMapping("/{id}")
	public Mono<?> findOne(@RequestParam String id) {
		return userService.findOne(id);
	}

	@PostMapping
	public Mono<UserDto> create(@RequestBody Mono<UserDto> entity) {
		return userService.saveOne(entity);
	}

	@PutMapping("/{id}")
	public Mono<?> updateOne(@PathVariable String id, @RequestBody Mono<UserDto> entity) {
		return userService.updateOne(entity, id);
	}

	@DeleteMapping("/{id}")
	public Mono<?> deleteOne(@RequestParam String param) {
		return userService.deleteOne(param);
	}
}
