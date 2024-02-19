package de.zalando.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductRequest {

  @NotEmpty(message = "Required field. Cannot be empty!")
  private String productName;
  @NotEmpty(message = "Required field. Cannot be empty!")
  private String productDescription;

  @NotEmpty(message = "Required field. Cannot be empty!")
  private double productPrice;
  @NotEmpty(message = "Required field. Cannot be empty!")
  @Min(value = 0, message = "Stock quantity cannot be negative.")
  @Max(value = 10_000,message = "Max stock quantity is 10,000.")
  private int stockQuantity;
}
