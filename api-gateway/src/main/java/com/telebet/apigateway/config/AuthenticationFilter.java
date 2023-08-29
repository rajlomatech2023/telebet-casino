package com.telebet.apigateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.telebet.apigateway.logging.LoggingFilter;
import com.telebet.apigateway.service.JwtUtil;
import com.telebet.apigateway.service.RouteValidator;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {
	
	@Autowired
	private RouteValidator routeValidator;
	
	@Autowired
	private JwtUtil jwtUtils;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		ServerHttpRequest request = exchange.getRequest();
		
		Logger logger = LoggerFactory.getLogger(LoggingFilter.class); 
		logger.info("Path of the request is -> {}", exchange.getRequest().getPath());
		
		if(routeValidator.isSecured.test(request)) {
			if(this.authMissing(request)) 
				return onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
			final String token = this.getAuthHeader(request);
			
			if (jwtUtils.isInvalid(token))
	            return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);

	        this.populateRequestWithHeaders(exchange, token);
		}
		
		return chain.filter(exchange);
	}
	
	 private String getAuthHeader(ServerHttpRequest request) {
	        return request.getHeaders().getOrEmpty("Authorization").get(0);
	 }
	
	private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
	}

	private boolean authMissing(ServerHttpRequest request) {
		return !request.getHeaders().containsKey("Äuthorization");
	}
	
	private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtils.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("userId", String.valueOf(claims.get("userId")))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }

}
