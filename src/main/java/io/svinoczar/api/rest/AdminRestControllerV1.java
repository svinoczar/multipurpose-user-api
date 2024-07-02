package io.svinoczar.api.rest;

import io.svinoczar.api.dto.RewardRequestDTO;
import io.svinoczar.api.entity.Response;
import io.svinoczar.api.experience.ExperienceService;
import io.svinoczar.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminRestControllerV1 {
    private UserService userService;

    @PostMapping("/rewardReason/add")
    public Response regRR(@RequestBody RewardRequestDTO reward){
        //TODO
        return null;
    }

    @DeleteMapping("/rewardReason/rm")
    public void rmRR(){
        //TODO
    }
}