package br.com.socialMeli.api.service;

import br.com.socialMeli.api.dto.response.UniqueUserFollowerResponseDTO;
import br.com.socialMeli.api.model.Followers;

import java.util.List;

public interface FollowersService {

    Followers saveFollow(final Long userId, final Long userIdFollowed);

    Long getFollowersCountById(final Long userId);

    List<UniqueUserFollowerResponseDTO> getFollowersListById(final Long userId);
}
