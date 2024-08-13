package io.svinoczar.api.rest;

import io.svinoczar.api.dto.RewardDTO;
import io.svinoczar.api.dto.RewardReasonDTO;
import io.svinoczar.api.dto.RewardResponseDTO;
import io.svinoczar.api.entity.Response;
import io.svinoczar.api.entity.UserRole;
import io.svinoczar.api.mapper.RewardMapper;
import io.svinoczar.api.mapper.RewardReasonMapper;
import io.svinoczar.api.security.CustomPrincipal;
import io.svinoczar.api.service.RewardService;
import io.svinoczar.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

/**
 * @since 0.0.0:10a
 * @author svinoczar
 * */
@Slf4j
@RestController
//@PreAuthorize("hasAuthority('ADMIN')")
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

    /**
     * @since 0.0.0:10a
     * @author svinoczar
     * */
    @PostMapping("/test")
    public Mono<RewardResponseDTO> testReward(@RequestBody RewardDTO dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        log.info("DTO: {}", dto);
        log.info("PRINCIPAL: {}", customPrincipal);
        return rewardService.registerReward(rewardMapper.map(dto), customPrincipal.getId());
    }

    /**
     * @since 0.0.0:10a
     * @author svinoczar
     * */
    @PostMapping("/add")
    public Mono<RewardResponseDTO> reward(@RequestHeader ("User-Agent") String userAgent,
                                          @RequestHeader ("Origin") String origin,
                                          @RequestBody RewardDTO dto,
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

    /**
     * @since 0.0.0:21a
     * @author svinoczar
     * */
    @PostMapping("/update")
    public Mono<RewardResponseDTO> updateReward(@RequestBody RewardDTO dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return rewardService.updateReward(rewardMapper.map(dto), customPrincipal.getRole());
    }

    /**
     * @since 0.0.0:21a
     * @author svinoczar
     * */
    @DeleteMapping("/delete")
    public Mono<RewardResponseDTO> deleteReward(@RequestBody RewardDTO dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return rewardService.deleteReward(rewardMapper.map(dto), customPrincipal.getRole());
    }

    /**
     * @since 0.0.0:21a
     * @author svinoczar
     * */
    @PostMapping("/reason/add")
    public Mono<Response> addRR(@RequestBody RewardReasonDTO dto, Authentication authentication){
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return rewardService.addNewRewardReason(rewardReasonMapper.map(dto), customPrincipal.getRole());
    }

    /**
     * @since 0.0.0:21a
     * @author svinoczar
     * */
    @PostMapping("/reason/update")
    public Mono<Response> updateRR(@RequestBody RewardReasonDTO dto, Authentication authentication){
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return rewardService.addNewRewardReason(rewardReasonMapper.map(dto), customPrincipal.getRole());
    }

    /**
     * @since 0.0.0:21a
     * @author svinoczar
     * */
    @DeleteMapping("/reason/delete")
    public Mono<Response> deleteRR(@RequestBody RewardReasonDTO dto, Authentication authentication){
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return rewardService.deleteRewardReason(rewardReasonMapper.map(dto), customPrincipal.getRole());
    }
}