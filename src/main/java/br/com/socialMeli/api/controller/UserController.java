package br.com.socialMeli.api.controller;

import br.com.socialMeli.api.dto.response.*;
import br.com.socialMeli.api.model.Followers;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.FollowersRepository;
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

    private final FollowersRepository followersRepository;

    private final FollowersService followersService;

    public UserController(final UserRepository userRepository, final FollowersRepository followersRepository, final FollowersService followersService) {
        this.userRepository = userRepository;
        this.followersRepository = followersRepository;
        this.followersService = followersService;
    }

    @ApiOperation(value = "Follow user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User followed with success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "User not encountered"),
            @ApiResponse(code = 409, message = "You can't follow yourself")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "int", value = "Id from user that's following"),
            @ApiImplicitParam(name = "userIdToFollow", dataType = "int", value = "Id from user that's being followed")
    })
    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followUser(@PathVariable("userId") final Long userId, @PathVariable("userIdToFollow") final Long userIdToFollow) {
        logger.info("POST - Social Meli - (followUser) User: " + userId + " following User: " + userIdToFollow);

        try {
            Optional<User> userFollowing = userRepository.findById(userId);
            Optional<User> userToBeFollowed = userRepository.findById(userIdToFollow);
            Optional<Followers> followExists = followersRepository.findFollowersByFollowerIdAndFollowedId(userId, userIdToFollow);

            if (userFollowing.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.NOT_FOUND);

            if (userToBeFollowed.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userIdToFollow), HttpStatus.NOT_FOUND);

            if (userId.equals(userIdToFollow))
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "You can't follow yourself."), HttpStatus.CONFLICT);

            if (!userToBeFollowed.get().getIsSeller())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "You can't follow an user that isn't a seller."), HttpStatus.BAD_REQUEST);

            if (followExists.isPresent())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "You already follow this user."), HttpStatus.BAD_REQUEST);

            followersService.saveFollow(userId, userIdToFollow);

            return new ResponseEntity<>(new FollowersResponseSaveDTO(true, "Follow executed with success!"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Unfollow user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User unfollowed with success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "User not encountered"),
            @ApiResponse(code = 409, message = "You can't unfollow yourself")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "int", value = "Id from user that's unfollowing"),
            @ApiImplicitParam(name = "userIdToFollow", dataType = "int", value = "Id from user that's being unfollowed")
    })
    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollowUser(@PathVariable("userId") final Long userId, @PathVariable("userIdToUnfollow") final Long userIdToUnfollow) {
        logger.info("POST - Social Meli - (unfollowUser) User: " + userId + " following User: " + userIdToUnfollow);

        try {
            Optional<User> userUnfollowing = userRepository.findById(userId);
            Optional<User> userToBeUnfollowed = userRepository.findById(userIdToUnfollow);
            Optional<Followers> followExists = followersRepository.findFollowersByFollowerIdAndFollowedId(userId, userIdToUnfollow);

            if (userUnfollowing.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.NOT_FOUND);

            if (userToBeUnfollowed.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userIdToUnfollow), HttpStatus.NOT_FOUND);

            if (userId.equals(userIdToUnfollow))
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "You can't unfollow yourself."), HttpStatus.CONFLICT);

            if (!userToBeUnfollowed.get().getIsSeller())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "You can't unfollow an user that isn't a seller."), HttpStatus.BAD_REQUEST);

            if (followExists.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "You can't unfollow an user that you're not following."), HttpStatus.BAD_REQUEST);

            followersService.saveUnfollow(followExists.get().getId());

            return new ResponseEntity<>(new FollowersResponseSaveDTO(true, "Unfollow executed with success!"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Follow count for user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User follower count returned with success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "User not encountered")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "int", value = "Id from user that the count will be made")
    })
    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<?> getFollowerCountForUser(@PathVariable("userId") final Long userId) {
        logger.info("GET - Social Meli - (getFollowerCountForUser) User: " + userId);

        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.NOT_FOUND);

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
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "User not encountered")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "int", value = "Id from user that the list will be made")
    })
    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<?> getFollowerListForUser(@PathVariable("userId") final Long userId, @RequestParam(required = false) final String order) {
        logger.info("GET - Social Meli - (getFollowerListForUser) User: " + userId);

        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.NOT_FOUND);

            if (!user.get().getIsSeller())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Only users that are sellers can have a list of followers."), HttpStatus.BAD_REQUEST);

            List<UniqueUserFollowerResponseDTO> followers = followersService.getFollowersListById(userId);
            List<UniqueUserFollowerResponseDTO> followersOrdered = followersService.sortFollowerListByOrder(followers, order);

            return new ResponseEntity<>(new UserFollowersResponseDTO(user.get().getId(), user.get().getName(), followersOrdered), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Followed list for user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User followed list returned with success"),
            @ApiResponse(code = 404, message = "User not encountered")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "int", value = "Id from user that the list will be made")
    })
    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<?> getFollowedListForUser(@PathVariable("userId") final Long userId, @RequestParam(required = false) final String order) {
        logger.info("GET - Social Meli - (getFollowedListForUser) User: " + userId);

        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.NOT_FOUND);

            List<UniqueUserFollowedResponseDTO> followeds = followersService.getFollowedsListById(userId);
            List<UniqueUserFollowedResponseDTO> followedsOrdered = followersService.sortFollowedListByOrder(followeds, order);

            return new ResponseEntity<>(new UserFollowedsResponseDTO(user.get().getId(), user.get().getName(), followedsOrdered), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
