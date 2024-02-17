package de.zalando.controller;

import de.zalando.exception.ProductNotFoundException;
import de.zalando.model.entities.Product;
import de.zalando.service.ProductService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
=======
import org.springframework.stereotype.Controller;
>>>>>>> parent of 58b08e3 ([PXZ-73] Implement POST, PUT and DELETE endpoints)
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<Page<Product>> getAllByArchivedIsFalse(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size
  ) {
    return ResponseEntity.ok(productService.getAllByArchivedIsFalse(page, size));
  }

  @GetMapping("/search")
  public ResponseEntity<?> getAllByArchivedIsFalseAndProductNameContainingIgnoreCase(
      @RequestParam("keyword") String keyword,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size
  ) {
    try {
      return ResponseEntity.ok(
          productService.getAllByArchivedIsFalseAndProductNameContainingIgnoreCase(keyword, page,
              size));
    } catch (ProductNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProductByProductIdAndArchivedIsFalse(
      @PathVariable("id") Long productId) {
    try {
      return ResponseEntity.ok(productService.getProductByProductIdAndArchivedIsFalse(productId));
    } catch (ProductNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
}
