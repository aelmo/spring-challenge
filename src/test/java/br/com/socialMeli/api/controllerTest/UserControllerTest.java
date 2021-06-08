package br.com.socialMeli.api.controllerTest;

import br.com.socialMeli.api.controller.UserController;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.FollowersRepository;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.FollowersService;
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
public class UserControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    private static final int HTTP_STATUS_CODE_OK = 200;
    private static final int HTTP_STATUS_CODE_BAD_REQUEST = 400;
    private static final int HTTP_STATUS_CODE_NOT_FOUND = 404;
    private static final int HTTP_STATUS_CODE_CONFLICT = 409;

    private static final Long USER_SELLER_ID = 1L;
    private static final Long USER_NOT_SELLER_ID = 2L;
    private static final Long USER_NOT_EXIST_ID = 50L;

    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowersRepository followersRepository;

    @Mock
    private FollowersService followersService;

    @Mock
    private User userMock;

    @Mock
    private User userMock1;

    @Before
    public void setup() {
        userController = new UserController(userRepository, followersRepository, followersService);
    }

    @Before
    public void init() {
        List<User> userList = new ArrayList<>();

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

        userMock1 = new User(
                2L,
                "Brandon",
                "brandon@gmail.com",
                "12345678910",
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new Date()
        );

        userList.add(userMock);
        userList.add(userMock);

        when(userRepository.findById(userMock.getId())).thenReturn(Optional.of(userMock));
        when(userRepository.findById(userMock1.getId())).thenReturn(Optional.of(userMock1));
    }

    @Test
    public void shouldSaveToFollowersListIfValidAndReturnOk() {
        logger.info("TEST - POST - Social Meli - (followUser) - shouldSaveToFollowersListIfValidAndReturnOk()");

        ResponseEntity<?> responseEntity = userController.followUser(USER_NOT_SELLER_ID, USER_SELLER_ID);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_OK);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotSaveToFollowersListIfUsersDontExistAndReturnNotFound() {
        logger.info("TEST - POST - Social Meli - (followUser) - shouldNotSaveToFollowersListIfUsersDontExistAndReturnNotFound()");

        ResponseEntity<?> responseEntity = userController.followUser(10L, 20L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_NOT_FOUND);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotSaveToFollowersListIfSameUserAndReturnConflict() {
        logger.info("TEST - POST - Social Meli - (followUser) - shouldNotSaveToFollowersListIfSameUserAndReturnConflict()");

        ResponseEntity<?> responseEntity = userController.followUser(USER_SELLER_ID, USER_SELLER_ID);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_CONFLICT);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotSaveToFollowersListIfUserIsNotSellerAndReturnBadRequest() {
        logger.info("TEST - POST - Social Meli - (followUser) - shouldNotSaveToFollowersListIfUserIsNotSellerAndReturnBadRequest()");

        ResponseEntity<?> responseEntity = userController.followUser(USER_SELLER_ID, USER_NOT_SELLER_ID);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_BAD_REQUEST);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldShowFollowersCountAndReturnOk() {
        logger.info("TEST - GET - Social Meli - (getFollowerCountForUser) - shouldShowFollowersCountAndReturnOk()");

        ResponseEntity<?> responseEntity = userController.getFollowerCountForUser(USER_SELLER_ID);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_OK);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotShowFollowersCountIfUserDontExistAndReturnNotFound() {
        logger.info("TEST - GET - Social Meli - (getFollowerCountForUser) - shouldNotShowFollowersCountIfUserDontExistAndReturnNotFound()");

        ResponseEntity<?> responseEntity = userController.getFollowerCountForUser(USER_NOT_EXIST_ID);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_NOT_FOUND);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotShowFollowersCountIfUserNotSellerAndReturnBadRequest() {
        logger.info("TEST - GET - Social Meli - (getFollowerCountForUser) - shouldNotShowFollowersCountIfUserNotSellerAndReturnBadRequest()");

        ResponseEntity<?> responseEntity = userController.getFollowerCountForUser(USER_NOT_SELLER_ID);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_BAD_REQUEST);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldShowFollowersListAndReturnOk() {
        logger.info("TEST - GET - Social Meli - (getFollowerListForUser) - shouldShowFollowersListAndReturnOk()");

        ResponseEntity<?> responseEntity = userController.getFollowerListForUser(USER_SELLER_ID, "");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_OK);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotShowFollowersListIfUserDontExistAndReturnNotFound() {
        logger.info("TEST - GET - Social Meli - (getFollowerListForUser) - shouldNotShowFollowersListIfUserDontExistAndReturnNotFound()");

        ResponseEntity<?> responseEntity = userController.getFollowerListForUser(USER_NOT_EXIST_ID, "");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_NOT_FOUND);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotShowFollowersListIfUserNotSellerAndReturnBadRequest() {
        logger.info("TEST - GET - Social Meli - (getFollowerListForUser) - shouldNotShowFollowersListIfUserNotSellerAndReturnBadRequest()");

        ResponseEntity<?> responseEntity = userController.getFollowerListForUser(USER_NOT_SELLER_ID, "");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_BAD_REQUEST);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldShowFollowedsListAndReturnOk() {
        logger.info("TEST - GET - Social Meli - (getFollowedListForUser) - shouldShowFollowedsListAndReturnOk()");

        ResponseEntity<?> responseEntity = userController.getFollowerListForUser(USER_SELLER_ID, "");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_OK);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotShowFollowedsListIfUserDontExistAndReturnNotFound() {
        logger.info("TEST - GET - Social Meli - (getFollowedListForUser) - shouldNotShowFollowedsListIfUserDontExistAndReturnNotFound()");

        ResponseEntity<?> responseEntity = userController.getFollowerListForUser(USER_NOT_EXIST_ID, "");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_NOT_FOUND);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotShowFollowedsListIfUserNotSellerAndReturnBadRequest() {
        logger.info("TEST - GET - Social Meli - (getFollowedListForUser) - shouldNotShowFollowedsListIfUserNotSellerAndReturnBadRequest()");

        ResponseEntity<?> responseEntity = userController.getFollowerListForUser(USER_NOT_SELLER_ID, "");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_BAD_REQUEST);
        assertNotNull(responseEntity.getBody());
    }
}
