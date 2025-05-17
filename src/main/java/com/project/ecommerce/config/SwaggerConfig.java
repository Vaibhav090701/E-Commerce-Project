package com.project.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI customConfig() {
		return new OpenAPI()
				.info(new Info()
						.title("E-Commerce Product API")
						.description("Search and Product Details API for E-commerce Platform")
						.version("1.0.0"));
		
	}

}
