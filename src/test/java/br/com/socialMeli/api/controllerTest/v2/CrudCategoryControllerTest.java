package br.com.socialMeli.api.controllerTest.v2;

import br.com.socialMeli.api.controller.v2.CrudCategoryController;
import br.com.socialMeli.api.dto.request.CategoryRequestDTO;
import br.com.socialMeli.api.dto.response.CategoryResponseSaveDTO;
import br.com.socialMeli.api.model.Category;
import br.com.socialMeli.api.service.CategoryService;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CrudCategoryControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(CrudCategoryControllerTest.class);

    private CrudCategoryController crudCategoryController;

    private static final int HTTP_STATUS_CODE_CREATED = 201;

    @Mock
    private CategoryService categoryService;

    @Mock
    private Category categoryMock;

    @Mock
    private CategoryRequestDTO categoryRequestDTOMock;

    @Before
    public void setup() {
        crudCategoryController = new CrudCategoryController(categoryService);
    }

    @Before
    public void init() {
        categoryMock = new Category(
                1L,
                "Test",
                new ArrayList<>()
        );

        categoryRequestDTOMock = new CategoryRequestDTO(
                "Test"
        );

        when(categoryService.createNewCategory(categoryRequestDTOMock)).thenReturn(categoryMock);
    }

    @Test
    public void shouldReturnCategoryWhenTryingToCreateNewOneAndReturnCreated() {
        logger.info("TEST - POST - Social Meli - (createNewCategory) - shouldReturnCategoryWhenTryingToCreateNewOneAndReturnCreated()");

        ResponseEntity<?> responseEntity = crudCategoryController.createNewCategory(categoryRequestDTOMock);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HTTP_STATUS_CODE_CREATED);
        assertThat(responseEntity.getBody().getClass()).isEqualTo(CategoryResponseSaveDTO.class);
        assertNotNull(responseEntity.getBody());
    }
}
