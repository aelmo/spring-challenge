package br.com.socialMeli.api.service;

import br.com.socialMeli.api.dto.request.PostRequestDTO;
import br.com.socialMeli.api.dto.request.PromoPostRequestDTO;
import br.com.socialMeli.api.dto.response.PostResponseFindDTO;
import br.com.socialMeli.api.dto.response.PromoPostResponseFindDTO;
import br.com.socialMeli.api.model.Post;
import br.com.socialMeli.api.model.User;

import java.util.List;

public interface PostService {

    Post saveNewPost(final PostRequestDTO postRequestDTO);

    Post saveNewPromoPost(final PromoPostRequestDTO promoPostRequestDTO);

    List<PostResponseFindDTO> findPostByFollowed(final Long userId);

    List<PromoPostResponseFindDTO> findPromoPostByUser(final User user);

    Long countPostsByUser(final User user);

    List<PostResponseFindDTO> sortPostsByOrder(List<PostResponseFindDTO> postsFound, String order);
}
