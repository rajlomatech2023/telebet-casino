package com.telebet.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelebetGatewayConfig {

	@Autowired
	private AuthenticationFilter filter;
	
	public RouteLocator routes(RouteLocatorBuilder builder) {
		
		return builder.routes()
				.route("user-service", r -> r.path("/users/**")
				     .filters(f -> f.filter(null))
				     .uri("lb://user-service"))
				.route("auth-service", r -> r.path("/auth/**")
					     .filters(f -> f.filter(null))
					     .uri("lb://auth-service"))
				.build();
		
	}
}
