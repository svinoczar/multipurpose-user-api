package io.svinoczar.api.service;

import io.svinoczar.api.entity.UserEntity;
import io.svinoczar.api.entity.UserRole;
import io.svinoczar.api.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<UserEntity> registerUser(UserEntity user) {
        return userRepository.save(
                user.toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .userRole(UserRole.USER)
                        .enabled(true)
                        .createdAt(OffsetDateTime.now())
                        .updatedAt(OffsetDateTime.now())
                        .build()
        ).doOnSuccess(u -> {
            log.info("user: {} created in `registerUser`", u);
        });
    }

    public Mono<UserEntity> updateUser(UserEntity user) {
        return userRepository.save(
                user.toBuilder()
                        .username(user.getUsername())
                        .userRole(user.getUserRole())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .xp(user.getXp())
                        .level(user.getLevel())
                        .enabled(user.isEnabled())
                        .updatedAt(OffsetDateTime.now())
                        .build()
        ).doOnSuccess(u -> {
            log.info("USER: (id={}, username={}) UPDATED.", u.getId(), u.getUsername());
        });
    }


    public Mono<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
