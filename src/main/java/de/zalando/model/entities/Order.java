package de.zalando.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Order {

  @Id
  @Column(name = "order_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long orderId;

  @Column(name = "order_date")
  private LocalDateTime orderDate;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "customer_id")
  private User customer;

  @Column(name = "total_price")
  private double totalPrice;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private OrderStatus status;

  public Order(User customer, double totalPrice, OrderStatus status) {
    this.orderDate = LocalDateTime.now();
    this.customer = customer;
    this.totalPrice = totalPrice;
    this.status = status;
  }
}