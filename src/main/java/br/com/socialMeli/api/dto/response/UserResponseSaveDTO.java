package br.com.socialMeli.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseSaveDTO extends DefaultEntityApiResponseDTO {

    public UserResponseSaveDTO(boolean success, String description, Long id) {
        super(success, description, id);
    }
}
