package de.zalando.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition
public class OpenAPIConfiguration {

  @Bean
  public OpenAPI openAPI() {
    Server devServer = new Server();
    devServer.setUrl("/"); // Pode tentar usar "/" como URL base
    devServer.setDescription("Server URL in Development Environment");

    Contact contact = new Contact();
    contact.setEmail("juliabaptistadeoliveira@gmail.com");
    contact.setName("Julia Baptista");

    Info info = new Info()
        .title("E-Commerce API")
        .version("1.0")
        .contact(contact)
        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
        .description("This API exposes endpoints to an e-commerce functionality.");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("bearer-key", OpenApiCustomiser::bearerSecurityScheme))
        .info(info)
        .servers(List.of(devServer));
  }
}
