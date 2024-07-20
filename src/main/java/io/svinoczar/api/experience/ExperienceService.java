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
import java.util.HashMap;
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

    private final Integer startLevel = startLevelIs0 ? 1 : 0; //FIXME: ЧЗХ!? true = 0... оно работает наоборот... как оно вообще работает...
    private final FileService fileService;


    //TODO: Позже протестировать на всех level.type отрицательное количество опыта.
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
                Map.Entry<Integer, Float> levelMapLastElement = levelMap.entrySet().stream().skip(levelMap.size()-1).findFirst().get();
                int lastDeclaredLevel = levelMapLastElement.getKey();
                float lastDeclaredXp = levelMapLastElement.getValue();

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
                log.info("prevItem: " + prevItem + ", xp: " + xp + ", currentXp: " + currentXP);
                if (currentXP > xp) {
                    user.setLevel(lastDeclaredLevel + (int) ((currentXP - xp) / levelStep));
                }
            }
            default -> user.setLevel(currentLVL);
        }
        return user;
    }

        private Map<Integer, Float> handleCustomLevelStep(String custom) {
            if (custom.isBlank()) {
                custom = fileService.readFileFromResources(".custom");
            } else if (custom.endsWith(".custom")) {
                custom = fileService.readFileFromResources(custom);
            }

            Map<Integer, Float> result = new HashMap<>();

            Arrays.stream(custom
                            .replaceAll("[{}]", "")
                            .strip()
                            .split(", "))
                    .forEach(element -> {
                        if (element.contains("-")) {
                            String[] parts = element.split(":");
                            float value = Float.parseFloat(parts[1]);
                            String[] rangeSplit = parts[0].split("-");
                            int start = Integer.parseInt(rangeSplit[0]);
                            int end = Integer.parseInt(rangeSplit[1]);
                            for (int i = start; i <= end; i++) {
                                result.put(i, value);
                            }
                        } else {
                            String[] keyValue = element.split(":");
                            int key = Integer.parseInt(keyValue[0]);
                            float value = Float.parseFloat(keyValue[1]);
                            result.put(key, value);
                        }
                    });
            return result;
        }
}