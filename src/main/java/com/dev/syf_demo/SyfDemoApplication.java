package com.dev.syf_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SyfDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyfDemoApplication.class, args);
	}

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
