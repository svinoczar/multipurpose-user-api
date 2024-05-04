package io.svinoczar.api.mapper;

import io.svinoczar.api.dto.RewardDTO;
import io.svinoczar.api.entity.RewardEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RewardMapper {
    RewardDTO map(RewardEntity rewardEntity);

    @InheritInverseConfiguration
    RewardEntity map(RewardDTO rewardDTO);
}
