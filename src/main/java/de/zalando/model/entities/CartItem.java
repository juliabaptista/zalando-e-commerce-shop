package de.zalando.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart")
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class CartItem {

  @JsonIgnore
  @EmbeddedId
  private CartId id;

  @Column
  private int quantity;

  @ManyToOne
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  private Product product;

  @JsonIgnore
  @ManyToOne
  @MapsId("customerId")
  @JoinColumn(name = "customer_id")
  private User customer;

  public CartItem(int quantity, Product product, User customer) {
    this.id=new CartId(product.getProductId(), customer.getUserId());
    this.quantity = quantity;
    this.product = product;
    this.customer = customer;
  }

  public void addQuantity(int quantity) {
    this.quantity += quantity;
  }
}
