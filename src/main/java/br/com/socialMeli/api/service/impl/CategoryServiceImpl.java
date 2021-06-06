package br.com.socialMeli.api.service.impl;

import br.com.socialMeli.api.dto.request.CategoryRequestDTO;
import br.com.socialMeli.api.model.Category;
import br.com.socialMeli.api.repository.CategoryRepository;
import br.com.socialMeli.api.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category createNewCategory(CategoryRequestDTO categoryDTO) {
        logger.info("Category Service - Create New Category");

        try {
            Category category = new Category(
                    categoryDTO.getName()
            );

            return categoryRepository.save(category);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }
}
