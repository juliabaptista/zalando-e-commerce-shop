package de.zalando.model.repositories;

import de.zalando.model.entities.Product;
import de.zalando.model.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


// CUSTOMER queries
  Optional<Product> getProductByProductIdAndArchivedIsFalse(Long productId);
  Page<Product> getAllByArchivedIsFalse(Pageable pageable);
  Page<Product> getAllByArchivedIsFalseAndProductNameContainingIgnoreCase (String productName, Pageable pageable);

  // ADMIN queries
  List<Product> getProductsByAdminAndArchivedIsFalse(User user);
  Optional<Product> getProductsByAdminAndArchived(User user, boolean archived);
  Optional<Product> getProductByAdminAndProductId(User user, Long productId);
  Optional<Product> getProductsByAdminAndProductNameContainingIgnoreCase(User user, String productName);
}
