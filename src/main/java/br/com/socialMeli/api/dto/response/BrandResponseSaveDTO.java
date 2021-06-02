package br.com.socialMeli.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponseSaveDTO extends DefaultEntityApiResponseDTO {

    public BrandResponseSaveDTO(boolean success, String description, Long id) {
        super(success, description, id);
    }
}
