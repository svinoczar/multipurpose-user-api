package io.svinoczar.api.service;

import io.svinoczar.api.entity.Response;
import io.svinoczar.api.entity.ScoreEntity;
import io.svinoczar.api.entity.UserEntity;
import io.svinoczar.api.repository.ScoreRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.OffsetDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final UserService userService;

    public Mono<Response> scoreUser(ScoreEntity score) {
        return scoreRepository.save(
                        score.toBuilder()
                                .value(score.getValue())
                                .ratedUserId(score.getRatedUserId())
                                .valuerId(score.getValuerId())
                                .createdAt(OffsetDateTime.now())
                                .updatedAt(OffsetDateTime.now())
                                .build())
                .flatMap(savedScore -> {
                    log.info("USER WITH ID={} WAS RATED {} BY USER WITH ID={}",
                            score.getRatedUserId(), score.getValue(), score.getValuerId());
                    return userService.getUserById(score.getRatedUserId())
                            .publishOn(Schedulers.boundedElastic())
                            .flatMap(user -> {
                                user.setScoresCount(user.getScoresCount() + 1);
                                return scoreRepository.findAll()
                                        .map(ScoreEntity::getValue)
                                        .reduce(Integer::sum)
                                        .flatMap(prevScoreSum -> {
                                            return scoreRepository.count()
                                                    .map(count -> {
                                                        float currentScore = prevScoreSum != null ?
                                                                (float) prevScoreSum / (count) :
                                                                score.getValue();
                                                        user.setScore(currentScore);
//                                                        log.info("USER SCORE = {}", user);
                                                        return (UserEntity) user;
                                                    })
                                                    .flatMap(userService::updateUser);
                                        });
                            })
                            .thenReturn(
                                    Response.builder()
                                            .timeStamp(OffsetDateTime.now())
                                            .message("USER WAS RATED SUCCESSFULLY")
                                            .statusCode(HttpStatus.CREATED.value())
                                            .build());
                });
    }
}