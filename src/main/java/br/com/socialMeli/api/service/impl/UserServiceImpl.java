package br.com.socialMeli.api.service.impl;

import br.com.socialMeli.api.dto.request.UserRequestDTO;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User createNewUser(UserRequestDTO userDTO) {
        logger.info("User Service - Create New User");

        try {
            String email = userDTO.getEmail();
            String cpf = userDTO.getCpf();

            if (userRepository.existsByEmail(email)) {
                logger.error("Email already registered");
                return null;
            }

            if (userRepository.existsByCpf(cpf)) {
                logger.error("Cpf already registered");
                return null;
            }

            User user = new User(
                    userDTO.getName(),
                    userDTO.getEmail(),
                    userDTO.getCpf(),
                    userDTO.getIsSeller(),
                    new Date()
            );

            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }
}
