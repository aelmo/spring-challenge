package br.com.socialMeli.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseFindDTO {

    private Long productId;

    private String productName;

    private String type;

    private String brand;

    private String color;

    private String notes;
}
