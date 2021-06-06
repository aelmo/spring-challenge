package br.com.socialMeli.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseSaveDTO extends DefaultEntityApiResponseDTO {

    public PostResponseSaveDTO(boolean success, String description) {
        super(success, description);
    }
}
