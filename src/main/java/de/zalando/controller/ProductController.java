package de.zalando.controller;

import de.zalando.exception.ProductNotFoundException;
import de.zalando.model.entities.Product;
import de.zalando.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<?> addNewProduct(
      @Validated @RequestBody ProductRequest productRequest,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    User user;
    Product product;
    try {
      user = userService.getUserByEmail(userDetails.getUsername());
      product = productService.addProduct(user, productRequest);
    } catch (UserNotFoundException | DuplicateProductException e) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
          .body(new ApiError(HttpStatus.NOT_ACCEPTABLE, e.getMessage()));
    }
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{product-id}")
        .buildAndExpand(product.getProductId())
        .toUri();
    return ResponseEntity.created(uri).body(product);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{product-id}")
  public ResponseEntity<?> updateProduct(
      @PathVariable("product-id") Long productId,
      @Validated @RequestBody ProductRequest productRequest,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    User user;
    Product product;
    try {
      user = userService.getUserByEmail(userDetails.getUsername());
      product = productService.updateProduct(user, productId, productRequest);
    } catch (UserNotFoundException | ProductNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
          .body(new ApiError(HttpStatus.NOT_ACCEPTABLE, e.getMessage()));
    }
    return ResponseEntity.ok(product);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{product-id}")
  public ResponseEntity<?> deleteProduct(
      @PathVariable("product-id") Long productId,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    User user;
    Product product;
    try {
      user = userService.getUserByEmail(userDetails.getUsername());
      product = productService.archiveProduct(user, productId);
    } catch (UserNotFoundException | ProductNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
          .body(new ApiError(HttpStatus.NOT_ACCEPTABLE, e.getMessage()));
    }
    return ResponseEntity.ok(product);
  }
}
