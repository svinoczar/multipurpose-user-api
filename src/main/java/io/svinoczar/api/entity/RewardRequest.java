package io.svinoczar.api.entity;

import io.svinoczar.api.dto.RewardDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RewardRequest extends Request{
    private RewardDTO rewardDTO;
}
