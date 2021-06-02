package br.com.socialMeli.api.controllerTest;

import br.com.socialMeli.api.controller.UserController;
import br.com.socialMeli.api.model.User;
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

    private static final int HTTP_STATUS_CODE_CREATED = 201;
    private static final int HTTP_STATUS_CODE_BAD_REQUEST = 400;

    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowersService followersService;

    @Mock
    private User userMock;

    @Mock
    private User userMock1;

    @Before
    public void setup() {
        userController = new UserController(userRepository, followersService);
    }

    @Before
    public void init() {
        List<User> userList = new ArrayList<>();

        userMock = new User(
                1L,
                "Joe",
                "joe@gmail.com",
                new Date(1999 - 8 - 3),
                "12345678910",
                true,
                new ArrayList<>(),
                new ArrayList<>(),
                new Date(),
                new Date()
        );

        userMock1 = new User(
                2L,
                "Brandon",
                "brandon@gmail.com",
                new Date(2001 - 8 - 3),
                "12345678910",
                true,
                new ArrayList<>(),
                new ArrayList<>(),
                new Date(),
                new Date()
        );

        userList.add(userMock);
        userList.add(userMock);

        when(userRepository.findById(userMock.getId())).thenReturn(Optional.of(userMock));
        when(userRepository.findById(userMock1.getId())).thenReturn(Optional.of(userMock1));
    }

    @Test
    public void shouldSaveToFollowersListIfValidAndReturnCreated() {
        logger.info("TEST - POST - Social Meli - (followUser) - shouldSaveToFollowersListIfValidAndReturnCreated()");

        ResponseEntity<?> responseEntity = userController.followUser(1L, 2L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_CREATED);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotSaveToFollowersListIfUsersDontExistAndReturnBadRequest() {
        logger.info("TEST - POST - Social Meli - (followUser) - shouldNotSaveToFollowersListIfUsersDontExistAndReturnBadRequest()");

        ResponseEntity<?> responseEntity = userController.followUser(10L, 20L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_BAD_REQUEST);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotSaveToFollowersListIfSameUserAndReturnBadRequest() {
        logger.info("TEST - POST - Social Meli - (followUser) - shouldNotSaveToFollowersListIfSameUserAndReturnBadRequest()");

        ResponseEntity<?> responseEntity = userController.followUser(1L, 1L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_BAD_REQUEST);
        assertNotNull(responseEntity.getBody());
    }

}
