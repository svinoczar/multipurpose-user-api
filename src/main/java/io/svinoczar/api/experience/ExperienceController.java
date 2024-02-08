package io.svinoczar.api.experience;

import io.svinoczar.api.entity.Request;
import io.svinoczar.api.entity.Response;
import io.svinoczar.api.entity.RewardRequest;
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
public class ExperienceController {
    private final ExperienceService service;

    @PostMapping("/reward")
    public ResponseEntity<Mono<Response>> reward(@RequestBody RewardRequest request) {
        return ResponseEntity.ok(service.reward(request)); //todo: check what need to use just Mono or ResponseEntity
    }
}