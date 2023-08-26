package com.telebet.telebetnamingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TelebetNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelebetNamingServerApplication.class, args);
	}

}
