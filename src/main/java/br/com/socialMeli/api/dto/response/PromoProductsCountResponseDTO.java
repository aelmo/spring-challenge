package br.com.socialMeli.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromoProductsCountResponseDTO {

    private Long userId;

    private String userName;

    private Long promoProductsCount;
}
