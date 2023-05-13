package com.javatechie.reactive.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.reactive.dto.ProductDto;
import com.javatechie.reactive.service.ProductService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@GetMapping
	public Flux<ProductDto> getProducts() {
		return productService.getProducts();
	}

	@GetMapping("/{id}")
	public Mono<ProductDto> getProduct(@PathVariable String id) {
		return productService.getProduct(id);
	}

	@GetMapping("/range")
	public Flux<ProductDto> getProductBetweenRange(@RequestParam("min") double min, @RequestParam("max") double max) {
		return productService.getProductInRange(min, max);
	}

	@GetMapping("/name")
	public Flux<ProductDto> getProductByName(@RequestParam("name") String name) {
		return productService.getProductByName(name);
	}

	@PostMapping
	public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> product) {
		return productService.saveProduct(product);
	}

	@PutMapping("/{id}")
	public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> product, @PathVariable String id) {
		return productService.updateProduct(product, id);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteProduct(@PathVariable String id) {
		return productService.deleteProduct(id);
	}
}
