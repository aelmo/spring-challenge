package br.com.socialMeli.api.serviceTest;

import br.com.socialMeli.api.dto.request.UserRequestDTO;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User userMock;

    @Mock
    private UserRequestDTO userRequestDTOMock;

    @Before
    public void setup() {
        userService = new UserServiceImpl(userRepository);
    }

    @Before
    public void init() {
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

        userRequestDTOMock = new UserRequestDTO(
                "Joe",
                "joe@gmail.com",
                "12345678910",
                true
        );
    }

    @Test
    public void shouldReturnNullWhenTryingToSaveUniqueEmail() {
        logger.info("TEST - User Service - Create New User - shouldReturnNullWhenTryingToSaveUniqueEmail()");

        when(userRepository.existsByEmail(userRequestDTOMock.getEmail())).thenReturn(Boolean.TRUE);

        assertThat(userService.createNewUser(userRequestDTOMock)).isNull();
    }

    @Test
    public void shouldReturnNullWhenTryingToSaveUniqueCpf() {
        logger.info("TEST - User Service - Create New User - shouldReturnNullWhenTryingToSaveUniqueCpf()");

        when(userRepository.existsByCpf(userRequestDTOMock.getCpf())).thenReturn(Boolean.TRUE);

        assertThat(userService.createNewUser(userRequestDTOMock)).isNull();
    }

    @Test
    public void shouldReturnNullWhenTryingToSaveInvalidDto() {
        logger.info("TEST - User Service - Create New User - shouldReturnNullWhenTryingToSaveInvalidDto()");

        assertThat(userService.createNewUser(userRequestDTOMock)).isNull();
    }
}
