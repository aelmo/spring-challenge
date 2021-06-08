package br.com.socialMeli.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "The product name is mandatory.")
    private String productName;

    @NotBlank(message = "The product type is mandatory.")
    private String type;

    @NotBlank(message = "The product brand is mandatory.")
    private String brand;

    private String color;

    private String notes;
}
