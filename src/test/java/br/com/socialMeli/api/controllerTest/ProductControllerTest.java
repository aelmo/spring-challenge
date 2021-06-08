package br.com.socialMeli.api.controllerTest;

import br.com.socialMeli.api.controller.ProductController;
import br.com.socialMeli.api.dto.request.PostRequestDTO;
import br.com.socialMeli.api.dto.request.ProductRequestDTO;
import br.com.socialMeli.api.dto.request.PromoPostRequestDTO;
import br.com.socialMeli.api.dto.response.*;
import br.com.socialMeli.api.model.Category;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.CategoryRepository;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductControllerTest.class);

    private static final int HTTP_STATUS_CODE_OK = 200;
    private static final int HTTP_STATUS_CODE_CREATED = 201;
    private static final int HTTP_STATUS_CODE_BAD_REQUEST = 400;
    private static final int HTTP_STATUS_CODE_NOT_FOUND = 404;

    private ProductController productController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PostService postService;

    @Mock
    private PostRequestDTO postRequestDTOMock;

    @Mock
    private PostRequestDTO postRequestDTOMockUserNotSeller;

    @Mock
    private PromoPostRequestDTO promoPostRequestDTOMock;

    @Mock
    private PromoPostRequestDTO promoPostRequestDTOMockUserNotSeller;

    @Mock
    private ProductRequestDTO productRequestDTOMock;

    @Mock
    private User userMock;

    @Mock
    private User userNotSellerMock;

    @Mock
    private Category categoryMock;

    @Before
    public void setup() {
        productController = new ProductController(userRepository, categoryRepository, postService);
    }

    @Before
    public void init() {
        productRequestDTOMock = new ProductRequestDTO(
                "Test",
                "Test",
                "Test",
                "Test",
                "Test"
        );

        postRequestDTOMock = new PostRequestDTO(
                1L,
                new ArrayList<>(List.of(productRequestDTOMock)),
                1L,
                400D
        );

        postRequestDTOMockUserNotSeller = new PostRequestDTO(
                2L,
                new ArrayList<>(List.of(productRequestDTOMock)),
                1L,
                400D
        );

        promoPostRequestDTOMock = new PromoPostRequestDTO(
                1L,
                new ArrayList<>(List.of(productRequestDTOMock)),
                1L,
                400D,
                0.25D
        );

        promoPostRequestDTOMockUserNotSeller = new PromoPostRequestDTO(
                2L,
                new ArrayList<>(List.of(productRequestDTOMock)),
                1L,
                400D,
                0.25D
        );

        userMock = new User(
                1L,
                "Joe",
                "joe@gmail.com",
                "12345678910",
                true,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new Date()
        );

        userNotSellerMock = new User(
                2L,
                "Joe",
                "joee@gmail.com",
                "12345678911",
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new Date()
        );

        categoryMock = new Category(
                1L,
                "Test",
                new ArrayList<>()
        );
    }

    @Test
    public void shouldCreateNewPostAndReturnCreated() {
        logger.info("TEST - POST - Social Meli - (registerNewPost) - shouldCreateNewPostAndReturnCreated()");

        when(userRepository.findById(postRequestDTOMock.getUserId())).thenReturn(Optional.of(userMock));
        when(categoryRepository.findById(postRequestDTOMock.getCategory())).thenReturn(Optional.of(categoryMock));
        ResponseEntity<?> responseEntity = productController.registerNewPost(postRequestDTOMock);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_CREATED);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(PostResponseSaveDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotCreateNewPostWhenUserNotFoundAndReturnNotFound() {
        logger.info("TEST - POST - Social Meli - (registerNewPost) - shouldNotCreateNewPostWhenUserNotFoundAndReturnNotFound()");

        when(categoryRepository.findById(postRequestDTOMock.getCategory())).thenReturn(Optional.of(categoryMock));
        ResponseEntity<?> responseEntity = productController.registerNewPost(postRequestDTOMock);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_NOT_FOUND);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotCreateNewPostWhenCategoryNotFoundAndReturnNotFound() {
        logger.info("TEST - POST - Social Meli - (registerNewPost) - shouldNotCreateNewPostWhenCategoryNotFoundAndReturnNotFound()");

        when(userRepository.findById(postRequestDTOMock.getUserId())).thenReturn(Optional.of(userMock));
        ResponseEntity<?> responseEntity = productController.registerNewPost(postRequestDTOMock);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_NOT_FOUND);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotCreateNewPostWhenUserNotSellerAndReturnBadRequest() {
        logger.info("TEST - POST - Social Meli - (registerNewPost) - shouldNotCreateNewPostWhenUserNotSellerAndReturnBadRequest()");

        when(userRepository.findById(postRequestDTOMockUserNotSeller.getUserId())).thenReturn(Optional.of(userNotSellerMock));
        when(categoryRepository.findById(postRequestDTOMockUserNotSeller.getCategory())).thenReturn(Optional.of(categoryMock));
        ResponseEntity<?> responseEntity = productController.registerNewPost(postRequestDTOMockUserNotSeller);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_BAD_REQUEST);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldCreateNewPromoPostAndReturnCreated() {
        logger.info("TEST - POST - Social Meli - (registerNewPromoPost) - shouldCreateNewPromoPostAndReturnCreated()");

        when(userRepository.findById(promoPostRequestDTOMock.getUserId())).thenReturn(Optional.of(userMock));
        when(categoryRepository.findById(promoPostRequestDTOMock.getCategory())).thenReturn(Optional.of(categoryMock));
        ResponseEntity<?> responseEntity = productController.registerNewPromoPost(promoPostRequestDTOMock);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_CREATED);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(PostResponseSaveDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotCreateNewPromoPostWhenUserNotFoundAndReturnNotFound() {
        logger.info("TEST - POST - Social Meli - (registerNewPromoPost) - shouldNotCreateNewPromoPostWhenUserNotFoundAndReturnNotFound()");

        when(categoryRepository.findById(promoPostRequestDTOMock.getCategory())).thenReturn(Optional.of(categoryMock));
        ResponseEntity<?> responseEntity = productController.registerNewPromoPost(promoPostRequestDTOMock);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_NOT_FOUND);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotCreateNewPromoPostWhenCategoryNotFoundAndReturnNotFound() {
        logger.info("TEST - POST - Social Meli - (registerNewPromoPost) - shouldNotCreateNewPromoPostWhenCategoryNotFoundAndReturnNotFound()");

        when(userRepository.findById(promoPostRequestDTOMock.getUserId())).thenReturn(Optional.of(userMock));
        ResponseEntity<?> responseEntity = productController.registerNewPromoPost(promoPostRequestDTOMock);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_NOT_FOUND);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotCreateNewPromoPostWhenUserNotSellerAndReturnBadRequest() {
        logger.info("TEST - POST - Social Meli - (registerNewPromoPost) - shouldNotCreateNewPromoPostWhenUserNotSellerAndReturnBadRequest()");

        when(userRepository.findById(promoPostRequestDTOMockUserNotSeller.getUserId())).thenReturn(Optional.of(userNotSellerMock));
        when(categoryRepository.findById(promoPostRequestDTOMockUserNotSeller.getCategory())).thenReturn(Optional.of(categoryMock));
        ResponseEntity<?> responseEntity = productController.registerNewPromoPost(promoPostRequestDTOMockUserNotSeller);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_BAD_REQUEST);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldReturnPostsFromFollowedSellersAndReturnOk() {
        logger.info("TEST - GET - Social Meli - (getPostsFromFollowedSellers) - shouldReturnPostsFromFollowedSellersAndReturnOk()");

        when(userRepository.findById(userMock.getId())).thenReturn(Optional.of(userMock));
        ResponseEntity<?> responseEntity = productController.getPostsFromFollowedSellers(userMock.getId(), "");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_OK);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(PostByUserResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotReturnPostsFromFollowedSellersWhenUserNotFoundAndReturnNotFound() {
        logger.info("TEST - GET - Social Meli - (getPostsFromFollowedSellers) - shouldNotReturnPostsFromFollowedSellersWhenUserNotFoundAndReturnNotFound()");

        ResponseEntity<?> responseEntity = productController.getPostsFromFollowedSellers(userMock.getId(), "");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_NOT_FOUND);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldReturnPromoPostsFromUserAndReturnOk() {
        logger.info("TEST - GET - Social Meli - (getPromoPostsFromUser) - shouldReturnPromoPostsFromUserAndReturnOk()");

        when(userRepository.findById(userMock.getId())).thenReturn(Optional.of(userMock));
        ResponseEntity<?> responseEntity = productController.getPromoPostsFromUser(userMock.getId());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_OK);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(PromoPostByUserResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotReturnPromoPostsFromUserWhenUserNotFoundAndReturnNotFound() {
        logger.info("TEST - GET - Social Meli - (getPromoPostsFromUser) - shouldNotReturnPromoPostsFromUserWhenUserNotFoundAndReturnNotFound()");

        ResponseEntity<?> responseEntity = productController.getPromoPostsFromUser(userMock.getId());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_NOT_FOUND);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotReturnPromoPostsFromUserWhenUserNotSellerAndReturnBadRequest() {
        logger.info("TEST - GET - Social Meli - (getPromoPostsFromUser) - shouldNotReturnPromoPostsFromUserWhenUserNotSellerAndReturnBadRequest()");

        when(userRepository.findById(userNotSellerMock.getId())).thenReturn(Optional.of(userNotSellerMock));
        ResponseEntity<?> responseEntity = productController.getPromoPostsFromUser(userNotSellerMock.getId());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_BAD_REQUEST);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldReturnCountOfPromosAndReturnOk() {
        logger.info("TEST - GET - Social Meli - (getCountOfPromoProducts) - shouldReturnCountOfPromosAndReturnOk()");

        when(userRepository.findById(userMock.getId())).thenReturn(Optional.of(userMock));
        ResponseEntity<?> responseEntity = productController.getCountOfPromoProducts(userMock.getId());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_OK);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(PromoProductsCountResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotReturnCountOfPromosWhenUserNotFoundAndReturnNotFound() {
        logger.info("TEST - GET - Social Meli - (getCountOfPromoProducts) - shouldNotReturnCountOfPromosWhenUserNotFoundAndReturnNotFound()");

        ResponseEntity<?> responseEntity = productController.getPromoPostsFromUser(userMock.getId());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_NOT_FOUND);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotReturnCountOfPromosWhenUserNotSellerAndReturnBadRequest() {
        logger.info("TEST - GET - Social Meli - (getCountOfPromoProducts) - shouldNotReturnCountOfPromosWhenUserNotSellerAndReturnBadRequest()");

        when(userRepository.findById(userNotSellerMock.getId())).thenReturn(Optional.of(userNotSellerMock));
        ResponseEntity<?> responseEntity = productController.getPromoPostsFromUser(userNotSellerMock.getId());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_BAD_REQUEST);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }
}
