package dev.lucas.user.service;

import dev.lucas.user.entity.UserEntity;
import dev.lucas.user.producer.UserProducer;
import dev.lucas.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Transactional
    public Optional<UserEntity> updateById(UUID id, UserEntity user) {
        return userRepository.findById(id).map(existingUser -> {

            if (user.getUsername() != null && !user.getUsername().isBlank()) {
                existingUser.setUsername(user.getUsername());
            }

            if (user.getEmail() != null && !user.getEmail().isBlank()) {
                existingUser.setEmail(user.getEmail());
            }

            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                existingUser.setPassword(user.getPassword());
            }

            return existingUser;
        });
    }


        public void deleteById (UUID id){
            userRepository.deleteById(id);
        }
    }
