package hey.io.hey.common.module.mapper;

import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
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
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "sido", ignore = true)
    @Mapping(target = "gugun", ignore = true)
    @Mapping(source = "performance.place", target = "placeId", ignore = true)
    PerformanceResponse toPerformanceDto(Performance performance);

}
