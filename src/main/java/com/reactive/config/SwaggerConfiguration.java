package com.reactive.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI() {
		List<Server> servers = new ArrayList<Server>();

		servers.add(new Server().url("http://localhost:8080"));

		return new OpenAPI().servers(servers);
	}
}
