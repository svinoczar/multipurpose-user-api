package io.svinoczar.api.experience;

import io.svinoczar.api.dto.RewardRequestDTO;
import io.svinoczar.api.dto.RewardResponseDTO;
import io.svinoczar.api.dto.UserDTO;
import io.svinoczar.api.entity.*;
import io.svinoczar.api.exception.RewardException;
import io.svinoczar.api.mapper.UserMapper;
import io.svinoczar.api.service.RewardService;
import io.svinoczar.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExperienceService {
    private final UserService userService;
    private final RewardService rewardService;
    private final UserMapper userMapper;

//    public Mono<RewardResponseDTO> reward(RewardRequestDTO dto) {
//        RewardEntity entity = new RewardEntity().toBuilder()
//                .value(dto.getValue())
//                .rewardReason(dto.getReason())
//                .rewardDescription(dto.getDescription())
//                .rewardedUserId(userService.getUserByUsername(dto.getRewardedUserName()).block())
//                .receivedAt(LocalDateTime.now())
//                .build();
//        rewardService.registerReward(entity);
//        RewardResponseDTO response = RewardResponseDTO.rewardResponseBuilder()
//                .value(entity.getValue())
//                .reason(entity.getRewardReason())
//                .rewardedUserName(entity.getRewardedUserId().getUsername())
//                .rewardedAt(entity.getReceivedAt())
//                .valid(true)
//                .isVisible(true) //todo: change method to set visibility
//                .build();
//        return Mono.just(response)
//                .onErrorResume(e -> Mono.error(new RewardException(e.getMessage())));
//    }

//    public Mono<RewardResponseDTO> test(RewardRequestDTO dto) {
//        RewardResponseDTO response = RewardResponseDTO.rewardResponseBuilder()
//                .value(1f)
//                .reason(RewardReason.TEST_REWARD)
//                .rewardedUserName("TEST")
//                .rewardedAt(LocalDateTime.now())
//                .valid(true)
//                .isVisible(false)
//                .build();
//        return Mono.just(response)
//                .onErrorResume(e -> Mono.error(new RewardException(e.getMessage())));
//    }

    public Mono<RewardResponseDTO> reward(RewardRequestDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO currentUser = (UserDTO) authentication.getPrincipal();
        RewardEntity entity = new RewardEntity().toBuilder()
                .value(dto.getValue())
                .rewardReason(dto.getReason())
                .rewardDescription(dto.getDescription())
                .rewardedUserId(userService.getUserByUsername(dto.getRewardedUserName()).block())
                .receivedAt(LocalDateTime.now())
                .build();
        rewardService.registerReward(entity);
        RewardResponseDTO response = RewardResponseDTO.rewardResponseBuilder()
                .value(entity.getValue())
                .reason(entity.getRewardReason())
                .rewardedUserName(entity.getRewardedUserId().getUsername())
                .rewardedAt(entity.getReceivedAt())
                .valid(true)
                .isVisible(true) //todo: change method to set visibility
                .build();
        return Mono.just(response)
                .onErrorResume(e -> Mono.error(new RewardException(e.getMessage())));
    }

}
