package br.com.socialMeli.api.controller.v2;

import br.com.socialMeli.api.dto.request.CategoryRequestDTO;
import br.com.socialMeli.api.dto.response.CategoryResponseSaveDTO;
import br.com.socialMeli.api.dto.response.DefaultApiResponseDTO;
import br.com.socialMeli.api.dto.response.UserResponseSaveDTO;
import br.com.socialMeli.api.model.Category;
import br.com.socialMeli.api.repository.CategoryRepository;
import br.com.socialMeli.api.service.CategoryService;
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

@RestController
@RequestMapping("/api/v2/category")
public class CrudCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CrudCategoryController.class);

    private final CategoryService categoryService;

    public CrudCategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "New category")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Categort created with success"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/newCategory")
    public ResponseEntity<?> createNewCategory(@ApiParam(value = "Object for new Category", required = true)
                                               @Valid @RequestBody final CategoryRequestDTO category) {
        logger.info("POST - Social Meli (v2) - (createNewCategory) Category: " + category);

        try {
            Category createdCategory = categoryService.createNewCategory(category);

            return new ResponseEntity<>(new CategoryResponseSaveDTO(true, "Category created with success!", createdCategory.getId()), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(new DefaultApiResponseDTO(false, "Internal server error: " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
