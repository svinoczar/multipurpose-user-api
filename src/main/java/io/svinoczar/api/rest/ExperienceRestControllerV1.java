package io.svinoczar.api.rest;

import io.svinoczar.api.dto.RewardDTO;
import io.svinoczar.api.entity.Response;
import io.svinoczar.api.entity.RewardRequest;
import io.svinoczar.api.experience.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/reward")
    public Mono<Response> reward(@RequestBody RewardDTO dto) {
        return experienceService.reward(dto); //todo: check what need to use just Mono or ResponseEntity
    }
}