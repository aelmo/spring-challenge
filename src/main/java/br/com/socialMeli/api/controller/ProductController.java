package br.com.socialMeli.api.controller;

import br.com.socialMeli.api.dto.request.PostRequestDTO;
import br.com.socialMeli.api.dto.response.DefaultApiResponseDTO;
import br.com.socialMeli.api.dto.response.PostResponseSaveDTO;
import br.com.socialMeli.api.model.Category;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.CategoryRepository;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
                                             @Valid @RequestBody PostRequestDTO postRequestDTO) {
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
}
