package br.com.socialMeli.api.service;

import br.com.socialMeli.api.dto.request.CategoryRequestDTO;
import br.com.socialMeli.api.model.Category;

public interface CategoryService {

    Category createNewCategory(final CategoryRequestDTO categoryDTO);
}
