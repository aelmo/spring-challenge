package br.com.socialMeli.api.service;

import br.com.socialMeli.api.model.Followers;

public interface FollowersService {

    Followers saveFollow(final Long userId, final Long userIdFollowed);

    Long followersCountById(final Long userId);
}
