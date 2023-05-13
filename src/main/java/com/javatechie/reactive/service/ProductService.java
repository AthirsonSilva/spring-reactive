package com.javatechie.reactive.service;

import com.javatechie.reactive.dto.ProductDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

	Flux<ProductDto> getProducts();

	Mono<ProductDto> getProduct(String id);

	Flux<ProductDto> getProductInRange(double min, double max);

	Mono<ProductDto> saveProduct(Mono<ProductDto> product);

	Mono<ProductDto> updateProduct(Mono<ProductDto> product, String id);

	Mono<Void> deleteProduct(String id);

	Flux<ProductDto> getProductByName(String name);
}