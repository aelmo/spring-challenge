package br.com.socialMeli.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseSaveDTO extends DefaultEntityApiResponseDTO {

    public CategoryResponseSaveDTO(boolean success, String description, Long id) {
        super(success, description, id);
    }
}
