package de.zalando.service;

import de.zalando.dto.CartResponse;
import de.zalando.exception.EmptyCartException;
import de.zalando.exception.InsufficientStockException;
import de.zalando.exception.OrderNotFoundException;
import de.zalando.model.entities.CartItem;
import de.zalando.model.entities.Order;
import de.zalando.model.entities.OrderStatus;
import de.zalando.model.entities.User;
import de.zalando.model.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final CartService cartService;
  private final ProductService productService;

  @Transactional
  public Order createOrderFromCart(User user)
      throws EmptyCartException, InsufficientStockException {
    CartResponse cartResponse = cartService.getCartItemsByCustomer(user);
    validateOrderCreation(cartResponse, user);

    Order newOrder = new Order();
    newOrder.setOrderDate(LocalDateTime.now());
    newOrder.setCustomer(user);
    newOrder.setTotalPrice(cartResponse.getTotalPrice());
    newOrder.setStatus(OrderStatus.PROCESSING);

    newOrder = orderRepository.save(newOrder);

    cartService.updateCartItemsForOrder(cartResponse, newOrder);

    cartService.clearCart(cartResponse);

    return newOrder;
  }

  private void validateOrderCreation(CartResponse cartResponse, User user)
      throws EmptyCartException, InsufficientStockException {
    if (cartResponse.getCartItems().isEmpty()) {
      throw new EmptyCartException("It is not possible to create an order with an empty cart.");
    }

    for (CartItem cartItem : cartResponse.getCartItems()) {
      if (cartItem.getQuantity() > productService.getProductStock(cartItem.getProduct())) {
        throw new InsufficientStockException("Insufficient stock for item: " + cartItem.getProduct().getProductName());
      }
    }
  }

  public Order getOrderByCustomerAndOrderId(User user, Long orderId) throws OrderNotFoundException {
    return orderRepository.getOrderByCustomerAndOrderId(user, orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
  }

  public List<Order> getOrdersByUser(User user) {
    return orderRepository.getOrdersByCustomer(user);
  }

  @Transactional
  public void updateOrderStatus(Long orderId, OrderStatus newStatus) throws OrderNotFoundException {
    Order order = orderRepository.getOrderByOrderId(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

    order.setStatus(newStatus);
    orderRepository.save(order);
  }



}

