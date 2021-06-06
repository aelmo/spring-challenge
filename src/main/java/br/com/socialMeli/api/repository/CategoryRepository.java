package br.com.socialMeli.api.repository;

import br.com.socialMeli.api.model.Category;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findById(final Long id);
}
