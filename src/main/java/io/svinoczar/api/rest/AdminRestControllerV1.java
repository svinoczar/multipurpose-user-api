package io.svinoczar.api.rest;

import io.svinoczar.api.dto.RewardRequestDTO;
import io.svinoczar.api.dto.UserDTO;
import io.svinoczar.api.entity.Response;
import io.svinoczar.api.entity.UserEntity;
import io.svinoczar.api.entity.UserRole;
import io.svinoczar.api.experience.ExperienceService;
import io.svinoczar.api.mapper.UserMapper;
import io.svinoczar.api.service.FileService;
import io.svinoczar.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminRestControllerV1 {
    private final UserService userService;
    private final FileService fileService;
    private final UserMapper userMapper;
    @Value("${admin.key.auth}")
    private String adminKey;

    @PostMapping("/register")
    public Mono<UserDTO> register(@RequestHeader ("Admin-Key") String headerKey,
//                                  @RequestBody String requestAdminKey,
                                  @RequestBody UserDTO dto) {
        Map<String, Object> headersMap = fileService.parseCSVtoMap(fileService.readFileFromResources("headers.csv"));
//        log.info("Admin-Key header: {}, \norigin header: {}, \nrequestAdminKey: {}, \nadmin key: {}",
//                headerKey, headersMap.get("Admin-Key"), requestAdminKey, adminKey);
        log.info("Admin-Key header: {}, \norigin header: {}",
                headerKey, headersMap.get("Admin-Key"));
//        if (requestAdminKey.equals(adminKey) && headersMap.get("Admin-Key").equals(headerKey)) {
        if (headersMap.get("Admin-Key").equals(headerKey)) {
            UserEntity entity = userMapper.map(dto);
            return userService.registerUser(entity, true)
                    .map(userMapper::map);
        } else {
            return Mono.just(UserDTO.builder()
                    .username("FORBIDDEN")
                    .userRole(UserRole.USER)
                    .enabled(false)
                    .build());
        }
    }

}