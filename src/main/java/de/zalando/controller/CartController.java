package de.zalando.controller;

import de.zalando.exception.ApiError;
import de.zalando.exception.UserNotFoundException;
import de.zalando.service.CartService;
import de.zalando.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;
  private final UserService userService;

  @GetMapping
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<?> getCartItemsByCustomer(@AuthenticationPrincipal UserDetails principal) {
    try {
      return ResponseEntity.ok(cartService.getCartItemsByCustomer(userService.getUserByEmail(principal.getUsername())));
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
          .body(new ApiError(HttpStatus.NOT_ACCEPTABLE, e.getMessage()));
    }
  }
}
