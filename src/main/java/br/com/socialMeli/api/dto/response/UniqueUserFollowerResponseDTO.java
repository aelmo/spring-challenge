package br.com.socialMeli.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniqueUserFollowerResponseDTO {

    private Long userId;

    private String userName;

}
