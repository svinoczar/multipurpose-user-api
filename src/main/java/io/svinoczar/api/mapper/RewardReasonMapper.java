package io.svinoczar.api.mapper;

import io.svinoczar.api.dto.RewardReasonDTO;
import io.svinoczar.api.entity.RewardReasonEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RewardReasonMapper {
    RewardReasonDTO map(RewardReasonEntity entity);

    @InheritInverseConfiguration
    RewardReasonEntity map(RewardReasonDTO dto);
}

