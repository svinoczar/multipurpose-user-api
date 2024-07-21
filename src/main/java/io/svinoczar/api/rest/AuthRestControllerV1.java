package io.svinoczar.api.rest;

import io.svinoczar.api.dto.AuthResponseDTO;
import io.svinoczar.api.dto.AuthRequestDTO;
import io.svinoczar.api.dto.UserDTO;
import io.svinoczar.api.entity.UserEntity;
import io.svinoczar.api.mapper.UserMapper;
import io.svinoczar.api.security.CustomPrincipal;
import io.svinoczar.api.security.SecurityService;
import io.svinoczar.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestControllerV1 {
    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDTO> register(@RequestBody UserDTO dto) {
        UserEntity entity = userMapper.map(dto);
        return userService.registerUser(entity)
                .map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDTO> login(@RequestBody AuthRequestDTO dto) {
        return securityService.authenticate(dto.getUsername(), dto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDTO.authResponseBuilder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getIssuedAt())
                                .expiredAt(tokenDetails.getExpiresAt())
                                .build()
                ));
    }

    @GetMapping("/info")
    public Mono<UserDTO> getUserInfo(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        log.debug(customPrincipal.getId().toString());
        log.debug(userService.getUserById(customPrincipal.getId()).toString());
        return userService.getUserById(customPrincipal.getId())
                .map(userMapper::map);
    }
}
