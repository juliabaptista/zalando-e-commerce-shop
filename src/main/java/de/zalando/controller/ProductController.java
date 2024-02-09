package de.zalando.controller;

import de.zalando.exception.ProductNotFoundException;
import de.zalando.model.entities.Product;
import de.zalando.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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
      @RequestParam("page") int page,
      @RequestParam("size") int size
  ) {
    return ResponseEntity.ok(productService.getAllByArchivedIsFalse(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProductByProductIdAndArchivedIsFalse(@PathVariable("id") Long productId) {
    try {
      return ResponseEntity.ok(productService.getProductByProductIdAndArchivedIsFalse(productId));
    } catch (ProductNotFoundException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
}
