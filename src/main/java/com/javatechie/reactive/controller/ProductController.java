package com.javatechie.reactive.controller;

import com.javatechie.reactive.dto.ProductDto;
import com.javatechie.reactive.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public Flux<ProductDto> getProducts() {
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDto> getProduct(@PathVariable String id) {
        return service.getProduct(id);
    }

    @GetMapping("/range")
    public Flux<ProductDto> getProductBetweenRange(@RequestParam("min") double min, @RequestParam("max") double max) {
        return service.getProductInRange(min, max);
    }

    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> product) {
        return service.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> product, @PathVariable String id) {
        return service.updateProduct(product, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return service.deleteProduct(id);
    }
}
