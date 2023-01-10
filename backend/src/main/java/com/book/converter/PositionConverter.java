package com.book.converter;

import com.book.domain.PersistentPositionEntity;
import com.book.model.PositionDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PositionConverter extends Convertable<PersistentPositionEntity, PositionDTO>{

    @Override
    public PersistentPositionEntity convertToEntity(PositionDTO dto) {
        return this.copyConvertToEntity(dto, new PersistentPositionEntity());
    }


    @Override
    public PositionDTO convertToDto(PersistentPositionEntity entity) {
        if (entity == null) {
            return null;
        }

        PositionDTO dto = new PositionDTO();

        dto.setId(entity.getId());
        dto.setDetail(entity.getDetail());
        dto.setTitle(entity.getTitle());

        return dto;
    }

    @Override
    public PersistentPositionEntity copyConvertToEntity(PositionDTO dto, PersistentPositionEntity entity) {
        if (dto == null || entity == null){
            return null;
        }

        entity.setTitle(trimString(dto.getTitle()));
        entity.setDetail(trimString(dto.getDetail()));

        return entity;
    }
}
