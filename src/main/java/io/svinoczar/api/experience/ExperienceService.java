package io.svinoczar.api.experience;

import io.svinoczar.api.dto.RewardDTO;
import io.svinoczar.api.dto.UserDTO;
import io.svinoczar.api.entity.*;
import io.svinoczar.api.exception.RewardException;
import io.svinoczar.api.exception.UnauthorizedException;
import io.svinoczar.api.mapper.DefaultUserMapper;
import io.svinoczar.api.mapper.UserMapper;
import io.svinoczar.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExperienceService {
    private final UserRepository userRepository;
    private final DefaultUserMapper defaultUserMapper;
    private void addExperiencePoints(UserDTO dto, int xp) {
        /*
        -
        -
        auth block here
        -
        -
        **/
        UserEntity user = defaultUserMapper.map(dto);
        user.setXp(user.getXp() + xp);
        userRepository.save(user).subscribe(); //todo: remove subscribe, rewrite save block
    }

    public Mono<Response> reward(RewardRequest request) {
        RewardDTO reward = request.getRewardDTO(); //todo: finish

        addExperiencePoints(request.getUser(), reward.getValue());
        return Mono.just(new Response())
                .onErrorResume(e -> Mono.error(new RewardException(e.getMessage())));
    }



}
