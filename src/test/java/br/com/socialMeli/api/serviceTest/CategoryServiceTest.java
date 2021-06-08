package br.com.socialMeli.api.serviceTest;

import br.com.socialMeli.api.dto.request.CategoryRequestDTO;
import br.com.socialMeli.api.model.Category;
import br.com.socialMeli.api.repository.CategoryRepository;
import br.com.socialMeli.api.service.impl.CategoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceTest.class);

    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private Category categoryMock;

    @Mock
    private CategoryRequestDTO categoryRequestDTOMock;

    @Before
    public void setup() {
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Before
    public void init() {
        categoryMock = new Category(
                1L,
                "Teste",
                new ArrayList<>()
        );

        categoryRequestDTOMock = new CategoryRequestDTO(
          "Teste"
        );
    }

    @Test
    public void shouldReturnCategoryWhenTryingToSave() {
        logger.info("TEST - Category Service - Create New Category - shouldReturnCategoryWhenTryingToSave()");

        when(categoryService.createNewCategory(categoryRequestDTOMock)).thenReturn(categoryMock);

        assertThat(categoryService.createNewCategory(categoryRequestDTOMock).getId()).isEqualTo(categoryMock.getId());
        assertNotNull(categoryService.createNewCategory(categoryRequestDTOMock));
    }

    @Test
    public void shouldReturnNullWhenTryingToSaveInvalidDto() {
        logger.info("TEST - Category Service - Create New Category - shouldReturnNullWhenTryingToSaveInvalidDto()");

        assertThat(categoryService.createNewCategory(new CategoryRequestDTO())).isNull();
    }
}
