package br.com.socialMeli.api.service;

import br.com.socialMeli.api.dto.request.PostRequestDTO;
import br.com.socialMeli.api.dto.response.PostResponseFindDTO;
import br.com.socialMeli.api.model.Post;

import java.util.List;

public interface PostService {

    Post saveNewPost(final PostRequestDTO postRequestDTO);

    List<PostResponseFindDTO> findPostByFollowed(final Long userId);
}
