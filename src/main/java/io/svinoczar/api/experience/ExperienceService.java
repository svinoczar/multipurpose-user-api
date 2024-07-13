package io.svinoczar.api.experience;

import io.svinoczar.api.entity.UserEntity;
import io.svinoczar.api.exception.ZeroXPStartValueError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author svinoczar
 * @since 0.0.0:12a
 *
 *
 * */
@Service
@Slf4j
@Getter
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

    public final Integer startLevel = startLevelIs0 ? 0 : 1;

    //TODO: Теперь custom при выходе за мапу уровней начинает считать, что каждый уровень стоит levelStep xp.
    public UserEntity updateLevel(UserEntity user) {
        int level = user.getLevel();
        float xp = user.getXp();
        Pair<Integer, Float> levelXp = Pair.of(level, xp) ;

        switch (levelStepType) {
            case MONO ->  user.setLevel((int) (xp / levelStep)); //DONE
            case LINEAR -> {
                float currentXP = startXp;
                int currentLVL = startLevel;
                float nextXp = levelStep;
                while ((currentXP + nextXp) <= xp){
                    currentXP += nextXp;
                    nextXp += levelStep;
                    currentLVL++;
                }
                user.setLevel(currentLVL);
            } //DONE

            case EXP -> {
                if (startXp != 0) {
                    int currentLVL = startLevel;
                    float multiplier = levelStep;
                    float requiredXP = startXp * multiplier;

                    while (requiredXP <= xp) {
                        currentLVL++;
                        requiredXP += (float) (startXp * Math.pow(multiplier, currentLVL - 1));
                    }
                    user.setLevel(currentLVL);
                } else {
                    throw new ZeroXPStartValueError(
                            "The initial value of experience must be greater than zero when `experience.level.step.type` = EXP",
                            "XP/EXP/0");
                } //TODO: В целом кажется готово, но следует хорошенько протестить + возможно обработать исключение.
            }

            case CUSTOM ->  {
                Map<Integer, Float> levelMap = handleCustomLevelStep(custom);
                user.setLevel(calcLevel(levelStepType, startLevel, levelMap.get(startLevel), levelMap.get(startLevel), levelXp, levelMap));
            } //DONE
            default -> user.setLevel(level);
        }
        return user;
    }

    private Map<Integer, Float> handleCustomLevelStep(String custom) {
        //TODO: Реализовать обработку случаев типа 5-10:alt
        return Arrays.stream(custom
                        .replaceAll("[{}]", "")
                        .strip()
                        .split(", "))
                .map(element -> element.split(":"))
                .collect(Collectors.toMap(
                        keyValue -> Integer.parseInt(keyValue[0]),
                        keyValue -> Float.parseFloat(keyValue[1])
                ));
    }

    private int sum (Integer numb) {
        int sum = 0;
        for (int i = 1; i < numb; i++) {
            sum += i;
        }
        return sum;
    }


    /**
     * @param type Level step type (LevelStepType enum)
     * @param lvl
     * @param prevLevelXp
     * @param sumLevelXp
     * @param userData Pair of user's level and xp
     * @param levelMap Map of level-xp pairs
     * */
    private Integer calcLevel(LevelStepType type, Integer lvl,
                                                        Float prevLevelXp, Float sumLevelXp,
                                                        Pair<Integer, Float> userData, Map<Integer, Float>... levelMap) {

        switch (type) {
            case CUSTOM -> {
                return (sumLevelXp + prevLevelXp <= userData.getSecond())
                        ? calcLevel(type, lvl++, (levelMap[0].containsKey(lvl++) ? levelMap[0].get(lvl++) : levelStep),
                        sumLevelXp + prevLevelXp, userData, levelMap)
                        : lvl;
            }
        }
        return lvl;
    }
}
