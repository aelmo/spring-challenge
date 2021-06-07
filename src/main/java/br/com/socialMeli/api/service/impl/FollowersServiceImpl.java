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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowersServiceImpl implements FollowersService {

    private static final Logger logger = LoggerFactory.getLogger(FollowersServiceImpl.class);

    private static final List<String> validOrderers = new ArrayList<>(List.of("name_asc", "desc_asc", "date_asc", "date_desc"));

    private final UserRepository userRepository;

    private final FollowersRepository followersRepository;

    public FollowersServiceImpl(final UserRepository userRepository, final FollowersRepository followersRepository) {
        this.userRepository = userRepository;
        this.followersRepository = followersRepository;
    }

    @Override
    @Transactional
    public Followers saveFollow(final Long userId, final Long userIdFollowed) {
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
    @Transactional
    public ResponseEntity<Object> saveUnfollow(final Long followersId) {
        logger.info("Followers Service - Save Unfollow");

        try {
            Optional<Followers> followers = followersRepository.findById(followersId);

            if (followers.isPresent()) {
                followers.map(record -> {
                    followersRepository.deleteById(followersId);
                    return ResponseEntity.ok().build();
                });
            }

            logger.error("You can't unfollow someone that you don't follow");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Long getFollowersCountById(final Long userId) {
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
    public List<UniqueUserFollowerResponseDTO> getFollowersListById(final Long userId) {
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
                            UniqueUserFollowerResponseDTO uniqueUser = new UniqueUserFollowerResponseDTO(userFound.get().getId(), userFound.get().getName(), userFound.get().getCreatedAt());
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
    public List<UniqueUserFollowedResponseDTO> getFollowedsListById(final Long userId) {
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
                            UniqueUserFollowedResponseDTO uniqueUser = new UniqueUserFollowedResponseDTO(userFound.get().getId(), userFound.get().getName(), userFound.get().getCreatedAt());
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

    @Override
    public List<UniqueUserFollowerResponseDTO> sortFollowerListByOrder(List<UniqueUserFollowerResponseDTO> followerResponseDTOList, String order) {
        if (validOrderers.contains(order)) {
            if (order.equals("name_desc"))
                return followerResponseDTOList.stream().sorted(Comparator.comparing(UniqueUserFollowerResponseDTO::getUserName).reversed()).collect(Collectors.toList());
            if (order.equals("name_asc"))
                return followerResponseDTOList.stream().sorted(Comparator.comparing(UniqueUserFollowerResponseDTO::getUserName)).collect(Collectors.toList());
            if (order.equals("date_asc"))
                return followerResponseDTOList.stream().sorted(Comparator.comparing(UniqueUserFollowerResponseDTO::getCreatedAt).reversed()).collect(Collectors.toList());
            if (order.equals("date_desc"))
                return followerResponseDTOList.stream().sorted(Comparator.comparing(UniqueUserFollowerResponseDTO::getCreatedAt)).collect(Collectors.toList());
        }
        logger.error("Order parameter is not valid");
        return followerResponseDTOList;
    }

    @Override
    public List<UniqueUserFollowedResponseDTO> sortFollowedListByOrder(List<UniqueUserFollowedResponseDTO> followedResponseDTOList, String order) {
        if (validOrderers.contains(order)) {
            if (order.equals("name_desc"))
                return followedResponseDTOList.stream().sorted(Comparator.comparing(UniqueUserFollowedResponseDTO::getUserName).reversed()).collect(Collectors.toList());
            if (order.equals("name_asc"))
                return followedResponseDTOList.stream().sorted(Comparator.comparing(UniqueUserFollowedResponseDTO::getUserName)).collect(Collectors.toList());
            if (order.equals("date_asc"))
                return followedResponseDTOList.stream().sorted(Comparator.comparing(UniqueUserFollowedResponseDTO::getCreatedAt).reversed()).collect(Collectors.toList());
            if (order.equals("date_desc"))
                return followedResponseDTOList.stream().sorted(Comparator.comparing(UniqueUserFollowedResponseDTO::getCreatedAt)).collect(Collectors.toList());
        }
        logger.error("Order parameter is not valid");
        return followedResponseDTOList;
    }
}
