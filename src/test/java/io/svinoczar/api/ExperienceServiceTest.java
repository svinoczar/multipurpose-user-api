package io.svinoczar.api;

import io.svinoczar.api.entity.UserEntity;
import io.svinoczar.api.experience.ExperienceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExperienceServiceTest {
    private final ExperienceService experienceService = new ExperienceService();

    @Test
    void updateLevelTest(){
        var test1 = generateUserEntity(1550f, 3);


        Assertions.assertEquals(4, test1.getLevel());
    }

    private UserEntity generateUserEntity(Float xp, Integer lvl) {
        return UserEntity.builder()
                .id(0L)
                .username("TEST")
                .xp(xp)
                .level(lvl)
                .build();
    }

}
