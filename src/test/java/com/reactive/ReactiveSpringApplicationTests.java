package com.reactive;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reactive.controller.PostController;
import com.reactive.dto.PostDto;
import com.reactive.service.PostService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest(PostController.class)
class ReactiveSpringApplicationTests {
	@Autowired
	private WebTestClient webTestClient;
	@MockBean
	private PostService service;

	@Test
	public void addProductTest() {
		Mono<PostDto> postDtoMono = Mono.just(generatedPostDto());
		when(service.saveOne(postDtoMono)).thenReturn(postDtoMono);

		webTestClient.post().uri("/posts")
				.body(Mono.just(postDtoMono), PostDto.class)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	public void getProductsTest() {
		Flux<PostDto> postDtoFlux = Flux.just(generatedPostDto(),
				generatedPostDto());
		when(service.findAll()).thenReturn(postDtoFlux);

		Flux<PostDto> responseBody = webTestClient.get().uri("/posts")
				.exchange()
				.expectStatus().isOk()
				.returnResult(PostDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(generatedPostDto())
				.expectNext(generatedPostDto())
				.verifyComplete();

	}

	@Test
	public void getProductTest() {
		Mono<PostDto> postDtoMono = Mono.just(generatedPostDto());
		when(service.findOne(any())).thenReturn(postDtoMono);

		Flux<PostDto> responseBody = webTestClient.get().uri("/posts/6473d67fc74ce276e442c4bd")
				.exchange()
				.expectStatus().isOk()
				.returnResult(PostDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p -> p.getTitle().equals("mobile"))
				.verifyComplete();
	}

	@Test
	public void updateProductTest() {
		Mono<PostDto> postDtoMono = Mono.just(generatedPostDto());
		when(service.updateOne(postDtoMono, "6473d67fc74ce276e442c4bd")).thenReturn(postDtoMono);

		webTestClient.put().uri("/posts/update/6473d67fc74ce276e442c4bd")
				.body(Mono.just(postDtoMono), PostDto.class)
				.exchange()
				.expectStatus().isOk();// 200
	}

	@Test
	public void deleteProductTest() {
		given(service.deleteOne(any())).willReturn(Mono.empty());
		webTestClient.delete().uri("/posts/delete/6473d67fc74ce276e442c4bd")
				.exchange()
				.expectStatus().isOk();// 200
	}

	private PostDto generatedPostDto() {
		return new PostDto("6473d67fc74ce276e442c4bd", "Random title", "Random content", LocalDateTime.now(),
				LocalDateTime.now());
	}
}
