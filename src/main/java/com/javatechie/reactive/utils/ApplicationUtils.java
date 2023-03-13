package com.javatechie.reactive.utils;

import com.javatechie.reactive.dto.ProductDto;
import com.javatechie.reactive.entity.Product;
import org.springframework.beans.BeanUtils;

public class ApplicationUtils {
    public static ProductDto convertEntityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product convertDtoToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
