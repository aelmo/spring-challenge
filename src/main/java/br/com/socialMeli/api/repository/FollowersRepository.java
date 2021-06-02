package br.com.socialMeli.api.repository;

import br.com.socialMeli.api.model.Followers;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public interface FollowersRepository extends CrudRepository<Followers, Long> {

    Optional<Followers> findById(final Long id);

    @Query(value = "SELECT COUNT(from) FROM Followers f WHERE f.from = :id", nativeQuery = true)
    Long getFollowerCountById(@Param("id") final Long id);

    @Query(value = "SELECT f.from FROM Followers f WHERE f.from = :id", nativeQuery = true)
    List<Long> getFollowerListById(@Param("id") final Long id);
}
