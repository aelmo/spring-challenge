package br.com.socialMeli.api.controllerTest.v2;

import br.com.socialMeli.api.controller.v2.CrudUserController;
import br.com.socialMeli.api.dto.request.UserRequestDTO;
import br.com.socialMeli.api.dto.response.DefaultApiResponseDTO;
import br.com.socialMeli.api.dto.response.UserResponseSaveDTO;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.UserService;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CrudUserControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(CrudUserControllerTest.class);

    private static final int HTTP_STATUS_CODE_CREATED = 201;
    private static final int HTTP_STATUS_CODE_BAD_REQUEST = 400;

    private CrudUserController crudUserController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private User userMock;

    @Mock
    private User userMock1;

    @Mock
    private UserRequestDTO userRequestDTOMock;

    @Mock
    private UserRequestDTO userRequestDTOMockSameEmail;

    @Mock
    private UserRequestDTO userRequestDTOMockSameCpf;

    @Before
    public void setup() {
        crudUserController = new CrudUserController(userRepository, userService);
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

        userRequestDTOMock = new UserRequestDTO(
                "Joe",
                "joe@gmail.com",
                "12345678910",
                true
        );

        userRequestDTOMockSameEmail = new UserRequestDTO(
                "Joe",
                "joe@gmail.com",
                "12345678919",
                true
        );

        userRequestDTOMockSameCpf = new UserRequestDTO(
                "Joe",
                "joee@gmail.com",
                "12345678910",
                true
        );

        when(userService.createNewUser(userRequestDTOMock)).thenReturn(userMock);
    }

    @Test
    public void shouldReturnUserWhenTryingToCreateNewOneAndReturnCreated() {
        logger.info("TEST - POST - Social Meli - (createNewUser) - shouldReturnCategoryWhenTryingToCreateNewOneAndReturnCreated()");

        ResponseEntity<?> responseEntity = crudUserController.createNewUser(userRequestDTOMock);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_CREATED);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(UserResponseSaveDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotReturnUserWhenTryingToCreateNewOneSameEmailAndReturnBadRequest() {
        logger.info("TEST - POST - Social Meli - (createNewUser) - shouldNotReturnUserWhenTryingToCreateNewOneSameEmailAndReturnBadRequest()");

        when(userRepository.existsByEmail(userRequestDTOMockSameEmail.getEmail())).thenReturn(true);
        ResponseEntity<?> responseEntity = crudUserController.createNewUser(userRequestDTOMockSameEmail);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_BAD_REQUEST);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldNotReturnUserWhenTryingToCreateNewOneSameCpfAndReturnBadRequest() {
        logger.info("TEST - POST - Social Meli - (createNewUser) - shouldNotReturnUserWhenTryingToCreateNewOneSameCpfAndReturnBadRequest()");

        when(userRepository.existsByCpf(userRequestDTOMockSameCpf.getCpf())).thenReturn(true);
        ResponseEntity<?> responseEntity = crudUserController.createNewUser(userRequestDTOMockSameCpf);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_BAD_REQUEST);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(DefaultApiResponseDTO.class);
        assertNotNull(responseEntity.getBody());
    }
}
