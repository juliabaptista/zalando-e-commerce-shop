package de.zalando.dto;

import de.zalando.model.entities.CartItem;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class CartResponse {

  private String message;
  private List<CartItem> cartItems;
  private double totalPrice;

  public CartResponse(String message, List<CartItem> cartItems) {
    this.message = message;
    this.cartItems = cartItems;
    this.totalPrice = cartItems
          .stream()
          .mapToDouble(cart -> cart.getQuantity() * cart.getProduct().getProductPrice())
          .sum();
  }
}
