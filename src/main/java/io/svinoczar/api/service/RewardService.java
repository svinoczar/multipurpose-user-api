package io.svinoczar.api.service;

import io.svinoczar.api.dto.RewardResponseDTO;
import io.svinoczar.api.entity.*;
import io.svinoczar.api.experience.ExperienceService;
import io.svinoczar.api.repository.RewardReasonRepository;
import io.svinoczar.api.repository.RewardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final RewardReasonRepository rewardReasonRepository;

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
                                            .createdAt(OffsetDateTime.now())
                                            .updatedAt(OffsetDateTime.now())
                                            .valid(savedReward.isValid())
                                            .isVisible(savedReward.isVisible())

                                            .message("Reward successfully registered.")
                                            .statusCode(HttpStatus.CREATED.value())
                                            .timeStamp(OffsetDateTime.now())
                                            .build()));
                });
    }


    public Mono<RewardResponseDTO> updateReward(RewardEntity reward, UserRole role) {
        if (role.status() >= 2) {
            log.info("REWARD UPDATING...");
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
                log.info("THE REWARD (id={}, userId={}, value={}, reason={}) SUCCESSFULLY UPDATED.",
                        savedReward.getId(), savedReward.getRewardedUserId(), savedReward.getValue(), savedReward.getReason());
            }).flatMap(savedReward -> Mono.just(
                    RewardResponseDTO.rewardResponseBuilder()
                            .value(savedReward.getValue())
                            .reason(savedReward.getReason())
                            .updatedAt(OffsetDateTime.now())
                            .valid(savedReward.isValid())
                            .isVisible(savedReward.isVisible())

                            .message("Reward successfully updated.")
                            .statusCode(HttpStatus.ACCEPTED.value())
                            .timeStamp(OffsetDateTime.now())
                            .build()));
        } else {
            log.warn("THE USER id={} DOESN'T HAVE PERMISSION TO UPDATE THE REWARD." , reward.getRewardedUserId());
            return Mono.create(response -> RewardResponseDTO.rewardResponseBuilder()
                    .message("Permission denied.")
                    .statusCode(HttpStatus.FORBIDDEN.value())
                    .timeStamp(OffsetDateTime.now())
                    .build());
        }
    }


    public Mono<RewardResponseDTO> deleteReward(RewardEntity reward, UserRole role) {
        if (role.status() >= 2) {
            return rewardRepository.deleteById(reward.getId()).flatMap(r -> Mono.just(
                RewardResponseDTO.rewardResponseBuilder()
                        .updatedAt(OffsetDateTime.now())
                        .message("Reward successfully removed.")
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .timeStamp(OffsetDateTime.now())
                        .build()));
            } else {
            log.warn("THE USER id={} DOESN'T HAVE PERMISSION TO REMOVE THE REWARD." , reward.getRewardedUserId());
            return Mono.create(response -> RewardResponseDTO.rewardResponseBuilder()
                    .message("Permission denied.")
                    .statusCode(HttpStatus.FORBIDDEN.value())
                    .timeStamp(OffsetDateTime.now())
                    .build());
        }
    }


    //NOTE: RewardReason block:
    public Mono<Response> addNewRewardReason(RewardReasonEntity reason, UserRole role) {
        if (role.status() >= 2) {
            return rewardReasonRepository.save(
                    reason.toBuilder()
                            .reason(reason.getReason())
                            .stringName(reason.getReason())
                            .description(reason.getDescription())
                            .type(reason.getType())
                            .enabled(true)
                            .createdAt(OffsetDateTime.now())
                            .updatedAt(OffsetDateTime.now())
                            .build()
            ).doOnSuccess(savedReason -> {
                log.info("REWARD REASON (id={}, reason={}, description={}) SUCCESSFULLY CREATED.",
                        savedReason.getId(), savedReason.getReason(), savedReason.getDescription());
            }).flatMap(savedReason -> Mono.just(
                    Response.builder()
                            .message("New reward reason successfully created.")
                            .statusCode(HttpStatus.CREATED.value())
                            .timeStamp(OffsetDateTime.now())
                            .devMessage("………")
                            .build()
            ));
        } else {
            log.warn("USER DOESN'T HAVE PERMISSION TO CREATE REWARD REASON.");
            return Mono.create(response -> Response.builder()
                    .message("Permission denied.")
                    .statusCode(HttpStatus.FORBIDDEN.value())
                    .timeStamp(OffsetDateTime.now())
                    .build());
        }
    }


    public Mono<Response> updateRewardReason(RewardReasonEntity reason, UserRole role) {
        if (role.status() >= 2) {
            return rewardReasonRepository.save(
                    reason.toBuilder()
                            .reason(reason.getReason())
                            .stringName(reason.getReason())
                            .description(reason.getDescription())
                            .type(reason.getType())
                            .enabled(reason.isEnabled())
                            .updatedAt(OffsetDateTime.now())
                            .build()
            ).doOnSuccess(savedReason -> {
                log.info("REWARD REASON (id={}, reason={}) SUCCESSFULLY UPDATED.",
                        savedReason.getId(), savedReason.getReason());
            }).flatMap(savedReason -> Mono.just(
                    Response.builder()
                            .message("Reward reason successfully updated.")
                            .statusCode(HttpStatus.ACCEPTED.value())
                            .timeStamp(OffsetDateTime.now())
                            .devMessage("………")
                            .build()
            ));
        } else {
            log.warn("USER DOESN'T HAVE PERMISSION TO UPDATE REWARD REASON.");
            return Mono.create(response -> Response.builder()
                    .message("Permission denied.")
                    .statusCode(HttpStatus.FORBIDDEN.value())
                    .timeStamp(OffsetDateTime.now())
                    .build());
        }
    }

    public Mono<Response> deleteRewardReason(RewardReasonEntity reason, UserRole role) {
        if (role.status() >= 2) {
            return rewardReasonRepository.deleteById(reason.getId()).flatMap(r -> Mono.just(
                    Response.builder()
                            .message("Reward reason successfully removed.")
                            .statusCode(HttpStatus.ACCEPTED.value())
                            .timeStamp(OffsetDateTime.now())
                            .build()));
        } else {
            log.warn("USER DOESN'T HAVE PERMISSION TO REMOVE REWARD REASON.");
            return Mono.create(response -> Response.builder()
                    .message("Permission denied.")
                    .statusCode(HttpStatus.FORBIDDEN.value())
                    .timeStamp(OffsetDateTime.now())
                    .build());
        }
    }
}