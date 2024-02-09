package de.zalando;

import de.zalando.model.entities.Product;
import de.zalando.model.entities.Role;
import de.zalando.model.entities.User;
import de.zalando.service.ProductService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MockData {

  private final ProductService productService;

  @PostConstruct
  public void addMockData() {

    User adminUser = new User(1L, "Admin first name", "Admin last name", "admin@gmail.com", "123456", Role.ADMIN, false);

    Product product1 = new Product("Product1", "Product1 description", 19.99,10, adminUser );
    Product product2 = new Product("Product2", "Product2 description", 29.99,20, adminUser );
    Product product3 = new Product("Product3", "Product3 description", 39.99,30, adminUser );
    productService.saveAll(List.of(product1, product2, product3));
  }
}
