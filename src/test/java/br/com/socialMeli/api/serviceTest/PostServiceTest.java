package br.com.socialMeli.api.serviceTest;

import br.com.socialMeli.api.dto.request.PostRequestDTO;
import br.com.socialMeli.api.dto.request.ProductRequestDTO;
import br.com.socialMeli.api.dto.request.PromoPostRequestDTO;
import br.com.socialMeli.api.model.Category;
import br.com.socialMeli.api.model.Post;
import br.com.socialMeli.api.model.Product;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.*;
import br.com.socialMeli.api.service.ProductService;
import br.com.socialMeli.api.service.impl.PostServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PostServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceTest.class);

    private PostServiceImpl postService;

    private static final Long USER_SELLER_ID = 1L;
    private static final Long VALID_CATEGORY_ID = 1L;
    private static final Long USER_NOT_EXISTS = 500L;
    private static final Long CATEGORY_NOT_EXISTS = 500L;
    private static final Long VALID_PRODUCT_ID = 1L;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private FollowersRepository followersRepository;

    @Mock
    private ProductService productService;

    @Mock
    private Post postMock;

    @Mock
    private Product productMock;

    @Mock
    private PostRequestDTO postRequestDTOMock;

    @Mock
    private PromoPostRequestDTO promoPostRequestDTOMock;

    @Mock
    private ProductRequestDTO productRequestDTOMock;

    @Mock
    private User userMock;

    @Mock
    private Category categoryMock;

    @Before
    public void setup() {
        postService = new PostServiceImpl(postRepository, userRepository, productRepository, categoryRepository, followersRepository, productService);
    }

    @Before
    public void init() {
        List<ProductRequestDTO> productsRequest = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        userMock = new User(
                USER_SELLER_ID,
                "Joe",
                "joe@gmail.com",
                "12345678910",
                true,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new Date()
        );

        categoryMock = new Category(
                VALID_CATEGORY_ID,
                "Test",
                new ArrayList<>()
        );

        productRequestDTOMock = new ProductRequestDTO(
                "Test",
                "Test",
                "Test",
                "Test",
                "Test"
        );

        productsRequest.add(productRequestDTOMock);

        postRequestDTOMock = new PostRequestDTO(
                USER_SELLER_ID,
                productsRequest,
                VALID_CATEGORY_ID,
                500D
        );

        promoPostRequestDTOMock = new PromoPostRequestDTO(
                USER_SELLER_ID,
                productsRequest,
                VALID_CATEGORY_ID,
                500D,
                0.25D
        );

        postMock = new Post(
                userMock,
                new Date(),
                categoryMock,
                500D,
                true,
                0.0D
        );

        productMock = new Product(
                VALID_PRODUCT_ID,
                "Test",
                "Test",
                "Test",
                "Test",
                "Test",
                postMock
        );

        products.add(productMock);

        postMock.setProduct(products);

        when(userRepository.findById(USER_SELLER_ID)).thenReturn(Optional.of(userMock));
        when(categoryRepository.findById(VALID_CATEGORY_ID)).thenReturn(Optional.of(categoryMock));
    }

    @Test
    public void shouldReturnPostWhenCreatedWithSuccess() {
        logger.info("TEST - Post Service - Save New Post - shouldReturnPostWhenCreatedWithSuccess()");

        assertThat(postService.saveNewPost(postRequestDTOMock)).isSameAs(postRepository.save(postMock));
    }

    @Test
    public void shouldReturnNullWhenUserNotFound() {
        logger.info("TEST - Post Service - Save New Post - shouldReturnNullWhenUserNotFound()");

        postRequestDTOMock.setUserId(USER_NOT_EXISTS);
        assertThat(postService.saveNewPost(postRequestDTOMock)).isNull();
    }

    @Test
    public void shouldReturnNullWhenUserNotSeller() {
        logger.info("TEST - Post Service - Save New Post - shouldReturnNullWhenUserNotSeller()");

        userMock.setIsSeller(false);
        assertThat(postService.saveNewPost(postRequestDTOMock)).isNull();
    }

    @Test
    public void shouldReturnNullWhenCategoryNotFound() {
        logger.info("TEST - Post Service - Save New Post - shouldReturnNullWhenCategoryNotFound()");

        categoryMock.setId(CATEGORY_NOT_EXISTS);
        assertThat(postService.saveNewPost(postRequestDTOMock)).isNull();
    }

    @Test
    public void shouldReturnPromoPostWhenCreatedWithSuccess() {
        logger.info("TEST - Post Service - Save New Promo Post - shouldReturnPromoPostWhenCreatedWithSuccess()");

        assertThat(postService.saveNewPromoPost(promoPostRequestDTOMock)).isSameAs(postRepository.save(postMock));
    }

    @Test
    public void shouldReturnNullWhenUserNotFoundPromoPost() {
        logger.info("TEST - Post Service - Save New Promo Post - shouldReturnNullWhenUserNotFoundPromoPost()");

        promoPostRequestDTOMock.setUserId(USER_NOT_EXISTS);
        assertThat(postService.saveNewPromoPost(promoPostRequestDTOMock)).isNull();
    }

    @Test
    public void shouldReturnNullWhenUserNotSellerPromoPost() {
        logger.info("TEST - Post Service - Save New Promo Post - shouldReturnNullWhenUserNotSellerPromoPost()");

        userMock.setIsSeller(false);
        assertThat(postService.saveNewPromoPost(promoPostRequestDTOMock)).isNull();
    }

    @Test
    public void shouldReturnNullWhenCategoryNotFoundPromoPost() {
        logger.info("TEST - Post Service - Save New Promo Post - shouldReturnNullWhenCategoryNotFoundPromoPost()");

        categoryMock.setId(CATEGORY_NOT_EXISTS);
        assertThat(postService.saveNewPromoPost(promoPostRequestDTOMock)).isNull();
    }
}
