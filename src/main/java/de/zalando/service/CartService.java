package de.zalando.service;

import de.zalando.dto.CartResponse;
import de.zalando.exception.InsufficientStockException;
import de.zalando.exception.ProductNotFoundException;
import de.zalando.model.entities.CartItem;
import de.zalando.model.entities.Product;
import de.zalando.model.entities.User;
import de.zalando.model.repositories.CartRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final ProductService productService;

  @Transactional
  public CartResponse addProductToCart(User user, Long productId, int quantity)
      throws ProductNotFoundException, InsufficientStockException {
    Product product = productService.getProductByProductIdAndArchivedIsFalse(productId);
    productService.reduceQuantity(product, quantity);

    Optional<CartItem> cartItemExists = cartRepository.getCartItemsByCustomerAndProduct(user,
        product);
    if (cartItemExists.isPresent()) {
      CartItem cartItem = cartItemExists.get();
      cartItem.addQuantity(quantity);
      cartRepository.save(cartItem);
    } else {
      cartRepository.save(new CartItem(quantity, product, user));
    }
    return getCartItemsByCustomer(user);
  }

  public CartResponse getCartItemsByCustomer(User user) {
    return new CartResponse(cartRepository.getCartItemsByCustomer(user));
  }

  //MockData -> saveCart
  public CartItem saveItem(CartItem cartItem) {
    return cartRepository.save(cartItem);
  }

}