package de.zalando.service;

import de.zalando.model.entities.CartItem;
import de.zalando.model.entities.Product;
import de.zalando.model.repositories.CartRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

  CartRepository cartRepository;

  @Autowired
  public CartService(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  //MockData -> saveCart
  public CartItem saveItem(CartItem cartItem) {
    return cartRepository.save(cartItem);
  }

}