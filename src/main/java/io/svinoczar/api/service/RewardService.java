package io.svinoczar.api.service;

import io.svinoczar.api.dto.RewardResponseDTO;
import io.svinoczar.api.entity.RewardEntity;
import io.svinoczar.api.experience.ExperienceService;
import io.svinoczar.api.repository.RewardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RewardService {
    private final ExperienceService experienceService;
    private final UserService userService;
    private final RewardRepository rewardRepository;

    public Mono<RewardResponseDTO> registerReward(RewardEntity reward, Long userId) {
        log.info("REWARD REGISTRATION...");
        return rewardRepository.save(
                        reward.toBuilder()
                                .value(reward.getValue())
                                .reason(reward.getReason())
                                .description(reward.getDescription())
                                .rewardedUserId(userId)
                                .createdAt(OffsetDateTime.now())
                                .valid(reward.isValid())
                                .isVisible(reward.isVisible())
                                .build())
                .flatMap(savedReward -> {
                    log.debug("REWARD (id={}, userId={}, value={}, reason={}) SUCCESSFULLY REGISTERED.",
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
                                            .createdAt(OffsetDateTime.now())
                                            .updatedAt(OffsetDateTime.now())
                                            .valid(savedReward.isValid())
                                            .isVisible(savedReward.isVisible())
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
                        .updatedAt(OffsetDateTime.now())
                        .valid(reward.isValid())
                        .isVisible(reward.isVisible())
                        .build()
        ).doOnSuccess(savedReward -> {
            log.debug("REWARD (id={}, userId={}, value={}, reason={}) SUCCESSFULLY UPDATED.",
                    savedReward.getId(), savedReward.getRewardedUserId(), savedReward.getValue(), savedReward.getReason());
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
