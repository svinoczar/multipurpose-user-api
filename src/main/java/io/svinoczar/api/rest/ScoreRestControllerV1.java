package io.svinoczar.api.rest;

import io.svinoczar.api.dto.ScoreDTO;
import io.svinoczar.api.entity.Response;
import io.svinoczar.api.entity.ScoreEntity;
import io.svinoczar.api.mapper.ScoreMapper;
import io.svinoczar.api.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/score")
@RequiredArgsConstructor
public class ScoreRestControllerV1 {
    private final ScoreService scoreService;
    private final ScoreMapper scoreMapper;

    @PostMapping("/add")
    public Mono<Response> add(@RequestBody ScoreDTO score) {
        return scoreService.scoreUser(scoreMapper.map(score));
    }
}
