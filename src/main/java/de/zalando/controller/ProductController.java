package de.zalando.controller;

import de.zalando.exception.ProductNotFoundException;
import de.zalando.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProductByProductId(@PathVariable("id") Long productId) {
    try {
      return ResponseEntity.ok(productService.getProductByProductId(productId));
    } catch (ProductNotFoundException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
}
