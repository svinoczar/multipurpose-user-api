package io.svinoczar.api.mapper;

import io.svinoczar.api.dto.UserDTO;
import io.svinoczar.api.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultUserMapper implements UserMapper {
    //todo: test this vvv
    @Override
    public UserDTO map(UserEntity userEntity) {
        return userEntity == null ? null : UserDTO.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .userRole(userEntity.getUserRole())
                .xp(userEntity.getXp())
                .level(userEntity.getLevel())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .enabled(userEntity.isEnabled())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }

    @Override
    public UserEntity map(UserDTO userDTO) {
        return userDTO == null ? null : UserEntity.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .userRole(userDTO.getUserRole())
                .xp(userDTO.getXp())
                .level(userDTO.getLevel())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .enabled(userDTO.isEnabled())
                .createdAt(userDTO.getCreatedAt())
                .updatedAt(userDTO.getUpdatedAt())
                .build();
    }
}