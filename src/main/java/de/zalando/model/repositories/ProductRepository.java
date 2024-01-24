package de.zalando.model.repositories;

import de.zalando.model.entities.Product;
import de.zalando.model.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> getProductById(Long productId);
  Page<Product> getAllProducts(Pageable pageable);
  List<Product> getProductByNameIgnoreCase(String productName);
  Page<Product> getProductByNameIgnoreCase(String productName, Pageable pageable);
  Optional<Product> getProductByNameAndUserIgnoreCase(String productName, User user);
  Optional<Product> getProductByIdAndUserIgnoreCase(Integer productId, User user);
  List<Product> getProductByUser(User user);

}
