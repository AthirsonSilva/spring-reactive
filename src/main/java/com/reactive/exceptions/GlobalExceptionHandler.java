package com.reactive.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import reactor.core.publisher.Mono;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler implements WebExceptionHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();

		if (ex instanceof ResponseStatusException) {
			return handleResponseStatusException((ResponseStatusException) ex, exchange);
		}

		// Set the response status code and return an empty response
		response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

		return response.setComplete();
	}

	private Mono<Void> handleResponseStatusException(ResponseStatusException ex, ServerWebExchange exchange) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(ex.getStatus());
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

		// You can customize the response body here if needed
		return response.setComplete();
	}
}
