package com.reactive.utils;

import org.springframework.beans.BeanUtils;

import com.reactive.dto.CommentDto;
import com.reactive.dto.PostDto;
import com.reactive.entity.Comment;
import com.reactive.entity.Post;

public class ApplicationUtils {
	public static PostDto convertPostEntityToDto(Post post) {
		PostDto productDto = new PostDto();

		BeanUtils.copyProperties(post, productDto);

		return productDto;
	}

	public static Post convertPostDtoToEntity(PostDto postDto) {
		Post product = new Post();

		BeanUtils.copyProperties(postDto, product);

		return product;
	}

	public static CommentDto convertCommentEntityToDto(Comment comment) {
		CommentDto productDto = new CommentDto();

		BeanUtils.copyProperties(comment, productDto);

		return productDto;
	}

	public static Comment convertCommentDtoToEntity(CommentDto comment) {
		Comment product = new Comment();

		BeanUtils.copyProperties(comment, product);

		return product;
	}
}
