package io.svinoczar.api.repository;

import io.svinoczar.api.entity.UserEntity;
import io.svinoczar.api.entity.UserRole;
import io.svinoczar.api.security.CustomPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
    Mono<UserEntity> findByUsername(String username);
    UserRole getRoleById(Long id);
    UserRole getRoleByUsername(String username);
}