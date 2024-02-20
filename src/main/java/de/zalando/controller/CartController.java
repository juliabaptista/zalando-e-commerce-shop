package de.zalando.controller;

import de.zalando.dto.CartRequest;
import de.zalando.dto.CartResponse;
import de.zalando.exception.ApiError;
import de.zalando.exception.DuplicateProductException;
import de.zalando.exception.InsufficientStockException;
import de.zalando.exception.ProductNotFoundException;
import de.zalando.exception.UserNotFoundException;
import de.zalando.model.entities.User;
import de.zalando.service.CartService;
import de.zalando.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;
  private final UserService userService;

  @GetMapping
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<?> getCartItemsByCustomer(
      @AuthenticationPrincipal UserDetails userDetails) {
    try {
      return ResponseEntity.ok(cartService.getCartItemsByCustomer(
          userService.getUserByEmail(userDetails.getUsername())));
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
          .body(new ApiError(HttpStatus.NOT_ACCEPTABLE, e.getMessage()));
    }
  }

  @PreAuthorize("hasRole('CUSTOMER')")
  @PostMapping
  public ResponseEntity<?> addProductToCart(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestBody CartRequest cartRequest
  ) {
    try {
      User user = userService.getUserByEmail(userDetails.getUsername());
      return ResponseEntity.ok(cartService.addProductToCart(user, cartRequest));
    } catch (UserNotFoundException | InsufficientStockException | ProductNotFoundException |
             DuplicateProductException e) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
          .body(new ApiError(HttpStatus.NOT_ACCEPTABLE, e.getMessage()));
    }
  }

  @PreAuthorize("hasRole('CUSTOMER')")
  @PutMapping
  public ResponseEntity<?> updateProductInCart(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestBody CartRequest cartRequest
  ) {
    try {
      User user = userService.getUserByEmail(userDetails.getUsername());
      return ResponseEntity.ok(cartService.updateProductInCart(user, cartRequest));
    } catch (UserNotFoundException | InsufficientStockException | ProductNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
          .body(new ApiError(HttpStatus.NOT_ACCEPTABLE, e.getMessage()));
    }
  }

  @PreAuthorize("hasRole('CUSTOMER')")
  @DeleteMapping("/{product-id}")
  public ResponseEntity<?> deleteProductFromCart(
      @PathVariable("product-id") Long productId,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    User user;
    CartResponse cartItem;
    try {
      user = userService.getUserByEmail(userDetails.getUsername());
      cartItem = cartService.deleteProductFromCart(user, productId);
    } catch (UserNotFoundException | ProductNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
          .body(new ApiError(HttpStatus.NOT_ACCEPTABLE, e.getMessage()));
    }
    return ResponseEntity.ok(cartItem);
  }
}
