package br.com.socialMeli.api.repository;

import br.com.socialMeli.api.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findById(final Long id);

    Boolean existsByEmail(final String email);

    Boolean existsByCpf(final String cpf);
}
