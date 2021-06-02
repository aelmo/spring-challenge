package br.com.socialMeli.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseSaveDTO extends DefaultEntityApiResponseDTO {

    public ProductResponseSaveDTO(boolean success, String description, Long id) {
        super(success, description, id);
    }
}
