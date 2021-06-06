package br.com.socialMeli.api.repository;

import br.com.socialMeli.api.model.Post;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface PostRepository extends CrudRepository<Post, Long> {
}
