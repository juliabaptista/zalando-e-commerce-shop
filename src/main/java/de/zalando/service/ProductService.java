package de.zalando.service;

import de.zalando.exception.InsufficientStockException;
import de.zalando.exception.ProductNotFoundException;
import de.zalando.model.entities.Product;
import de.zalando.model.repositories.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

  ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Page<Product> getAllByArchivedIsFalse(int page, int size) {
    PageRequest request = PageRequest.of(page, size);
    return productRepository.getAllByArchivedIsFalse(request);
  }

  public Page<Product> getAllByArchivedIsFalseAndProductNameContainingIgnoreCase(String keyword,
      int page, int size)
      throws ProductNotFoundException {
    PageRequest request = PageRequest.of(page, size);
    Page<Product> products = productRepository.getAllByArchivedIsFalseAndProductNameContainingIgnoreCase(
        keyword, request);
    if (products.isEmpty()) {
      throw new ProductNotFoundException("No products found with the keyword: " + keyword);
    } else {
      return products;
    }
  }

  public Product getProductByProductIdAndArchivedIsFalse(Long productId)
      throws ProductNotFoundException {
    Optional<Product> optionalProduct = productRepository.findById(productId);
    if (optionalProduct.isPresent()) {
      return optionalProduct.get();
    } else {
      throw new ProductNotFoundException("Product not found.");
    }
  }

  public Product addProduct(User user, ProductRequest productRequest)
      throws DuplicateProductException {
    Optional<Product> optionalProduct = productRepository.getProductsByAdminAndProductNameContainingIgnoreCase(
        user, productRequest.getProductName());
    if (optionalProduct.isEmpty()) {
      return productRepository.save(new Product(
          productRequest.getProductName(),
          productRequest.getProductDescription(),
          productRequest.getProductPrice(),
          productRequest.getStockQuantity(),
          user
      ));
    } else {
      throw new DuplicateProductException("Product name already exists for this user.");
    }
  }

  public Product updateProduct(User user, Long productId, ProductRequest productRequest)
      throws ProductNotFoundException {
    Optional<Product> optionalProduct = productRepository.getProductByAdminAndProductId(user,
        productId);
    if (optionalProduct.isPresent()) {
      Product product = optionalProduct.get();
      product.setProductName(productRequest.getProductName());
      product.setProductDescription(productRequest.getProductDescription());
      product.setProductPrice(productRequest.getProductPrice());
      product.setStockQuantity(product.getStockQuantity());
      return productRepository.save(product);
    } else {
      throw new ProductNotFoundException("Product not found.");
    }
  }

  public Product archiveProduct(User user, Long productId) throws ProductNotFoundException {
    Optional<Product> optionalProduct = productRepository.getProductByAdminAndProductId(user,
        productId);
    if (optionalProduct.isPresent()) {
      Product product = optionalProduct.get();
      product.setArchived(true);
      return productRepository.save(product);
    } else {
      throw new ProductNotFoundException("Product not found.");
    }
  }

  //MockData -> saveProduct to save one product
  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  //MockData -> saveAll to save a list of products
  public List<Product> saveAll(List<Product> products) {
    return productRepository.saveAll(products);
  }
}
