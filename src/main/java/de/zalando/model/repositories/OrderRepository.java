package de.zalando.model.repositories;

import de.zalando.model.entities.Order;
import de.zalando.model.entities.OrderStatus;
import de.zalando.model.entities.User;
import java.util.List;
import java.util.Optional;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  Optional<Order> getOrderByCustomerAndOrderId(User user, Long orderId);
  Optional<Order> getOrderByOrderId(Long orderId);
  Page<Order> getOrdersByCustomer(User user, Pageable pageable);

  @Query("SELECT o FROM Order o WHERE o.customer = :user AND (:orderStatus IS NULL OR o.status = :orderStatus)")
  Page<Order> getOrdersByCustomerAndStatus(@Param("user") User user, @Param("orderStatus") OrderStatus orderStatus, Pageable pageable);
}
