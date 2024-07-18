package io.svinoczar.api.service;

import io.svinoczar.api.dto.RewardRequestDTO;
import io.svinoczar.api.dto.RewardResponseDTO;
import io.svinoczar.api.entity.Response;
import io.svinoczar.api.entity.RewardEntity;
import io.svinoczar.api.entity.RewardReason;
import io.svinoczar.api.entity.RewardReasonEntity;
import io.svinoczar.api.experience.ExperienceService;
import io.svinoczar.api.mapper.UserMapper;
import io.svinoczar.api.repository.RewardReasonRepository;
import io.svinoczar.api.repository.RewardRepository;
import io.svinoczar.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RewardService {
    private final ExperienceService experienceService;
    private final UserService userService;

    private final UserRepository userRepository;
    private final RewardRepository rewardRepository;
    private final RewardReasonRepository rewardReasonRepository;

    public Mono<RewardResponseDTO> registerReward(RewardEntity reward, Long userId) {
        log.info("REWARD REGISTRATION...");
        return rewardRepository.save(
                        reward.toBuilder()
                                .value(reward.getValue())
                                .reason(reward.getReason())
                                .description(reward.getDescription())
                                .rewardedUserId(userId)
                                .receivedAt(OffsetDateTime.now())
                                .valid(reward.isValid())
                                .isVisible(reward.isVisible())
                                .build())
                .flatMap(savedReward -> {
                    log.info("REWARD (id={}, userId={}, value={}, reason={}) SUCCESSFULLY REGISTERED.",
                            savedReward.getId(), savedReward.getRewardedUserId(), savedReward.getValue(), savedReward.getReason());
                    return userService.getUserById(userId)
                            .doOnSuccess(user -> {
                                var currentXp = user.getXp();
                                user.setXp(currentXp + savedReward.getValue());
                                user = experienceService.updateLevel(user);
                                userService.updateUser(user).subscribe();
                            })
                            .flatMap(user -> Mono.just(
                                    RewardResponseDTO.rewardResponseBuilder()
                                            .value(savedReward.getValue())
                                            .reason(savedReward.getReason())
                                            .rewardedUserName(user.getUsername())
                                            .receivedAt(OffsetDateTime.now())
                                            .valid(false)
                                            .isVisible(false)
                                            .build()));
                });
    }


    public Mono<RewardEntity> updateReward(RewardEntity reward) {
        return rewardRepository.save(
                reward.toBuilder()
                        .value(reward.getValue())
                        .reason(reward.getReason())
                        .description(reward.getDescription())
                        .rewardedUserId(reward.getRewardedUserId())
                        .receivedAt(OffsetDateTime.now())
                        .valid(true)
                        .isVisible(true)
                        .build()
        ).doOnSuccess(u -> {
            log.debug("user: {} updated in `updateUser`", u);
        });
    }

//    public Mono<Response> addRewardReason(RewardReasonEntity reason) {
//        return new Response.ResponseBuilder().build(); rewardReasonRepository.save(
//                reason.toBuilder()
//                        .rewardReason(reason.getRewardReason())
//                        .build()
//        ).doOnSuccess(r -> {
//            log.info("Added new reward reason - {}", r);
//        });
//    }

}
