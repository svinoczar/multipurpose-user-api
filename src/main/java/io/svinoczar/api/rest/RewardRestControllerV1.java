package io.svinoczar.api.rest;

import io.svinoczar.api.dto.RewardDTO;
import io.svinoczar.api.dto.RewardReasonDTO;
import io.svinoczar.api.dto.RewardResponseDTO;
import io.svinoczar.api.entity.Response;
import io.svinoczar.api.mapper.RewardMapper;
import io.svinoczar.api.mapper.RewardReasonMapper;
import io.svinoczar.api.security.CustomPrincipal;
import io.svinoczar.api.service.RewardService;
import io.svinoczar.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/reward")
public class RewardRestControllerV1 {
    private final RewardService rewardService;
    private final UserService userService;

    private final RewardMapper rewardMapper;
    private final RewardReasonMapper rewardReasonMapper;

    //TODO: ПРОТЕСТИТЬ ВСЕ ЭНД-ПОИНТЫ!
    //TODO: ПРОТЕСТИТЬ ВСЕ ЭНД-ПОИНТЫ!
    //TODO: ПРОТЕСТИТЬ ВСЕ ЭНД-ПОИНТЫ!
    //TODO: ПРОТЕСТИТЬ ВСЕ ЭНД-ПОИНТЫ!
    //TODO: ПРОТЕСТИТЬ ВСЕ ЭНД-ПОИНТЫ!

    @PostMapping("/test")
    public Mono<RewardResponseDTO> testReward(@RequestBody RewardDTO dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        log.info("DTO: {}", dto);
        log.info("PRINCIPAL: {}", customPrincipal);
        return rewardService.registerReward(rewardMapper.map(dto), customPrincipal.getId());
    }

    @PostMapping("/add")
    public Mono<RewardResponseDTO> reward(@RequestBody RewardDTO dto,
                                          @RequestHeader ("User-Agent") String userAgent,
                                          @RequestHeader ("Origin") String origin,
                                          Authentication authentication) {
        if (userAgent != null && origin != null) {
            CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
            return rewardService.registerReward(rewardMapper.map(dto), customPrincipal.getId());
        } else {
            return Mono.just(
                    RewardResponseDTO.rewardResponseBuilder()
                            .message("Permission denied.")
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .timeStamp(OffsetDateTime.now())
                            .build());
        }
    }

    @PostMapping("/update")
    public Mono<RewardResponseDTO> updateReward(@RequestBody RewardDTO dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return rewardService.updateReward(rewardMapper.map(dto), userService.getUserRole(customPrincipal));
    }

    @DeleteMapping("/delete")
    public Mono<RewardResponseDTO> deleteReward(@RequestBody RewardDTO dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return rewardService.deleteReward(rewardMapper.map(dto), userService.getUserRole(customPrincipal));
    }


    @PostMapping("/reason/add")
    public Mono<Response> addRR(@RequestBody RewardReasonDTO dto, Authentication authentication){
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return rewardService.addNewRewardReason(rewardReasonMapper.map(dto), userService.getUserRole(customPrincipal));
    }

    @PostMapping("/reason/update")
    public Mono<Response> updateRR(@RequestBody RewardReasonDTO dto, Authentication authentication){
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return rewardService.addNewRewardReason(rewardReasonMapper.map(dto), userService.getUserRole(customPrincipal));
    }

    @DeleteMapping("/reason/delete")
    public Mono<Response> deleteRR(@RequestBody RewardReasonDTO dto, Authentication authentication){
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return rewardService.deleteRewardReason(rewardReasonMapper.map(dto), userService.getUserRole(customPrincipal));
    }
}