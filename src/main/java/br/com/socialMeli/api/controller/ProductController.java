package br.com.socialMeli.api.controller;

import br.com.socialMeli.api.dto.request.PostRequestDTO;
import br.com.socialMeli.api.dto.request.PromoPostRequestDTO;
import br.com.socialMeli.api.dto.response.*;
import br.com.socialMeli.api.model.Category;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.CategoryRepository;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.PostService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final PostService postService;

    public ProductController(final UserRepository userRepository, final CategoryRepository categoryRepository, final PostService postService) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.postService = postService;
    }

    @ApiOperation(value = "New post")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Post created with success"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/newPost")
    public ResponseEntity<?> registerNewPost(@ApiParam(value = "Object for creating a new post", required = true)
                                             @Valid @RequestBody final PostRequestDTO postRequestDTO) {
        logger.info("POST - Social Meli - (registerNewPost) Body: " + postRequestDTO);

        try {
            Optional<User> user = userRepository.findById(postRequestDTO.getUserId());
            Optional<Category> category = categoryRepository.findById(postRequestDTO.getCategory());

            if (user.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + postRequestDTO.getUserId()), HttpStatus.BAD_REQUEST);

            if (category.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Category not found for id: " + postRequestDTO.getCategory()), HttpStatus.BAD_REQUEST);

            if (!user.get().getIsSeller())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "You can't create a post if you aren't a seller."), HttpStatus.BAD_REQUEST);

            postService.saveNewPost(postRequestDTO);

            return new ResponseEntity<>(new PostResponseSaveDTO(true, "Post created with success!"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "New promo post")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Promo post created with success"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/newPromoPost")
    public ResponseEntity<?> registerNewPromoPost(@ApiParam(value = "Object for creating a new promo post", required = true)
                                             @Valid @RequestBody final PromoPostRequestDTO promoPostRequestDTO) {
        logger.info("POST - Social Meli - (registerNewPromoPost) Body: " + promoPostRequestDTO);

        try {
            Optional<User> user = userRepository.findById(promoPostRequestDTO.getUserId());
            Optional<Category> category = categoryRepository.findById(promoPostRequestDTO.getCategory());

            if (user.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + promoPostRequestDTO.getUserId()), HttpStatus.BAD_REQUEST);

            if (category.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Category not found for id: " + promoPostRequestDTO.getCategory()), HttpStatus.BAD_REQUEST);

            if (!user.get().getIsSeller())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "You can't create a promo post if you aren't a seller."), HttpStatus.BAD_REQUEST);

            postService.saveNewPromoPost(promoPostRequestDTO);

            return new ResponseEntity<>(new PostResponseSaveDTO(true, "Promo post created with success!"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get posts from followed sellers for user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Posts returned with success"),
            @ApiResponse(code = 400, message = "User not encountered")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "int", value = "Id from user that the search will be made")
    })
    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<?> getPostsFromFollowedSellers(@PathVariable("userId") Long userId) {
        logger.info("GET - Social Meli - (getPostsFromFollowedSellers) User: " + userId);

        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.BAD_REQUEST);

            List<PostResponseFindDTO> postsFound = postService.findPostByFollowed(userId);

            return new ResponseEntity<>(new PostByUserResponseDTO(userId, postsFound), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get promo posts from user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Promo posts returned with success"),
            @ApiResponse(code = 400, message = "User not encountered")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "int", value = "Id from user that the search will be made")
    })
    @GetMapping("/{userId}/list")
    public ResponseEntity<?> getPromoPostsFromUser(@PathVariable("userId") Long userId) {
        logger.info("GET - Social Meli - (getPromoPostsFromUser) User: " + userId);

        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.BAD_REQUEST);

            if (!user.get().getIsSeller())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "You can't return a list of promo posts from a user that isn't a seller: " + userId), HttpStatus.BAD_REQUEST);

            List<PromoPostResponseFindDTO> promoPostsFound = postService.findPromoPostByUser(user.get());

            return new ResponseEntity<>(new PromoPostByUserResponseDTO(userId, user.get().getName(), promoPostsFound), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get count of promo products from seller")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Posts returned with success"),
            @ApiResponse(code = 400, message = "User not encountered")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "int", value = "Id from user that the search will be made")
    })
    @GetMapping("/{userId}/countPromo")
    public ResponseEntity<?> getCountOfPromoProducts(@PathVariable("userId") final Long userId) {
        logger.info("GET - Social Meli - (getCountOfPromoProducts) User: " + userId);

        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "User not found for id: " + userId), HttpStatus.BAD_REQUEST);

            if (!user.get().getIsSeller())
                return new ResponseEntity<>(new DefaultApiResponseDTO(false, "You can't return a count of promo posts from a user that isn't a seller: " + userId), HttpStatus.BAD_REQUEST);

            Long promoPostsCount = postService.countPostsByUser(user.get());

            return new ResponseEntity<>(new PromoProductsCountResponseDTO(userId, user.get().getName(), promoPostsCount), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
