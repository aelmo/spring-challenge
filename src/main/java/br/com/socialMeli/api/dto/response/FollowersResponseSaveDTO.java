package br.com.socialMeli.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowersResponseSaveDTO extends DefaultEntityApiResponseDTO {

    public FollowersResponseSaveDTO(boolean success, String description) {
        super(success, description);
    }
}
