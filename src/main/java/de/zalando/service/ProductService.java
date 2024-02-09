package de.zalando.service;

import de.zalando.exception.ProductNotFoundException;
import de.zalando.model.entities.Product;
import de.zalando.model.repositories.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    return productRepository.getAllByArchivedIsFalse(
        PageRequest.of(page, size));
  }

  public Product getProductByProductIdAndArchivedIsFalse(Long productId) throws ProductNotFoundException {
    Optional<Product> optionalProduct = productRepository.findById(productId);
    if (optionalProduct.isPresent()) {
      return optionalProduct.get();
    } else throw new ProductNotFoundException("Product not found.");
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
