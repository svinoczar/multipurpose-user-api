package io.svinoczar.api.rest;

import io.svinoczar.api.dto.RewardDTO;
import io.svinoczar.api.dto.RewardResponseDTO;
import io.svinoczar.api.experience.ExperienceService;
import io.svinoczar.api.mapper.RewardMapper;
import io.svinoczar.api.mapper.UserMapper;
import io.svinoczar.api.security.CustomPrincipal;
import io.svinoczar.api.service.RewardService;
import io.svinoczar.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MarkerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/xp")
public class ExperienceRestControllerV1 {
    private final ExperienceService experienceService;
    private final RewardService rewardService;
    private final RewardMapper rewardMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/testReward")
    public Mono<RewardResponseDTO> testReward(@RequestBody RewardDTO dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        log.info("dto: {}", dto);
        log.info("principal: {}", customPrincipal);
        return rewardService.registerReward(rewardMapper.map(dto), customPrincipal.getId());
    }

}