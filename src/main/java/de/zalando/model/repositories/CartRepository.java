package de.zalando.model.repositories;

import de.zalando.model.entities.CartItem;
import de.zalando.model.entities.Product;
import de.zalando.model.entities.User;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
  List<CartItem> getCartItemsByCustomer(User user);
  Optional<CartItem> getCartItemsByCustomerAndProduct(User user, Product product);
}
