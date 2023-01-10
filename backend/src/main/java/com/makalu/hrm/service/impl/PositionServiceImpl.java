
package com.makalu.hrm.service.impl;

import com.makalu.hrm.converter.PositionConverter;
import com.makalu.hrm.domain.PersistentPositionEntity;
import com.makalu.hrm.repository.PositionRepository;
import com.makalu.hrm.service.PositionService;
import com.makalu.hrm.validation.PositionValidation;
import com.makalu.hrm.validation.error.PositionError;
import com.makalu.hrm.model.PositionDTO;
import com.makalu.hrm.model.RestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    private final PositionValidation positionValidation;

    private final PositionConverter positionConverter;

    @Override
    public List<PositionDTO> list() {
        return positionConverter.convertToDtoList(positionRepository.findAll());
    }

    @Override
    @Transactional
    public RestResponseDto save(@NotNull PositionDTO positionDTO) {
        try {

            PositionError error = positionValidation.validateOnSave(positionDTO);

            if(!error.isValid()){
                return RestResponseDto.INSTANCE().validationError().detail(Map.of("error",error,"data",positionDTO));
            }

            return RestResponseDto
                    .INSTANCE()
                    .success()
                    .detail(positionConverter.convertToDto(
                            positionRepository.saveAndFlush(positionConverter.convertToEntity(positionDTO))));


        }catch (Exception e){
            log.error("Error while creating position", e);
            return RestResponseDto
                    .INSTANCE()
                    .internalServerError()
                    .detail(positionDTO);
        }
    }

    @Override
    public RestResponseDto getResponseById(UUID positionId) {

        PersistentPositionEntity positionEntity = positionRepository.findById(positionId).orElse(null);

        if(positionEntity ==  null){
            return RestResponseDto.INSTANCE().notFound().message("Not found");
        }
        return RestResponseDto
                .INSTANCE()
                .success()
                .detail(positionConverter.convertToDto(positionEntity));
    }

    @Override
    @Transactional
    public RestResponseDto update(PositionDTO positionDTO) {
        try {
            PositionError error = positionValidation.validateOnUpdate(positionDTO);
            if (!error.isValid()) {
                return RestResponseDto.INSTANCE().validationError().detail(Map.of("error", error, "data", positionDTO));
            }

            PersistentPositionEntity positionEntity = positionRepository.findById(positionDTO.getId()).orElse(null);
            if (positionEntity == null) {
                return RestResponseDto.INSTANCE()
                        .notFound().message("Position not found").detail(positionDTO);
            }
            return RestResponseDto.INSTANCE()
                    .success()
                    .detail(positionConverter.convertToDto(
                            positionRepository.saveAndFlush(positionConverter.copyConvertToEntity(positionDTO, positionEntity))
                    ));
        }catch (Exception e){
            log.error("Error while creating position", e);
            return RestResponseDto.INSTANCE()
                    .internalServerError()
                    .detail(positionDTO);
        }
    }

    @Override
    @Transactional
    public RestResponseDto delete(UUID positionId) {
        try {
            PersistentPositionEntity position = positionRepository.findById(positionId).orElse(null);
            if (position == null) {
                return RestResponseDto.INSTANCE()
                        .notFound().message("Position not found");
            }
            positionRepository.delete(position);
            return RestResponseDto.INSTANCE().success();
        }catch (Exception e){
            log.error("Error while creating department", e);
            return RestResponseDto.INSTANCE()
                    .internalServerError();
        }

    }

    @Override
    public List<PositionDTO> findAllPosition(Iterable<UUID> uuids) {
        List<PositionDTO> dtoList = positionConverter.convertToDtoList(positionRepository.findAllById(uuids));
        return dtoList;
    }
}
