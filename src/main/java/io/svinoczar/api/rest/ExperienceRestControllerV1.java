package io.svinoczar.api.rest;

import io.svinoczar.api.dto.RewardDTO;
import io.svinoczar.api.dto.RewardResponseDTO;
import io.svinoczar.api.entity.RewardEntity;
import io.svinoczar.api.entity.UserEntity;
import io.svinoczar.api.experience.ExperienceService;
import io.svinoczar.api.mapper.RewardMapper;
import io.svinoczar.api.mapper.UserMapper;
import io.svinoczar.api.security.CustomPrincipal;
import io.svinoczar.api.service.RewardService;
import io.svinoczar.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
    private final RewardService rewardService;
    private final RewardMapper rewardMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/testReward")
    public Mono<RewardResponseDTO> testReward(@RequestBody RewardDTO dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
//        dto.getRewardedUserId(userMapper.map(userService.getUserById(customPrincipal.getId()).flatMap(d -> Mono.just(UserEntity.builder()))));
        System.out.println(dto);
        System.out.println(customPrincipal.getName() + " " + customPrincipal.getId());
        return rewardService.registerReward(rewardMapper.map(dto), customPrincipal.getId())
                .flatMap(reward -> Mono.just(
                        RewardResponseDTO.rewardResponseBuilder()
                                .value(reward.getValue())
                                .receivedAt(reward.getReceivedAt())
                                .valid(reward.isValid())
                                .isVisible(reward.isVisible())
                                .rewardedUserName(customPrincipal.getName())
                                .reason(reward.getRewardReason())
                                .build()
                ));
    }
}