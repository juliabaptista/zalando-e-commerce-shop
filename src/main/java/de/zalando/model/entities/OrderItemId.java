package de.zalando.model.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderItemId implements Serializable {

  @Column(name = "order_id")
  private Long orderId;

  @Column(name = "product_id")
  private Long productId;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof OrderItemId)) return false;
    OrderItemId that = (OrderItemId) object;
    return Objects.equals(getProductId(), that.getProductId()) &&
        Objects.equals(getOrderId(), that.getOrderId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getProductId(), getOrderId());
  }
}
