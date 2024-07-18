package io.svinoczar.api.experience;

import io.svinoczar.api.entity.UserEntity;
import io.svinoczar.api.exception.ZeroXPStartValueException;
import io.svinoczar.api.service.FileService;
import lombok.Getter;
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

    private final Integer startLevel = startLevelIs0 ? 1 : 0; //FIXME: ЧЗХ!? true = 0... оно работает наоборот...
    private final FileService fileService;

    //TODO: Теперь custom при выходе за мапу уровней начинает считать, что каждый уровень стоит levelStep xp.
    public UserEntity updateLevel(UserEntity user) {
        log.debug("!!startLevel = " + startLevel + " (startLevelIs0 = {})", startLevelIs0);
        int currentLVL = user.getLevel();
        float currentXP = user.getXp();
        Pair<Integer, Float> levelXp = Pair.of(currentLVL, currentXP) ;

        switch (levelStepType) {
            case MONO ->  user.setLevel((int) (currentXP / levelStep)); //DONE
            case LINEAR -> {
                int level = startLevel;
                float xp = startXp;
                float nextXp = levelStep;
                while ((xp + nextXp) <= currentXP){
                    xp += nextXp;
                    nextXp += levelStep;
                    level++;
                }
                user.setLevel(level);
            } //DONE

            case EXP -> {
                if (startXp == 0) {
                    throw new ZeroXPStartValueException(
                            "The initial value of experience must be greater than zero when `experience.level.step.type` = EXP",
                            "XP/EXP/0");

                }
                int level = startLevel;
                float multiplier = levelStep;
                float requiredXP = startXp * multiplier;
                while (requiredXP <= currentXP) {
                    level++;
                    requiredXP += (float) (startXp * Math.pow(multiplier, level - 1));
                }
                user.setLevel(level);
                } //DONE

            case CUSTOM ->  {
                Map<Integer, Float> levelMap = handleCustomLevelStep(custom);
                log.debug("levelMap: " + levelMap);
                float xp = startXp;
                Pair<Integer, Float> prevItem = Pair.of(currentLVL, currentXP);
                for (Map.Entry<Integer, Float> entry : levelMap.entrySet()) {
                    int key = entry.getKey();
                    float value = entry.getValue();
                    xp += value;
                    if (currentXP < xp) {
                        user.setLevel(prevItem.getFirst());
                        break;
                    } else if (currentXP == xp) {
                        user.setLevel(key);
                        break;
                    }
                    prevItem = Pair.of(key, value);
                }
            } //DONE!
            default -> user.setLevel(currentLVL);
        }
        return user;
    }

        //TODO: Реализовать обработку случаев типа 5-10:alt
    private Map<Integer, Float> handleCustomLevelStep(String custom) {
        if (custom.isBlank()) {
            custom = fileService.readFileFromResources(".custom");
        } else if (custom.endsWith(".custom")) {
            custom = fileService.readFileFromResources(custom);
        }
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
}