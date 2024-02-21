package de.zalando.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @ToString
public class ProductRequest {

  @NotEmpty(message = "Required field. Cannot be empty!")
  private String productName;
  @NotEmpty(message = "Required field. Cannot be empty!")
  private String productDescription;

  @DecimalMin(value = "0", inclusive = true, message = "Price cannot be negative.")
  @Digits(integer=7, fraction=3, message = "Invalid price format.")
  private double productPrice;

  @NotNull(message = "Required field. Cannot be null!")
  @Min(value = 0, message = "Stock quantity cannot be negative.")
  @Max(value = 10_000,message = "Max stock quantity is 10,000.")
  private int stockQuantity;
}
