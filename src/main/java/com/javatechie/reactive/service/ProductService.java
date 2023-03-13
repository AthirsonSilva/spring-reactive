package com.javatechie.reactive.service;

import com.javatechie.reactive.dto.ProductDto;
import com.javatechie.reactive.repository.ProductRepository;
import com.javatechie.reactive.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;


    public Flux<ProductDto> getProducts() {
        return repository
                .findAll()
                .map(ApplicationUtils::convertEntityToDto);
    }

    public Mono<ProductDto> getProduct(String id) {
        return repository
                .findById(id)
                .map(ApplicationUtils::convertEntityToDto);
    }

    public Flux<ProductDto> getProductInRange(double min, double max) {
        return repository.findByPriceBetween(Range.closed(min, max));
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
}
