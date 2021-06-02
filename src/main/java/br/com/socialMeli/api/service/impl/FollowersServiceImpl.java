package br.com.socialMeli.api.service.impl;

import br.com.socialMeli.api.dto.response.UniqueUserFollowedResponseDTO;
import br.com.socialMeli.api.dto.response.UniqueUserFollowerResponseDTO;
import br.com.socialMeli.api.model.Followers;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.FollowersRepository;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.FollowersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
    public Long getFollowersCountById(Long userId) {
        logger.info("Followers Service - Get Followers Count By Id");

        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                if (user.get().getIsSeller())
                    return followersRepository.getFollowerCountById(userId);
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

    @Override
    public List<UniqueUserFollowerResponseDTO> getFollowersListById(Long userId) {
        logger.info("Followers Service - Get Followers List By Id");

        try {
            Optional<User> user = userRepository.findById(userId);
            List<UniqueUserFollowerResponseDTO> followerList = new ArrayList<>();

            if (user.isPresent()) {
                if (user.get().getIsSeller()) {
                    List<Long> followersId = followersRepository.getFollowerListById(userId);
                    for (Long id : followersId) {
                        Optional<User> userFound = userRepository.findById(id);
                        if (userFound.isPresent()) {
                            UniqueUserFollowerResponseDTO uniqueUser = new UniqueUserFollowerResponseDTO(userFound.get().getId(), userFound.get().getName());
                            followerList.add(uniqueUser);
                        }
                    }
                    return followerList;
                }
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

    @Override
    public List<UniqueUserFollowedResponseDTO> getFollowedsListById(Long userId) {
        logger.info("Followers Service - Get Followeds List By Id");

        try {
            Optional<User> user = userRepository.findById(userId);
            List<UniqueUserFollowedResponseDTO> followedsList = new ArrayList<>();

            if (user.isPresent()) {
                if (user.get().getIsSeller()) {
                    List<Long> followedsId = followersRepository.getFollowedListById(userId);
                    for (Long id : followedsId) {
                        Optional<User> userFound = userRepository.findById(id);
                        if (userFound.isPresent()) {
                            UniqueUserFollowedResponseDTO uniqueUser = new UniqueUserFollowedResponseDTO(userFound.get().getId(), userFound.get().getName());
                            followedsList.add(uniqueUser);
                        }
                    }
                    return followedsList;
                }
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
