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
public class CartRequest {

  @NotEmpty(message = "Required field. Cannot be empty!")
  private Long productId;

  @NotEmpty(message = "Required field. Cannot be empty!")
  @Min(value = 1, message = "Quantity cannot be less than 1.")
  private int quantity;
}
