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
public class CartId implements Serializable {

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "customer_id")
  private Long customerId;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof CartId cartId)) return false;
    return getProductId() == cartId.getProductId() && getCustomerId() == cartId.getCustomerId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getProductId(), getCustomerId());
  }
}
