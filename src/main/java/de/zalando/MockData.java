package de.zalando;

import de.zalando.model.entities.CartItem;
import de.zalando.model.entities.Product;
import de.zalando.model.entities.Role;
import de.zalando.model.entities.User;
import de.zalando.service.CartService;
import de.zalando.service.ProductService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MockData {

  private final ProductService productService;
  private final CartService cartService;

  @PostConstruct
  public void addMockData() {

    User adminUser = new User(1L, "Admin first name", "Admin last name", "admin@gmail.com", "123456", Role.ADMIN, false);
    User customer = new User(2L, "Customer first name", "Customer last name", "customer@gmail.com", "123456", Role.CUSTOMER, false);

    Product product1 = new Product("Product One", "Product One description", 19.99,10, adminUser );
    Product product2 = new Product("Product Two", "Product Two description", 29.99,20, adminUser );
    Product product3 = new Product("Product Three", "Product Three description", 39.99,30, adminUser );
    productService.saveAll(List.of(product1, product2, product3));

    CartItem cartItem1 = new CartItem(1, product1,customer);
    CartItem cartItem2 = new CartItem(1, product2,customer);
    CartItem cartItem3 = new CartItem(1, product3,customer);
    cartService.saveItem(cartItem1);
    cartService.saveItem(cartItem2);
    cartService.saveItem(cartItem3);
  }
}
