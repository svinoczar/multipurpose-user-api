package io.svinoczar.api.service;

import io.svinoczar.api.entity.Response;
import io.svinoczar.api.entity.UserEntity;
import io.svinoczar.api.entity.UserRole;
import io.svinoczar.api.repository.UserRepository;
import io.svinoczar.api.security.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
            log.info("USER: (id={}, username={}) REGISTERED.", u.getId(), u.getUsername());
        });
    }

    public Mono<UserEntity> registerUser(UserEntity user, boolean accessFlag) {
        if (accessFlag) {
            return userRepository.save(
                    user.toBuilder()
                            .password(passwordEncoder.encode(user.getPassword()))
                            .userRole(UserRole.ADMIN)
                            .enabled(true)
                            .createdAt(OffsetDateTime.now())
                            .updatedAt(OffsetDateTime.now())
                            .build()
            ).doOnSuccess(u -> {
                log.info("USER: (id={}, username={}) REGISTERED.", u.getId(), u.getUsername());
            });
        } else {
            log.warn("ADMINISTRATOR REGISTRATION IS FORBIDDEN.");
        }
        return Mono.just(UserEntity.builder().username("FORBIDDEN").build());
    }

    public Mono<UserEntity> updateUser(UserEntity user) {
        log.info("USER UPDATING...");
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
                        .score(user.getScore())
                        .scoresCount(user.getScoresCount())
                        .updatedAt(OffsetDateTime.now())
                        .build()
        ).doOnSuccess(u -> {
            log.info("USER (id={}, username={}) UPDATED.", u.getId(), u.getUsername());
        });
    }


    public Mono<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserRole getUserRole(CustomPrincipal principal) {
        try {
            return userRepository.getRoleById(principal.getId());
        } catch (Exception e) {
            return userRepository.getRoleByUsername(principal.getName());
        }
    }
}
