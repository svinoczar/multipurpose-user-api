package io.svinoczar.api.rest;

import io.svinoczar.api.dto.RewardDTO;
import io.svinoczar.api.dto.RewardResponseDTO;
import io.svinoczar.api.mapper.RewardMapper;
import io.svinoczar.api.security.CustomPrincipal;
import io.svinoczar.api.service.RewardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/xp")
public class ExperienceRestControllerV1 {
    private final RewardService rewardService;
    private final RewardMapper rewardMapper;

    @PostMapping("/testReward")
    public Mono<RewardResponseDTO> testReward(@RequestBody RewardDTO dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        log.info("DTO: {}", dto);
        log.info("PRINCIPAL: {}", customPrincipal);
        return rewardService.registerReward(rewardMapper.map(dto), customPrincipal.getId());
    }

    @PostMapping("/reward")
    public Mono<RewardResponseDTO> reward(@RequestBody RewardDTO dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return rewardService.registerReward(rewardMapper.map(dto), customPrincipal.getId());
    }
}