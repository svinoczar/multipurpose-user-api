package io.svinoczar.api.rest;

import io.svinoczar.api.dto.AuthResponseDTO;
import io.svinoczar.api.dto.AuthRequestDTO;
import io.svinoczar.api.dto.UserDTO;
import io.svinoczar.api.entity.UserEntity;
import io.svinoczar.api.mapper.UserMapper;
import io.svinoczar.api.repository.UserRepository;
import io.svinoczar.api.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestControllerV1 {
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDTO> register(@RequestBody UserDTO dto) {
        UserEntity entity = userMapper.map(dto);
        return userRepository.save(entity)
                .map(userMapper::map); //todo: use this mapping everywhere
    }

    @PostMapping("/login")
    public Mono<AuthResponseDTO> login(@RequestBody AuthRequestDTO dto) {
        return securityService.authenticate(dto.getUsername(), dto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDTO.builder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getIssuedAt())
                                .expiredAt(tokenDetails.getExpiresAt())
                                .build()
                ));
    }
}
