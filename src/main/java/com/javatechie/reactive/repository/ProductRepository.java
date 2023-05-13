package com.javatechie.reactive.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.javatechie.reactive.dto.ProductDto;
import com.javatechie.reactive.entity.Product;

import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
	@Query("{'price':{$gt: ?0, $lt: ?1}}")
	Flux<ProductDto> findByPriceBetween(Double min, Double max);

	@Query("{'name': {$regex: ?0, $options: 'i'}}")
	Flux<ProductDto> findByName(String name);
}
