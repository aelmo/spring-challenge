package br.com.socialMeli.api.repository;

import br.com.socialMeli.api.model.Followers;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface FollowersRepository extends CrudRepository<Followers, Long> {

    Optional<Followers> findById(final Long id);
}
