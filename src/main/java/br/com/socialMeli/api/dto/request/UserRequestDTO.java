package br.com.socialMeli.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "The name is mandatory.")
    private String name;

    @NotBlank(message = "The email is mandatory.")
    private String email;

    @NotBlank(message = "The cpf is mandatory.")
    private String cpf;

    @NotBlank(message = "The is seller is mandatory.")
    private Boolean isSeller;
}
