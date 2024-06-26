package hey.io.hey.module.mapper;

import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.dto.PerformanceDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PerformanceMapper {

    PerformanceMapper INSTANCE = Mappers.getMapper(PerformanceMapper.class);

    @Mapping(source = "performance.storyUrls", target = "storyUrls", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(source = "performance.place", target = "placeId", ignore = true)
    PerformanceDetailResponse toPerformanceDto(Performance performance);

}
