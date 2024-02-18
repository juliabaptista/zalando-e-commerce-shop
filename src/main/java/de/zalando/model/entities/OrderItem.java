package de.zalando.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderItem {

  @JsonIgnore
  @EmbeddedId
  private OrderItemId id;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "order_id", insertable = false, updatable = false)
  private Order order;

  @ManyToOne
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  private Product product;

  private int quantity;
  private double price;

  public OrderItem(Order order, Product product, int quantity, double price) {
    this.id = new OrderItemId(order.getOrderId(), product.getProductId());
    this.order = order;
    this.product = product;
    this.quantity = quantity;
  }
}