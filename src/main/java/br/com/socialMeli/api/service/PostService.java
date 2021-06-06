package br.com.socialMeli.api.service;

import br.com.socialMeli.api.dto.request.PostRequestDTO;
import br.com.socialMeli.api.model.Post;

public interface PostService {

    Post saveNewPost(final PostRequestDTO postRequestDTO);
}
