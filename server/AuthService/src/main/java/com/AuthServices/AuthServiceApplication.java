package com.AuthServices;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        dotenv.entries().forEach(entries ->
            System.setProperty(entries.getKey(), entries.getValue())
        );

        SpringApplication.run(AuthServiceApplication.class, args);
	}



	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
}
