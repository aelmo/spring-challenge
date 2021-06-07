package br.com.socialMeli.api.repository;

import br.com.socialMeli.api.model.Post;
import br.com.socialMeli.api.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Primary
public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findAllByUserAndCreatedAtBetweenOrderByCreatedAtAsc(@Param("user") final User user, final Date now, final Date twoWeeksEarlier);

    List<Post> getAllByUserAndHasPromo(@Param("user") final User user, @Param("hasPromo") final Boolean hasPromo);

    Long countAllByUserAndHasPromo(@Param("user") final User user, @Param("hasPromo") final Boolean hasPromo);
}
