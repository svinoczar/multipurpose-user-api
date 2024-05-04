package io.svinoczar.api.service;

import io.svinoczar.api.entity.RewardEntity;
import io.svinoczar.api.repository.RewardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RewardService {
    private final RewardRepository rewardRepository;
    private final UserService userService;

    public Mono<RewardEntity> registerReward(RewardEntity reward) {
        return rewardRepository.save(
                reward.toBuilder()
                        .value(reward.getValue())
                        .rewardReason(reward.getRewardReason())
                        .rewardDescription(reward.getRewardDescription())
                        .rewardedUserId(reward.getRewardedUserId())
                        .receivedAt(LocalDateTime.now())
                        .build()
        ).doOnSuccess(r -> {
            log.info("reward: {} added in `addReward`", r);
        });
    }

    public Mono<RewardEntity> updateReward(RewardEntity reward) {
        return rewardRepository.save(
                reward.toBuilder()
                        .value(reward.getValue())
                        .rewardReason(reward.getRewardReason())
                        .rewardDescription(reward.getRewardDescription())
                        .rewardedUserId(reward.getRewardedUserId())
                        .receivedAt(LocalDateTime.now())
                        .valid(true)
                        .isVisible(true)
                        .build()
        ).doOnSuccess(u -> {
            log.info("user: {} updated in `updateUser`", u);
        });
    }
}