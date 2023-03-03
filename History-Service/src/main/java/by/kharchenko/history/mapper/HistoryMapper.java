package by.kharchenko.history.mapper;

import by.kharchenko.history.dto.HistoryDto;
import by.kharchenko.history.entity.History;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryMapper {
    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);

    HistoryDto HistoryToHistoryDto(History history);

    List<HistoryDto> HistoriesToHistoriesDto(List<History> histories);
}
