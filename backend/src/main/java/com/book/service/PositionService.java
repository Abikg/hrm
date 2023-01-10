package com.book.service;

import com.book.model.PositionDTO;
import com.book.model.PositionFilterDto;
import com.book.model.RestResponseDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface PositionService {

    List<PositionDTO> list();

    RestResponseDto save(PositionDTO positionDTO);

    RestResponseDto getResponseById(@NotNull UUID positionId);

    RestResponseDto update(PositionDTO positionDTO);

    RestResponseDto delete(UUID positionId);
}
