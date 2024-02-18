package de.zalando.service;

import de.zalando.dto.CartRequest;
import de.zalando.dto.CartResponse;
import de.zalando.exception.DuplicateProductException;
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
  public CartResponse addProductToCart(User user, CartRequest cartRequest)
      throws ProductNotFoundException, InsufficientStockException, DuplicateProductException {

    int quantity = cartRequest.getQuantity();
    Product product = productService.getProductByProductIdAndArchivedIsFalse(cartRequest.getProductId());
    Optional<CartItem> optionalCartItem = cartRepository.getCartItemsByCustomerAndProduct(user,
        product);

    if (optionalCartItem.isPresent()) {
      throw new DuplicateProductException("Product name already exists for user's cart.");
    } else {
      if (product.getStockQuantity() >= cartRequest.getQuantity()) {
        cartRepository.save(new CartItem(quantity, product, user));
      } else throw new InsufficientStockException("Insufficient stock for the quantity required.");
    }
    return getCartItemsByCustomer(user);
  }

  public CartResponse getCartItemsByCustomer(User user) {
    return new CartResponse("Your cart: " + user.getFirstName(), cartRepository.getCartItemsByCustomer(user));
  }

  @Transactional
  public CartResponse updateProductInCart(User user, CartRequest cartRequest)
      throws ProductNotFoundException, InsufficientStockException {
    int newQuantity = cartRequest.getQuantity();
    Product product = productService.getProductByProductIdAndArchivedIsFalse(cartRequest.getProductId());
    Optional<CartItem> optionalCartItem = cartRepository.getCartItemsByCustomerAndProduct(user, product);
    if (optionalCartItem.isEmpty()) {
      throw new ProductNotFoundException("Product not found for user's cart");
    }
    CartItem cartItem = optionalCartItem.get();
    if (product.getStockQuantity() >= newQuantity) {
      cartItem.setQuantity(newQuantity);
    } else throw new InsufficientStockException("Insufficient stock for the quantity required.");

    cartRepository.save(cartItem);
    return getCartItemsByCustomer(user);
  }

  @Transactional
  public CartResponse deleteProductFromCart(User user, Long productId)
      throws ProductNotFoundException {
    Product product = productService.getProductByProductIdAndArchivedIsFalse(productId);
    Optional<CartItem> optionalCartItem = cartRepository.getCartItemsByCustomerAndProduct(user, product);

    if (optionalCartItem.isPresent()) {
      CartItem cartItem = optionalCartItem.get();
      cartRepository.delete(cartItem);
    } else throw new ProductNotFoundException("Product not found for user's cart");
    return getCartItemsByCustomer(user);
  }
}
