package br.com.socialMeli.api.controller;

import br.com.socialMeli.api.dto.response.DefaultApiResponseDTO;
import br.com.socialMeli.api.dto.response.FollowersResponseSaveDTO;
import br.com.socialMeli.api.model.Followers;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.FollowersService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    private final FollowersService followersService;

    public UserController(final UserRepository userRepository, final FollowersService followersService) {
        this.userRepository = userRepository;
        this.followersService = followersService;
    }

    @ApiOperation(value = "Follow user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User followed with success"),
            @ApiResponse(code = 400, message = "User not encountered")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "int", value = "Id from user that's following"),
            @ApiImplicitParam(name = "userIdToFollow", dataType = "int", value = "Id from user that's being followed")
    })
    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followUser(@PathVariable("userId") Long userId, @PathVariable("userIdToFollow") Long userIdToFollow) {
        logger.info("POST - Social Meli - (followUser) User: " + userId + " following User: " + userIdToFollow);

        try {
            Optional<User> user = userRepository.findById(userId);
            Optional<User> userToBeFollowed = userRepository.findById(userIdToFollow);

            if (user.isEmpty())
                return new ResponseEntity(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.BAD_REQUEST);

            if (userToBeFollowed.isEmpty())
                return new ResponseEntity(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.BAD_REQUEST);

            if (userId.equals(userIdToFollow))
                return new ResponseEntity(new DefaultApiResponseDTO(false, "You can't follow yourself."), HttpStatus.BAD_REQUEST);

            followersService.saveFollow(userId, userIdToFollow);

            return new ResponseEntity(new FollowersResponseSaveDTO(true, "Follow executed with success!"), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
