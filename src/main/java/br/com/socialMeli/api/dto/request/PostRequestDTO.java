package br.com.socialMeli.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {

    @NotBlank(message = "The userId is mandatory.")
    private Long userId;

    private List<ProductRequestDTO> details;

    @NotBlank(message = "The category is mandatory.")
    private Long category;

    @NotBlank(message = "The price is mandatory")
    private Double price;

}
