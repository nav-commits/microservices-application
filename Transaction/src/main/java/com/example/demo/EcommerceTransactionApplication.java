package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class EcommerceTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceTransactionApplication.class, args);
	}

	@LoadBalanced
	@Bean
	RestTemplate rest() {
		RestTemplate rest = new RestTemplate();
		rest.getInterceptors().add((request, body, execution) -> {
			org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				return execution.execute(request, body);
			}

			if (!(authentication.getCredentials() instanceof AbstractOAuth2Token)) {
				return execution.execute(request, body);
			}

			AbstractOAuth2Token token = (AbstractOAuth2Token) authentication.getCredentials();
		    request.getHeaders().setBearerAuth(token.getTokenValue());
		    return execution.execute(request, body);
		});
		return rest;
	}


}
