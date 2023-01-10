package com.book.converter;

import com.book.domain.PersistentDepartmentEntity;
import com.book.model.DepartmentDTO;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverter extends Convertable<PersistentDepartmentEntity, DepartmentDTO>{

    @Override
    public PersistentDepartmentEntity convertToEntity(DepartmentDTO dto) {
        return this.copyConvertToEntity(dto, new PersistentDepartmentEntity());
    }

    @Override
    public PersistentDepartmentEntity copyConvertToEntity(DepartmentDTO dto, PersistentDepartmentEntity entity) {

        if (entity == null || dto == null){
            return null;
        }

        entity.setDetail(trimString(dto.getDetail()));
        entity.setTitle(trimString(dto.getTitle()));
        entity.setDepartmentCode(trimString(dto.getDepartmentCode()));

        return entity;
    }

    @Override
    public DepartmentDTO convertToDto(PersistentDepartmentEntity entity) {
        if (entity == null){
            return null;
        }

        DepartmentDTO dto = new DepartmentDTO();

        dto.setId(entity.getId());
        dto.setDepartmentCode(entity.getDepartmentCode());
        dto.setTitle(entity.getTitle());
        dto.setDetail(entity.getDetail());

        return dto;
    }
}
