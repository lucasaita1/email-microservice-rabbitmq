package dev.lucas.user.service;

import dev.lucas.user.entity.UserEntity;
import dev.lucas.user.producer.UserProducer;
import dev.lucas.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;

    @Transactional
    public UserEntity saveAndPublish(UserEntity user) {
        userRepository.save(user);
        userProducer.sendMensagem(user);
        return user;
    }
}
