package de.zalando.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {

  @Id
  @Column(name = "product_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long productId;
  @Column(name = "product_name")
  private String productName;
  @Column(name = "product_description")
  private String productDescription;
  @Column(name = "product_price")
  private double productPrice;
  @Column(name = "stock")
  private int stockQuantity;
  @Column(name = "is_archived")
  private boolean archived;

  @ManyToOne
  @JoinColumn(name = "admin", referencedColumnName = "user_id")
  private User admin;

  public Product(String productName, String productDescription, double productPrice,
      int stockQuantity, User admin) {
    this.productName = productName;
    this.productDescription = productDescription;
    this.productPrice = productPrice;
    this.stockQuantity = stockQuantity;
    this.admin = admin;
  }
}
