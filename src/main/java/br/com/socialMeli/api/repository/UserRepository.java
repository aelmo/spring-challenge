package br.com.socialMeli.api.repository;

import br.com.socialMeli.api.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findById(final Long id);

    Page<User> findAll(final Pageable pageable);
}
