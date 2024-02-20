package de.zalando.model.repositories;

import de.zalando.model.entities.Order;
import de.zalando.model.entities.User;
import java.util.List;
import java.util.Optional;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  Optional<Order> getOrderByCustomerAndOrderId(User user, Long orderId);
  List<Order> getOrdersByCustomer(User user);
  Optional<Order> getOrderByOrderId(Long orderId);
}
