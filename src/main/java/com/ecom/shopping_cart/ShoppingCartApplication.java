package com.ecom.shopping_cart;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@OpenAPIDefinition
@EnableJpaRepositories
public class ShoppingCartApplication{

    public static void main(String[] args) {
        SpringApplication.run ( ShoppingCartApplication.class, args );
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate ();
    }

}
