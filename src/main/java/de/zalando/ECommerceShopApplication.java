package de.zalando;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EntityScan("de.zalando.model.entities")
@EnableJpaRepositories("de.zalando.model.repositories")
@RestController
@OpenAPIDefinition(info = @Info(
		title = "Zalando E-commerce Shop",
		version = "1.0",
		description = "API documentation"
))
public class ECommerceShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceShopApplication.class, args);
	}
}
