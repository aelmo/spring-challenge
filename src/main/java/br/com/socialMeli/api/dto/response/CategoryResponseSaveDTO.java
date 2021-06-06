package br.com.socialMeli.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseSaveDTO {

    private boolean success;

    private String description;

    private Long id;
}
