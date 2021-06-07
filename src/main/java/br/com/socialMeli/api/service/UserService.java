package br.com.socialMeli.api.service;

import br.com.socialMeli.api.dto.request.UserRequestDTO;
import br.com.socialMeli.api.model.User;

public interface UserService {

    User createNewUser(final UserRequestDTO userDTO);
}
