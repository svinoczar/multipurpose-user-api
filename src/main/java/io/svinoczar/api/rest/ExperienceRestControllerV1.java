package io.svinoczar.api.rest;

import io.svinoczar.api.dto.RewardRequestDTO;
import io.svinoczar.api.dto.RewardResponseDTO;
import io.svinoczar.api.exception.RewardException;
import io.svinoczar.api.experience.ExperienceService;
import io.svinoczar.api.mapper.RewardMapper;
import io.svinoczar.api.mapper.UserMapper;
import io.svinoczar.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/xp")
public class ExperienceRestControllerV1 {
    private final ExperienceService experienceService;
    private final RewardMapper rewardMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/testReward")
    public Mono<RewardResponseDTO> testReward(@RequestBody RewardRequestDTO dto) {
        return experienceService.reward(dto)
                        .onErrorResume(e -> Mono.error(new RewardException(e.getMessage())));
    }

    @PostMapping("/test")
    public Mono<RewardResponseDTO> test(@RequestBody RewardRequestDTO dto) {
        return experienceService.reward(dto)
                .onErrorResume(e -> Mono.error(new RewardException(e.getMessage())));
    }
}