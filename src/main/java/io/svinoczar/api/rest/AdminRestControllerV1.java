package io.svinoczar.api.rest;

import io.svinoczar.api.dto.RewardRequestDTO;
import io.svinoczar.api.entity.Response;
import io.svinoczar.api.experience.ExperienceService;
import io.svinoczar.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminRestControllerV1 {
    private UserService userService;



}