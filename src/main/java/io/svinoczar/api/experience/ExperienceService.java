package io.svinoczar.api.experience;

import io.svinoczar.api.dto.RewardRequestDTO;
import io.svinoczar.api.dto.RewardResponseDTO;
import io.svinoczar.api.entity.*;
import io.svinoczar.api.exception.RewardException;
import io.svinoczar.api.mapper.UserMapper;
import io.svinoczar.api.repository.UserRepository;
import io.svinoczar.api.security.AuthenticationManager;
import io.svinoczar.api.security.CustomPrincipal;
import io.svinoczar.api.service.RewardService;
import io.svinoczar.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ExperienceService {
    private final UserService userService;
    private final RewardService rewardService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

//    public Mono<RewardResponseDTO> reward(RewardRequestDTO dto) {
//        RewardEntity entity = new RewardEntity().toBuilder()
//                .value(dto.getValue())
//                .rewardReason(dto.getReason())
//                .rewardDescription(dto.getDescription())
//                .rewardedUser(new UserEntity())
//                .receivedAt(LocalDateTime.now())
//                .build();
//        rewardService.registerReward(entity, Long userId);
//        RewardResponseDTO response = RewardResponseDTO.rewardResponseBuilder()
//                .value(entity.getValue())
//                .reason(entity.getRewardReason())
//                .rewardedUserName(dto.getRewardedUserName())
//                .receivedAt(LocalDateTime.now())
//                .valid(true)
//                .isVisible(true) //todo: change method to set visibility
//                .build();
//        return Mono.just(response)
//                .onErrorResume(e -> Mono.error(new RewardException(e.getMessage())));
//    }

}
