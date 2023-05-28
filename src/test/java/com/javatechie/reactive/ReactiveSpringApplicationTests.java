package com.javatechie.reactive;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

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
		Mono<PostDto> productDtoMono = Mono.just(new PostDto("102", "mobile", 1, 10000));
		when(service.saveOne(productDtoMono)).thenReturn(productDtoMono);

		webTestClient.post().uri("/products")
				.body(Mono.just(productDtoMono), PostDto.class)
				.exchange()
				.expectStatus().isOk();// 200

	}

	@Test
	public void getProductsTest() {
		Flux<PostDto> productDtoFlux = Flux.just(new PostDto("102", "mobile", 1, 10000),
				new PostDto("103", "TV", 1, 50000));
		when(service.findAll()).thenReturn(productDtoFlux);

		Flux<PostDto> responseBody = webTestClient.get().uri("/products")
				.exchange()
				.expectStatus().isOk()
				.returnResult(PostDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new PostDto("102", "mobile", 1, 10000))
				.expectNext(new PostDto("103", "TV", 1, 50000))
				.verifyComplete();

	}

	@Test
	public void getProductTest() {
		Mono<PostDto> productDtoMono = Mono.just(new PostDto("102", "mobile", 1, 10000));
		when(service.findOne(any())).thenReturn(productDtoMono);

		Flux<PostDto> responseBody = webTestClient.get().uri("/products/102")
				.exchange()
				.expectStatus().isOk()
				.returnResult(PostDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p -> p.getName().equals("mobile"))
				.verifyComplete();
	}

	@Test
	public void updateProductTest() {
		Mono<PostDto> productDtoMono = Mono.just(new PostDto("102", "mobile", 1, 10000));
		when(service.updateOne(productDtoMono, "102")).thenReturn(productDtoMono);

		webTestClient.put().uri("/products/update/102")
				.body(Mono.just(productDtoMono), PostDto.class)
				.exchange()
				.expectStatus().isOk();// 200
	}

	@Test
	public void deleteProductTest() {
		given(service.deleteOne(any())).willReturn(Mono.empty());
		webTestClient.delete().uri("/products/delete/102")
				.exchange()
				.expectStatus().isOk();// 200
	}

}
