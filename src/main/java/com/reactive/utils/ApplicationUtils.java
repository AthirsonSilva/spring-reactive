package com.reactive.utils;

import org.springframework.beans.BeanUtils;

import com.reactive.dto.PostDto;
import com.reactive.entity.Post;

public class ApplicationUtils {
	public static PostDto convertEntityToDto(Post product) {
		PostDto productDto = new PostDto();

		BeanUtils.copyProperties(product, productDto);

		return productDto;
	}

	public static Post convertDtoToEntity(PostDto productDto) {
		Post product = new Post();

		BeanUtils.copyProperties(productDto, product);

		return product;
	}
}
