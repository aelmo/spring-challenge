package br.com.socialMeli.api.serviceTest;

import br.com.socialMeli.api.model.Followers;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.FollowersRepository;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.impl.FollowersServiceImpl;
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
public class FollowersServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(FollowersServiceTest.class);

    private FollowersServiceImpl followersService;

    private static final Long USER_SELLER_ID = 1L;
    private static final Long USER_NOT_SELLER_ID = 2L;
    private static final Long USER_NOT_EXISTS = 50L;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowersRepository followersRepository;

    @Mock
    private User userMock;

    @Mock
    private User userMock1;

    @Mock
    private Followers followersMock;

    @Before
    public void setup() {
        followersService = new FollowersServiceImpl(userRepository,followersRepository);
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
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                new Date(),
                new Date()
        );

        followersMock = new Followers(
                1L,
                userMock,
                userMock1
        );

        userList.add(userMock);
        userList.add(userMock);

        when(userRepository.findById(USER_SELLER_ID)).thenReturn(Optional.of(userMock));
        when(userRepository.findById(USER_NOT_SELLER_ID)).thenReturn(Optional.of(userMock1));

        when(followersRepository.save(followersMock)).thenReturn(followersMock);

        when(followersService.saveFollow(USER_NOT_SELLER_ID, USER_SELLER_ID)).thenReturn(followersMock);
    }

    @Test
    public void shouldReturnFollowersWhenCreatedWithSuccess() {
        logger.info("TEST - Followers Service - Save Follow - shouldReturnIdWhenCreatedWithSuccess()");

        assertThat(followersService.saveFollow(USER_NOT_SELLER_ID, USER_SELLER_ID)).isSameAs(followersRepository.save(followersMock));
    }

    @Test
    public void shouldReturnNullWhenFailedToCreate() {
        logger.info("TEST - Followers Service - Save Follow - shouldReturnNullWhenFailedToCreate()");

        assertThat(followersService.saveFollow(0L, 3L)).isNull();
    }

    @Test
    public void shouldReturnNullWhenUserNotSeller() {
        logger.info("TEST - Followers Service - Save Follow - shouldReturnNullWhenUserNotSeller()");

        assertThat(followersService.saveFollow(USER_SELLER_ID, USER_NOT_SELLER_ID)).isNull();
    }

    @Test
    public void shouldReturnFollowerCountWhenUserFoundAndSeller() {
        logger.info("TEST - Followers Service - Followers Count By Id - shouldReturnFollowerCountWhenUserFoundAndSeller()");

        assertThat(followersService.followersCountById(USER_SELLER_ID)).isNotNull();
    }

    @Test
    public void shouldReturnNullWhenUserFoundAndNotSeller() {
        logger.info("TEST - Followers Service - Followers Count By Id - shouldReturnNullWhenUserFoundAndNotSeller()");

        assertThat(followersService.followersCountById(USER_NOT_SELLER_ID)).isNull();
    }

    @Test
    public void shoudlReturnNullWhenUserNotFound() {
        logger.info("TEST - Followers Service - Followers Count By Id - shoudlReturnNullWhenUserNotFound()");

        assertThat(followersService.followersCountById(USER_NOT_EXISTS)).isNull();
    }
}
