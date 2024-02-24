package io.svinoczar.api.experience;

import io.svinoczar.api.dto.RewardDTO;
import io.svinoczar.api.dto.UserDTO;
import io.svinoczar.api.entity.*;
import io.svinoczar.api.exception.RewardException;
import io.svinoczar.api.mapper.UserMapper;
import io.svinoczar.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExperienceService {
    private final UserService userService;
    private final UserMapper userMapper;
    private void addExperiencePoints(UserDTO dto, int xp) {
        /*
        -
        -
        auth block here
        -
        -
        **/
        UserEntity user = userMapper.map(dto);
        dto.setXp(dto.getXp() + xp);
        userService.updateUser(user).subscribe(); //todo: remove subscribe, rewrite save block
    }

    public Mono<Response> reward(RewardDTO dto) {
//        addExperiencePoints(dto.getUserId(), reward.getValue());
        UserEntity user = userMapper.map(dto.getUserId());
        dto.getUserId().setXp(dto.getUserId().getXp() + dto.getValue());
        return Mono.just(new Response())
                .onErrorResume(e -> Mono.error(new RewardException(e.getMessage())));
    }



}
