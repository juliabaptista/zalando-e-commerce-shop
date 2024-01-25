package de.zalando.model.repositories;

import de.zalando.model.entities.Product;
import de.zalando.model.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> getProductByProductId(Long productId);
}
