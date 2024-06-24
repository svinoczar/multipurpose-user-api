package io.svinoczar.api.experience;

import io.svinoczar.api.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ExperienceService {
//    private final Float firstLevelXp = 0f;
    @Value("${experience.level.step.type}")
    private LevelStepType levelStepType;
    @Value("${experience.level.step.value}")
    private Float levelStep;
    @Value("${experience.level.step.custom}")
    private String custom;
    @Value("${experience.level.start.value}")
    private Float startXp;
    @Value("${experience.level.start.0is1}")
    private boolean startLevelIs0;

    private Integer startLevel = startLevelIs0 ? 0 : 1;

    public UserEntity updateLevel(UserEntity user) {
        int level = user.getLevel();
        float xp = user.getXp();
        switch (levelStepType) {
            case MONO ->  user.setLevel((int) (xp / levelStep)); //DONE
            case LINEAR -> user.setLevel(expProcessor(levelStepType, startLevel, startXp, startXp, Pair.of(level, xp)).getFirst()); //DONE
            case EXP -> user.setLevel(expProcessor(levelStepType, startLevel, startXp, startXp, Pair.of(level, xp)).getFirst()); //DONE
            case CUSTOM ->  user.setLevel(handleCustomLevelStep(custom).get(0)); //TODO: Finish
            default -> user.setLevel(level);
        }
        return user;
    }

    private Map<Float, Integer> handleCustomLevelStep(String custom) {
        return Arrays.stream(custom
                        .replaceAll("[{}]", "")
                        .strip()
                        .split(", "))
                .map(element -> element.split(":"))
                .collect(Collectors.toMap(
                        keyValue -> Float.parseFloat(keyValue[1]),
                        keyValue -> Integer.parseInt(keyValue[0])
                ));
    }

    private int sum (Integer numb) {
        int sum = 0;
        for (int i = 1; i < numb; i++) {
            sum += i;
        }
        return sum;
    }


    private Pair<Integer, Pair<Float, Float>> expProcessor(LevelStepType type, Integer lvl, Float prevLevelXp, Float sumLevelXp, Pair<Integer, Float> userData) {
        switch (type) {
            case LINEAR -> {
                return (sumLevelXp + prevLevelXp + 2 * levelStep <= userData.getSecond())
                        ? expProcessor(type, lvl++,prevLevelXp + levelStep, sumLevelXp + prevLevelXp + levelStep, userData)
                        : Pair.of(lvl++, Pair.of(prevLevelXp + levelStep, sumLevelXp + prevLevelXp + levelStep));
            }
            //                                  lvl:2   xp:800
            //                    prevLevelXp                 sumLevelXp
            // 1.                     100                        100
            // 2.                100 + 100 = 200             100 + 200 = 300
            // 3.                200 + 100 = 300             300 + 300 = 600
            // 4.                300 + 100 = 400             600 + 400 = 1000
            // 5.                400 + 100 = 500            1000 + 500 = 1500
            case EXP -> {
                return (sumLevelXp + prevLevelXp * levelStep * levelStep <= userData.getSecond())
                    ? expProcessor(type, lvl++,prevLevelXp * levelStep, sumLevelXp + prevLevelXp * levelStep, userData)
                    : Pair.of(lvl++, Pair.of(prevLevelXp * levelStep, sumLevelXp + prevLevelXp * levelStep));
            }
            //                                  lvl:2   xp:800
            //                    prevLevelXp                 sumLevelXp
            // 1.                     100                        100
            // 2.                100 x 2 = 200             100 + 200 = 300
            // 3.                200 x 2 = 400             300 + 400 = 700
            // 4.                400 x 2 = 800             700 + 800 = 1500
            // 5.                800 x 2 = 1600            1500 + 1600 = 3100
        }
        return null;
    }

    public void setLevelProperties(LevelStepType levelStepType, Float levelStep, Float startLevel, UserEntity user) {
        this.levelStepType = levelStepType;
        this.startXp = startLevel;
        this.levelStep = levelStep;
    }

}
