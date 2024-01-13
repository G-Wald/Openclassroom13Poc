package com.poc.yourcaryourwarback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableWebSocketMessageBroker
public class YourcaryourwarbackApplication {
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
		String[] allowDomains = new String[3];
		allowDomains[0] = "http://localhost:4200";
		allowDomains[1] = "http://localhost:3000";
		allowDomains[2] = "http://localhost:8080";

		System.out.println("CORS configuration....");
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(allowDomains);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(YourcaryourwarbackApplication.class, args);
	}

}
