package com.telebet.telebetconfigservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class TelebetConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelebetConfigServiceApplication.class, args);
	}

}
