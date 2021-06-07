package br.com.socialMeli.api.service;

import br.com.socialMeli.api.dto.response.UniqueUserFollowedResponseDTO;
import br.com.socialMeli.api.dto.response.UniqueUserFollowerResponseDTO;
import br.com.socialMeli.api.model.Followers;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowersService {

    Followers saveFollow(final Long userId, final Long userIdFollowed);

    ResponseEntity<Object> saveUnfollow(final Long followersId);

    Long getFollowersCountById(final Long userId);

    List<UniqueUserFollowerResponseDTO> getFollowersListById(final Long userId, final String order);

    List<UniqueUserFollowedResponseDTO> getFollowedsListById(final Long userId, final String order);
}
