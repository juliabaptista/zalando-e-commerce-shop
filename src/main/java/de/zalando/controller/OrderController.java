package de.zalando.controller;

import de.zalando.exception.ApiError;
import de.zalando.exception.EmptyCartException;
import de.zalando.exception.InsufficientStockException;
import de.zalando.exception.InvalidOrderStatusException;
import de.zalando.exception.OrderNotFoundException;
import de.zalando.exception.UserNotFoundException;
import de.zalando.model.entities.Order;
import de.zalando.exception.OrderNotFoundException;
import de.zalando.exception.UserNotFoundException;
import de.zalando.model.entities.Order;
import de.zalando.model.entities.User;
import de.zalando.service.OrderService;
import de.zalando.service.UserService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final UserService userService;

  @PreAuthorize("hasRole('CUSTOMER')")
  @PostMapping
  public ResponseEntity<?> createOrderFromCart(
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    Order order = null;
    try {
      order = orderService.createOrderFromCart(
          userService.getUserByEmail(userDetails.getUsername()));
    } catch (UserNotFoundException | EmptyCartException | InsufficientStockException e) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
          .body(new ApiError(HttpStatus.NOT_ACCEPTABLE, e.getMessage()));
    }
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{order-id}")
        .buildAndExpand(order.getOrderId())
        .toUri();
    return ResponseEntity.created(uri).body(order);
  }

  @PreAuthorize("hasRole('CUSTOMER')")
  @GetMapping("/{order-id}")
  public ResponseEntity<?> getOrderByCustomer(
      @AuthenticationPrincipal UserDetails userDetails,
      @PathVariable("order-id") Long orderId
  ) {
    try {
      return ResponseEntity.ok(orderService.getOrderByCustomerAndOrderId(userService.getUserByEmail(userDetails.getUsername()), orderId));
    } catch (OrderNotFoundException | UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
          .body(new ApiError(HttpStatus.NOT_ACCEPTABLE, e.getMessage()));
    }
  }

  @PreAuthorize("hasRole('CUSTOMER')")
  @GetMapping("/my-orders")
  public ResponseEntity<?> getOrdersByUser(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam(name = "orderStatus", required = false) String orderStatus,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size
  ) {
    try {
      return ResponseEntity.ok(orderService.getOrdersByUser(userService.getUserByEmail(userDetails.getUsername()), orderStatus, page, size));
    } catch (UserNotFoundException | OrderNotFoundException | InvalidOrderStatusException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ApiError(HttpStatus.NOT_FOUND, e.getMessage()));
    }
  }
}
