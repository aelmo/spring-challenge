package br.com.socialMeli.api.service.impl;

import br.com.socialMeli.api.model.Followers;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.FollowersRepository;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.FollowersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FollowersServiceImpl implements FollowersService {

    private static final Logger logger = LoggerFactory.getLogger(FollowersServiceImpl.class);

    private final UserRepository userRepository;

    private final FollowersRepository followersRepository;

    public FollowersServiceImpl(final UserRepository userRepository, final FollowersRepository followersRepository) {
        this.userRepository = userRepository;
        this.followersRepository = followersRepository;
    }

    @Override
    @Transactional
    public Followers saveFollow(Long userId, Long userIdFollowed) {
        logger.info("Followers Service - Save Follow");

        try {
            Optional<User> user = userRepository.findById(userId);
            Optional<User> userToBeFollowed = userRepository.findById(userIdFollowed);

            if (user.isPresent() && userToBeFollowed.isPresent()) {
                if (userToBeFollowed.get().getIsSeller()) {
                    Followers followers = new Followers(user.get(), userToBeFollowed.get());
                    return followersRepository.save(followers);
                }
                logger.error("User is not a seller");
                return null;
            }

            logger.error("Users not found");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long followersCountById(Long userId) {
        logger.info("Followers Service - Followers Count By Id");

        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                if (user.get().getIsSeller())
                    return followersRepository.followerCountById(userId);
                logger.error("User is not a seller");
                return null;
            }

            logger.error("User not found");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }
}
