package com.telebet.apigateway.service;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

@Service
public class RouteValidator {

	
	public static final List<String> openEndPoints = List.of("/auth/register");
	
	public Predicate<ServerHttpRequest> isSecured = request -> openEndPoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
