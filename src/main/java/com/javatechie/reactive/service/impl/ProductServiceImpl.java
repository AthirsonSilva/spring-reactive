package com.javatechie.reactive.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.javatechie.reactive.dto.ProductDto;
import com.javatechie.reactive.exceptions.ProductNotFoundException;
import com.javatechie.reactive.repository.ProductRepository;
import com.javatechie.reactive.service.ProductService;
import com.javatechie.reactive.utils.ApplicationUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {
	private final ProductRepository repository;

	public Flux<ProductDto> getProducts() {
		return repository
				.findAll()
				.map(ApplicationUtils::convertEntityToDto);
	}

	public Mono<ProductDto> getProduct(String id) {
		Mono<ProductDto> findById = repository
				.findById(id)
				.map(ApplicationUtils::convertEntityToDto);

		if (findById == null) {
			log.info(id + " not found");
			throw new ProductNotFoundException(HttpStatus.NOT_FOUND, "Product not found", null);
		}

		return findById;
	}

	public Flux<ProductDto> getProductInRange(double min, double max) {
		return repository.findByPriceBetween(min, max);
	}

	public Mono<ProductDto> saveProduct(Mono<ProductDto> product) {
		return product.map(ApplicationUtils::convertDtoToEntity)
				.flatMap(repository::insert)
				.map(ApplicationUtils::convertEntityToDto);
	}

	public Mono<ProductDto> updateProduct(Mono<ProductDto> product, String id) {
		return repository.findById(id)
				.flatMap(p -> product.map(ApplicationUtils::convertDtoToEntity)
						.doOnNext(e -> e.setId(id)))
				.flatMap(repository::save)
				.map(ApplicationUtils::convertEntityToDto);

	}

	public Mono<Void> deleteProduct(String id) {
		return repository.deleteById(id);
	}

	public Flux<ProductDto> getProductByName(String name) {
		return repository.findByName(name);
	}
}
