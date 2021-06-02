package br.com.socialMeli.api.controller;

import br.com.socialMeli.api.dto.response.*;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.FollowersService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.BAD_REQUEST);

            if (userToBeFollowed.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.BAD_REQUEST);

            if (userId.equals(userIdToFollow))
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "You can't follow yourself."), HttpStatus.BAD_REQUEST);

            if (!userToBeFollowed.get().getIsSeller())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "You can't follow an user that isn't a seller."), HttpStatus.BAD_REQUEST);

            followersService.saveFollow(userId, userIdToFollow);

            return new ResponseEntity<>(new FollowersResponseSaveDTO(true, "Follow executed with success!"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Follow count for user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User follower count returned with success"),
            @ApiResponse(code = 400, message = "User not encountered")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "int", value = "Id from user that the count will be made")
    })
    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<?> getFollowerCountForUser(@PathVariable("userId") Long userId) {
        logger.info("GET - Social Meli - (getFollowerCountForUser) User: " + userId);

        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.BAD_REQUEST);

            if (!user.get().getIsSeller())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Only users that are sellers can have followers."), HttpStatus.BAD_REQUEST);

            Long count = followersService.getFollowersCountById(userId);

            return new ResponseEntity<>(new FollowersCountResponseDTO(user.get().getId(), user.get().getName(), count), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Followers list for user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User follower list returned with success"),
            @ApiResponse(code = 400, message = "User not encountered")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "int", value = "Id from user that the list will be made")
    })
    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<?> getFollowerListForUser(@PathVariable("userId") Long userId) {
        logger.info("GET - Social Meli - (getFollowerListForUser) User: " + userId);

        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.BAD_REQUEST);

            if (!user.get().getIsSeller())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Only users that are sellers can have a list of followers."), HttpStatus.BAD_REQUEST);

            List<UniqueUserFollowerResponseDTO> followers = followersService.getFollowersListById(userId);

            return new ResponseEntity<>(new UserFollowersResponseDTO(user.get().getId(), user.get().getName(), followers), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
