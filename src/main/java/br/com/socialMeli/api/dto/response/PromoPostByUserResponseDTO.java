package br.com.socialMeli.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromoPostByUserResponseDTO {

    private Long userId;

    private String userName;

    private List<PromoPostResponseFindDTO> posts;
}
