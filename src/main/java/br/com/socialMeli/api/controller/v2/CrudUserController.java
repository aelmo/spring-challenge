package br.com.socialMeli.api.controller.v2;

import br.com.socialMeli.api.dto.request.UserRequestDTO;
import br.com.socialMeli.api.dto.response.DefaultApiResponseDTO;
import br.com.socialMeli.api.dto.response.UserResponseSaveDTO;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v2/user")
public class CrudUserController {

    private static final Logger logger = LoggerFactory.getLogger(CrudUserController.class);

    private final UserRepository userRepository;

    private final UserService userService;

    public CrudUserController(final UserRepository userRepository, final UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @ApiOperation(value = "New user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User created with success"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/newUser")
    public ResponseEntity<?> createNewUser(@ApiParam(value = "Object for new User", required = true)
                                           @Valid @RequestBody final UserRequestDTO user) {
        logger.info("POST - Social Meli (v2) - (createNewUser) User: " + user);

        try {
            if (userRepository.existsByEmail(user.getEmail()))
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Email already registered for another user: " + user.getEmail()), HttpStatus.BAD_REQUEST);

            if (userRepository.existsByCpf(user.getCpf()))
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Cpf already registered for another user: " + user.getCpf()), HttpStatus.BAD_REQUEST);

            User createdUser = userService.createNewUser(user);

            return new ResponseEntity<>(new UserResponseSaveDTO(true, "User created with success!", createdUser.getId()), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
