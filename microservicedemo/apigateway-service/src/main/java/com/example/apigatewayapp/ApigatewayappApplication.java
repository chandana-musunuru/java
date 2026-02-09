package com.example.apigatewayapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import org.springframework.web.reactive.config.CorsRegistry;

@EnableDiscoveryClient
@SpringBootApplication
public class ApigatewayappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayappApplication.class, args);
	}
	
	@Bean
	public RouteLocator configureRoute(RouteLocatorBuilder builder) {
	       return builder.routes()
	      .route("BOOKAPP", r->r.path("/app1/**").uri("lb://BOOKAPP")) //dynamic routing
	      .route("CUSTAPP", r->r.path("/app2/**").uri("lb://CUSTAPP")) //dynamic routing
	     // .route("APIGATEWAYAPP", r->r.path("/app3/**").uri("lb://APIGATEWAYAPP")) //dynamic routing
	      .build();
	}
	/*
	public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/")
                .allowedOrigins("http://localhost:8081")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }*/
	

}
