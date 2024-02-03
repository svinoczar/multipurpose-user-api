package io.svinoczar.api.repository;

import io.netty.util.concurrent.Promise;
import io.svinoczar.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Promise<UserEntity> findByUsername(String username);
}
