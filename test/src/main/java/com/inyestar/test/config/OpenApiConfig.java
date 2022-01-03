package com.inyestar.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info()
						.title("TEST REST API")
						.description("Documentation for TEST REST API")
						.contact(new Contact()
									.name("inyestar")
									.url("https://github.com/inyestar/test-api-server")
									.email("yuinye@gmail.com"))
						.version("1.0")
						.license(new License()
									.name("License")));
	}
}
