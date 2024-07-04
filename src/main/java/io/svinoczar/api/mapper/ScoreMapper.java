package io.svinoczar.api.mapper;

import io.svinoczar.api.dto.ScoreDTO;
import io.svinoczar.api.entity.ScoreEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScoreMapper {
    ScoreDTO map(ScoreEntity entity);

    @InheritInverseConfiguration
    ScoreEntity map(ScoreDTO dto);
}
