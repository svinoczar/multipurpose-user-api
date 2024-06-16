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
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ExperienceService {
    private final UserService userService;
    private final RewardService rewardService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

//    public Mono<RewardResponseDTO> updateXp(RewardRequestDTO dto) {
////        Float currentXp = 0f;
//        var u = userService.getUserById(dto.getRewardedUserId()).doOnSuccess(user -> {
//            var currentXp = user.getXp();
//            user.setXp(currentXp + dto.getValue());
//            userService.updateUser(user);
//        }).flatMap(user -> Mono.just(
//                RewardResponseDTO.rewardResponseBuilder()
//                        .value(dto.getValue())
//                        .reason(dto.getReason())
//                        .rewardedUserName(user.getUsername())
//                        .receivedAt(OffsetDateTime.now())
//                        .valid(false)
//                        .isVisible(false)
//                        .build()));
//    }

}
