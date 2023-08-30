package com.telebet.apigateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelebetGatewayConfig {

	Logger logger = LoggerFactory.getLogger(TelebetGatewayConfig.class);
	
	@Autowired
	private AuthenticationFilter authFilter;
	
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		
		logger.info("in telebetGatewayConfig creating routes-> ");
		
		return builder.routes()
				.route("user-service", r -> r.path("/users/**") 
						.filters(f -> f.filter(authFilter))
						.uri("lb://user-service"))
				.route("auth-service", r -> r.path("/auth/**")
						.filters(f -> f.filter(authFilter))
						.uri("lb://telebet-auth-service"))
				.route(r -> r.path("/user-service/**")
				     .uri("lb://user-service"))
				.route(r -> r.path("/telebet-auth-service/**")
					     .uri("lb://telebet-auth-service"))
				.route("login-service", r -> r.path("/login/**") 
						.filters(f -> f.filter(authFilter))
						.uri("http://192.168.0.39:8091/api/auth/signin"))
				.build();
		
	}
}
