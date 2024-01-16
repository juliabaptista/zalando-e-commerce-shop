package de.zalando;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
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

	@GetMapping(path = "/")
	public String greetings() {
		return "Hello World";
	}

}
