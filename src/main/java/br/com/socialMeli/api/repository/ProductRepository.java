package br.com.socialMeli.api.repository;

import br.com.socialMeli.api.model.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findById(final Long id);
}
